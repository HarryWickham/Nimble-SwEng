package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;

public class CounterFragment extends Fragment {

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter, container, false);
        getActivity().setTitle("Counter Page");

        Button ReturnButton = view.findViewById(R.id.ReturnButton);
        ReturnButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

            }
        });

        Button MuteButton = view.findViewById(R.id.MuteButton);
        MuteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

            }
        });

        ArrayList<Routine> routineArrayList = new ArrayList<>();

        Routine routine = new Routine().getExampleRoutine();
        Routine routine2 = new Routine().getExampleRoutine();

        routineArrayList.add(routine);
        routineArrayList.add(routine2);


        Button toFinishPage = view.findViewById(R.id.toFinishPage);
        FinishFragment finishFragment = new FinishFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("routine",routine);
        finishFragment.setArguments(bundle);
        toFinishPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, finishFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}