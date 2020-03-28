package com.omnicuris.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.omnicuris.quizapp.R;
import com.omnicuris.quizapp.models.UserTO;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText tietName, tietEmail, tietMobile;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tietName = findViewById(R.id.tietName);
        tietEmail = findViewById(R.id.tietEmail);
        tietMobile = findViewById(R.id.tietMobile);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                if (tietName.getText().length() < 1) {
                    tietName.setError("Enter Your Name");
                    requestFocus(tietName);
                    return;
                }
                if (!isValidEmail(tietEmail.getText().toString())) {
                    Log.d("log", "Invalid email");
                    tietEmail.setError("Enter Valid Email");
                    requestFocus(tietEmail);
                    return;
                }
                int mobileNumLen = tietMobile.getText().length();
                if (mobileNumLen < 1) {
                    tietMobile.setError("Enter Your Mobile Number");
                    requestFocus(tietMobile);
                    return;
                } else if (mobileNumLen < 10 || mobileNumLen > 10) {
                    tietMobile.setError("Please Enter Valid 10 Digit Mobile Number");
                    requestFocus(tietMobile);
                    return;
                }

                Intent intent = new Intent(this, QuizActivity.class);
                UserTO userTO = new UserTO();
                userTO.setName(tietName.getText().toString());
                userTO.setEmail(tietEmail.getText().toString());
                userTO.setMobile(tietMobile.getText().toString());
                intent.putExtra("USER_TO", userTO);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            default:
                break;
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}