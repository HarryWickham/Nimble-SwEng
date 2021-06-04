package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.io.Serializable;

import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Profile.ProfileTabsFragment;

public class RoutineAndExerciseActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_and_exercise);

        StartSummaryFragment startSummaryFragment = new StartSummaryFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, startSummaryFragment);
        fragmentTransaction.commit();
    }
}