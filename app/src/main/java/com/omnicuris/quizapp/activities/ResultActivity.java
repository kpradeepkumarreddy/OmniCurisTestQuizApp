package com.omnicuris.quizapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.omnicuris.quizapp.R;
import com.omnicuris.quizapp.models.ResultTO;
import com.omnicuris.quizapp.models.UserTO;

import java.util.List;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvName, tvEmail, tvMobile, tvQ1, tvQ2, tvQ3, tvQ4;
    private Button btnDone;
    private static final String CORRECT = "correct";
    private static final String WRONG = "wrong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_result);

            tvName = findViewById(R.id.tvName);
            tvEmail = findViewById(R.id.tvEmail);
            tvMobile = findViewById(R.id.tvMobile);
            btnDone = findViewById(R.id.btnDone);
            tvQ1 = findViewById(R.id.tvQ1);
            tvQ2 = findViewById(R.id.tvQ2);
            tvQ3 = findViewById(R.id.tvQ3);
            tvQ4 = findViewById(R.id.tvQ4);
            TextView tvTotalScore = findViewById(R.id.tvTotalScore);

            UserTO userTO = (UserTO) getIntent().getSerializableExtra("USER_TO");
            List<ResultTO> resultTOList = (List<ResultTO>) getIntent().getSerializableExtra("RESULT_TO");
            Log.d("log", "userTO" + userTO);
            Log.d("log", "resultTO" + resultTOList);
            tvName.setText(userTO.getName());
            tvEmail.setText(userTO.getEmail());
            tvMobile.setText(userTO.getMobile());
            ResultTO resultTO = resultTOList.get(0);

            int marks = 0;
            String resultStr = WRONG;
            if (resultTO.getCorrectAnswer().equals(resultTO.getYourAnswer())) {
                resultStr = CORRECT;
                marks++;
            }
            tvQ1.setText(resultTO.getQuestionNumber() + ")    " + resultStr);

            resultTO = resultTOList.get(1);
            resultStr = WRONG;
            if (resultTO.getCorrectAnswer().equals(resultTO.getYourAnswer())) {
                resultStr = CORRECT;
                marks++;
            }
            tvQ2.setText(resultTO.getQuestionNumber() + ")    " + resultStr);

            resultTO = resultTOList.get(2);
            resultStr = WRONG;
            if (resultTO.getCorrectAnswer().equals(resultTO.getYourAnswer())) {
                resultStr = CORRECT;
                marks++;
            }
            tvQ3.setText(resultTO.getQuestionNumber() + ")    " + resultStr);

            resultTO = resultTOList.get(3);
            resultStr = WRONG;
            if (resultTO.getCorrectAnswer().equals(resultTO.getYourAnswer())) {
                resultStr = CORRECT;
                marks++;
            }
            tvQ4.setText(resultTO.getQuestionNumber() + ")    " + resultStr);

            btnDone.setOnClickListener(this);
            Log.d("log", "marks = " + marks);
            tvTotalScore.setText("Total Score : " + String.valueOf(marks));
        } catch (Exception ex) {
            Log.d("log", "Exception in ResultActivity" + ex);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDone) {
            finish();
        }
    }
}
