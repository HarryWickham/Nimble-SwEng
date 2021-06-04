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

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
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

        Routine routine = new Routine().getExampleRoutine();

        getActivity().setTitle(routine.getRoutineName());

        TextView routineSummary = view.findViewById(R.id.routine_summary);
        routineSummary.setText(routine.getRoutineSummary());

        TextView starRating = view.findViewById(R.id.star_rating);
        starRating.setText(String.format("%d/5", routine.getRating()));

        TextView routineSets = view.findViewById(R.id.sets);
        routineSets.setText(String.format("%d sets", routine.getSets()));

        ListView listView = view.findViewById(R.id.start_summary_list_view);
        listView.setEnabled(false);

        MovesListAdapter movesListAdapter = new MovesListAdapter(getContext(), routine.getExerciseArrayList());

        listView.setAdapter(movesListAdapter);

        Button toInfoPage = view.findViewById(R.id.toInfoPage);
        InformationFragment informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("routine",routine);
        informationFragment.setArguments(bundle);
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