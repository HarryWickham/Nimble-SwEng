package uk.ac.york.nimblefitness.Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import uk.ac.york.nimblefitness.HelperClasses.UserDetails;
import uk.ac.york.nimblefitness.HelperClasses.Verification;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class SignupActivity extends AppCompatActivity {
    private static final int GOOGLE_SIGNIN_CODE = 10005;
    private EditText userEmail, userPassword, userConfirmPassword;
    private TextInputLayout userEmailLayout, userPasswordLayout, userConfirmPasswordLayout;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private CallbackManager mCallbackManager;
    private GoogleSignInClient signInClient;

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

        InitialiseGoogleLogin();
        InitialiseFacebook();

        //validates the email text box when the user clicks away from them
        Objects.requireNonNull(userEmailLayout.getEditText()).setOnFocusChangeListener((view, b) -> {
            if (!b) {
                validateEmail(userDetails);
            }
        });
        //validates the password text box when the user clicks away from them
        Objects.requireNonNull(userPasswordLayout.getEditText()).setOnFocusChangeListener((view,
                                                                                           b) -> {
            if (!b) {
                validatePassword(userDetails);
            }
        });
        //validates the confirm password text box when the user clicks away from them
        Objects.requireNonNull(userConfirmPasswordLayout.getEditText()).setOnFocusChangeListener((view, b) -> {
            if (!b) {
                validateConfirmPassword(userDetails);
            }
        });

        signUpButton.setOnClickListener(v -> {
            String email1 = userEmail.getText().toString().trim();
            String password1 = userPassword.getText().toString().trim();

            if (validateEmail(userDetails) & validatePassword(userDetails) & validateConfirmPassword(userDetails)) {

                checkSignUpDetails(email1, password1);
            }

        });
    }

    public void checkSignUpDetails(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //send verification link

                userConfirmPasswordLayout.setError(null);//resets errors
                userConfirmPasswordLayout.setErrorEnabled(false);//resets errors

                FirebaseUser user = firebaseAuth.getCurrentUser();
                assert user != null;
                user.sendEmailVerification().addOnSuccessListener(aVoid -> Toast.makeText(
                        SignupActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT)
                        .show()).addOnFailureListener(e -> {
                });
                Toast.makeText(SignupActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                receiveData();

            } else {
                //progressBar.setVisibility(View.GONE);
                userEmailLayout.setError(Objects.requireNonNull(task.getException()).getMessage());
            }
        });

    }

    private void InitialiseGoogleLogin() {
        SignInButton googleSignIn = findViewById(R.id.googleSignIn);
        googleLogin();
    }

    private void googleLogin() {
        SignInButton googleSignIn = findViewById(R.id.googleSignUp);
        //Creating Google Signin Option Object
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        requestIdToken("1082440501674-dsinj9sev8md1518nc8u5bal4rkll72b.apps.googleusercontent.com")
                        .requestEmail().build();

        signInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        googleSignIn.setOnClickListener(v -> {
            Intent gSignIn = signInClient.getSignInIntent();
            startActivityForResult(gSignIn, GOOGLE_SIGNIN_CODE);
        });
    }

    private void InitialiseFacebook() {
        LoginButton loginButton = findViewById(R.id.fb_SignUp);
        mCallbackManager = CallbackManager.Factory.create();

        loginButton.setPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookLogin(loginResult);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    private void facebookLogin(LoginResult loginResult) {
        AuthCredential credential =
                FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(SignupActivity.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    receiveData();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Here");
        //Check Result from Google
        if (requestCode == GOOGLE_SIGNIN_CODE) {
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                System.out.println("Here");
                GoogleSignInAccount signInAcc = signInTask.getResult(ApiException.class);
                processFirebaseLogin(signInAcc.getIdToken());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void processFirebaseLogin(String token) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(token, null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(
                SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    receiveData();

                }
            }
        });
    }

    //called from the TextView in with id/already_a_member called using
    // (android:onClick="onClickGoToLogin")
    public void onClickGoToLogin(View v) {
        //changes current activity from signin to signup
        Intent mIntent = new Intent(SignupActivity.this, SigninActivity.class);
        startActivity(mIntent);
        finish();
    }

    // calls the validate email method in the verification class
    private Boolean validateEmail(Verification userDetails) {
        userDetails.setEmail(userEmail.getText().toString().trim());
        String reply = userDetails.validateEmail();
        if (!reply.equals("Valid")) {
            userEmailLayout.setError(reply);
            progressBar.setVisibility(View.GONE);
            return false;
        } else {
            userEmailLayout.setError(null);
            userEmailLayout.setErrorEnabled(false);
            return true;
        }
    }

    // calls the validate password method in the verification class
    private Boolean validatePassword(Verification userDetails) {
        userDetails.setPassword(userPassword.getText().toString().trim());
        String reply = userDetails.validatePassword();
        if (!reply.equals("Valid")) {
            userPasswordLayout.setError(reply);
            progressBar.setVisibility(View.GONE);
            return false;
        } else {
            userPasswordLayout.setError(null);
            userPasswordLayout.setErrorEnabled(false);
            return true;
        }
    }

    // calls the validate confirm password method in the verification class
    private Boolean validateConfirmPassword(Verification userDetails) {

        userDetails.setConfirmPassword(userConfirmPassword.getText().toString().trim());
        String reply = userDetails.validateConfirmPassword();

        if (!reply.equals("Valid")) {
            userConfirmPasswordLayout.setError(reply);
            progressBar.setVisibility(View.GONE);
            return false;
        } else {
            userConfirmPasswordLayout.setError(null);
            userConfirmPasswordLayout.setErrorEnabled(false);
            return true;
        }
    }

    private void receiveData() {
        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser != null) {
            DatabaseReference rootReference =
                    rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
            rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserDetails userDetails =
                            snapshot.child("userDetails").getValue(UserDetails.class);
                    if (userDetails != null) {
                        String userFullName = String.format("%s %s", userDetails.getFirstName(),
                                userDetails.getLastName());

                        SharedPreferences prefs =
                                getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(currentFirebaseUser + "membershipPlan",
                                userDetails.getMembershipPlan());
                        editor.putString(currentFirebaseUser + "userFullName", userFullName);
                        editor.putInt(currentFirebaseUser + "weeklyGoal",
                                userDetails.getWeeklyGoal());
                        editor.putInt(currentFirebaseUser + "currentMoves",
                                userDetails.getCurrentMoves());
                        editor.putInt(currentFirebaseUser + "completedRoutines",
                                userDetails.getCompletedRoutines());
                        editor.putBoolean(currentFirebaseUser + "acceptedTC",
                                userDetails.isAcceptedTC());
                        editor.putBoolean(currentFirebaseUser + "onBoarded",
                                userDetails.isOnBoarded());
                        editor.apply();

                    }
                    routing(currentFirebaseUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    routing(null);
                }
            }, 1500);
        }
    }

    private void routing(FirebaseUser currentFirebaseUser) {
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        String userName = prefs.getString(currentFirebaseUser + "userFullName", "error");
        String membershipPlan = prefs.getString(currentFirebaseUser + "membershipPlan", "error");
        boolean acceptedTC = prefs.getBoolean(currentFirebaseUser + "acceptedTC", false);
        boolean onBoarded = prefs.getBoolean(currentFirebaseUser + "onBoarded", false);
        if (!acceptedTC) {
            startActivity(new Intent(SignupActivity.this, TermsAndConditionsActivity.class));
            finish();
        } else if (membershipPlan.equals("error")) {
            startActivity(new Intent(SignupActivity.this, PaymentActivity.class));
            finish();
        } else if ((userName.equals("error") || userName.equals("null null"))) {
            startActivity(new Intent(SignupActivity.this, UserDetailsActivity.class));
            finish();
        } else if (!onBoarded) {
            startActivity(new Intent(SignupActivity.this, OnBoardingActivity.class));
            finish();
        } else {
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
            finish();
        }
    }
}