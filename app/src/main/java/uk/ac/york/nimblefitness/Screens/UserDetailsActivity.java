package uk.ac.york.nimblefitness.Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
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
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import uk.ac.york.nimblefitness.HelperClasses.UserDetails;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * The UserDetailsActivity allows the user to personalise their account by inputting their name,
 * age, gender and exercise level. this will be saved to the firebase database and received
 * every time they open the app and are logged in
 */

public class UserDetailsActivity extends AppCompatActivity {

    public static InputFilter[] myFilter = new InputFilter[]{new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!Character.isLetter(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    }};
    Button save_user_details_button;
    TextInputLayout user_account_first_name, user_account_last_name, user_account_age,
            gender_selector, activity_level_selector, exercise_type_selector, user_account_goal;
    MaterialBetterSpinner gender_selector_spinner, activity_level_selector_spinner,
            exercise_type_selector_spinner;
    TextInputEditText user_account_first_name_edit_text, user_account_last_name_edit_text,
            user_account_age_edit_text, user_account_goal_edit_text;
    ArrayAdapter<String> arrayAdapter_gender, arrayAdapter_activity_level,
            arrayAdapter_exercise_type_selector;
    FirebaseDatabase rootDatabase;
    DatabaseReference rootReference;
    DatabaseReference rootReferenceScoreBoard;
    UserDetails helperClass;
    UserDetails helperClass2;
    String membershipPlan;
    int currentMoves, completedRoutines, lastLogin, lastLoginWeek;
    boolean acceptedTC, onBoarded;

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

