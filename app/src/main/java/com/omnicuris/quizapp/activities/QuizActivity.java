package com.omnicuris.quizapp.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.omnicuris.quizapp.R;
import com.omnicuris.quizapp.adapters.QuizPagerAdapter;
import com.omnicuris.quizapp.fragments.QuestionFragment;
import com.omnicuris.quizapp.models.QuestionTO;
import com.omnicuris.quizapp.models.ResultTO;
import com.omnicuris.quizapp.models.UserTO;
import com.omnicuris.quizapp.utils.NoSwipeViewPager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener,
        QuestionFragment.OnFragmentInteractionListener {
    private Button btnNext;
    private Button btnPrev;
    private QuizPagerAdapter quizPageAdapter = null;
    private NoSwipeViewPager vpQuizFragment = null;
    private RadioGroup rbgQuestion;
    private UserTO userTO = null;
    private List<ResultTO> resultTOList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_quiz);
            btnNext = findViewById(R.id.btnNext);
            btnNext.setOnClickListener(this);

            btnPrev = findViewById(R.id.btnPrev);
            // by default keeping the previous button greyed out, will enable it from 2nd question
            btnPrev.setAlpha(0.3f);
            btnPrev.setOnClickListener(null);

            vpQuizFragment = findViewById(R.id.vpQuizFragment);

            quizPageAdapter = new QuizPagerAdapter(getSupportFragmentManager());

            String jsonStr = loadFileFromAssets("questions.json");
            JSONObject quizJsonObject = new JSONObject(jsonStr);
            JSONArray quizJsonArray = quizJsonObject.getJSONArray("quiz");

            for (int i = 0; i < quizJsonArray.length(); i++) {
                JSONObject questionJsonObj = (JSONObject) quizJsonArray.get(i);

                QuestionTO question = new QuestionTO();
                question.setQuestion(questionJsonObj.getString("question"));

                // getting the options from json
                JSONArray optionsJsonArray = questionJsonObj.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < optionsJsonArray.length(); j++) {
                    JSONObject optionJsonObj = (JSONObject) optionsJsonArray.get(j);
                    options.add(optionJsonObj.getString("option"));
                }
                question.setOptions(options);

                QuestionFragment questionFragment = QuestionFragment.newInstance(question, null);
                quizPageAdapter.addFragment(questionFragment);

                // getting the answer from json
                ResultTO resultTO = new ResultTO();
                resultTO.setQuestionNumber("Q" + (i + 1));
                resultTO.setCorrectAnswer(questionJsonObj.getString("answer"));

                resultTOList.add(resultTO);
            }

            vpQuizFragment.setAdapter(quizPageAdapter);
            Timber.d("quizPageAdapter.getCount() = " + quizPageAdapter.getCount());
            userTO = (UserTO) getIntent().getSerializableExtra("USER_TO");
        } catch (Exception ex) {
            Timber.d("Exception in QuizActivity " + ex);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNext) {
            codeToRunOnNextBtnClick();
        } else if (v.getId() == R.id.btnPrev) {
            codeToRunOnPrevBtnClick();
        }
    }

    private void codeToRunOnPrevBtnClick() {
        try {
            vpQuizFragment.setCurrentItem(vpQuizFragment.getCurrentItem() - 1);
            if (vpQuizFragment.getCurrentItem() == 0) {
                // greying out the previous button, when the user is in first question
                btnPrev.setAlpha(0.3f);
                btnPrev.setOnClickListener(null);
            }
        } catch (Exception ex) {
            Timber.e(ex, "Exception in codeToRunOnPrevBtnClick()");
        }
    }

    private void codeToRunOnNextBtnClick() {
        try {
            if (btnNext.getText().equals("Finish")) {
                rbgQuestion = ((QuestionFragment) quizPageAdapter.getItem(vpQuizFragment.getCurrentItem()))
                        .getRbgQuestion();
                if (rbgQuestion.getCheckedRadioButtonId() == -1) {
                    // no radio buttons are checked
                    Toast toast = Toast.makeText(this, "Select an option", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                } else {
                    ResultTO resultTO = resultTOList.get(vpQuizFragment.getCurrentItem());

                    if (rbgQuestion.getCheckedRadioButtonId() == R.id.rbOption1) {
                        RadioButton rb = ((QuestionFragment) quizPageAdapter.getItem(
                                vpQuizFragment.getCurrentItem())).getRbOption1();
                        resultTO.setYourAnswer(rb.getText().toString());
                    } else if (rbgQuestion.getCheckedRadioButtonId() == R.id.rbOption2) {
                        RadioButton rb = ((QuestionFragment) quizPageAdapter.getItem(
                                vpQuizFragment.getCurrentItem())).getRbOption2();
                        resultTO.setYourAnswer(rb.getText().toString());
                    } else if (rbgQuestion.getCheckedRadioButtonId() == R.id.rbOption3) {
                        RadioButton rb = ((QuestionFragment) quizPageAdapter.getItem(
                                vpQuizFragment.getCurrentItem())).getRbOption3();
                        resultTO.setYourAnswer(rb.getText().toString());
                    } else if (rbgQuestion.getCheckedRadioButtonId() == R.id.rbOption4) {
                        RadioButton rb = ((QuestionFragment) quizPageAdapter.getItem(
                                vpQuizFragment.getCurrentItem())).getRbOption4();
                        resultTO.setYourAnswer(rb.getText().toString());
                    }
                    Timber.d("selected an option type");
                }
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("USER_TO", userTO);
                intent.putExtra("RESULT_TO", (Serializable) resultTOList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                return;
            }
            rbgQuestion = ((QuestionFragment) quizPageAdapter.getItem(vpQuizFragment.getCurrentItem()))
                    .getRbgQuestion();
            if (rbgQuestion.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
                Toast toast = Toast.makeText(this, "Select an option", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            } else {
                ResultTO resultTO = resultTOList.get(vpQuizFragment.getCurrentItem());

                if (rbgQuestion.getCheckedRadioButtonId() == R.id.rbOption1) {
                    RadioButton rb = ((QuestionFragment) quizPageAdapter.getItem(
                            vpQuizFragment.getCurrentItem())).getRbOption1();
                    resultTO.setYourAnswer(rb.getText().toString());
                } else if (rbgQuestion.getCheckedRadioButtonId() == R.id.rbOption2) {
                    RadioButton rb = ((QuestionFragment) quizPageAdapter.getItem(
                            vpQuizFragment.getCurrentItem())).getRbOption2();
                    resultTO.setYourAnswer(rb.getText().toString());
                } else if (rbgQuestion.getCheckedRadioButtonId() == R.id.rbOption3) {
                    RadioButton rb = ((QuestionFragment) quizPageAdapter.getItem(
                            vpQuizFragment.getCurrentItem())).getRbOption3();
                    resultTO.setYourAnswer(rb.getText().toString());
                } else if (rbgQuestion.getCheckedRadioButtonId() == R.id.rbOption4) {
                    RadioButton rb = ((QuestionFragment) quizPageAdapter.getItem(
                            vpQuizFragment.getCurrentItem())).getRbOption4();
                    resultTO.setYourAnswer(rb.getText().toString());
                }
                Timber.d("selected an option type");
            }
            vpQuizFragment.setCurrentItem(vpQuizFragment.getCurrentItem() + 1);
            // from the 2nd question onwards show the previous button
            if (vpQuizFragment.getCurrentItem() > 0) {
                if (btnPrev.getAlpha() < 1.0f) {
                    btnPrev.setAlpha(1.0f);
                    btnPrev.setOnClickListener(this);
                }
            }
            Timber.d("vpQuizFragment.getCurrentItem() = " + vpQuizFragment.getCurrentItem());
            if (vpQuizFragment.getCurrentItem() == (quizPageAdapter.getCount() - 1)) {
                btnNext.setText("Finish");
            }
        } catch (Exception ex) {
            Timber.e(ex, "Exception in codeToRunOnNextBtnClick()");
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private String loadFileFromAssets(String mFileName) {
        String json = null;
        try {
            AssetManager manager = this.getAssets();
            InputStream is = manager.open(mFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ioe) {
            Timber.d("exception in loadFileFromAssets = " + ioe);
            return null;
        }
        return json;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}