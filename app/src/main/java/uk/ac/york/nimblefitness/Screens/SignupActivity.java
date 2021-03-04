package uk.ac.york.nimblefitness.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import uk.ac.york.nimblefitness.R;

public class SignupActivity extends AppCompatActivity {
    EditText  userEmail, userPassword, userConfirmPassword;
    Button signUpButton;
    TextView loginButton;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userEmail = findViewById(R.id.SignUpEmail);
        userPassword = findViewById(R.id.SignUpPassword);
        userConfirmPassword = findViewById(R.id.SignUpPasswordConfirm);
        signUpButton = findViewById(R.id.sign_up_button);
        loginButton = findViewById(R.id.login_button);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_circular);


        Log.i("userEmail", String.valueOf(userEmail));
        Log.i("userPassword", String.valueOf(userPassword));
        Log.i("userConfirmPassword", String.valueOf(userConfirmPassword));


        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                progressBar.setVisibility(v.VISIBLE);

                if(TextUtils.isEmpty(email)){
                    userEmail.setError("Email is Required");
                    progressBar.setVisibility(v.GONE);
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    userPassword.setError("Password is Required");
                    progressBar.setVisibility(v.GONE);
                    return;
                }

                if ((password.length()< 6)){
                    userPassword.setError("Password must be at least 6 characters long");
                    progressBar.setVisibility(v.GONE);
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }else{
                            progressBar.setVisibility(v.GONE);
                            Toast.makeText(SignupActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }
        public void onClickGoToLogin(View v) {//called from the TextView in with id/already_a_member called using (android:onClick="onClickGoToLogin")
            Intent mIntent = new Intent(SignupActivity.this, SigninActivity.class);//changes current activity from signin to signup
            startActivity(mIntent);
            finish();
        }

}