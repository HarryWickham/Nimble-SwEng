package uk.ac.york.nimblefitness.Screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import uk.ac.york.nimblefitness.HelperClasses.Verification;
import uk.ac.york.nimblefitness.R;

public class SigninActivity extends AppCompatActivity {
    private static final int GOOGLE_SIGNIN_CODE = 10005;
    private EditText userEmail, userPassword;
    private GoogleSignInClient signInClient;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private TextInputLayout userEmailLayout, userPasswordLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        userEmail = findViewById(R.id.SignInEmail);
        userPassword = findViewById(R.id.SignInPassword);
        Button login_button = findViewById(R.id.sign_in_button);
        SignInButton googleSignIn = findViewById(R.id.googleSignIn);
        progressBar = findViewById(R.id.progress_circular);
        firebaseAuth = FirebaseAuth.getInstance();
        userEmailLayout = findViewById(R.id.SignInEmailLayout);
        userPasswordLayout = findViewById(R.id.SignInPasswordLayout);
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        Verification userDetails = new Verification(password, email);

        userEmailLayout.setErrorIconDrawable(null);
        userPasswordLayout.setErrorIconDrawable(null);


        userEmailLayout.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateEmail(userDetails);
            }
        });

        userPasswordLayout.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validatePassword(userDetails);
            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validateEmail(userDetails) & validatePassword(userDetails)) {
                    //Shows the user a loading symbol to reassure them that something is happening
                    progressBar.setVisibility(View.VISIBLE);
                    //passes the login details to firebase to authenticate
                    firebaseAuth.signInWithEmailAndPassword(userDetails.getEmail(),userDetails.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SigninActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                invalidUser();

                            }
                        }
                    });
                }

            }
        });
        //to enable signin with google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1082440501674-dsinj9sev8md1518nc8u5bal4rkll72b.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignIn.setSize(SignInButton.SIZE_WIDE);
        signInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null || firebaseAuth.getCurrentUser() != null) {
           startActivity(new Intent(this, MainActivity.class));
       }

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gSignIn = signInClient.getSignInIntent();
                startActivityForResult(gSignIn, GOOGLE_SIGNIN_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGNIN_CODE) {
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount signInAcc = signInTask.getResult(ApiException.class);

                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAcc.getIdToken(), null);
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(), "Google Account Connected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                Toast.makeText(this, "Goolge Account Connected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickGoToSignUp(View v) {//called from the TextView in with id/new_member called using (android:onClick="onClickGoToSignUp")
        Intent mIntent = new Intent(SigninActivity.this, SignupActivity.class); //changes current activity from signin to signup
        startActivity(mIntent);
        finish();
    }

    //to enable user to reset password
    public void onClickForgottenPassword(View v) {
        EditText recoveryEmail = new EditText(v.getContext());
        recoveryEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
        passwordResetDialog.setTitle("Reset Password");
        passwordResetDialog.setMessage("Enter Your Email Address");
        passwordResetDialog.setView(recoveryEmail);

        passwordResetDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //extract email and send the reset link
                String email = recoveryEmail.getText().toString();
                firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SigninActivity.this, "Check Your Emails", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SigninActivity.this, "Error, No account with specified email found", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        passwordResetDialog.create().show();
        Toast toast = Toast.makeText(getApplicationContext(), "Reset Password", Toast.LENGTH_SHORT);//function called to initiate forgotten password user story
        toast.show();

    }

    private Boolean validateEmail(Verification userDetails) {
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

    private Boolean validatePassword(Verification userDetails) {
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

    private void invalidUser(){//displays an error if Firebase fails at logging the user in
        progressBar.setVisibility(View.GONE);
        userPasswordLayout.setError("Incorrect username or password");
        userEmailLayout.setError("Incorrect username or password");
    }

}