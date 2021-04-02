package uk.ac.york.nimblefitness.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import uk.ac.york.nimblefitness.HelperClasses.Verification;
import uk.ac.york.nimblefitness.R;

public class SignupActivity extends AppCompatActivity {
    private EditText userEmail, userPassword, userConfirmPassword;
    private TextInputLayout userEmailLayout, userPasswordLayout, userConfirmPasswordLayout;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userEmail = findViewById(R.id.SignUpEmail);
        userPassword = findViewById(R.id.SignUpPassword);
        userConfirmPassword = findViewById(R.id.SignUpPasswordConfirm);

        userEmailLayout = findViewById(R.id.SignUpEmailLayout);
        userPasswordLayout = findViewById(R.id.SignUpPasswordLayout);
        userConfirmPasswordLayout = findViewById(R.id.SignUpPasswordConfirmLayout);

        Button signUpButton = findViewById(R.id.sign_up_button);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.signup_progress_circular);
        userEmailLayout = findViewById((R.id.SignUpEmailLayout));
        userPasswordLayout = findViewById((R.id.SignUpPasswordLayout));
        userConfirmPasswordLayout = findViewById(R.id.SignUpPasswordConfirmLayout);
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String confirmPassword = userConfirmPassword.getText().toString().trim();
        Verification userDetails = new Verification(password, email, confirmPassword);


        userEmailLayout.setErrorIconDrawable(null);
        userPasswordLayout.setErrorIconDrawable(null);
        userConfirmPasswordLayout.setErrorIconDrawable(null);

        Objects.requireNonNull(userEmailLayout.getEditText()).setOnFocusChangeListener((view, b) -> {//validates the email text box when the user clicks away from them
            if(!b){
                validateEmail(userDetails);
            }
        });

        Objects.requireNonNull(userPasswordLayout.getEditText()).setOnFocusChangeListener((view, b) -> {//validates the password text box when the user clicks away from them
            if(!b){
                validatePassword(userDetails);
            }
        });

        Objects.requireNonNull(userConfirmPasswordLayout.getEditText()).setOnFocusChangeListener((view, b) -> {//validates the confirm password text box when the user clicks away from them
            if(!b){
                validateConfirmPassword(userDetails);
            }
        });


        if (firebaseAuth.getCurrentUser() != null) {// this checks to see if the user is already logged in from last session - avoids need for user to relogin everytime apps opens
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        signUpButton.setOnClickListener(v -> {
            String email1 = userEmail.getText().toString().trim();
            String password1 = userPassword.getText().toString().trim();
            //progressBar.setVisibility(View.VISIBLE);
            if(validateEmail(userDetails) & validatePassword(userDetails) & validateConfirmPassword(userDetails)) {

                checkSignUpDetails(email1, password1);
            }

        });
    }

    public void checkSignUpDetails(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //send verification link

                userConfirmPasswordLayout.setError(null);//resets errors
                userConfirmPasswordLayout.setErrorEnabled(false);//resets errors

                FirebaseUser user = firebaseAuth.getCurrentUser();
                assert user != null;
                user.sendEmailVerification().addOnSuccessListener(aVoid -> Toast.makeText(SignupActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> {
                });
                Toast.makeText(SignupActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), PaymentActivity.class));

            } else {
                //progressBar.setVisibility(View.GONE);
                userEmailLayout.setError(Objects.requireNonNull(task.getException()).getMessage());
            }
        });

    }

    public void onClickGoToLogin(View v) {//called from the TextView in with id/already_a_member called using (android:onClick="onClickGoToLogin")
        Intent mIntent = new Intent(SignupActivity.this, SigninActivity.class);//changes current activity from signin to signup
        startActivity(mIntent);
        finish();
    }


    private Boolean validateEmail(Verification userDetails) {// calls the validate email method in the verification class
        userDetails.setEmail(userEmail.getText().toString().trim());
        String reply = userDetails.validateEmail();
        if(!reply.equals("Valid")){
            userEmailLayout.setError(reply);
            progressBar.setVisibility(View.GONE);
            return false;
        }
        else{
            userEmailLayout.setError(null);
            userEmailLayout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(Verification userDetails) {// calls the validate password method in the verification class
        userDetails.setPassword(userPassword.getText().toString().trim());
        String reply = userDetails.validatePassword();
        if(!reply.equals("Valid")){
            userPasswordLayout.setError(reply);
            progressBar.setVisibility(View.GONE);
            return false;
        }
        else{
            userPasswordLayout.setError(null);
            userPasswordLayout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateConfirmPassword(Verification userDetails){// calls the validate confirm password method in the verification class

        userDetails.setConfirmPassword(userConfirmPassword.getText().toString().trim());
        String reply = userDetails.validateConfirmPassword();

        if(!reply.equals("Valid")){
            userConfirmPasswordLayout.setError(reply);
            progressBar.setVisibility(View.GONE);
            return false;
        }
        else{
            userConfirmPasswordLayout.setError(null);
            userConfirmPasswordLayout.setErrorEnabled(false);
            return true;
        }
    }
}