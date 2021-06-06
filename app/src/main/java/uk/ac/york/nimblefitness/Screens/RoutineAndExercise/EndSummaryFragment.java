package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uk.ac.york.nimblefitness.HelperClasses.CreateNotification;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

public class EndSummaryFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_summary, container, false);

        Routine routine = (Routine) getArguments().getSerializable("routine");

        getActivity().setTitle(routine.getRoutineName());

        RatingBar ratingBar = view.findViewById(R.id.rating_bar);

        Button returnHome = view.findViewById(R.id.end_summary_home_button);
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();

                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });



        TextView summaryTextView = view.findViewById(R.id.end_summary_text_view);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        summaryTextView.setText(String.format("You have just completed the %s routine and gained %d moves. %s",routine.getRoutineName(), totalMoves(routine), movesToGoal(prefs, currentFirebaseUser)));

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
            CreateNotification createNotification = new CreateNotification(R.drawable.ic_stat_name, "Congratulations!", String.format("You have reached your weekly goal. Your current moves are: %s", String.format("%d/%d", prefs.getInt(currentFirebaseUser + "currentMoves", 0), prefs.getInt(currentFirebaseUser + "weeklyGoal",0))), MainActivity.class, "goalReachedChannelID", 1, getApplicationContext());
            return "Congratulations you have reached your weekly goal!";
        }
    }

    private void rateRoutine() {

    }

    private void favouriteRoutine() {
        ToggleButton favouritesButton = getView().findViewById(R.id.favourite_button);
        favouritesButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_favorite_24));

        favouritesButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (favouritesButton.isChecked()) {
                    favouritesButton.setBackgroundColor(Color.parseColor("#FF69B4"));

                }
            }
        });
    }

}