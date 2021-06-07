package uk.ac.york.nimblefitness.Screens.Profile.Goal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.HelperClasses.SavableExercise;
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
    MovesListAdapter listAdapter;
    private FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

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
    public MovesListAdapter todaysMoves(Context context, ListView listView) {
        ArrayList<Exercise> exercises = new ArrayList<>();

        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        DatabaseReference completedExercisesRootReference = rootDatabase.getReference("users").child(currentFirebaseUser.getUid()).child("exercises");

        completedExercisesRootReference.child(currentDayNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Retrieve", "onDataChange");
                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.i("Retrieve", "DataSnapshot");
                    if (ds.getValue(SavableExercise.class) != null) {
                        Log.i("Retrieve", "ds.getValue(Exercise.class)");
                        SavableExercise exercise = ds.getValue(SavableExercise.class);
                        Exercise exercise1 = new Exercise();
                        exercise1.setExerciseName(exercise.getExerciseName());
                        exercise1.setColour(exercise.getColour());
                        exercise1.setMovesPerRep(exercise.getMovesPerRep());
                        exercise1.setReps(exercise.getReps());
                        exercise1.setRepType(exercise.getRepType());
                        exercises.add(exercise1);
                    }
                }
                Log.i("onDataChange", "notify dataset changed");
                listAdapter = new MovesListAdapter(context, exercises);
                listView.setAdapter(listAdapter);
                setListViewHeightBasedOnChildren(listView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        listAdapter = new MovesListAdapter(context, exercises);
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

    //  These three methods (currentDayNumber, monthText & datePrefix) set the string which
    //  represent's today's date which is used by firebase to retrieve the exercises completed so
    //  far today.
    public String currentDayNumber() {
        SimpleDateFormat month = new SimpleDateFormat("M", Locale.UK);
        SimpleDateFormat day = new SimpleDateFormat("d", Locale.UK);
        Date currentTime = Calendar.getInstance().getTime();
        String monthString = month.format(currentTime);
        String dayString = day.format(currentTime);
        String currentDayNumber = String.format("%s %s%s", monthText(Integer.parseInt(monthString)),
                dayString, datePrefix(Integer.parseInt(dayString)));
        return currentDayNumber;
    }

    public String monthText(int month){
        switch (month){
            case 1: return("January");
            case 2: return("February");
            case 3: return("March");
            case 4: return("April");
            case 5: return("May");
            case 6: return("June");
            case 7: return("July");
            case 8: return("August");
            case 9: return("September");
            case 10: return("October");
            case 11: return("November");
            case 12: return("December");
            default: return("error");
        }
    }

    public String datePrefix(int dayOfMonth){
        switch (dayOfMonth){
            case 1:
            case 21:
            case 31:
                return ("st");
            case 2:
            case 22:
                return ("nd");
            case 3:
            case 23:
                return ("rd");
            default: return ("th");
        }
    }
}