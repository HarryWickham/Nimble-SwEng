package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeType;
import uk.ac.york.nimblefitness.MediaHandlers.Images.ImageLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextModule;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.jupiter.api.Assertions.assertTrue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RoutineAndExerciseActivityTest {

    public static final long CONVERT_FROM_NANO_TO_SECONDS = 1000000000;

    public Routine getExampleRoutine(){
        ArrayList<Exercise> exercises = new ArrayList<>();
        ArrayList<ShapeType> shapeTypes = new ArrayList<>();

        shapeTypes.clear();
        shapeTypes.add(new ShapeType(25,237,600,1030,Color.parseColor("#303F9F"),"RECTANGLE",0)); //video box
        shapeTypes.add(new ShapeType(25,862,560,780,Color.parseColor("#303F9F"),"RECTANGLE",0)); //Image box

        ArrayList<TextLayout> textLayouts = new ArrayList<>();
        textLayouts.add(new TextLayout("Push Ups",
                TextModule.fontFamily.default_bold,
                Integer.toString(32),
                "#000000",
                50,
                50,
                null,
                null));
        textLayouts.add(new TextLayout("With your hands placed a shoulder width apart and a straight back, lower yourself to the ground keeping your elbows tucked in. Hold the position. Then push off of the floor to your start position to complete a rep.",
                TextModule.fontFamily.sans_serif,
                Integer.toString(13),
                "#000000",
                50,
                1512,
                null,
                null));

        exercises.add(new Exercise(new ImageLayout(50,
                887,
                726,
                550,
                0,
                "https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisemusclegroups/normal_push_up.png",
                null,
                null),
                shapeTypes,
                "Push Ups",
                "With your hands placed a shoulder width apart and a straight back, lower yourself to the ground keeping your elbows tucked in. Hold the position. Then push off of the floor to your start position to complete a rep.",
                "number",
                1,
                1,
                1,
                5,
                Color.parseColor("#008080"),
                new VideoLayout("https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Normal-Push-Up.mp4",
                        980, // The '-100' accounts for the rectangle 'border' behind the video.
                        550,
                        50,
                        262,
                        "",
                        0,
                        true,
                        null,
                        null),
                textLayouts,
                null));

        ArrayList<TextLayout> textLayouts2 = new ArrayList<>();
        textLayouts2.add(new TextLayout("Plank",
                TextModule.fontFamily.default_bold,
                Integer.toString(32),
                "#000000",
                50,
                50,
                null,
                null));
        textLayouts2.add(new TextLayout("From a normal push up position, lower yourself down so that your weight is resting on your forearms. With a straight back, hold this position by engaging your core muscles.",
                TextModule.fontFamily.sans_serif,
                Integer.toString(13),
                "#000000",
                50,
                1512,
                null,
                null));

        exercises.add(new Exercise(new ImageLayout(50,
                887,
                726,
                550,
                8,
                "https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisemusclegroups/plank.png",
                null,
                null),
                shapeTypes,
                "Plank",
                "From a normal push up position, lower yourself down so that your weight is resting on your forearms. With a straight back, hold this position by engaging your core muscles.",
                "time",
                1,
                5,
                1,
                5,
                Color.parseColor("#FF2400"),
                new VideoLayout("https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Plank.mp4",
                        980, // The '-100' accounts for the rectangle 'border' behind the video.
                        550,
                        50,
                        262,
                        "",
                        0,
                        true,
                        null,
                        null),
                textLayouts2,
                null));

        return new Routine("","Beginners Upper Body","This is a test routine to ensure all routine related pages take the correct information from this single object",3,2, 60, 2,0, exercises);
    }

    /**Instantiates the Routine and Exercise Activity so the fragments that are contained can be
     * tested. The fragments are linked as a chain which also follows the use case, so each page will
     * be checked for visual elements and functionality until the test routine is completed.
     */
    @Rule
    public ActivityTestRule<RoutineAndExerciseActivity> routineAndExerciseActivityRule = new ActivityTestRule<RoutineAndExerciseActivity>(RoutineAndExerciseActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, RoutineAndExerciseActivity.class);
            Exercise exercise = new Exercise();
            exercise.setExerciseName("fake");
            result.putExtra("routine", getExampleRoutine());
            result.putExtra("exercise", exercise);
            return result;
        }
        };

    //Verifies that all the visual elements in the start summary page fragment are displayed
    public void verifyStartSummaryFragmentContent(){
        //Visual elements check
        onView(withId(R.id.routine_summary)).check(matches(isDisplayed()));
        onView(withId(R.id.star_rating)).check(matches(isDisplayed()));
        onView(withId(R.id.sets)).check(matches(isDisplayed()));
        onView(withId(R.id.start_summary_list_view)).check(matches(isDisplayed()));
        onView(withId(R.id.toInfoPage)).check(matches(isDisplayed()));

        onView(withId(R.id.toInfoPage)).perform(click());//Info Page check

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**Verifies that all the visual elements in the information page fragment are displayed. Then
     * the subsequent page - the counter page is loaded using an onView click.
     */
    public void verifyInformationFragment(){
        //Visual elements check
        onView(withId(R.id.infoPage)).check(matches(isDisplayed()));
        onView(withId(R.id.toCounterPage)).check(matches(isDisplayed()));
        onView(withId(R.id.exit_to_menu)).check(matches(isDisplayed()));
        onView(withId(R.id.information_shape_view)).check(matches(isDisplayed()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Move to the next page - Counter page
        onView(withId(R.id.toCounterPage)).perform(click());//Counter Page navigation
    }

    /**
     * Verifies that all the visual elements in the counter page fragment, for rep-based exercises,
     * are displayed. The finish screen is loaded next automatically.
     */
    public void verifyRepCounterFragment(){
        //Visual element check
        onView(withId(R.id.StartButton)).check(matches(withText("Start Exercise")));
        onView(withId(R.id.CounterButtons)).check(matches(isDisplayed()));
        onView(withId(R.id.ExerciseTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.MuscleImage)).check(matches(isDisplayed()));

        //Counter started
        onView(withId(R.id.StartButton)).perform(click());
        onView(withId(R.id.PointsEarntOutput)).check(matches(isDisplayed()));
        onView(withId(R.id.TimeRemaining)).check(matches(isDisplayed()));
        onView(withId(R.id.RepCounter)).check(matches(isDisplayed()));
        onView(withId(R.id.Slidertitle)).check(matches(isDisplayed()));
        onView(withId(R.id.repTimeSlider)).check(matches(isDisplayed()));

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies that all the visual elements in the counter page fragment, for time-based exercises,
     * are displayed. The finish screen is loaded next automatically.
     */
    public void verifyTimeCounterFragment(){

        onView(withId(R.id.StartButton)).check(matches(withText("Start Exercise")));
        onView(withId(R.id.CounterButtons)).check(matches(isDisplayed()));
        onView(withId(R.id.ExerciseTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.MuscleImage)).check(matches(isDisplayed()));
        onView(withId(R.id.StartButton)).perform(click());
        onView(withId(R.id.PointsEarntOutput)).check(matches(isDisplayed()));
        onView(withId(R.id.TimeRemaining)).check(matches(isDisplayed()));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies that all the visual elements in the finish page fragment, for rep-based exercises,
     * are displayed. If there are more sets, the loop restarts. If not, the final summary
     * page is loaded automatically and the visual/functional elements are tested.
     */
    public void verifyFinishFragment(){
        //visual element check
        onView(withId(R.id.finish_text)).check(matches(isDisplayed()));
        onView(withId(R.id.remaining_exercises)).check(matches(isDisplayed()));
        onView(withId(R.id.finish_list_view)).check(matches(isDisplayed()));
        onView(withId(R.id.restText)).check(matches(isDisplayed()));
        onView(withId(R.id.finish_rest_remaining)).check(matches(isDisplayed()));
        onView(withId(R.id.continue_button)).check(matches(isDisplayed()));
        onView(withId(R.id.exit_button)).check(matches(isDisplayed()));

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
    * Test case - Goes through the user flow from the routine start summary page to the end summary
     * page. The previously defined functions are called to verify visual elements in each fragment
     * for each part of the process. The favourites button at the end summary page and the return
     * to main activity are also tested here.
     */
    @Test
    public void testRoutineFlow(){
        //Verifies the visual elements in the start summary
        verifyStartSummaryFragmentContent();

        //Exercise 1 - Push Ups Set 1
        verifyInformationFragment();
        verifyRepCounterFragment();
        //Moves to Finish Fragment automatically after set completion
        verifyFinishFragment();

        //Exercise 2 - Plank Set 2
        verifyInformationFragment();
        verifyTimeCounterFragment();
        //Moves to Finish Fragment automatically after set completion
        verifyFinishFragment();

        //Moves to Next Set
        onView(withId(R.id.continue_button)).perform(click());

        //Exercise 3 - Push Up Set 2
        verifyInformationFragment();
        verifyRepCounterFragment();
        //Moves to Finish Fragment automatically after set completion
        verifyFinishFragment();

        //Exercise 4 - Plank Set 2
        verifyInformationFragment();
        verifyTimeCounterFragment();

        //End Summary Page navigation is automatic

        //Testing adding to favourites button functionality, with text change
        onView(withId(R.id.favourite_button)).check(matches(withText("Add routine to favourites")));
        onView(withId(R.id.favourite_button)).perform(click());
        onView(withId(R.id.favourite_button)).check(matches(withText("Remove routine from favourites")));
        onView(withId(R.id.favourite_button)).perform(click());
        onView(withId(R.id.favourite_button)).check(matches(withText("Add routine to favourites")));

        //Test return to main activity in 2 seconds using Intents
        init();
        onView(withId(R.id.end_summary_home_button)).perform(click());

        long timeCheckStart = System.nanoTime();
        intended(hasComponent(MainActivity.class.getName()));
        long timeCheckEnd = System.nanoTime();
        long timeCheck = timeCheckEnd - timeCheckStart;

        //Checks if the button press takes the user to the next class in under 2 seconds
        assertTrue((timeCheck/CONVERT_FROM_NANO_TO_SECONDS)<=2);
        release();
    }

    //Testing that the user can return to the home page in 2 seconds from the info page
    @Test
    public void testReturnFromInfoPage(){
        init();
        onView(withId(R.id.toInfoPage)).perform(click());
        onView(withId(R.id.exit_to_menu)).perform(click());

        long timeCheckStart = System.nanoTime();
        intended(hasComponent(MainActivity.class.getName()));
        long timeCheckEnd = System.nanoTime();
        long timeCheck = timeCheckEnd - timeCheckStart;
        //Checks if the button press takes the user to the next class in under 2 seconds
        assertTrue((timeCheck/CONVERT_FROM_NANO_TO_SECONDS)<=2);
        release();
    }

    //Test return button from finish page only takes 2 seconds
    @Test 
    public void testReturnFromFinishPage(){
        //Goes to the finish page
        init();
        onView(withId(R.id.toInfoPage)).perform(click());
        onView(withId(R.id.toCounterPage)).perform(click());
        onView(withId(R.id.StartButton)).perform(click());
        //Wait for the finish page
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Return button
        onView(withId(R.id.exit_button)).perform(click());

        long timeCheckStart = System.nanoTime();
        intended(hasComponent(MainActivity.class.getName()));
        long timeCheckEnd = System.nanoTime();
        long timeCheck = timeCheckEnd - timeCheckStart;
        //Checks if the button press takes the user to the next class in under 2 seconds
        assertTrue((timeCheck/CONVERT_FROM_NANO_TO_SECONDS)<=2);
        release();
    }

    //Tests return button from counter page
    @Test
    public void testReturnFromCounterPage(){
        //Goes to the counter fragment
        onView(withId(R.id.toInfoPage)).perform(click());
        onView(withId(R.id.toCounterPage)).perform(click());
        //Return button
        onView(withId(R.id.ReturnButton)).perform(click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.infoPage)).check(matches(isDisplayed()));
    }

    //Test the Mute Button
    @Test
    public void testMuteButton(){
        //Goes to the counter fragment
        onView(withId(R.id.toInfoPage)).perform(click());
        onView(withId(R.id.toCounterPage)).perform(click());

        //Checks mute button text is correct
        onView(withId(R.id.MuteButton)).check(matches(withText("Mute")));
        //Clicks the mute button
        onView(withId(R.id.MuteButton)).perform(click());
        //Checks if the mute button text has changed correctly
        onView(withId(R.id.MuteButton)).check(matches(withText("Unmute")));
    }

    //Test Pause Button
    @Test
    public void testPauseButton(){
        //Goes to the counter fragment
        onView(withId(R.id.toInfoPage)).perform(click());
        onView(withId(R.id.toCounterPage)).perform(click());

        //Checks mute button text is correct
        onView(withId(R.id.PauseButton)).check(matches(withText("Pause")));
        //Start timer
        onView(withId(R.id.StartButton)).perform(click());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Clicks the mute button
        onView(withId(R.id.PauseButton)).perform(click());
        //Checks if the mute button text has changed correctly
        onView(withId(R.id.PauseButton)).check(matches(withText("Resume")));
    }

}
