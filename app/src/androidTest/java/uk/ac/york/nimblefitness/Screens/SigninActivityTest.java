package uk.ac.york.nimblefitness.Screens;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.common.SignInButton;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class SigninActivityTest {

    public static final long CONVERT_FROM_NANO_TO_SECONDS = 1000000000;
    public static final String VALID_EMAIL_TO_BE_ENTERED = "test240@test.com";
    public static final String VALID_PASSWORD_TO_BE_ENTERED = "Password123";
    public static final String INVALID_EMAIL_TO_BE_ENTERED = "invalid@invalid.com";
    public static final String INVALID_PASSWORD_TO_BE_ENTERED = "invalidpassword";

    @Rule
    public ActivityTestRule<SigninActivity> signinActivityTestRule = new ActivityTestRule<>(SigninActivity.class);
    private SigninActivity mActivity;


    @Before
    public void setUp() throws Exception {
        mActivity = signinActivityTestRule.getActivity();
    }

    //test all visual elements are displayed
    @Test
    public void testLaunchSigninActivity() {

        ImageView signInHeaderImageView = mActivity.findViewById(R.id.signInHeaderImageView);
        TextView signInHeaderTextView = mActivity.findViewById(R.id.signInHeaderTextView);
        TextInputLayout SignInEmailLayout = mActivity.findViewById(R.id.SignInEmailLayout);
        TextInputEditText SignInEmail = mActivity.findViewById(R.id.SignInEmail);
        TextInputLayout SignInPasswordLayout = mActivity.findViewById(R.id.SignInPasswordLayout);
        TextInputEditText SignInPassword = mActivity.findViewById(R.id.SignInPassword);
        TextView forgotten_password = mActivity.findViewById(R.id.forgotten_password);
        ProgressBar signin_progress_circular = mActivity.findViewById(R.id.signin_progress_circular);
        Button sign_in_button = mActivity.findViewById(R.id.sign_in_button);
        SignInButton googleSignIn = mActivity.findViewById(R.id.googleSignIn);
        TextView new_member = mActivity.findViewById(R.id.new_member);


        assertNotNull(signInHeaderImageView);
        assertNotNull(signInHeaderTextView);
        assertNotNull(SignInEmailLayout);
        assertNotNull(SignInEmail);
        assertNotNull(SignInPasswordLayout);
        assertNotNull(SignInPassword);
        assertNotNull(forgotten_password);
        assertNotNull(signin_progress_circular);
        assertNotNull(sign_in_button);
        assertNotNull(googleSignIn);
        assertNotNull(new_member);
    }

    //Test case - sign in with an invalid email address and password
    @Test
    public void signInInvalidEmail(){
        onView(withId(R.id.SignInEmail))
                .perform(typeText(INVALID_EMAIL_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignInPassword))
                .perform(typeText(INVALID_PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.sign_in_button)).perform(click());
        TextInputLayout SignInEmailLayout = mActivity.findViewById(R.id.SignInEmailLayout);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNull("", SignInEmailLayout.getError());
    }

    //Test case - sign in with valid email and invalid password
    @Test
    public void signInInvalidPassword(){
        onView(withId(R.id.SignInEmail))
                .perform(typeText(VALID_EMAIL_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignInPassword))
                .perform(typeText(INVALID_PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.sign_in_button)).perform(click());
        TextInputLayout SignInPasswordLayout = mActivity.findViewById(R.id.SignInPasswordLayout);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Invalid Password must be more than 6 characters long with at least 1 lower case letter and at least 1 upper case letter and a number", SignInPasswordLayout.getError());
    }

    //Test case - new member takes to the sign up page successfully
    @Test
    public void goToSignUpPage(){
        init();
        onView(withId(R.id.new_member)).perform(click());
        long timeCheckStart = System.nanoTime();
        intended(hasComponent(SignupActivity.class.getName()));
        long timeCheckEnd = System.nanoTime();
        long timeCheck = timeCheckEnd - timeCheckStart;
        //Checks if the button press takes the user to the next class in under 2 seconds
        assertTrue((timeCheck/CONVERT_FROM_NANO_TO_SECONDS)<=2);
        release();
    }

    //Test case - sign in with valid email and valid password
    @Test
    public void signInCorrectDetails(){
        onView(withId(R.id.SignInEmail))
                .perform(typeText(VALID_EMAIL_TO_BE_ENTERED), closeSoftKeyboard());
        onView(withId(R.id.SignInPassword))
                .perform(typeText(VALID_PASSWORD_TO_BE_ENTERED), closeSoftKeyboard());
        init();
        onView(withId(R.id.sign_in_button)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(PaymentActivity.class.getName()));
        release();
    }




}