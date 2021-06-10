package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;

import uk.ac.york.nimblefitness.R;

/**
 * The routing required to open the first fragment on the RoutineAndExerciseActivity, if the user
 * comes from the exercise fragment they will be taken to the InformationFragment.
 * If the user comes from the routine fragment they will be taken to the StartSummaryFragment.
 */

public class RoutineAndExerciseActivity extends AppCompatActivity implements Serializable {
    String fragment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_and_exercise);

        if (getIntent().getStringExtra("fragment") != null) {
            fragment = getIntent().getStringExtra("fragment");
        }
        if (fragment.equals("information")) {
            InformationFragment informationFragment = new InformationFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
            fragmentTransaction.commit();
        } else {
            StartSummaryFragment startSummaryFragment = new StartSummaryFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, startSummaryFragment);
            fragmentTransaction.commit();
        }
    }
}