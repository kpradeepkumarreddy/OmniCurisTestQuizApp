package com.omnicuris.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicuris.quizapp.R;
import com.omnicuris.quizapp.adapters.ResultsRecyclerAdapter;
import com.omnicuris.quizapp.models.ResultTO;
import com.omnicuris.quizapp.models.UserTO;

import java.util.Iterator;
import java.util.List;

import timber.log.Timber;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvName, tvEmail, tvMobile;
    private Button btnDone, btnRetry;
    private RecyclerView rvResults;
    private ResultsRecyclerAdapter resultsRecyclerAdapter;
    private int marks = 0;
    private UserTO userTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_result);

            tvName = findViewById(R.id.tvName);
            tvEmail = findViewById(R.id.tvEmail);
            tvMobile = findViewById(R.id.tvMobile);
            btnDone = findViewById(R.id.btnDone);
            btnRetry = findViewById(R.id.btnRetry);
            rvResults = findViewById(R.id.rvResults);

            TextView tvTotalScore = findViewById(R.id.tvTotalScore);

            userTO = (UserTO) getIntent().getSerializableExtra("USER_TO");
            List<ResultTO> resultTOList = (List<ResultTO>) getIntent().getSerializableExtra("RESULT_TO");
            Timber.d("userTO = %s", userTO.toString());
            Timber.d("resultTO size() = %d", resultTOList.size());
            tvName.setText(userTO.getName());
            tvEmail.setText(userTO.getEmail());
            tvMobile.setText(userTO.getMobile());

            btnDone.setOnClickListener(this);
            btnRetry.setOnClickListener(this);

            resultsRecyclerAdapter = new ResultsRecyclerAdapter(resultTOList, this);
            rvResults.setLayoutManager(new LinearLayoutManager(this));
            rvResults.setItemAnimator(new DefaultItemAnimator());
            rvResults.setAdapter(resultsRecyclerAdapter);
            resultsRecyclerAdapter.notifyDataSetChanged();

            Iterator<ResultTO> resultListIterator = resultTOList.iterator();
            while (resultListIterator.hasNext()) {
                ResultTO resultTO = resultListIterator.next();
                Timber.d("resultTO = " + resultTO.toString());
                if (resultTO.getYourAnswer().equals(resultTO.getCorrectAnswer())) {
                    resultTO.setMark(1);
                    marks++;
                }
            }

            Timber.d("marks = %d", marks);
            tvTotalScore.setText("Total Score : " + marks);
        } catch (Exception ex) {
            Timber.d(ex, "Exception in ResultActivity ");
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                finish();
                break;
            case R.id.btnRetry:
                Intent intent = new Intent(this, QuizActivity.class);
                intent.putExtra("USER_TO", userTO);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            default:
                break;
        }
    }
}