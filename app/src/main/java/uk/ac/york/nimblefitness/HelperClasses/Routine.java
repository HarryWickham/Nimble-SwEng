package uk.ac.york.nimblefitness.HelperClasses;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;

import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextModule;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.R;

public class Routine implements Serializable {
    private int routineImage;
    private String routineName;
    private String routineSummary;
    private int rating;
    private int sets;
    private int restBetweenSets;
    private int setsRemaining;
    private int currentExercise;
    private ArrayList<Exercise> exerciseArrayList;

    public Routine(int routineImage, String routineName, String routineSummary, int rating, int sets, int restBetweenSets, int setsRemaining, int currentExercise, ArrayList<Exercise> exerciseArrayList) {
        this.routineImage = routineImage;
        this.routineName = routineName;
        this.routineSummary = routineSummary;
        this.rating = rating;
        this.sets = sets;
        this.restBetweenSets = restBetweenSets;
        this.setsRemaining = setsRemaining;
        this.currentExercise = currentExercise;
        this.exerciseArrayList = exerciseArrayList;
    }

    public Routine(int routineImage, String routineName, int sets, ArrayList<Exercise> exerciseArrayList) {
        this.routineImage = routineImage;
        this.routineName = routineName;
        this.sets = sets;
        this.exerciseArrayList = exerciseArrayList;
    }

    public Routine() {
    }

    public int getRoutineImage() {
        return routineImage;
    }

    public String getRoutineName() {
        return routineName;
    }

    public int getRating() {
        return rating;
    }

    public int getSets() {
        return sets;
    }

    public int getRestBetweenSets() {
        return restBetweenSets;
    }

    public ArrayList<Exercise> getExerciseArrayList() {
        return exerciseArrayList;
    }

    public void setRoutineImage(int routineImage) {
        this.routineImage = routineImage;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setRestBetweenSets(int restBetweenSets) {
        this.restBetweenSets = restBetweenSets;
    }

    public void setExerciseArrayList(ArrayList<Exercise> exerciseArrayList) {
        this.exerciseArrayList = exerciseArrayList;
    }

    public String getRoutineSummary() {
        return routineSummary;
    }

    public void setRoutineSummary(String routineSummary) {
        this.routineSummary = routineSummary;
    }

    public int getSetsRemaining() {
        return setsRemaining;
    }

    public void setSetsRemaining(int setsRemaining) {
        this.setsRemaining = setsRemaining;
    }

    public int getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentExercise(int currentExercise) {
        this.currentExercise = currentExercise;
    }

    public Routine getExampleRoutine(){
        ArrayList<Exercise> exercises = new ArrayList<>();

        exercises.add(new Exercise(null,
                null,
                "Push Ups",
                "With your hands placed a shoulder width apart and a straight back, lower yourself to the ground keeping your elbows tucked in. Hold the position. Then push off of the floor to your start position to complete a rep.",
                "number",
                5,
                5,
                1,
                30,
                Color.parseColor("#008080"),
                new VideoLayout("https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Normal-Push-Up.mp4",
                        500,
                        500,
                        50,
                        150,
                        "",
                        0,
                        false,
                        null,
                        null),
                new TextLayout("Push Ups",
                        TextModule.fontFamily.sans_serif,
                        "16",
                        "#000000",
                        50,
                        50,
                        null,
                        null),
                new TextLayout("With your hands placed a shoulder width apart and a straight back, lower yourself to the ground keeping your elbows tucked in. Hold the position. Then push off of the floor to your start position to complete a rep.",
                        TextModule.fontFamily.sans_serif,
                        "16",
                        "#000000",
                        50,
                        100,
                        null,
                        null)));

        exercises.add(new Exercise(null,
                null,
                "Plank",
                "From a normal push up position, lower yourself down so that your weight is resting on your forearms. With a straight back, hold this position by engaging your core muscles.",
                "time",
                20,
                0,
                1,
                30,
                Color.parseColor("#FF2400"),
                new VideoLayout("https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Plank.mp4",
                        500,
                        500,
                        50,
                        150,
                        "",
                        0,
                        false,
                        null,
                        null),
                new TextLayout("Plank",
                        TextModule.fontFamily.sans_serif,
                        "16",
                        "#000000",
                        50,
                        50,
                        null,
                        null),
                new TextLayout("From a normal push up position, lower yourself down so that your weight is resting on your forearms. With a straight back, hold this position by engaging your core muscles.",
                        TextModule.fontFamily.sans_serif,
                        "16",
                        "#000000",
                        50,
                        100,
                        null,
                        null)));

        exercises.add(new Exercise(null,
                null,
                "Tricep Dip",
                "Using a chair, put your weight onto your hands then lower yourself slowly down so that your legs are straight and your body forms an 'L' shape. Push off of the chair to return to your start position to complete a rep.",
                "number",
                5,
                5,
                1,
                30,
                Color.parseColor("#FFDB58"),
                new VideoLayout("https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Tricep-Dip.mp4",
                        500,
                        500,
                        50,
                        150,
                        "",
                        0,
                        false,
                        null,
                        null),
                new TextLayout("Tricep Dip",
                        TextModule.fontFamily.sans_serif,
                        "16",
                        "#000000",
                        50,
                        50,
                        null,
                        null),
                new TextLayout("Using a chair, put your weight onto your hands then lower yourself slowly down so that your legs are straight and your body forms an 'L' shape. Push off of the chair to return to your start position to complete a rep.",
                        TextModule.fontFamily.sans_serif,
                        "16",
                        "#000000",
                        50,
                        100,
                        null,
                        null)));

        exercises.add(new Exercise(null,
                null,
                "Superman",
                "Start by laying on your front. Raise your legs off of the floor while simultaneously raising your arms off of the floor using your shoulder and back muscles. Hold this position.",
                "number",
                5,
                4,
                1,
                30,
                Color.parseColor("#BFFF00"),
                new VideoLayout("https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Superman.mp4",
                        500,
                        500,
                        50,
                        150,
                        "",
                        0,
                        false,
                        null,
                        null),
                new TextLayout("Superman",
                        TextModule.fontFamily.sans_serif,
                        "16",
                        "#000000",
                        50,
                        50,
                        null,
                        null),
                new TextLayout("Start by laying on your front. Raise your legs off of the floor while simultaneously raising your arms off of the floor using your shoulder and back muscles. Hold this position.",
                        TextModule.fontFamily.sans_serif,
                        "16",
                        "#000000",
                        50,
                        100,
                        null,
                        null)));

        return new Routine(R.drawable.upperbody,"Beginners Upper Body","This is a test routine to ensure all routine related pages take the correct information from this single object",3,4, 60, 4,1, exercises);
    }
}
