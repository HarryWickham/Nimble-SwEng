package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.HelperClasses.CreateNotification;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.MediaHandlers.Images.ImageLayout;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

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
                //Toast.makeText(getActivity(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();

                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });



        TextView summaryTextView = view.findViewById(R.id.end_summary_text_view);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        summaryTextView.setText(String.format("You have just completed the %s routine and gained %d moves. %s",routine.getRoutineName(), totalMoves(routine), movesToGoal(prefs, currentFirebaseUser)));
        //favouriteRoutine(view);
        return view;
    }

    private int totalMoves(Routine routine){
        int routineTotalMoves = 0;

        for(int i = 0; i < routine.getExerciseArrayList().size(); i++){
            routineTotalMoves = (int) (routineTotalMoves + routine.getExerciseArrayList().get(i).getMovesPerRep()*routine.getExerciseArrayList().get(i).getReps());
        }

        routineTotalMoves = routineTotalMoves*routine.getSets();

        return routineTotalMoves;
    }

    @SuppressLint("DefaultLocale")
    private String movesToGoal(SharedPreferences prefs, FirebaseUser currentFirebaseUser){

        int movesGoalDifference = prefs.getInt(currentFirebaseUser+"weeklyGoal", 0)-prefs.getInt(currentFirebaseUser+"currentMoves", 0);

        if(movesGoalDifference > 0) {
            return String.format("You are %d moves from your goal!", movesGoalDifference);
        }else {
            return "Congratulations you have reached your weekly goal!";
        }
    }

    private void rateRoutine() {

    }

    private void favouriteRoutine(View view) {
        Button favouritesButton = view.findViewById(R.id.favourite_button);

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onCheckedChanged", "onCheckedChanged: ");
                currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();  // Getting the unique ID for the current user for their information
                mDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentFirebaseUser.getUid()).child("favorites");
                ArrayList<String> favoriteRoutines = new ArrayList<>();
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.i("onDataChange", "onDataChange: ");
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if(dataSnapshot != null) {
                                    favoriteRoutines.add(dataSnapshot.getValue(String.class));
                                }
                            }
                            Gson gson = new Gson();
                        Log.i("DataSnapshot", gson.toJson(snapshot));
                        addNewFavouriteRoutine(favoriteRoutines);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void addNewFavouriteRoutine(ArrayList<String> favoriteRoutines){
        favoriteRoutines.add(routine.getRoutineName());
        Log.i("addNewFavouriteRoutine", favoriteRoutines.get(favoriteRoutines.size()-1));
        Log.i("size", String.valueOf(favoriteRoutines.size()));
        //mDatabase.setValue(favoriteRoutines);
    }

    private void birdAnimation(View view) {
        ImageLayout birdGIF;

        String imageSource = Integer.toString(R.drawable.bird_animation2);
        Context context = this.getActivity();
        FrameLayout parentLayout = view.findViewById(R.id.bird_animation_frame);
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        birdGIF = new ImageLayout(0, 1000, screenWidth, 390, 0, imageSource, parentLayout, context);
        birdGIF.draw();
    }

}