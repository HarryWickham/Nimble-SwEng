package uk.ac.york.nimblefitness.Screens;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
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
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupActivityTest {

    public static final long CONVERT_FROM_NANO_TO_SECONDS = 1000000000;
    public static final String EXISTING_EMAIL_TO_BE_ENTERED = "Email@email.com";
    public static final String PASSWORD_TO_BE_ENTERED = "Password123";
    public static final String ERROR_PASSWORD_TO_BE_ENTERED = "Password113";
    public static final String SHORT_PASSWORD_TO_BE_ENTERED = "Pass1";
    public static final String WEAK_PASSWORD_TO_BE_ENTERED = "passwordone";


    public static int randomPasswordAddend = (int)Math.floor(Math.random()*(1000+1));
    public static final String RANDOM_EMAIL_TO_BE_ENTERED = "test"+ String.valueOf(randomPasswordAddend) + "@test.com";

    @Rule
    public ActivityTestRule<SignupActivity> signUpActivityTestRule = new ActivityTestRule<>(SignupActivity.class);

    private SignupActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity  = signUpActivityTestRule.getActivity();
    }

    //Test all visual elements are displayed
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
    //Test case - sign up with pre-existing email address
    @Test
    public void signUpExistingEmail(){
        onView(withId(R.id.SignUpEmail))
                .perform(typeText(EXISTING_EMAIL_TO_BE_ENTERED), closeSoftKeyboard());
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

    //Test case - sign up with non-matching passwords
    @Test
    public void signUpPassError(){
        onView(withId(R.id.SignUpEmail))
                .perform(typeText(RANDOM_EMAIL_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignUpPassword))
                .perform(typeText(PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignUpPasswordConfirm))
                .perform(typeText(ERROR_PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.sign_up_button)).perform(click());
        TextInputLayout SignUpPasswordConfirmLayout = mActivity.findViewById(R.id.SignUpPasswordConfirmLayout);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Confirm Password must be the same as Password", SignUpPasswordConfirmLayout.getError());
    }
    //Test case - weak password, too short
    @Test
    public void signUpShortPass(){
        onView(withId(R.id.SignUpEmail))
                .perform(typeText(RANDOM_EMAIL_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignUpPassword))
                .perform(typeText(SHORT_PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignUpPasswordConfirm))
                .perform(typeText(SHORT_PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.sign_up_button)).perform(click());
        TextInputLayout SignUpPasswordConfirmLayout = mActivity.findViewById(R.id.SignUpPasswordConfirmLayout);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNull("", SignUpPasswordConfirmLayout.getError());

    }

    //Test case - weak password, no capitals or numbers
    @Test
    public void signUpWeakPass(){
        onView(withId(R.id.SignUpEmail))
                .perform(typeText(RANDOM_EMAIL_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignUpPassword))
                .perform(typeText(WEAK_PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignUpPasswordConfirm))
                .perform(typeText(WEAK_PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.sign_up_button)).perform(click());
        TextInputLayout SignUpPasswordConfirmLayout = mActivity.findViewById(R.id.SignUpPasswordConfirmLayout);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNull("", SignUpPasswordConfirmLayout.getError());

    }

    //Test case - go to sign in page for existing user
    @Test
    public void goToSignInPage(){
        init();
        onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click());
        long timeCheckStart = System.nanoTime();
        intended(hasComponent(SigninActivity.class.getName()));
        long timeCheckEnd = System.nanoTime();
        long timeCheck = timeCheckEnd - timeCheckStart;
        //Checks if the button press takes the user to the next class in under 2 seconds
        assertTrue((timeCheck/CONVERT_FROM_NANO_TO_SECONDS)<=2);
        release();
    }

    //Test case - sign up with new email address
    @Test
    public void signUpNewEmail(){
        onView(withId(R.id.SignUpEmail))
                .perform(typeText(RANDOM_EMAIL_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignUpPassword))
                .perform(typeText(PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignUpPasswordConfirm))
                .perform(typeText(PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());

        onView(withId(R.id.sign_up_button)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextInputLayout SignUpEmailLayout = mActivity.findViewById(R.id.SignUpEmailLayout);

        assertNull("Success", SignUpEmailLayout.getError());
    }




}