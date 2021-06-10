package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeType;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeView;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;

/**
 * This class loads in the information for the current exercise in a routine including its video,
 * image, title and description. The fragment appears after the StartSummary fragment and before the
 * Counter fragment, with the ability to go back to the profile page which acts as the 'main menu'.
 */

public class InformationFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        requireActivity().setTitle("Exercise Information");
        // This button used to advance to the counter fragment. //
        Button toCounterPage = view.findViewById(R.id.toCounterPage);
        // This button is used to exit the current exercise and return to the profile tab. //
        Button exitToMenu = view.findViewById(R.id.exit_to_menu);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        Exercise exercise = (Exercise) bundle.getSerializable("exercise");

        if (exercise.getExerciseName().equals("fake")) {
            /* This is the routine object shared between this page, the counter, finish summary and
               end summary pages.
             */
            Routine routine = (Routine) getArguments().getSerializable("routine");
            loadExercise(view, routine.getExerciseArrayList().get(routine.getCurrentExercise()));
        } else if (bundle.getSerializable("exercise") != null) {
            loadExercise(view, exercise);
            toCounterPage.setVisibility(View.GONE);
        }
        // A new counter page is created upon the button mentioned above is pressed.
        CounterFragment counterFragment = new CounterFragment();
        counterFragment.setArguments(getArguments());
        /*  The listener which opens the counter fragment and closes this fragment when the
            corresponding button is pressed. */
        toCounterPage.setOnClickListener(new View.OnClickListener() {
            /** The listener which opens the counter fragment and closes this fragment when the
             * corresponding button is pressed.
             */
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction =
                        requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, counterFragment);
                fragmentTransaction.commit();
            }
        });

        exitToMenu.setOnClickListener(new View.OnClickListener() {
            /** The listener which closes this fragment and opens the profile tab in the main
             * activity when the corresponding button is pressed.
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                requireActivity().finish();
            }
        });

        return view;
    }

    /**
     * Initialises the information for the current exercise in the routine.
     */
    public void loadExercise(View view, Exercise exercise) {
        /* Sets the context and parent layout for the current exercise's name and description and
           displays it in the fragment.
         */
        for (TextLayout textLayout : exercise.getTextLayouts()) {
            textLayout.setContext(getActivity());
            textLayout.setParentLayout(view.findViewById(R.id.infoPage));
            textLayout.draw();
        }

        // A rectangle is drawn behind the video and image. //
        ShapeView rectangles = view.findViewById(R.id.information_shape_view);
        for (ShapeType shapeType : exercise.getBackgroundShapes()) {
            rectangles.addShape(shapeType.getxStart(), shapeType.getyStart(),
                    shapeType.getWidth(), shapeType.getHeight(), shapeType.getColour(),
                    shapeType.getShape_type(), shapeType.getDuration());
        }

        /* Sets the context and parent layout for the current exercise's video and displays it in
           the fragment.
         */
        exercise.getExerciseVideo().setContext(getActivity());
        exercise.getExerciseVideo().setParentLayout(view.findViewById(R.id.infoPage));
        exercise.getExerciseVideo().draw();

        /* Sets the context and parent layout for the current exercise's image and displays it in
           the fragment.
         */
        exercise.getMuscleGroupImage().setContext(getActivity());
        exercise.getMuscleGroupImage().setParentLayout(view.findViewById(R.id.infoPage));
        exercise.getMuscleGroupImage().draw();
    }
}