package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeType;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeView;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;

/** This class loads in the information for the current exercise in a routine including its video,
 * image, title and description. The fragment appears after the StartSummary fragment and before the
 * Counter fragment, with the ability to go back to the profile page which acts as the 'main menu'.
 */

public class InformationFragment extends Fragment {
    /** This method initialises the fragment. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /** This method creates the appearance of the fragment and inflates the exercise information
     * page layout so it's visible.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        requireActivity().setTitle("Exercise Information");
        /* This button used to advance to the counter fragment. */
        Button toCounterPage = view.findViewById(R.id.toCounterPage);
        String toCounterPageButtonText = "Ready!";
        toCounterPage.setText(toCounterPageButtonText);
        /* This button is used to exit the current exercise and return to the profile tab. */
        Button exitToMenu = view.findViewById(R.id.exit_to_menu);
        exitToMenu.setText("Exit to Main Menu");
        /*  This is the routine object shared between this page, the counter, finish summary and end
            summary pages. */

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        Log.i("TAG", String.valueOf(bundle.getSerializable("exercise")));
        Exercise exercise = (Exercise) bundle.getSerializable("exercise");

        Log.i("TAG", String.valueOf(exercise.getExerciseName()));

        if(exercise.getExerciseName().equals("fake")) {
            Routine routine = (Routine) getArguments().getSerializable("routine");
            loadExercise(view, routine.getExerciseArrayList().get(routine.getCurrentExercise()));
        }else if(bundle.getSerializable("exercise")!=null){
            loadExercise(view, exercise);
            toCounterPage.setVisibility(View.GONE);
        }else{
            Log.i("onCreateView", "onCreateView: ");
        }
        // A new counter page is created upon the button mentioned above is pressed.
        CounterFragment counterFragment = new CounterFragment();
        //Bundle bundle = new Bundle();
        //bundle.putSerializable("routine",routine);
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
    /** Initialises the information for the current exercise in the routine. */
    public void loadExercise(View view, Exercise exercise) {
        /*  The current exercise displayed is determined by the currentExercise property of the
            Routine object. */

        /* Sets the context and parent layout for the current exercise's name and description and
           displays it in the fragment.
         */
        for (TextLayout textLayout : exercise.getTextLayouts()){
            textLayout.setContext(getActivity());
            textLayout.setParentLayout(view.findViewById(R.id.infoPage));
            textLayout.writeText();
        }

        /* A rectangle is drawn behind the video and image. */
        ShapeView rectangles = view.findViewById(R.id.information_shape_view);
        for(ShapeType shapeType : exercise.getBackgroundShapes()){
            rectangles.addShape(shapeType.getxStart(),shapeType.getyStart(),
                    shapeType.getWidth(),shapeType.getHeight(),
                    shapeType.getColour(),shapeType.getShape_type(),shapeType.getDuration());
        }

        /* Sets the context and parent layout for the current exercise's video and displays it in
           the fragment.
         */
        exercise.getExerciseVideo().setContext(getActivity());
        exercise.getExerciseVideo().setParentLayout(view.findViewById(R.id.infoPage));
        exercise.getExerciseVideo().PlayVideo();

        /* Sets the context and parent layout for the current exercise's image and displays it in
           the fragment.
         */
        exercise.getMuscleGroupImage().setContext(getActivity());
        exercise.getMuscleGroupImage().setParentLayout(view.findViewById(R.id.infoPage));
        exercise.getMuscleGroupImage().setImage();
    }
}