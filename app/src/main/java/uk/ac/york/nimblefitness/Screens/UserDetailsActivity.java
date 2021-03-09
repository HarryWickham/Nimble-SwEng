package uk.ac.york.nimblefitness.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Queue;

import uk.ac.york.nimblefitness.HelperClasses.UserHelperClass;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Settings.SettingsFragment;

public class UserDetailsActivity extends AppCompatActivity {

    Button save_user_details_button;
    TextInputLayout user_account_first_name, user_account_last_name, user_account_age,  gender_selector, activity_level_selector, exercise_type_selector;
    AutoCompleteTextView actv_gender_selector, actv_activity_level_selector, actv_exercise_type_selector;

    TextInputEditText user_account_first_name_edit_text, user_account_last_name_edit_text, user_account_age_edit_text;

    ArrayAdapter<String> arrayAdapter_gender, arrayAdapter_activity_level, arrayAdapter_exercise_type_selector;

    FirebaseDatabase rootDatabase;
    DatabaseReference rootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        gender_selector = findViewById(R.id.gender_selector);
        actv_gender_selector = findViewById(R.id.actv_gender_selector);
        String[] arrayList_gender = {"Female", "Male", "Other"};
        arrayAdapter_gender = new ArrayAdapter<>(getApplicationContext(), R.layout.material_spinner_layout, arrayList_gender);
        actv_gender_selector.setAdapter(arrayAdapter_gender);

        activity_level_selector = findViewById(R.id.activity_level_selector);
        actv_activity_level_selector = findViewById(R.id.actv_activity_level_selector);
        String[] arrayList_activity_level = {"None", "Less than 1 hour", "Between 1 and 2 hours",
                "Between 2 and 4 hours", "Between 4 and 8 hours", "More than 8 hours"};
        arrayAdapter_activity_level = new ArrayAdapter<>(getApplicationContext(), R.layout.material_spinner_layout, arrayList_activity_level);
        actv_activity_level_selector.setAdapter(arrayAdapter_activity_level);


        exercise_type_selector = findViewById(R.id.exercise_type_selector);
        actv_exercise_type_selector = findViewById(R.id.actv_exercise_type_selector);
        String[] arrayList_exercise_type_selector = {"None", "Aerobic: cycling, running...", "Anaerobic: weight training...",
                "Calisthenics: large muscle group exercises", "Strength Training: compound or isolated exercise", "Stretching Exercises"}; //the text that goes in each different list view item
        arrayAdapter_exercise_type_selector = new ArrayAdapter<>(getApplicationContext(), R.layout.material_spinner_layout, arrayList_exercise_type_selector);
        actv_exercise_type_selector.setAdapter(arrayAdapter_exercise_type_selector);

        save_user_details_button = findViewById(R.id.save_user_details);
        user_account_first_name = findViewById(R.id.user_account_first_name);
        user_account_first_name_edit_text = findViewById(R.id.user_account_first_name_edit_text);
        user_account_last_name_edit_text = findViewById(R.id.user_account_last_name_edit_text);
        user_account_age_edit_text = findViewById(R.id.user_account_age_edit_text);


        user_account_last_name = findViewById(R.id.user_account_last_name);
        user_account_age = findViewById(R.id.user_account_age);

        Log.i("onCreate ", "previouslyEnteredValues");

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

        gender_selector.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateGender();
            }
        });

        activity_level_selector.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateExerciseDuration();
            }
        });

        exercise_type_selector.getEditText().setOnFocusChangeListener((view, b) -> {
            if(!b){
                validateExerciseType();
            }
        });

        rootDatabase = FirebaseDatabase.getInstance();
        rootReference = rootDatabase.getReference("users");

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        save_user_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFirstName() & validateLastName() & validateAge() & validateGender() & validateExerciseDuration() & validateExerciseType()) {

                    String firstName = user_account_first_name.getEditText().getText().toString().trim();
                    String lastName = user_account_last_name.getEditText().getText().toString().trim();
                    String userAge = user_account_age.getEditText().getText().toString().trim();
                    String gender = gender_selector.getEditText().getText().toString();
                    String exerciseType = exercise_type_selector.getEditText().getText().toString();
                    String exerciseDuration = activity_level_selector.getEditText().getText().toString();

                    UserHelperClass helperClass = new UserHelperClass(firstName, lastName, gender, exerciseType, exerciseDuration, userAge);
                    rootReference.child(currentFirebaseUser.getUid()).setValue(helperClass);

                    Intent mIntent = new Intent(UserDetailsActivity.this, MainActivity.class);
                    startActivity(mIntent);
                    finish();
                }
            }
        });

        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user_firstName = snapshot.child(currentFirebaseUser.getUid()).child("firstName").getValue(String.class);
                user_account_first_name_edit_text.setText(user_firstName);
                String user_lastName = snapshot.child(currentFirebaseUser.getUid()).child("lastName").getValue(String.class);
                user_account_last_name_edit_text.setText(user_lastName);
                String user_age = snapshot.child(currentFirebaseUser.getUid()).child("age").getValue(String.class);
                user_account_age_edit_text.setText(user_age);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserDetailsActivity.this, "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        });

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

}