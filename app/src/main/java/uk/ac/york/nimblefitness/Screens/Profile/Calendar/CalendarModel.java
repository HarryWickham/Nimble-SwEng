package uk.ac.york.nimblefitness.Screens.Profile.Calendar;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.MediaHandlers.Images.ImageLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

public class CalendarModel implements CalendarContract.Model {

    private static final String TAG = "log";
    private String userName;
    private FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference rootReference = rootDatabase.getReference("users");
    private FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    // This method sets the displayed text for the corresponding month.
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

    // This method is used to set which prefix is displayed after the date number.
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

    // The default selected day is the current day which is displayed in text below the calendar
    // view in the Calendar tab in the profile page.
    @Override
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

    // The earliest selectable date on the calendar is set by when the user signed up to the app.
    @Override
    public long userStartDate() {
        long startDate = 0;
        if (currentFirebaseUser != null) {
            startDate = currentFirebaseUser.getMetadata().getCreationTimestamp();
        }
        return startDate;
    }

    // When the user selects a day on the calendar view, the date is displayed below the calendar as
    // text.
    @Override
    public String selectedDay(int month, int dayOfMonth) {
        String dayNumber = String.format(Locale.UK, "%s %d%s",
                monthText(month + 1), dayOfMonth, datePrefix(dayOfMonth));
        return dayNumber;
    }

    // The current user's name is retrieved from the Firebase database to be displayed on the
    // Calendar tab of the profile page.
    @Override
    public String currentUser() {
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        userName = prefs.getString(currentFirebaseUser+"userFullName", "Error Getting Name");
        Log.i(TAG, " Full name : "+userName);
        return userName;
    }

    // The data for the moves the user needs to complete today will be retrieved from the Firebase
    // database and is used to populate this list in the Calendar tab.
    @Override
    public MovesListAdapter completedMoves(Context context) {
        MovesListAdapter listAdapter;
        String[] completedMoves = {"Press-ups", "Sit-ups", "Plank", "Crunches"};
        String[] moveDetails = {"5 sets of 10 reps", "5 sets of 30 reps",
                                "5 sets of 1 minute", "5 sets of 30 reps"};
        String[] numberOfMoves = {"Moves: 50", "Moves: 150", "Moves: 100", "Moves: 150"};
        int[] exerciseIcon = {R.drawable.ic_baseline_accessibility_24,
                R.drawable.ic_baseline_accessibility_24,
                R.drawable.ic_baseline_accessibility_24,
                R.drawable.ic_baseline_accessibility_24};
        ArrayList<Exercise> exercises = new ArrayList<>();
        ImageLayout muscleGroupImage = null;
        TextLayout exerciseNameLayout = null;
        TextLayout exerciseDescriptionLayout = null;
        VideoLayout exerciseVideoLayout = null;
        exercises.add(new Exercise(muscleGroupImage,"","Plank",0,60,1, Color.parseColor("#ffffff"), exerciseVideoLayout , exerciseNameLayout, exerciseDescriptionLayout, 0));
        exercises.add(new Exercise(muscleGroupImage,"","Squats",0,20,1,Color.parseColor("#ffffff"), exerciseVideoLayout, exerciseNameLayout, exerciseDescriptionLayout, 0));
        exercises.add(new Exercise(muscleGroupImage,"","Sit-ups",0,15,1,Color.parseColor("#ffffff"), exerciseVideoLayout, exerciseNameLayout, exerciseDescriptionLayout, 0));
        exercises.add(new Exercise(muscleGroupImage,"","Press-ups",0,10,1,Color.parseColor("#ffffff"), exerciseVideoLayout, exerciseNameLayout, exerciseDescriptionLayout, 0));

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
}
