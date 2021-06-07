package uk.ac.york.nimblefitness.Screens.Profile.Goal;

import android.content.Context;
import android.widget.ListView;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;

/*
 This contract interface file allows the Model, View and Presenter for the Goal tab of the profile
 page to interact with each other.
*/
public interface GoalContract {

    interface GoalView {
        Context getContext();
    }

    interface Model {
        String motivationQuote();
        String currentUser();
        int updateGauge(int currentValue, int valueAdded);
        int gaugeEndValue();
        MovesListAdapter todaysMoves(Context context, ListView listView);
        void setListViewHeightBasedOnChildren (ListView listView);
    }

    interface Presenter {
        String displayQuote();
        String displayUserName();
        int displayGaugeInfo();
        int setGaugeEndValue();
        MovesListAdapter setTodaysMovesList(ListView listView);
        void setListViewHeightBasedOnChildren (ListView listView);
    }
}
