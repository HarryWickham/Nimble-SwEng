package uk.ac.york.nimblefitness.Screens.Profile.Goal;

import android.content.Context;
import android.content.SharedPreferences;
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
import uk.ac.york.nimblefitness.HelperClasses.SavableExercise;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * This class handles the logic and database aspects of the Goal tab in the profile page of the
 * app.
 */

public class GoalModel implements GoalContract.Model {
    /**
     * List of quotes which may appear on the 'goals' tab of the profile page.
     */
    private final String[] quotesList = {"You can do it!", "Feel the burn.", "Time to make some " +
            "gains.", "Time to exercise!"};
    MovesListAdapter listAdapter;
    private final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    /**
     * This method will update the value of the gauge when the user has completed an exercise. The
     * user's number of completed moves is used in valueAdded.  All the values are retrieved from
     * user's firebase account.
     */
    @Override
    public int updateGauge(int currentValue, int valueAdded) {
        return currentValue + valueAdded;
    }

    /**
     * The weekly goal set on sign up is used to set the gauge's end value.
     */
    @Override
    public int gaugeEndValue() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        return prefs.getInt(currentFirebaseUser + "weeklyGoal", 0);
    }

    /**
     * A random motivational quote is chosen to be displayed on the Goal tab of the profile page.
     */
    @Override
    public String motivationQuote() {
        String default_quote = "Improve your health today!";
        String quote = default_quote;
        /* The quote changes each time the user opens the goal tab. */
        Random rand = new Random();
        int n = rand.nextInt(quotesList.length);
        /* This 'if' statement always ensures a quote is shown. */
        if ((n >= 0) && (n < quotesList.length)) {
            quote = quotesList[n];
        } else {
            quote = default_quote;
        }
        return quote;
    }

    /**
     * The current user's name is retrieved from the Firebase database to be displayed on the Goal
     * tab of the profile page.
     */
    @Override
    public String currentUser() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        return prefs.getString(currentFirebaseUser + "userFullName", "Error Getting Name");
    }

    /**
     * The data for the moves the user needs to complete today will be retrieved from the Firebase
     * database and is used to populate this list in the Goal tab.
     */
    @Override
    public MovesListAdapter todaysMoves(Context context, ListView listView) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        /* The relevant user's completed exercises for the current day is retrieved from the
          database.
         */
        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        DatabaseReference completedExercisesRootReference = rootDatabase.
                getReference("users").child(currentFirebaseUser.getUid()).child("exercises");
        /* The completed exercises displayed in the list changes when the date rolls over and is
          retrieved from the firebase database.
         */
        completedExercisesRootReference.child(currentDayNumber()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (ds.getValue(SavableExercise.class) != null) {
                                SavableExercise exercise = ds.getValue(SavableExercise.class);
                                Exercise exercise1 = new Exercise();
                                assert exercise != null;
                                exercise1.setExerciseName(exercise.getExerciseName());
                                exercise1.setColour(exercise.getColour());
                                exercise1.setMovesPerRep(exercise.getMovesPerRep());
                                exercise1.setReps(exercise.getReps());
                                exercise1.setRepType(exercise.getRepType());
                                exercises.add(exercise1);
                            }
                        }
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

    /**
     * The height of the list of completed exercises is set by how many items (children) it
     * contains.
     */
    @Override
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /**
     * These three methods (currentDayNumber, monthText & datePrefix) set the string which
     * represent's today's date which is used by firebase to retrieve the exercises completed so far
     * today.
     */
    public String currentDayNumber() {
        SimpleDateFormat month = new SimpleDateFormat("M", Locale.UK);
        SimpleDateFormat day = new SimpleDateFormat("d", Locale.UK);
        Date currentTime = Calendar.getInstance().getTime();
        String monthString = month.format(currentTime);
        String dayString = day.format(currentTime);
        return String.format("%s %s%s", monthText(Integer.parseInt(monthString)), dayString,
                datePrefix(Integer.parseInt(dayString)));
    }

    public String monthText(int month) {
        switch (month) {
            case 1:
                return ("January");
            case 2:
                return ("February");
            case 3:
                return ("March");
            case 4:
                return ("April");
            case 5:
                return ("May");
            case 6:
                return ("June");
            case 7:
                return ("July");
            case 8:
                return ("August");
            case 9:
                return ("September");
            case 10:
                return ("October");
            case 11:
                return ("November");
            case 12:
                return ("December");
            default:
                return ("error");
        }
    }

    public String datePrefix(int dayOfMonth) {
        switch (dayOfMonth) {
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
            default:
                return ("th");
        }
    }
}