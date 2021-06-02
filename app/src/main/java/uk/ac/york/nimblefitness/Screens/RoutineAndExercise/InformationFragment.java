package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextModule;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.R;

public class InformationFragment extends Fragment {

    private Exercise currentExercise; // Will be updated to the next exercise once the previous is completed.

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
        layoutSetup(view);
        return view;
    }

    public void layoutSetup(View view){
        /*// This variable will be passed here from the 'StartSummaryFragment'.
        String chosenRoutine = "Hard Core";

        // Shared parameters between all sections of text, the video, and image.
        String fontcolour = "black";
        FrameLayout infoPage = (FrameLayout) view.findViewById(R.id.infoPage);
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int borderSize = 16; // The common space between each component and the screen edge.

        // Writes the exercise title to the screen.
        String title = selectedRoutine(chosenRoutine).getExerciseName();
        String titleFontSize = Integer.toString((int) (screenWidth*0.03)); // Text is scaled depending on screen size. Based on 'Table 4' from https://www.uxmatters.com/mt/archives/2015/09/type-sizes-for-every-device.php
        TextModule.styleFamily titleStyle = TextModule.styleFamily.bold;
        TextModule.fontFamily titleFont = TextModule.fontFamily.serif;
        int titleX = borderSize;
        int titleY = borderSize;
        TextLayout titleLayout = new TextLayout(title, titleFont, titleFontSize, fontcolour,titleStyle, titleX, titleY, infoPage, this.getContext());
        titleLayout.writeText();

        // Displays the exercise video on the screen.
        //String videoUri = selectedRoutine(chosenRoutine).getExerciseVideo();
        int videoWidth = screenWidth - 2*borderSize; // There is a border/margin either side of the video.
        int videoHeight = (int) Math.round(videoWidth*0.5625); // The video resolution is kept at a 16:9 aspect ratio.
        int videoX = borderSize;
        int videoY = borderSize + 5*Integer.parseInt(titleFontSize) + borderSize; // The x5 multiplier ensures the video is at least below the characters' (y, g, p, etc) descender.
        VideoLayout exerciseVideo = new VideoLayout(videoUri, videoWidth, videoHeight, videoX, videoY, "", 0, false, infoPage, this.getContext());
        exerciseVideo.PlayVideo();

        // Writes the exercise description to the screen.
        String description = selectedRoutine(chosenRoutine).getExerciseDescription();
        String descriptionFontSize = Integer.toString((int) (screenWidth*0.0118));
        TextModule.styleFamily descriptionStyle = TextModule.styleFamily.normal;
        TextModule.fontFamily descriptionFont = TextModule.fontFamily.sans_serif; // Sans-serif text is better for readability of large text chunks.
        int descriptionX = borderSize;
        int descriptionY = videoY + videoHeight + borderSize;
        TextLayout descriptionLayout = new TextLayout(description, descriptionFont, descriptionFontSize, fontcolour, descriptionStyle, descriptionX, descriptionY, infoPage, this.getContext());
        descriptionLayout.writeText();

        // Displays the image which highlights muscles utilised in the current exercise.
    }

    // This sets the information for the first exercise in a routine depending on the routine selected. The sets & reps will be passed to the 'CounterFragment'.
    public Exercise selectedRoutine(String routineName) {
        Exercise exercise;
        switch (routineName){
            case "Beginners Upper Body": case "Easy Upper Body": case "Intermediate Upper Body": case "Hard Upper Body":
                exercise = new Exercise(
                        "http://www-users.york.ac.uk/~hew550/NimbleAssets/exercisemusclegroups/normal_push_up.png",
                        "https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Normal-Push-Up.mp4",
                        "Normal Push Ups",
                        "With your hands placed a shoulder width apart and a straight back, lower yourself to the ground keeping your elbows tucked in. Hold the position. Then push off of the floor to your start position to complete a rep.",
                        4,5,0,0, exerciseNameLayout, exerciseDescriptionLayout);
                switch (routineName){
                    case "Easy Upper Body": case "Intermediate Upper Body":
                        exercise.setMovesPerRep(10);
                    break;
                    case "Hard Upper Body":
                        exercise.setReps(3);
                        exercise.setMovesPerRep(10);
                    break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + routineName);
                }
                break;
            case "Beginners Lower Body": case "Easy Lower Body": case "Intermediate Lower Body": case "Hard Lower Body":
                exercise = new Exercise(
                        "http://www-users.york.ac.uk/~hew550/NimbleAssets/exercisemusclegroups/squats.png",
                        "https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Squats.mp4",
                        "Bodyweight Squats",
                        "From a standing position, space your legs about a shoulder width apart, toes facing forwards and bend at the knee to lower your body. Ensure that you do not arch your back; keep it straight, and bend sufficiently at the knee to lower yourself. Then, raise yourself by engaging leg and core muscles back to the start position to complete a rep.",
                        4,5,0,0, exerciseNameLayout, exerciseDescriptionLayout);
                switch (routineName){
                    case "Easy Lower Body": case "Intermediate Lower Body":
                        exercise.setMovesPerRep(10);
                    break;
                    case "Hard Lower Body":
                        exercise.setReps(3);
                        exercise.setMovesPerRep(20);
                    break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + routineName);
                }
                break;
            case "Beginners Core": case "Easy Core": case "Intermediate Core": case "Hard Core":
                exercise = new Exercise(
                        "http://www-users.york.ac.uk/~hew550/NimbleAssets/exercisemusclegroups/plank.png",
                        "https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Plank.mp4",
                        "Plank",
                        "From a normal push up position, lower yourself down so that your weight is resting on your forearms. With a straight back, hold this position by engaging your core muscles.",
                        4,20,0,0, exerciseNameLayout, exerciseDescriptionLayout);
                switch (routineName){
                    case "Easy Core":
                        exercise.setMovesPerRep(25);
                    break;
                    case "Intermediate Core":
                        exercise.setMovesPerRep(45);
                    break;
                    case "Hard Core":
                        exercise.setReps(3);
                        exercise.setMovesPerRep(60);
                    break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + routineName);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + routineName);
        }
        currentExercise = exercise;
        return exercise;*/
    }

    // After the first exercise is completed, the next exercise and its associated information is loaded into the fragment. This is passed here by the 'FinishFragment'.
    /*public Exercise nextExercise(String exerciseName) {
        Exercise exercise = currentExercise;
        switch (currentExercise.getExerciseName()){
            case ...
        }

    }*/
}