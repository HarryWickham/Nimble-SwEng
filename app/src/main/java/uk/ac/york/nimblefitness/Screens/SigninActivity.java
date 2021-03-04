package uk.ac.york.nimblefitness.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import uk.ac.york.nimblefitness.R;

public class SigninActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText userEmail, userPassword;
    Button login_button;
    TextView signUpButton, forgottenPassword;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        userEmail = findViewById(R.id.SignInEmail);
        userPassword = findViewById(R.id.SignInPassword);
        login_button = findViewById(R.id.sign_in_button);
        forgottenPassword = findViewById(R.id.forgotten_password);
        signUpButton = findViewById(R.id.sign_up_button);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_circular);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    userEmail.setError("Email is Required");
                    progressBar.setVisibility(v.GONE);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    userPassword.setError("Password is Required");
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
                            Toast.makeText(SigninActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
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
