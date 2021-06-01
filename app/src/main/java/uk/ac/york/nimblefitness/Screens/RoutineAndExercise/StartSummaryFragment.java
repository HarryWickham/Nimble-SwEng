package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;

public class StartSummaryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_summary, container, false);
        getActivity().setTitle("Start Summary");

        ArrayList<Exercise> exercises = new ArrayList<>();

        Routine routine = new Routine(0,"Name","Summary",3,4,0,exercises);

        TextView routineName = view.findViewById(R.id.routine_name);
        routineName.setText(routine.getRoutineName());

        TextView routineSummary = view.findViewById(R.id.routine_summary);
        routineSummary.setText(routine.getRoutineSummary());

        TextView starRating = view.findViewById(R.id.star_rating);
        starRating.setText(String.format("%d/5", routine.getRating()));

        TextView routineSets = view.findViewById(R.id.sets);
        routineSets.setText(String.format("%d sets", routine.getSets()));

        exercises.add(new Exercise("","Plank","Plank",1,60,1, R.drawable.ic_baseline_accessibility_24,null,null,null));
        exercises.add(new Exercise("","Squats","Squats",1,20,1, R.drawable.ic_baseline_accessibility_24,null,null,null));
        exercises.add(new Exercise("","Sit-ups","Sit-ups",1,15,1, R.drawable.ic_baseline_accessibility_24,null,null,null));
        exercises.add(new Exercise("","Press-ups","Press-ups",1,10,1, R.drawable.ic_baseline_accessibility_24,null,null,null));

        ListView listView = view.findViewById(R.id.start_summary_list_view);

        MovesListAdapter movesListAdapter = new MovesListAdapter(getContext(), routine.getExerciseArrayList());

        listView.setAdapter(movesListAdapter);

        Button toInfoPage = view.findViewById(R.id.toInfoPage);
        InformationFragment informationFragment = new InformationFragment();
        toInfoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}