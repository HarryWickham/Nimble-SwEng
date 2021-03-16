package uk.ac.york.nimblefitness.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        progressBar = findViewById(R.id.progress_circular);
        userEmailLayout = findViewById((R.id.SignUpEmailLayout));
        userPasswordLayout = findViewById((R.id.SignUpPasswordLayout));
        userConfirmPasswordLayout = findViewById(R.id.SignUpPasswordConfirmLayout);


        userEmailLayout.setErrorIconDrawable(null);
        userPasswordLayout.setErrorIconDrawable(null);
        userConfirmPasswordLayout.setErrorIconDrawable(null);

        userEmailLayout.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateEmail();
            }
        });

        userPasswordLayout.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validatePassword();
            }
        });

        userConfirmPasswordLayout.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateConfirmPassword();
            }
        });


        if (firebaseAuth.getCurrentUser() != null) {// this checks to see if the user is already logged in from last session - avoids need for user to relogin everytime apps opens
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);

                if(validateEmail() & validatePassword() & validateConfirmPassword()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //send verification link

                                userConfirmPasswordLayout.setError(null);
                                userConfirmPasswordLayout.setErrorEnabled(false);

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignupActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                                Toast.makeText(SignupActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            } else {
                                progressBar.setVisibility(View.GONE);
                                userEmailLayout.setError(task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });


    }

    public void onClickGoToLogin(View v) {//called from the TextView in with id/already_a_member called using (android:onClick="onClickGoToLogin")
        Intent mIntent = new Intent(SignupActivity.this, SigninActivity.class);//changes current activity from signin to signup
        startActivity(mIntent);
        finish();
    }


    private Boolean validateEmail() {
        String email = userEmail.getText().toString().trim();

        if (email.isEmpty()) {//checks to see if an email address has been entered
            userEmailLayout.setError("Email is Required");
            progressBar.setVisibility(View.GONE);
            return false;
        } else if (!email.matches(String.valueOf(Patterns.EMAIL_ADDRESS))) {//checks to see if the email address entered follows the correct pattern
            userEmailLayout.setError("Invalid email address");
            progressBar.setVisibility(View.GONE);
            return false;
        } else {//removes any error messages that appeared if the email was incorrect previously
            userEmailLayout.setError(null);
            userEmailLayout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String password = userPassword.getText().toString().trim();

        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (password.isEmpty()) {//checks to see if a password has been entered
            userPasswordLayout.setError("Password is Required");
            progressBar.setVisibility(View.GONE);
            return false;
        } else if (!password.matches(passwordVal)) {//checks to see if the password entered follows the correct pattern
            userPasswordLayout.setError("Invalid Password must be more than 6 characters long with at least 1 lower case letter and at least 1 upper case letter");
            progressBar.setVisibility(View.GONE);
            return false;
        } else {//removes any error messages that appeared if the password was incorrect previously
            userPasswordLayout.setError(null);
            userPasswordLayout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateConfirmPassword(){
        String confirmPassword = userConfirmPassword.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        if (confirmPassword.isEmpty()) {//checks to see if a confirm password has been entered
            userConfirmPasswordLayout.setError("Password is Required");
            progressBar.setVisibility(View.GONE);
            return false;
        }else if (!confirmPassword.equals(password)) {//checks to see if a confirm password and password are the same
            userConfirmPasswordLayout.setError("Confirm Password must be the same as Password");
            progressBar.setVisibility(View.GONE);
            return false;
        } else {//removes any error messages that appeared if the password was incorrect previously
            userConfirmPasswordLayout.setError(null);
            userConfirmPasswordLayout.setErrorEnabled(false);
            return true;
        }
    }
}