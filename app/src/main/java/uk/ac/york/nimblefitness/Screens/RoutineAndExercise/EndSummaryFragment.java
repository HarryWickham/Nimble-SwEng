package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import uk.ac.york.nimblefitness.HelperClasses.CreateNotification;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;
import uk.ac.york.nimblefitness.Screens.SplashScreen;
import uk.ac.york.nimblefitness.Screens.UserDetailsActivity;

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
            }
        });



        TextView summaryTextView = view.findViewById(R.id.end_summary_text_view);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(currentFirebaseUser + "currentMoves", prefs.getInt(currentFirebaseUser+"currentMoves", 0)+totalMoves(routine));
        editor.apply();
        summaryTextView.setText(String.format("You have just completed the %s routine and gained %d moves. %s",routine.getRoutineName(), totalMoves(routine), movesToGoal(prefs, currentFirebaseUser)));

        return view;
    }

    private int totalMoves(Routine routine){
        int routineTotalMoves = 0;

        for(int i = 0; i < routine.getExerciseArrayList().size(); i++){
            routineTotalMoves = routineTotalMoves + routine.getExerciseArrayList().get(i).getMovesPerRep()*routine.getExerciseArrayList().get(i).getReps();
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

    private void rateRoutine(){

    }

}