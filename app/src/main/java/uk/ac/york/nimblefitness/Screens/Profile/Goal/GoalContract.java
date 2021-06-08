package uk.ac.york.nimblefitness.Screens.Profile.Goal;

import android.content.Context;
import android.widget.ListView;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;

/** This contract interface file allows the Model, View and Presenter for the Goal tab of the
 * profile page to interact with each other. All the methods are overridden in their respective
 * classes.
 */
public interface GoalContract {

    interface GoalView {
        /** Retrieves the goal fragment's context. */
        Context getContext();
    }

    interface Model {
        /** A random motivational quote is chosen to be displayed on the Goal tab of the profile
         * page.
         */
        String motivationQuote();
        /** The current user's name is retrieved from the Firebase database to be displayed on the
         * Goal tab of the profile page.
         */
        String currentUser();
        /** This method will update the value of the gauge when the user has completed an exercise.
         * The user's number of completed moves is used in valueAdded.  All the values are retrieved
         * from user's firebase account.
         */
        int updateGauge(int currentValue, int valueAdded);
        /** The weekly goal set on sign up is used to set the gauge's end value. */
        int gaugeEndValue();
        /** The data for the moves the user needs to complete today will be retrieved from the
         * Firebase database and is used to populate this list in the Goal tab.
         */
        MovesListAdapter todaysMoves(Context context, ListView listView);
        /** The height of the list of completed exercises is set by how many items (children) it
         * contains.
         */
        void setListViewHeightBasedOnChildren (ListView listView);
    }

    interface Presenter {
        /** Displays the motivational quote on the screen. */
        String displayQuote();
        /** Display's the user's name on the screen. */
        String displayUserName();
        /** Displays the gauge values: current moves gained for the week, total goal amount of moves
         * set for the week.
         */
        int displayGaugeInfo();
        /** Sets the end value of the gauge which is the weekly goal set by the user. */
        int setGaugeEndValue();
        /** Displays the list of exercises the user has completed on the current day. */
        MovesListAdapter setTodaysMovesList(ListView listView);
        /** Sets the height of the list which contains the completed exercises. */
        void setListViewHeightBasedOnChildren (ListView listView);
    }
}