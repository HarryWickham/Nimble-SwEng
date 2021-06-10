package uk.ac.york.nimblefitness.Screens.Profile.Calendar;

import android.content.Context;
import android.widget.ListView;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;

/**
 * This contract interface file allows the Model, View and Presenter for the Calendar tab of the
 * profile page to interact with each other. All the methods are overridden in their respective
 * classes.
 */
public interface CalendarContract {

    interface CalendarView {
        /**
         * Retrieves the calendar fragment's context.
         */
        Context getContext();
    }

    interface Model {
        /**
         * The default selected day is the current day which is displayed in text below the
         * calendar view in the Calendar tab in the profile page.
         */
        String currentDayNumber();

        /**
         * The earliest selectable date on the calendar is set by when the user signed up to the
         * app.
         */
        long userStartDate();

        /**
         * When the user selects a day on the calendar view, the date is displayed below the
         * calendar as text.
         */
        String selectedDay(int month, int dayOfMonth);

        /**
         * The current user's name is retrieved from the Firebase database to be displayed on the
         * Calendar tab of the profile page.
         */
        String currentUser();

        /**
         * The data for the moves the user needs to complete today is be retrieved from the
         * Firebase database and is used to populate this list in the Calendar tab.
         */
        MovesListAdapter completedMoves(Context context, String dayNumber, ListView listView);

        /**
         * The height of the list of completed exercises is set by how many items (children) it
         * contains.
         */
        void setListViewHeightBasedOnChildren(ListView listView);
    }

    interface Presenter {

        String displayCurrentDayNumber();

        /**
         * Sets the start date of selectable days on the calendar view.
         */
        long setStartDate();

        /**
         * The selected day on the calendar is displayed as text below it.
         */
        String displaySelectedDay(int month, int dayOfMonth);

        /**
         * Displays the user's name between the calendar and list of completed moves.
         */
        String displayUserName();

        /**
         * The list of completed moves is set with respect to the day they were completed on when
         * that day is selected in the calendar.
         */
        MovesListAdapter setCompletedMovesList(String dayNumber, ListView listView);

        /**
         * Sets the height of the list which contains the completed exercises.
         */
        void setListViewHeightBasedOnChildren(ListView listView);
    }
}