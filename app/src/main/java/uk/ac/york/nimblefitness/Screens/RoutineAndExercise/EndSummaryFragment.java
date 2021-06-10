package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.MediaHandlers.Images.ImageLayout;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * This fragment will only appear once the user has completed their entire routine and will allow
 * the user to rate the routine and add it to their favorites.
 */

public class EndSummaryFragment extends Fragment {
    Routine routine;
    FirebaseUser currentFirebaseUser;
    DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_summary, container, false);

        routine = (Routine) getArguments().getSerializable("routine");

        getActivity().setTitle(routine.getRoutineName());
        birdAnimation(view);
        RatingBar ratingBar = view.findViewById(R.id.rating_bar);

        Button returnHome = view.findViewById(R.id.end_summary_home_button);
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), String.valueOf(ratingBar.getRating()), Toast
                // .LENGTH_LONG).show();

                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });


        TextView summaryTextView = view.findViewById(R.id.end_summary_text_view);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        summaryTextView.setText(String.format("You have just completed the %s routine and gained "
                + "%d moves. %s", routine.getRoutineName(), totalMoves(routine),
                movesToGoal(prefs, currentFirebaseUser)));
        favouriteRoutine(view);
        return view;
    }

    //Calculates the total moves that have been gained while doing this routine
    private int totalMoves(Routine routine) {
        int routineTotalMoves = 0;

        for (int i = 0; i < routine.getExerciseArrayList().size(); i++) {
            routineTotalMoves =
                    (int) (routineTotalMoves + routine.getExerciseArrayList()
                            .get(i).getMovesPerRep() * routine.getExerciseArrayList().get(i).getReps());
        }

        routineTotalMoves = routineTotalMoves * routine.getSets();

        return routineTotalMoves;
    }

    //Calculates and returns a string based on how close the user is to their goal
    @SuppressLint("DefaultLocale")
    private String movesToGoal(SharedPreferences prefs, FirebaseUser currentFirebaseUser) {

        int movesGoalDifference =
                prefs.getInt(currentFirebaseUser + "weeklyGoal", 0) -
                        prefs.getInt(currentFirebaseUser + "currentMoves", 0);

        if (movesGoalDifference > 0) {
            return String.format("You are %d moves from your goal!", movesGoalDifference);
        } else {
            return "Congratulations you have reached your weekly goal!";
        }
    }

    //A call to firebase is made to see if the routine is already in their favourites, if it is
    // then the add to favourites button wil change to say remove from favourites.
    private void favouriteRoutine(View view) {
        Button favouritesButton = view.findViewById(R.id.favourite_button);

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();  // Getting
        // the unique ID for the current user for their information
        mDatabase =
                FirebaseDatabase.getInstance().getReference("users")
                        .child(currentFirebaseUser.getUid()).child("favorites");
        ArrayList<String> favoriteRoutines = new ArrayList<>();
        //Upon clicking the button the program will work out if they want to add or remove the
        // favourites and will send them to the correct function
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favouritesButton.getText().equals("Add routine to favourites")) {
                    favouritesButton.setEnabled(false);
                    addNewFavouriteRoutine(favoriteRoutines);
                    favouritesButton.setText("Remove routine from favourites");
                } else if (favouritesButton.getText().equals("Remove routine from favourites")) {
                    favouritesButton.setEnabled(false);
                    removeFavouriteRoutine(favoriteRoutines);
                    favouritesButton.setText("Add routine to favourites");
                }
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteRoutines.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot != null) {
                        favoriteRoutines.add(dataSnapshot.getValue(String.class));
                        if (dataSnapshot.getValue(String.class).equals(routine.getRoutineName())) {
                            favouritesButton.setText("Remove routine from favourites");
                        }
                    }
                }
                favouritesButton.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    //to remove routine form their favourites the routine is checked against the list collected
    // from firebase and if their name match that routine will be removed. The new list will then
    // added back to firebase where it can be re-downloaded next time.
    private void removeFavouriteRoutine(ArrayList<String> favoriteRoutines) {
        for (int i = 0; i < favoriteRoutines.size(); i++) {
            if (favoriteRoutines.get(i).equals(routine.getRoutineName())) {
                favoriteRoutines.remove(i);
                mDatabase.setValue(favoriteRoutines);
            }
        }
    }

    //to add to the favorites the routine list is checked to see if it is already in the
    // database, if not then it will be added, if it is it will be ignored.
    private void addNewFavouriteRoutine(ArrayList<String> favoriteRoutines) {
        boolean alreadyThere = false;
        for (String favs : favoriteRoutines) {
            if (favs.equals(routine.getRoutineName())) {
                alreadyThere = true;
            }
        }
        if (!alreadyThere) {

            favoriteRoutines.add(routine.getRoutineName());

            mDatabase.setValue(favoriteRoutines);
        }
    }

    /**
     * This method instantiates the GIF on this fragment view, using the image media handler.
     */
    private void birdAnimation(View view) {
        String imageSource =
                "https://www-users.york.ac.uk/~hew550/NimbleAssets/bird_animation2" + ".gif";
        Context context = this.getActivity();
        FrameLayout parentLayout = view.findViewById(R.id.bird_animation_frame);
        /* The width of the GIF is as wide as the device's screen and the height is calculated from
           that, retaining the original aspect ratio of 3:2.
         */
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = (int) Math.round(screenWidth * 0.6667);

        ImageLayout birdGIF = new ImageLayout(0, 0, screenWidth, height, 0, imageSource,
                parentLayout, context);
        birdGIF.draw();
    }

}