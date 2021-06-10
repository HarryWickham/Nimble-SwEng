package uk.ac.york.nimblefitness.Screens;

import android.widget.Button;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import uk.ac.york.nimblefitness.R;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDetailsActivityTest {

    //Instantiates the User Detail Activity so it can be tested
    @Rule
    public ActivityTestRule<UserDetailsActivity> UserDetailsActivityTestRule = new ActivityTestRule<>(UserDetailsActivity.class);

    private UserDetailsActivity mActivity;

    //Gets the User Detail Activity
    @Before
    public void seUp() throws Exception {
        mActivity = UserDetailsActivityTestRule.getActivity();
    }

    //Test case - tests all visual elements are displayed as expected
    @Test
    public void testLaunchUserDetailsActivity() {
        TextView personalInformation = (TextView) mActivity.findViewById(R.id.personal_information);
        TextInputLayout userAccountFirstName = (TextInputLayout) mActivity.findViewById(R.id.user_account_first_name);
        TextInputEditText userAccountFirstNameEditText = (TextInputEditText) mActivity.findViewById(R.id.user_account_first_name_edit_text);
        TextInputLayout userAccountLastName = (TextInputLayout) mActivity.findViewById(R.id.user_account_last_name);
        TextInputEditText userAccountLastNameEditText = (TextInputEditText) mActivity.findViewById(R.id.user_account_last_name_edit_text);
        TextInputLayout userAccountAge = (TextInputLayout) mActivity.findViewById(R.id.user_account_age);
        TextInputEditText userAccountAgeEditText = (TextInputEditText) mActivity.findViewById(R.id.user_account_age_edit_text);
        TextInputLayout genderSelection = (TextInputLayout) mActivity.findViewById(R.id.gender_selector);
        MaterialBetterSpinner genderSelectorSpinner = (MaterialBetterSpinner) mActivity.findViewById(R.id.gender_selector_spinner);
        TextView exerciseAndHealth = (TextView) mActivity.findViewById(R.id.exercise_and_Health);
        TextInputLayout activityLevelSelector = (TextInputLayout) mActivity.findViewById(R.id.activity_level_selector);
        MaterialBetterSpinner activityLevelSelectorSpinner = (MaterialBetterSpinner) mActivity.findViewById(R.id.activity_level_selector_spinner);
        TextInputLayout exerciseTypeSelector = (TextInputLayout) mActivity.findViewById(R.id.exercise_type_selector);
        MaterialBetterSpinner exerciseTypeSelectorSpinner = (MaterialBetterSpinner) mActivity.findViewById(R.id.exercise_type_selector_spinner);
        TextInputLayout userAccountGoal = (TextInputLayout) mActivity.findViewById(R.id.user_account_goal);
        TextInputEditText userAccountGoalEditText = (TextInputEditText) mActivity.findViewById(R.id.user_account_goal_edit_text);
        Button saveUserDetails = (Button) mActivity.findViewById(R.id.save_user_details);
        Button deleteUserDetails = (Button) mActivity.findViewById(R.id.delete_user_details);

        //Asserts that the elements are displayed
        assertNotNull(personalInformation);
        assertNotNull(userAccountFirstName);
        assertNotNull(userAccountFirstNameEditText);
        assertNotNull(userAccountLastName);
        assertNotNull(userAccountLastNameEditText);
        assertNotNull(userAccountAge);
        assertNotNull(userAccountAgeEditText);
        assertNotNull(genderSelection);
        assertNotNull(genderSelectorSpinner);
        assertNotNull(exerciseAndHealth);
        assertNotNull(activityLevelSelector);
        assertNotNull(activityLevelSelectorSpinner);
        assertNotNull(exerciseTypeSelector);
        assertNotNull(exerciseTypeSelectorSpinner);
        assertNotNull(userAccountGoal);
        assertNotNull(userAccountGoalEditText);
        assertNotNull(deleteUserDetails);

        try {
            Thread.sleep(100000);
        } catch(InterruptedException e){
            e.printStackTrace();;
        }

    }


}