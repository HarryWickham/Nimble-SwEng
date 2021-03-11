package uk.ac.york.nimblefitness.Screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import uk.ac.york.nimblefitness.R;

public class SigninActivity extends AppCompatActivity {
    private static final int GOOGLE_SIGNIN_CODE = 10005;
    private EditText userEmail, userPassword;
    private Button login_button;
    private TextView signUpButton, forgottenPassword;
    private SignInButton googleSignIn;
    private GoogleSignInClient signInClient;
    private GoogleSignInOptions gso;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private TextInputLayout userEmailLayout, userPassowrdLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        userEmail = findViewById(R.id.SignInEmail);
        userPassword = findViewById(R.id.SignInPassword);
        login_button = findViewById(R.id.sign_in_button);
        forgottenPassword = findViewById(R.id.forgotten_password);
        signUpButton = findViewById(R.id.sign_up_button);
        googleSignIn = findViewById(R.id.googleSignIn);
        progressBar = findViewById(R.id.progress_circular);
        firebaseAuth = FirebaseAuth.getInstance();
        userEmailLayout = findViewById(R.id.SignInEmailLayout);
        userPassowrdLayout = findViewById(R.id.SignInPasswordLayout);





        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                progressBar.setVisibility(v.VISIBLE);
                userEmailLayout.setError(null);
                userEmailLayout.setErrorEnabled(false);
                userPassowrdLayout.setError(null);
                userPassowrdLayout.setErrorEnabled(false);


                if (TextUtils.isEmpty(email)) {
                    userEmailLayout.setError("Email is Required");
                    progressBar.setVisibility(v.GONE);
                    return;
                }


                if (TextUtils.isEmpty(password)) {
                    userPassowrdLayout.setError("Password is Required");
                    progressBar.setVisibility(v.GONE);
                    return;
                }


                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SigninActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            userEmailLayout.setError(task.getException().getMessage());
                            System.out.println(task.getException());
                            progressBar.setVisibility(v.GONE);
                        }
                    }
                });

            }
        });
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1082440501674-dsinj9sev8md1518nc8u5bal4rkll72b.apps.googleusercontent.com")
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null || firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this,MainActivity.class));
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
        if(requestCode == GOOGLE_SIGNIN_CODE){
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount signInAcc = signInTask.getResult(ApiException.class);

                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAcc.getIdToken(),null);
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(),"Google Account Connected",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }) .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                Toast.makeText(this, "Goolge Account Connected",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
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
    public void onClickForgottenPassword(View v) {
        EditText recoveryEmail = new EditText(v.getContext());
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
                        Toast.makeText(SigninActivity.this, "Check Your Emails",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SigninActivity.this, "Error, Reset Email Not Sent",Toast.LENGTH_SHORT).show();
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



}