    //Downloading the users information off firebase if they are already logged in
    private void firebaseSetup() {

        rootDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rootReference = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
        rootReferenceScoreBoard =
                rootDatabase.getReference("scoreBoard").child(currentFirebaseUser.getUid());
        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                helperClass = snapshot.child("userDetails").getValue(UserDetails.class);
                if (helperClass != null) {
                    user_account_first_name_edit_text.setText(helperClass.getFirstName());
                    user_account_last_name_edit_text.setText(helperClass.getLastName());
                    user_account_age_edit_text.setText(String.valueOf(helperClass.getAge()).
                            equals("0") ? "" : String.valueOf(helperClass.getAge()));
                    gender_selector_spinner.setText(helperClass.getGender());
                    activity_level_selector_spinner.setText(helperClass.getExerciseDuration());
                    exercise_type_selector_spinner.setText(helperClass.getExerciseType());
                    if (helperClass.getWeeklyGoal() != 0) {
                        user_account_goal_edit_text.setText(String.valueOf(helperClass.getWeeklyGoal()));
                    }
                    currentMoves = helperClass.getCurrentMoves();
                    membershipPlan = helperClass.getMembershipPlan();
                    completedRoutines = helperClass.getCompletedRoutines();
                    lastLogin = helperClass.getLastLogin();
                    lastLoginWeek = helperClass.getLastLoginWeek();
                    acceptedTC = helperClass.isAcceptedTC();
                    onBoarded = helperClass.isOnBoarded();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserDetailsActivity.this, "Failed to get data.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Once the save button is pressed the user information is saved back to firebase and some
        // fields are saved in local phone storage to speed up later information displays
        save_user_details_button.setOnClickListener(view -> {
            if (validateFirstName() & validateLastName() & validateAge() & validateGender() &
                    validateExerciseDuration() & validateExerciseType() & validateWeeklyGoal()) {

                String firstName =
                        user_account_first_name.getEditText().getText().toString().trim();
                String lastName = user_account_last_name.getEditText().getText().toString().trim();
                int userAge =
                        Integer.parseInt(user_account_age.getEditText().getText().toString().trim());
                String gender = gender_selector.getEditText().getText().toString();
                String exerciseType = exercise_type_selector.getEditText().getText().toString();
                String exerciseDuration =
                        activity_level_selector.getEditText().getText().toString();
                int weeklyGoal =
                        Integer.parseInt(user_account_goal.getEditText().getText().toString().trim());

                helperClass2 = new UserDetails(firstName, lastName, gender, exerciseType,
                        exerciseDuration, userAge, membershipPlan, weeklyGoal, currentMoves,
                        completedRoutines, lastLogin, lastLoginWeek, acceptedTC, onBoarded);
                Gson gson = new Gson();

                rootReference.child("userDetails").setValue(helperClass2);

                String userFullName = String.format("%s %s", firstName, lastName);

                SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(currentFirebaseUser + "membershipPlan", membershipPlan);
                editor.putString(currentFirebaseUser + "userFullName", userFullName);
                editor.putInt(currentFirebaseUser + "weeklyGoal", weeklyGoal);
                editor.putInt(currentFirebaseUser + "currentMoves", currentMoves);
                editor.putInt(currentFirebaseUser + "completedRoutines", completedRoutines);
                editor.putBoolean(currentFirebaseUser + "acceptedTC", true);
                editor.apply();
                if (!onBoarded) {
                    startActivity(new Intent(UserDetailsActivity.this, OnBoardingActivity.class));
                } else {
                    startActivity(new Intent(UserDetailsActivity.this, MainActivity.class));
                }
                finish();
            }
        });


    }

    private void textEntrySetup() {
        save_user_details_button = findViewById(R.id.save_user_details);

        user_account_first_name = findViewById(R.id.user_account_first_name);
        user_account_first_name_edit_text = findViewById(R.id.user_account_first_name_edit_text);
        user_account_first_name_edit_text.setFilters(myFilter);

        user_account_last_name = findViewById(R.id.user_account_last_name);
        user_account_last_name_edit_text = findViewById(R.id.user_account_last_name_edit_text);
        user_account_last_name_edit_text.setFilters(myFilter);

        user_account_age_edit_text = findViewById(R.id.user_account_age_edit_text);
        user_account_age = findViewById(R.id.user_account_age);

        user_account_first_name.getEditText().setOnFocusChangeListener((view, b) -> {
            if (!b) {
                validateFirstName();
            }
        });

        user_account_last_name.getEditText().setOnFocusChangeListener((view, b) -> {
            if (!b) {
                validateLastName();
            }
        });

        user_account_age.getEditText().setOnFocusChangeListener((view, b) -> {
            if (!b) {
                validateAge();
            }
        });
    }

    private void genderSelectorSetup() {
        gender_selector = findViewById(R.id.gender_selector);
        gender_selector_spinner = findViewById(R.id.gender_selector_spinner);
        String[] arrayList_gender = {"Female", "Male", "Other"};
        arrayAdapter_gender = new ArrayAdapter<>(getApplicationContext(),
                R.layout.material_spinner_layout, arrayList_gender);
        gender_selector_spinner.setAdapter(arrayAdapter_gender);


        gender_selector.getEditText().setOnFocusChangeListener((view, b) -> {
            if (!b) {
                validateGender();
            }
        });
    }

    private void activityLevelSelectorSetup() {
        activity_level_selector = findViewById(R.id.activity_level_selector);
        activity_level_selector_spinner = findViewById(R.id.activity_level_selector_spinner);
        String[] arrayList_activity_level = {"None", "Less than 1 hour", "Between 1 and 2 hours",
                "Between 2 and 4 hours", "Between 4 and 8 hours", "More than 8 hours"};
        arrayAdapter_activity_level = new ArrayAdapter<>(getApplicationContext(),
                R.layout.material_spinner_layout, arrayList_activity_level);
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
            if (!b) {
                validateExerciseDuration();
            }
        });
    }

    private void exerciseTypeSelectorSetup() {
        exercise_type_selector = findViewById(R.id.exercise_type_selector);
        exercise_type_selector_spinner = findViewById(R.id.exercise_type_selector_spinner);
        String[] arrayList_exercise_type_selector = {"None", "Aerobic: cycling, running...",
                "Anaerobic: weight training...", "Calisthenics: large muscle group exercises",
                "Strength Training: compound or isolated exercise", "Stretching Exercises"};
        //the text that goes in each different list view item
        arrayAdapter_exercise_type_selector = new ArrayAdapter<>(getApplicationContext(),
                R.layout.material_spinner_layout, arrayList_exercise_type_selector);
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
            if (!b) {
                validateExerciseType();

            }
        });
    }

    private void weeklyGoalDisplay() {
        user_account_goal = findViewById(R.id.user_account_goal);
        user_account_goal_edit_text = findViewById(R.id.user_account_goal_edit_text);
        if ((!exercise_type_selector.getEditText().getText().toString().trim().equals("")) &&
                (!activity_level_selector.getEditText().getText().toString().trim().equals(""))) {
            user_account_goal_edit_text.setText(weeklyGoalCalculation());
        }
        user_account_goal.getEditText().setOnFocusChangeListener((view, b) -> {
            if (!b) {
                validateWeeklyGoal();
            }
        });
    }


    //calculates the user recommending weekly goal based on the information they have put into the
    // fields
    private String weeklyGoalCalculation() {

        switch (activity_level_selector.getEditText().getText().toString()) {
            case "None":
                switch (exercise_type_selector.getEditText().getText().toString()) {
                    case "None":
                    case "Aerobic: cycling, running...":
                    case "Anaerobic: weight training...":
                    case "Calisthenics: large muscle group exercises":
                    case "Strength Training: compound or isolated exercise":
                    case "Stretching Exercises":
                        return "450";
                }
            case "Less than 1 hour":
                switch (exercise_type_selector.getEditText().getText().toString()) {
                    case "None":
                        return "450";
                    case "Aerobic: cycling, running...":
                    case "Strength Training: compound or isolated exercise":
                    case "Stretching Exercises":
                        return "825";
                    case "Anaerobic: weight training...":
                    case "Calisthenics: large muscle group exercises":
                        return "1080";
                }
            case "Between 1 and 2 hours":
                switch (exercise_type_selector.getEditText().getText().toString()) {
                    case "None":
                        return "450";
                    case "Aerobic: cycling, running...":
                    case "Strength Training: compound or isolated exercise":
                    case "Stretching Exercises":
                        return "1080";
                    case "Anaerobic: weight training...":
                    case "Calisthenics: large muscle group exercises":
                        return "1260";
                }
            case "Between 2 and 4 hours":
                switch (exercise_type_selector.getEditText().getText().toString()) {
                    case "None":
                        return "450";
                    case "Aerobic: cycling, running...":
                    case "Strength Training: compound or isolated exercise":
                    case "Stretching Exercises":
                        return "1260";
                    case "Anaerobic: weight training...":
                    case "Calisthenics: large muscle group exercises":
                        return "1710";
                }
            case "Between 4 and 8 hours":
                switch (exercise_type_selector.getEditText().getText().toString()) {
                    case "None":
                        return "450";
                    case "Aerobic: cycling, running...":
                    case "Strength Training: compound or isolated exercise":
                    case "Stretching Exercises":
                        return "1710";
                    case "Anaerobic: weight training...":
                    case "Calisthenics: large muscle group exercises":
                        return "2610";
                }
            case "More than 8 hours":
                switch (exercise_type_selector.getEditText().getText().toString()) {
                    case "None":
                        return "450";
                    case "Aerobic: cycling, running...":
                    case "Strength Training: compound or isolated exercise":
                    case "Stretching Exercises":
                        return "2610";
                    case "Anaerobic: weight training...":
                    case "Calisthenics: large muscle group exercises":
                        return "3780";
                }
            default:
                return "0";
        }

    }

    private Boolean validateFirstName() {
        String firstName = user_account_first_name.getEditText().getText().toString().trim();

        if (firstName.isEmpty()) {
            user_account_first_name.setError("First name cannot be empty");
            return false;
        } else {
            user_account_first_name.setError(null);
            user_account_first_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateLastName() {
        String lastName = user_account_last_name.getEditText().getText().toString().trim();

        if (lastName.isEmpty()) {
            user_account_last_name.setError("Last name cannot be empty");
            return false;
        } else {
            user_account_last_name.setError(null);
            user_account_last_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateAge() {
        String userAge = user_account_age.getEditText().getText().toString().trim();
        if (userAge.isEmpty()) {
            user_account_age.setError("Age cannot be empty");
            return false;
        } else if (Integer.parseInt(userAge) < 18) {
            user_account_age.setError("You Must be 18 or older");
            return false;
        } else {
            user_account_age.setError(null);
            user_account_age.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateGender() {
        String gender = gender_selector.getEditText().getText().toString().trim();

        if (gender.isEmpty()) {
            gender_selector.setError("Gender cannot be empty");
            return false;
        } else {
            gender_selector.setError(null);
            gender_selector.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateExerciseDuration() {
        String exerciseDuration = activity_level_selector.getEditText().getText().toString().trim();

        if (exerciseDuration.isEmpty()) {
            activity_level_selector.setError("Activity level cannot be empty");
            return false;
        } else {
            activity_level_selector.setError(null);
            activity_level_selector.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateExerciseType() {
        String exerciseType = exercise_type_selector.getEditText().getText().toString().trim();

        if (exerciseType.isEmpty()) {
            exercise_type_selector.setError("Type of exercise cannot be empty");
            return false;
        } else {
            exercise_type_selector.setError(null);
            exercise_type_selector.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateWeeklyGoal() {
        String weeklyGoal = user_account_goal.getEditText().getText().toString().trim();

        if (Integer.parseInt(weeklyGoal) <= 0 || Integer.parseInt(weeklyGoal) > 9000) {
            user_account_goal.setError("Choose a goal greater than 0 or less than 9000");
            return false;
        } else {
            user_account_goal.setError(null);
            user_account_goal.setErrorEnabled(false);
            return true;
        }
    }

    //starts the account deletion process
    private void deleteAccount() {
        Button deleteAccount = findViewById(R.id.delete_user_details);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAuthenticateUser();
            }
        });
    }

    //requests confirmation that they want to delete their account, then deletes their
    // information from everywhere in the data base and removes their account for the
    // authentication service
    private void deleteAccountAlertBuilder(AuthCredential credential) {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogStyle).setTitle("Are you sure " + "you" + " would like to delete your account?").setMessage("This cannot be " + "undone!").setCancelable(true).setPositiveButton("Yes, Delete", (dialog, id) -> {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            rootReference.removeValue();
            rootReferenceScoreBoard.removeValue();
            user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(UserDetailsActivity.this,
                                        SignupActivity.class));
                                finish();
                            } else {
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserDetailsActivity.this, "Incorrect Password, Please Try " +
                            "Again", Toast.LENGTH_LONG).show();
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

    //reAuthenticates the user to allow the account deletion to occur
    private void reAuthenticateUser() {
        String provider =
                FirebaseAuth.getInstance().getCurrentUser().getIdToken(false).getResult().getSignInProvider();
        AuthCredential credential;
        switch (provider) {
            case "google.com":
                GoogleSignInAccount acct =
                        GoogleSignIn.getLastSignedInAccount(UserDetailsActivity.this);
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
                passwordInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);//ensures that
                // text box can only take one line

                AlertDialog.Builder passwordReAuthenticateDialog =
                        new AlertDialog.Builder(UserDetailsActivity.this);
                passwordReAuthenticateDialog.setTitle("ReAuthenticate to Delete Account");
                passwordReAuthenticateDialog.setMessage("Please Enter Your Password");
                passwordReAuthenticateDialog.setView(passwordInput);

                passwordReAuthenticateDialog.setPositiveButton("Submit", (dialog, which) -> {
                    AuthCredential credential2 =
                            EmailAuthProvider.getCredential(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                    passwordInput.getText().toString());
                    deleteAccountAlertBuilder(credential2);

                });
                passwordReAuthenticateDialog.setNegativeButton("Cancel", (dialog, which) -> {

                });
                passwordReAuthenticateDialog.show();

                break;
            default:
                break;
        }
    }

}

