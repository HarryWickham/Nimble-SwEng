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
        setContentView(R.layout.activity_signin);

        login_button = findViewById(R.id.sign_in_button);

        login_button.setOnClickListener(view -> {
            Intent mIntent = new Intent(SigninActivity.this, MainActivity.class); //will eventually send signin details to firebase, for demonstration purposes just takes user to profile page
            startActivity(mIntent);
            finish();
        });
    }

    public void onClickGoToSignUp(View v) {//called from the TextView in with id/new_member called using (android:onClick="onClickGoToSignUp")
        Intent mIntent = new Intent(SigninActivity.this, SignupActivity.class); //changes current activity from signin to signup
        startActivity(mIntent);
        finish();
    }
    public void onClickForgottenPassword(View v) {
        Toast toast = Toast.makeText(getApplicationContext(), "Reset Password", Toast.LENGTH_SHORT);//function called to initiate forgotten password user story
        toast.show();

    }



}
