package uk.ac.york.nimblefitness.Screens;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.york.nimblefitness.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OnBoardingActivityTest {

    public static final long CONVERT_FROM_NANO_TO_SECONDS = 1000000000;

    //Instantiates the relevant activity
    @Rule
    public ActivityTestRule<OnBoardingActivity> OnBoardingActivityTestRule = new ActivityTestRule(OnBoardingActivity.class);

    private OnBoardingActivity mActivity;

    @Before
    public void setUp() throws Exception{
        mActivity = OnBoardingActivityTestRule.getActivity();
    }

    //Tests to see if all visual elements are instantiated
    @Test
    public void testOnBoardingActivity(){
        LinearLayout container = (LinearLayout) mActivity.findViewById(R.id.container);
        AppCompatButton skipButton = (AppCompatButton) mActivity.findViewById(R.id.skip_button);
        AppCompatButton getStartedButton = (AppCompatButton) mActivity.findViewById(R.id.getStarted_button);
        ImageView image = (ImageView) mActivity.findViewById(R.id.image);
        TextView title = (TextView) mActivity.findViewById(R.id.title);
        TextView description = (TextView) mActivity.findViewById(R.id.description);

        //Checks using the xml id's
        assertNotNull(container);
        assertNotNull(skipButton);
        assertNotNull(getStartedButton);
        assertNotNull(image);
        assertNotNull(title);
        assertNotNull(description);

        //Checks the list items within the xml ids - swiping left after each check to move to the next image.
        onView(withId(R.id.title)).check(matches(withText("Calendar Tracker")));
        onView(withId(R.id.description)).check(matches(withText("Automatically track your progress and view anytime")));
        onView(withId(R.id.container)).perform(swipeLeft());

        onView(withId(R.id.title)).check(matches(withText("Choose a Routine")));
        onView(withId(R.id.description)).check(matches(withText("All our routines are created by industry professionals")));
        onView(withId(R.id.container)).perform(swipeLeft());

        onView(withId(R.id.title)).check(matches(withText("Set a Goal")));
        onView(withId(R.id.description)).check(matches(withText("Set a monthly goal to ensure you stay on track")));
        onView(withId(R.id.container)).perform(swipeLeft());

        onView(withId(R.id.title)).check(matches(withText("Leaderboard")));
        onView(withId(R.id.description)).check(matches(withText("Share your progress with others on our live leaderboard")));

    }

    //Test to see if skip button works, intent checks if main activity is opened within the required time
    @Test
    public void testSkipButton(){
        init();
        onView(withId(R.id.skip_button)).perform(click());
        long timeCheckStart = System.nanoTime();
        intended(hasComponent(MainActivity.class.getName()));
        long timeCheckEnd = System.nanoTime();
        long timeCheck = timeCheckEnd - timeCheckStart;
        //Checks if the button press takes the user to the next class in under 2 seconds
        assertTrue((timeCheck/CONVERT_FROM_NANO_TO_SECONDS)<=2);
        release();

    }
}