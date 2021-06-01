package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

        Button toFinishPage = view.findViewById(R.id.toFinishPage);
        FinishFragment finishFragment = new FinishFragment();
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