package uk.ac.york.nimblefitness.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import uk.ac.york.nimblefitness.R;

public class SignupActivity extends AppCompatActivity {

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
        public void onClickGoToLogin(View v) {//called from the TextView in with id/already_a_member called using (android:onClick="onClickGoToLogin")
            Intent mIntent = new Intent(SignupActivity.this, SigninActivity.class);//changes current activity from signin to signup
            startActivity(mIntent);
            finish();
        }

    public void SignUpButton(View view) {
        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(view.VISIBLE);
    }
}