package uk.ac.york.nimblefitness.Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import uk.ac.york.nimblefitness.HelperClasses.UserHelperClass;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class UserDetailsActivity extends AppCompatActivity {

    Button save_user_details_button;
    TextInputLayout user_account_first_name, user_account_last_name, user_account_age,  gender_selector, activity_level_selector, exercise_type_selector, user_account_goal;
    MaterialBetterSpinner gender_selector_spinner, activity_level_selector_spinner, exercise_type_selector_spinner;

    TextInputEditText user_account_first_name_edit_text, user_account_last_name_edit_text, user_account_age_edit_text, user_account_goal_edit_text;

    ArrayAdapter<String> arrayAdapter_gender, arrayAdapter_activity_level, arrayAdapter_exercise_type_selector;

    FirebaseDatabase rootDatabase;
    DatabaseReference rootReference;

    UserHelperClass helperClass;
    UserHelperClass helperClass2;

    String membershipPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        setTitle("Account");
        textEntrySetup();
        genderSelectorSetup();
        activityLevelSelectorSetup();
        exerciseTypeSelectorSetup();
        weeklyGoalDisplay();
        firebaseSetup();
        deleteAccount();

    }

    private void firebaseSetup(){
        
        rootDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rootReference = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                helperClass = snapshot.child("userDetails").getValue(UserHelperClass.class);
                membershipPlan = snapshot.child("userDetails").child("membershipPlan").getValue(String.class);
                if (helperClass != null) {
                    user_account_first_name_edit_text.setText(helperClass.getFirstName());
                    user_account_last_name_edit_text.setText(helperClass.getLastName());
                    user_account_age_edit_text.setText(helperClass.getAge());
                    gender_selector_spinner.setText(helperClass.getGender());
                    activity_level_selector_spinner.setText(helperClass.getExerciseDuration());
                    exercise_type_selector_spinner.setText(helperClass.getExerciseType());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserDetailsActivity.this, "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        save_user_details_button.setOnClickListener(view -> {
            if(validateFirstName() & validateLastName() & validateAge() & validateGender() & validateExerciseDuration() & validateExerciseType()) {

                String firstName = user_account_first_name.getEditText().getText().toString().trim();
                String lastName = user_account_last_name.getEditText().getText().toString().trim();
                String userAge = user_account_age.getEditText().getText().toString().trim();
                String gender = gender_selector.getEditText().getText().toString();
                String exerciseType = exercise_type_selector.getEditText().getText().toString();
                String exerciseDuration = activity_level_selector.getEditText().getText().toString();
                String weeklyGoal = user_account_goal.getEditText().getText().toString().trim();

                String userFullName = String.format("%s %s", firstName, lastName);
                SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(currentFirebaseUser+"userFullName", userFullName);
                editor.putString(currentFirebaseUser+"weeklyGoal", weeklyGoal);
                editor.apply();

                Log.i(currentFirebaseUser+"userFullName ", "firebaseSetup: " + prefs.getString(currentFirebaseUser+"userFullName", "Error getting name"));

                helperClass2 = new UserHelperClass(firstName, lastName, gender, exerciseType, exerciseDuration, userAge, membershipPlan, weeklyGoal);
                rootReference.child("userDetails").setValue(helperClass2);
                startActivity(new Intent(UserDetailsActivity.this, MainActivity.class));
                finish();
            }
        });


    }

    private void textEntrySetup(){
        save_user_details_button = findViewById(R.id.save_user_details);

        user_account_first_name = findViewById(R.id.user_account_first_name);
        user_account_first_name_edit_text = findViewById(R.id.user_account_first_name_edit_text);

        user_account_last_name = findViewById(R.id.user_account_last_name);
        user_account_last_name_edit_text = findViewById(R.id.user_account_last_name_edit_text);

        user_account_age_edit_text = findViewById(R.id.user_account_age_edit_text);
        user_account_age = findViewById(R.id.user_account_age);

        user_account_first_name.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateFirstName();
            }
        });

        user_account_last_name.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateLastName();
            }
        });

        user_account_age.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateAge();
            }
        });
    }

    private void genderSelectorSetup(){
        gender_selector = findViewById(R.id.gender_selector);
        gender_selector_spinner = findViewById(R.id.gender_selector_spinner);
        String[] arrayList_gender = {"Female", "Male", "Other"};
        arrayAdapter_gender = new ArrayAdapter<>(getApplicationContext(), R.layout.material_spinner_layout, arrayList_gender);
        gender_selector_spinner.setAdapter(arrayAdapter_gender);


        gender_selector.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateGender();
            }
        });
    }

    private void activityLevelSelectorSetup(){
        activity_level_selector = findViewById(R.id.activity_level_selector);
        activity_level_selector_spinner = findViewById(R.id.activity_level_selector_spinner);
        String[] arrayList_activity_level = {"None", "Less than 1 hour", "Between 1 and 2 hours",
                "Between 2 and 4 hours", "Between 4 and 8 hours", "More than 8 hours"};
        arrayAdapter_activity_level = new ArrayAdapter<>(getApplicationContext(), R.layout.material_spinner_layout, arrayList_activity_level);
        activity_level_selector_spinner.setAdapter(arrayAdapter_activity_level);

        activity_level_selector.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weeklyGoalDisplay();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activity_level_selector.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateExerciseDuration();
            }
        });
    }

    private void exerciseTypeSelectorSetup(){
        exercise_type_selector = findViewById(R.id.exercise_type_selector);
        exercise_type_selector_spinner = findViewById(R.id.exercise_type_selector_spinner);
        String[] arrayList_exercise_type_selector = {"None", "Aerobic: cycling, running...", "Anaerobic: weight training...",
                "Calisthenics: large muscle group exercises", "Strength Training: compound or isolated exercise", "Stretching Exercises"}; //the text that goes in each different list view item
        arrayAdapter_exercise_type_selector = new ArrayAdapter<>(getApplicationContext(), R.layout.material_spinner_layout, arrayList_exercise_type_selector);
        exercise_type_selector_spinner.setAdapter(arrayAdapter_exercise_type_selector);

        exercise_type_selector.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weeklyGoalDisplay();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        exercise_type_selector.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateExerciseType();

            }
        });
    }

    private void weeklyGoalDisplay(){
        user_account_goal = findViewById(R.id.user_account_goal);
        user_account_goal_edit_text = findViewById(R.id.user_account_goal_edit_text);
        Log.i("exercise_type_selector", String.valueOf((!exercise_type_selector.getEditText().getText().toString().trim().equals(""))));
        Log.i("activity_level_selector", String.valueOf((!activity_level_selector.getEditText().getText().toString().trim().equals(""))));
        if((!exercise_type_selector.getEditText().getText().toString().trim().equals(""))&&(!activity_level_selector.getEditText().getText().toString().trim().equals(""))){
            user_account_goal_edit_text.setText(weeklyGoalCalculation());
        }
    }

    private String weeklyGoalCalculation(){
        //todo complete algorithm to give user an accurate goal
        return "50";
    }

    private Boolean validateFirstName(){
        String firstName = user_account_first_name.getEditText().getText().toString().trim();

        if(firstName.isEmpty()){
            user_account_first_name.setError("First name cannot be empty");
            return false;
        }
        else{
            user_account_first_name.setError(null);
            user_account_first_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateLastName(){
        String firstName = user_account_last_name.getEditText().getText().toString().trim();

        if(firstName.isEmpty()){
            user_account_last_name.setError("Last name cannot be empty");
            return false;
        }
        else{
            user_account_last_name.setError(null);
            user_account_last_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateAge(){
        String firstName = user_account_age.getEditText().getText().toString().trim();

        if(firstName.isEmpty()){
            user_account_age.setError("Age cannot be empty");
            return false;
        }
        else{
            user_account_age.setError(null);
            user_account_age.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateGender(){
        String firstName = gender_selector.getEditText().getText().toString().trim();

        if(firstName.isEmpty()){
            gender_selector.setError("Gender cannot be empty");
            return false;
        }
        else{
            gender_selector.setError(null);
            gender_selector.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateExerciseDuration(){
        String firstName = activity_level_selector.getEditText().getText().toString().trim();

        if(firstName.isEmpty()){
            activity_level_selector.setError("Activity level cannot be empty");
            return false;
        }
        else{
            activity_level_selector.setError(null);
            activity_level_selector.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateExerciseType(){
        String firstName = exercise_type_selector.getEditText().getText().toString().trim();

        if(firstName.isEmpty()){
            exercise_type_selector.setError("Type of exercise cannot be empty");
            return false;
        }
        else{
            exercise_type_selector.setError(null);
            exercise_type_selector.setErrorEnabled(false);
            return true;
        }
    }


    private void deleteAccount() {
        Button deleteAccount = findViewById(R.id.delete_user_details);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAuthenticateUser();
            }
        });
    }

    private void deleteAccountAlertBuilder(AuthCredential credential){
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogStyle)
        .setTitle("Are you sure you would like to delete your account?")
        .setMessage("This cannot be undone!")
        .setCancelable(true)
        .setPositiveButton("Yes, Delete", (dialog, id) -> {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            rootReference.removeValue();
            user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i("PositiveButton", " attempting account deletion");
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i("onComplete", " account deleted");
                                startActivity(new Intent(UserDetailsActivity.this, SignupActivity.class));
                                finish();
                            }else{
                                Log.i("onComplete", " account NOT deleted FAILED");
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserDetailsActivity.this, "Incorrect Password, Please Try Again", Toast.LENGTH_LONG).show();
                    reAuthenticateUser();
                }
            });

        })

        .setNegativeButton("Cancel", (dialog, id) -> {
            dialog.cancel();
        })
        // create alert dialog
        .show();
    }

    private void reAuthenticateUser(){
        String provider = FirebaseAuth.getInstance().getCurrentUser().getIdToken(false).getResult().getSignInProvider(); //Currently only outputs firebase as provider????
        String password;
        AuthCredential credential = null;
        Log.i("reAuthenticateUser", " : "+provider);
        switch (provider) {
            case "google.com":
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(UserDetailsActivity.this);
                credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                deleteAccountAlertBuilder(credential);
                break;
            case "facebook.com":
                AccessToken token = AccessToken.getCurrentAccessToken();
                credential = FacebookAuthProvider.getCredential(token.getToken());
                deleteAccountAlertBuilder(credential);
                break;
            case "password":
                EditText passwordInput = new EditText(UserDetailsActivity.this);
                passwordInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);//ensures that text box can only take one line

                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(UserDetailsActivity.this);
                passwordResetDialog.setTitle("ReAuthenticate to Delete Account");
                passwordResetDialog.setMessage("Please Enter Your Password");
                passwordResetDialog.setView(passwordInput);

                passwordResetDialog.setPositiveButton("Submit", (dialog, which) -> {
                    AuthCredential credential2 = EmailAuthProvider.getCredential(FirebaseAuth.getInstance().getCurrentUser().getEmail(), passwordInput.getText().toString());
                    deleteAccountAlertBuilder(credential2);

                });
                passwordResetDialog.setNegativeButton("Cancel", (dialog, which) -> {

                });
                passwordResetDialog.show();
                Log.i("reAuthenticateUser", FirebaseAuth.getInstance().getCurrentUser().getEmail());

                break;
            default:
                Log.i("reAuthenticateUser", "Default");
                break;
        }
    }

}

