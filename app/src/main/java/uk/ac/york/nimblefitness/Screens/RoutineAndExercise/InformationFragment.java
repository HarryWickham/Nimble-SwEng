package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uk.ac.york.nimblefitness.R;

public class InformationFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        getActivity().setTitle("Information Fragment");

        Button toCounterPage = view.findViewById(R.id.toCounterPage);
        CounterFragment counterFragment = new CounterFragment();
        toCounterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, counterFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}