package uk.ac.york.nimblefitness.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import uk.ac.york.nimblefitness.R;

public class SigninActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_button = findViewById(R.id.sign_in_button);

        login_button.setOnClickListener(view -> {
            Intent mIntent = new Intent(SigninActivity.this, MainActivity.class);
            startActivity(mIntent);
            finish();
        });
    }

    public void onClickGoToSignUp(View v) {
        Intent mIntent = new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(mIntent);
        finish();
    }
    public void onClickForgottenPassword(View v) {
        Toast toast = Toast.makeText(getApplicationContext(), "Reset Password", Toast.LENGTH_SHORT);
        toast.show();

    }



}
