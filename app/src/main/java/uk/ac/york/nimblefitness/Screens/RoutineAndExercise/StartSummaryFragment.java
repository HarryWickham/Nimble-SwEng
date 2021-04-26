package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uk.ac.york.nimblefitness.R;

public class StartSummaryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_summary, container, false);
        getActivity().setTitle("Start Summary");

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