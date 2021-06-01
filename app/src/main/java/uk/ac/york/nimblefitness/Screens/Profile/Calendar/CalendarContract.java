package uk.ac.york.nimblefitness.Screens.Profile.Calendar;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;

/*
 This contract interface file allows the Model, View and Presenter for the Calendar tab of the
 profile page to interact with each other.
*/
public interface CalendarContract {

    interface CalendarView {
        Context getContext();
    }

    interface Model {
        String currentDayNumber();
        long userStartDate();
        String selectedDay(int month, int dayOfMonth);
        String currentUser();
        MovesListAdapter completedMoves(Context context);
        void setListViewHeightBasedOnChildren (ListView listView);
    }

    interface Presenter {
        String displayCurrentDayNumber();
        long setStartDate();
        String displaySelectedDay(int month, int dayOfMonth);
        String displayUserName();
        MovesListAdapter setCompletedMovesList();
        void setListViewHeightBasedOnChildren (ListView listView);
    }
}
