package uk.ac.york.nimblefitness.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

import uk.ac.york.nimblefitness.HelperClasses.Verification;
import uk.ac.york.nimblefitness.R;

public class SigninActivity extends AppCompatActivity {
    private static final int GOOGLE_SIGNIN_CODE = 10005;
    private EditText userEmail, userPassword;
    private GoogleSignInClient signInClient;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private TextInputLayout userEmailLayout, userPasswordLayout;
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //Initilise Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        InitiallizeEmailLogin();
        InitiallizeGoogleLogin();
        InitiallizeFacebook();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

    }

    private void InitiallizeEmailLogin(){
        userEmail = findViewById(R.id.SignInEmail);
        userPassword = findViewById(R.id.SignInPassword);
        Button login_button = findViewById(R.id.sign_in_button);
        progressBar = findViewById(R.id.signin_progress_circular);
        userEmailLayout = findViewById(R.id.SignInEmailLayout);
        userPasswordLayout = findViewById(R.id.SignInPasswordLayout);

        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        Verification userDetails = new Verification(password, email);

        userEmailLayout.setErrorIconDrawable(null);
        userPasswordLayout.setErrorIconDrawable(null);

        //validates the email text box when the user clicks away from them
        Objects.requireNonNull(userEmailLayout.getEditText()).setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateEmail(userDetails);
            }
        });

        //validates the password text box when the user clicks away from them
        Objects.requireNonNull(userPasswordLayout.getEditText()).setOnFocusChangeListener((view, b) -> {
            if(!b){
                validatePassword(userDetails);
            }
        });

        login_button.setOnClickListener(v -> {

            if (validateEmail(userDetails) & validatePassword(userDetails)) {
                //Shows the user a loading symbol to reassure them that something is happening
                progressBar.setVisibility(View.VISIBLE);
                //passes the login details to firebase to authenticate
                firebaseAuth.signInWithEmailAndPassword(userDetails.getEmail(),userDetails.getPassword()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SigninActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));//takes user the main page
                    } else {
                        invalidUser();
                    }
                });
            }

        });
    }

    private void InitiallizeGoogleLogin() {
        SignInButton googleSignIn = findViewById(R.id.googleSignIn);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleLogin();
            }
        });
    }

    private void googleLogin() {
        SignInButton googleSignIn = findViewById(R.id.googleSignIn);
        //Creating Google Signin Option Object
        GoogleSignInOptions gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1082440501674-dsinj9sev8md1518nc8u5bal4rkll72b.apps.googleusercontent.com")
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        googleSignIn.setOnClickListener(v -> {
            Intent gSignIn = signInClient.getSignInIntent();
            startActivityForResult(gSignIn, GOOGLE_SIGNIN_CODE);
            //startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
        });
    }

    private void InitiallizeFacebook() {
        LoginButton loginButton = findViewById(R.id.fb_login_button);
        mCallbackManager=CallbackManager.Factory.create();

        loginButton.setPermissions("email","public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Facebook","On Success");
                facebookLogin(loginResult);
            }

            @Override
            public void onCancel() {
                Log.d("Facebook","On Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Facebook","On Error");
            }
        });
    }

    private void facebookLogin(LoginResult loginResult){
        AuthCredential credential= FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //SendUserData(user);
                            Log.d("Login","Success");
                            startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                            finish();
                        }
                        else{
                            Log.d("Login","Error");
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Here");
        //Check Result from Google
        if(requestCode==GOOGLE_SIGNIN_CODE){
            Task<GoogleSignInAccount> signInTask=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                System.out.println("Here");
                GoogleSignInAccount signInAcc=signInTask.getResult(ApiException.class);
                processFirebaseLogin(signInAcc.getIdToken());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            mCallbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void processFirebaseLogin(String token){
        AuthCredential authCredential= GoogleAuthProvider.getCredential(token,null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), PaymentActivity.class));

                        }
                    }
                });
    }

    //to enable user to reset password
    public void onClickForgottenPassword(View v) {
        EditText recoveryEmail = new EditText(v.getContext());
        recoveryEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);//ensures that text box can only take one line
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
        passwordResetDialog.setTitle("Reset Password");
        passwordResetDialog.setMessage("Enter Your Email Address");
        passwordResetDialog.setView(recoveryEmail);

        passwordResetDialog.setPositiveButton("Submit", (dialog, which) -> {
            //extract email and send the reset link
            String email = recoveryEmail.getText().toString();
            firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(aVoid -> Toast.makeText(SigninActivity.this, "Check Your Emails", Toast.LENGTH_LONG).show()).addOnFailureListener(e -> Toast.makeText(SigninActivity.this, "Error, No account with specified email found", Toast.LENGTH_LONG).show());
        });
        passwordResetDialog.setNegativeButton("Cancel", (dialog, which) -> {

        });

        passwordResetDialog.create().show();
        Toast toast = Toast.makeText(getApplicationContext(), "Reset Password", Toast.LENGTH_SHORT);//function called to initiate forgotten password user story
        toast.show();
    }

    public void onClickGoToSignUp(View v) {//called from the TextView in with id/new_member called using (android:onClick="onClickGoToSignUp")
        Intent mIntent = new Intent(SigninActivity.this, SignupActivity.class); //changes current activity from signin to signup
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

    private void invalidUser(){//displays an error if Firebase fails at logging the user in
        progressBar.setVisibility(View.GONE);
        userPasswordLayout.setError("Incorrect username or password");
        userEmailLayout.setError("Incorrect username or password");
    }

}