package uk.ac.york.nimblefitness.Screens.Profile.Goal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

/*
 This class handles the logic and database aspects of the Goal tab in the profile page of the app.
 */

public class GoalModel implements GoalContract.Model{
    // List of quotes which may appear on the 'goals' tab of the profile page.
    private final String[] quotesList = {"You can do it!",
            "Feel the burn.",
            "Time to make some gains.",
            "Time to exercise!"};
    private String userName;

    /*
     This method will update the value of the gauge when the user has completed a move/
     exercise. This will eventually be coded such that the user's number of completed moves is used
     in valueAdded and the moves to complete for a week/month is used in setEndValue.
    */
    @Override
    public int updateGauge(int currentValue, int valueAdded) {
        return currentValue + valueAdded;
    }

    @Override
    public int gaugeEndValue() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        Log.i("gaugeEndValue", String.valueOf(prefs.getInt(currentFirebaseUser+"weeklyGoal", 0)));
        return prefs.getInt(currentFirebaseUser+"weeklyGoal", 0);
    }
    // A random motivational quote is chosen to be displayed on the Goal tab of the profile page.
    @Override
    public String motivationQuote() {
        String default_quote = "Improve your health today!";
        String quote = default_quote;
        // The quote changes each time the user opens the goal tab.
        Random rand = new Random();
        int n = rand.nextInt(quotesList.length);
        // This 'if' statement always ensures a quote is shown.
        if ((n >= 0) && (n < quotesList.length)) {
            quote = quotesList[n];
        } else {
            quote = default_quote;
        }
        return quote;
    }
    // The current user's name is retrieved from the Firebase database to be displayed on the Goal
    // tab of the profile page.
    @Override
    public String currentUser() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        userName = prefs.getString(currentFirebaseUser+"userFullName", "Error Getting Name");
        return userName;
    }
    // The data for the moves the user needs to complete today will be retrieved from the Firebase
    // database and is used to populate this list in the Goal tab.
    @Override
    public MovesListAdapter todaysMoves(Context context) {
        MovesListAdapter listAdapter;
        String[] movesToDo = {"Plank", "Squats", "Sit-ups", "Press-ups"};
        String[] moveDetails = {"3 sets of 1 minute", "5 sets of 3 reps",
                                "5 sets of 3 reps", "7 sets of 5 reps"};
        String[] numberOfMoves = {"Moves: 3", "Moves: 15", "Moves: 15", "Moves: 35"};
        int[] exerciseIcon = {R.drawable.ic_baseline_accessibility_24,
                                R.drawable.ic_baseline_accessibility_24,
                                R.drawable.ic_baseline_accessibility_24,
                                R.drawable.ic_baseline_accessibility_24};

        Routine routine = new Routine().getExampleRoutine();
        listAdapter = new MovesListAdapter(context, routine.getExerciseArrayList());
        return listAdapter;
    }

    @Override
    public void setListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
