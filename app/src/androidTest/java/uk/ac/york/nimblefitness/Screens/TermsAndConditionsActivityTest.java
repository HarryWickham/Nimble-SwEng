package uk.ac.york.nimblefitness.Screens;

import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.york.nimblefitness.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TermsAndConditionsActivityTest {
    public static final long CONVERT_FROM_NANO_TO_SECONDS = 1000000000;

    @Rule
    public ActivityTestRule<TermsAndConditionsActivity> termsAndConditionsActivityTestRule = new ActivityTestRule<>(TermsAndConditionsActivity.class);

    private TermsAndConditionsActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity  = termsAndConditionsActivityTestRule.getActivity();
    }

    //Test all the visual elements are displayed
    @Test
    public void testLaunchTermsAndConditionsActivity(){
        Button AcceptTC = (Button) mActivity.findViewById(R.id.acceptTC);
        TextView TCText = (TextView) mActivity.findViewById(R.id.TCText);
        ScrollView TCScroll = (ScrollView) mActivity.findViewById(R.id.TCScroll);

        assertNotNull(AcceptTC);
        assertNotNull(TCText);
    }

    //Test - The T&C acceptance takes the user to the payment page
    @Test
    public void acceptConditions(){
        init();
        onView(withId(R.id.acceptTC)).perform(ViewActions.click());
        long timeCheckStart = System.nanoTime();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(PaymentActivity.class.getName()));
        long timeCheckEnd = System.nanoTime();
        long timeCheck = timeCheckEnd - timeCheckStart;
        //Checks if the button press takes the user to the next class in under 2 seconds
        assertTrue((timeCheck/CONVERT_FROM_NANO_TO_SECONDS)<=2);
        release();

    }



}