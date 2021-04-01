package uk.ac.york.nimblefitness.Screens.Profile.Goal;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.R;

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
        int endValue = 100;
        return endValue;
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
        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootReference = rootDatabase.getReference("users");
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (currentFirebaseUser != null) {
                    String user_firstName = snapshot.child(
                            currentFirebaseUser.getUid()).child("firstName").getValue(String.class);
                    String user_lastName = snapshot.child(
                            currentFirebaseUser.getUid()).child("lastName").getValue(String.class);
                    //The user's name is set in the section of the layout above the moves list.
                    userName = String.format("%s %s", user_firstName, user_lastName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
        listAdapter = new MovesListAdapter(context, movesToDo, moveDetails, numberOfMoves,
                                                                                exerciseIcon);
        return listAdapter;
    }
}
