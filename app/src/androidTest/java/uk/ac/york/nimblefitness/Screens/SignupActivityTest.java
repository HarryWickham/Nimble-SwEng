package uk.ac.york.nimblefitness.Screens;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.york.nimblefitness.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupActivityTest {

    public static final String EMAIL_TO_BE_ENTERED = "Email@email.com";
    public static final String PASSWORD_TO_BE_ENTERED = "Password123";

    @Rule
    public ActivityTestRule<SignupActivity> signUpActivityTestRule = new ActivityTestRule<>(SignupActivity.class);

    private SignupActivity mActivity;

    @Before
    public void seUp() throws Exception {
        mActivity  = signUpActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchSignupActivity() {
        ImageView signUpHeaderImageView = mActivity.findViewById(R.id.signUpHeaderImageView);
        TextView signUpHeaderTextView = mActivity.findViewById(R.id.signUpHeaderTextView);
        TextInputLayout SignUpEmailLayout = mActivity.findViewById(R.id.SignUpEmailLayout);
        TextInputEditText SignUpEmail = mActivity.findViewById(R.id.SignUpEmail);
        TextInputLayout SignUpPasswordLayout = mActivity.findViewById(R.id.SignUpPasswordLayout);
        TextInputEditText SignUpPassword = mActivity.findViewById(R.id.SignUpPassword);
        TextInputLayout SignUpPasswordConfirmLayout = mActivity.findViewById(R.id.SignUpPasswordConfirmLayout);
        TextInputEditText SignUpPasswordConfirm = mActivity.findViewById(R.id.SignUpPasswordConfirm);
        ProgressBar signup_progress_circular = mActivity.findViewById(R.id.signup_progress_circular);
        Button sign_up_button = mActivity.findViewById(R.id.sign_up_button);
        TextView login_button = mActivity.findViewById(R.id.login_button);

        assertNotNull(signUpHeaderImageView);
        assertNotNull(signUpHeaderTextView);
        assertNotNull(SignUpEmailLayout);
        assertNotNull(SignUpEmail);
        assertNotNull(SignUpPasswordLayout);
        assertNotNull(SignUpPassword);
        assertNotNull(SignUpPasswordConfirmLayout);
        assertNotNull(SignUpPasswordConfirm);
        assertNotNull(signup_progress_circular);
        assertNotNull(sign_up_button);
        assertNotNull(login_button);
    }

    @Test
    public void SignUp(){
        onView(withId(R.id.SignUpEmail))
                .perform(typeText(EMAIL_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignUpPassword))
                .perform(typeText(PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignUpPasswordConfirm))
                .perform(typeText(PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.sign_up_button)).perform(click());
        TextInputLayout SignUpEmailLayout = mActivity.findViewById(R.id.SignUpEmailLayout);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("The email address is already in use by another account.", SignUpEmailLayout.getError());
    }

    /*@Test
    public void moveToSignIn(){
        intentsTestRule.getActivity().onClickGoToLogin(null);
        onView(withId(R.id.login_button)).perform(click());
        // Espresso will have recorded the intent being fired - now use the intents
        // API to assert that the expected intent was launched...
        Intents.intended(hasComponent(SigninActivity.class.getName()));
    }*/

}