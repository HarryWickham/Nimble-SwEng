package uk.ac.york.nimblefitness.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import uk.ac.york.nimblefitness.R;

public class SignupActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
        public void onClickGoToLogin(View v) {
            Intent mIntent = new Intent(SignupActivity.this, SigninActivity.class);
            startActivity(mIntent);
            finish();
        }

}