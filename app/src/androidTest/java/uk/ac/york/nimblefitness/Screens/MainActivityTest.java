package uk.ac.york.nimblefitness.Screens;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.york.nimblefitness.R;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public static final long CONVERT_FROM_NANO_TO_SECONDS = 1000000000;

    //String array containing all the exercises that are included in the app
    String[] exercises = {"Push Up", "Wide Push Up", "Closed Push Up", "Spiderman Push Up",
            "Tricep Dip", "Plank", "Side Plank Kicks", "Flutter Kicks", "Cross Kicks",
            "Russian Twists", "Ankle Taps", "Sit Ups", "Superman Raises", "Body Weight Squats",
            "Lunges", "Calf Raises", "Hip Thrusters", "Side Plank Kicks", "Burpees",  "Step Up-Downs"};
    //String array that contains all the routine names
    String[] routines = {"Beginner Upper Body", "Beginner Lower Body", "Beginner Core",
            "Easy Upper Body", "Easy Lower Body", "Easy Core", "Intermediate Upper Body", "Intermediate Lower Body",
            "Intermediate Core", "Hard Upper Body", "Hard Lower Body", "Hard Core"};


    //Instantiates the main activity so the fragments can be tested
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, MainActivity.class);
            return result;
        }
    };

    //Checks the profile page is displayed
    public void verifyProfileTab(){
        //Clicks on the profile page tab
        onView(withId(R.id.profile_page)).perform(click());

        //Asserts that the page is displayed using view elements
        onView(withId(R.id.profile_tabs)).check(matches(isDisplayed()));

    }

    //Checks the routines page is displayed
    public void verifyRoutinesTab(){
        //Clicks on the routines page tab
        onView(withId(R.id.routines_page)).perform(click());

        //Asserts that the page is displayed using view elements
        onView(withId(R.id.routine_exp_list)).check(matches(isDisplayed()));
        onView(withId(R.id.routine_search)).check(matches(isDisplayed()));

    }

    //Checks the exercises page is displayed
    public void verifyExercisesTab(){
        //Clicks the exercise page tab
        onView(withId(R.id.exercises_page)).perform(click());

        //Asserts that the page is displayed using view elements
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        onView(withId(R.id.search)).check(matches(isDisplayed()));
    }

    //Checks the settings page is displayed
    public void verifySettingsTab(){
        //Clicks the settings page tab
        onView(withId(R.id.settings_page)).perform(click());

        //Asserts that the page is displayed using view elements
        onView(withId(R.id.settings_list)).check(matches(isDisplayed()));
    }

    /**
    *Test case - checks that the bottom navigation bar in the main activity works using the
    * functions that verify the corresponding visual elements for each tab page
    */
    @Test
    public void testMainActivityBottomNav(){
        //Verifies that the main activity has been instantiated and the view is displayed
        onView(withId(R.id.main_frame)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));

        //Verifies the tab elements take the user to the corresponding page
        verifyProfileTab();
        verifyRoutinesTab();
        verifyExercisesTab();
        verifySettingsTab();

    }

    /**
     * Using the string array of all listed exercise in the app, a for loop traverses through all
     * possible valid search entries and then one invalid search entry is used to verify that the
     * correct result is displayed.
     */
    @Test
    public void testExerciseSearch(){
        //Takes the user to the exercise page
        onView(withId(R.id.exercises_page)).perform(click());

        //Searches through all the listed exercises
        for(String exercise: exercises) {
            onView(withId(R.id.search)).perform(typeText(exercise));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onData(is(exercise)).inAdapterView(withId(R.layout.fragment_exercises));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Clears the search bar
            for (int i = 0; i <= exercise.length(); i++) {
                onView(withId(R.id.search)).perform(pressKey(KeyEvent.KEYCODE_DEL));
            }
        }

        //Check an invalid exercise name
        onView(withId(R.id.search)).perform(typeText("Invalid Exercise"));
        onView(withId(R.id.nothingfoundmessage)).check(matches(isDisplayed()));
    }

    /**
     * Using the string array of all listed routines in the app, a for loop traverses through all
     * possible valid search entries and then one invalid search entry is used to verify the search
     * functionality
     */
    @Test
    public void testRoutineSearch(){
        //Takes the user to the routine page
        onView(withId(R.id.routines_page)).perform(click());

        //Searches through all listed routines
        for(String routine: routines) {
            onView(withId(R.id.routine_search)).perform(typeText(routine));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onData(is(routine)).inAdapterView(withId(R.layout.fragment_routines));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Clears search bar
            for (int i = 0; i <= routine.length(); i++) {
                onView(withId(R.id.routine_search)).perform(pressKey(KeyEvent.KEYCODE_DEL));

            }
        }

        //Check an invalid exercise name
        onView(withId(R.id.routine_search)).perform(typeText("Invalid Routine"));
        onView(withId(R.id.nothing_found_routines)).check(matches(isDisplayed()));
    }

    //Tests that all the view elements in the settings tab is displayed
    @Test
    public void testSettingsTabContent(){
        //Takes the user to the settings page
        onView(withId(R.id.settings_page)).perform(click());

        //Asserts that all the relevants elements are displayed
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(0)
                .check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(1)
                .check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(3)
                .check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(4)
                .check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(5)
                .check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(6)
                .check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //Tests that all the view elements in the user details page is displayed
    @Test
    public void testUserDetails(){
        //Takes the user to the settings page and clicks the 1st list element
        onView(withId(R.id.settings_page)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(0)
                .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Checks if all fields are there
        onData(is("Personal Information")).inAdapterView(withId(R.layout.activity_user_details));
        onData(is("First Name")).inAdapterView(withId(R.layout.activity_user_details));
        onData(is("Last Name")).inAdapterView(withId(R.layout.activity_user_details));
        onData(is("Age")).inAdapterView(withId(R.layout.activity_user_details));
        onData(is("Please select your gender")).inAdapterView(withId(R.layout.activity_user_details));
        onData(is("Exercise and Health")).inAdapterView(withId(R.layout.activity_user_details));
        onData(is("How long do you exercise a week?")).inAdapterView(withId(R.layout.activity_user_details));
        onData(is("What type of exercise do you do?")).inAdapterView(withId(R.layout.activity_user_details));
        onData(is("Suggested Weekly Goal")).inAdapterView(withId(R.layout.activity_user_details));
        onData(is("Save")).inAdapterView(withId(R.layout.activity_user_details));
        onData(is("Delete Account")).inAdapterView(withId(R.layout.activity_user_details));
    }

    //Tests that all the view elements in the membership page is displayed
    @Test
    public void testMembershipPlan(){
        //Takes the user to the settings page and clicks the 2nd list element
        onView(withId(R.id.settings_page)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(1)
                .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Checks if all fields are there
        onData(is("More Details")).inAdapterView(withId(R.layout.activity_payment));
    }

    //Tests that all the view elements in the billing page is displayed
    @Test
    public void testBilling(){
        //takes the user to the settings page and clicks the 3rd element
        onView(withId(R.id.settings_page)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(2)
                .perform(click());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //The emulator is used to check if the google play store app is launched
    }

    //Tests that all the view elements in the report a problem page is displayed
    @Test
    public void testReportProblem(){
        //takes the user to the settings page and clicks the 4th element
        onView(withId(R.id.settings_page)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(3)
                .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //The emulator is used to check if the browser is opened to the relevant form.

    }

    //Tests that all the view elements in the terms and conditions page is displayed
    @Test
    public void testTermsAndConditions(){
        //Takes the user to the settings page and clicks the 5th element
        onView(withId(R.id.settings_page)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(4)
                .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Asserts that the T&C text is displayed
        onData(is("These rules and regulations are regarding and surrounding")).inAdapterView(withId(R.layout.activity_terms_and_conditions));

    }

    //Tests that all the view elements in the load new presentation page is displayed
    @Test
    public void testLoadNewPresentation(){
        //takes the user to the settings page and clicks the 7th element
        onView(withId(R.id.settings_page)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(6)
                .perform(click());

        onData(is("")).inAdapterView(withId(R.layout.activity_handler_test));

    }


    //Tests that the log out button works
    @Test
    public void testLogOut(){
        //takes the user to the settings page and clicks the 6th element
        onView(withId(R.id.settings_page)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Intent used to confirm that the sign in activity starts after the user is logged out
        init();
        onData(anything()).inAdapterView(withId(R.id.settings_list)).atPosition(5)
                .perform(click());

        intended(hasComponent(SigninActivity.class.getName()));
    }


}