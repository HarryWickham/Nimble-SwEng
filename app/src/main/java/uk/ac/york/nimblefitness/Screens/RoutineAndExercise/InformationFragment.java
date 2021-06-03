package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeView;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Routines.RoutinesFragment;

public class InformationFragment extends Fragment {

    private int currentExercise = 0; // Will be updated to the next exercise once the previous is completed.

    public void setCurrentExercise(int currentExercise){
        this.currentExercise = currentExercise;
    }

    public int getCurrentExercise(){
        return currentExercise;
    }

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
        loadExercise(view);
        return view;
    }

    public void loadExercise(View view) {
        RoutinesFragment routinesFragment = new RoutinesFragment();
        List<Exercise> testRoutine = routinesFragment.setUpTestRoutine(view.findViewById(R.id.infoPage), this.getContext());
        int lastExercise = testRoutine.size();
        Exercise exercise = testRoutine.get(getCurrentExercise());

        // Add rectangles around video(?), description & image.
        Canvas canvas = new Canvas();
        ShapeView rectangles = new ShapeView(this.getContext());
        rectangles.addShape(50,50,50,50, Color.parseColor("#008080"), "RECTANGLE", 10);
        rectangles.draw(canvas);

        exercise.getExerciseNameLayout().writeText();
        exercise.getExerciseVideo().PlayVideo();
        exercise.getExerciseDescriptionLayout().writeText();
        exercise.getMuscleGroupImage().setImage();
        //setCurrentExercise(1);
    }
}

/*

 */