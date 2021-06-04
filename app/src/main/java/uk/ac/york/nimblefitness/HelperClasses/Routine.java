package uk.ac.york.nimblefitness.HelperClasses;

import android.content.res.Resources;
import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;

import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeType;
import uk.ac.york.nimblefitness.MediaHandlers.Images.ImageLayout;
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
        ArrayList<ShapeType> shapeTypes = new ArrayList<>();

        shapeTypes.clear();
        shapeTypes.add(new ShapeType(25,237,600,1030,Color.parseColor("#303F9F"),"RECTANGLE",0)); //video box
        shapeTypes.add(new ShapeType(25,862,560,780,Color.parseColor("#303F9F"),"RECTANGLE",0)); //Image box

        ArrayList<TextLayout> textLayouts = new ArrayList<>();
        textLayouts.add(new TextLayout("Push Ups",
                TextModule.fontFamily.default_bold,
                Integer.toString(32),
                "#000000",
                50,
                50,
                null,
                null));
        textLayouts.add(new TextLayout("With your hands placed a shoulder width apart and a straight back, lower yourself to the ground keeping your elbows tucked in. Hold the position. Then push off of the floor to your start position to complete a rep.",
                TextModule.fontFamily.sans_serif,
                Integer.toString(13),
                "#000000",
                50,
                1512,
                null,
                null));

        exercises.add(new Exercise(new ImageLayout(50,
                887,
                726,
                550,
                0,
                "https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisemusclegroups/normal_push_up.png",
                null,
                null),
                shapeTypes,
                "Push Ups",
                "With your hands placed a shoulder width apart and a straight back, lower yourself to the ground keeping your elbows tucked in. Hold the position. Then push off of the floor to your start position to complete a rep.",
                "number",
                5,
                5,
                1,
                30,
                Color.parseColor("#008080"),
                new VideoLayout("https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Normal-Push-Up.mp4",
                        980, // The '-100' accounts for the rectangle 'border' behind the video.
                        550,
                        50,
                        262,
                        "",
                        0,
                        true,
                        null,
                        null),
                textLayouts,
                null));

        ArrayList<TextLayout> textLayouts2 = new ArrayList<>();
        textLayouts2.add(new TextLayout("Plank",
                TextModule.fontFamily.default_bold,
                Integer.toString(32),
                "#000000",
                50,
                50,
                null,
                null));
        textLayouts2.add(new TextLayout("From a normal push up position, lower yourself down so that your weight is resting on your forearms. With a straight back, hold this position by engaging your core muscles.",
                TextModule.fontFamily.sans_serif,
                Integer.toString(13),
                "#000000",
                50,
                1512,
                null,
                null));

        exercises.add(new Exercise(new ImageLayout(50,
                887,
                726,
                550,
                8,
                "https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisemusclegroups/plank.png",
                null,
                null),
                shapeTypes,
                "Plank",
                "From a normal push up position, lower yourself down so that your weight is resting on your forearms. With a straight back, hold this position by engaging your core muscles.",
                "time",
                20,
                0,
                1,
                30,
                Color.parseColor("#FF2400"),
                new VideoLayout("https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Plank.mp4",
                        980, // The '-100' accounts for the rectangle 'border' behind the video.
                        550,
                        50,
                        262,
                        "",
                        0,
                        true,
                        null,
                        null),
                textLayouts2,
                null));

        ArrayList<TextLayout> textLayouts3 = new ArrayList<>();
        textLayouts3.add(new TextLayout("Tricep Dip",
                TextModule.fontFamily.sans_serif,
                "16",
                "#000000",
                50,
                50,
                null,
                null));
        textLayouts3.add(new TextLayout("Using a chair, put your weight onto your hands then lower yourself slowly down so that your legs are straight and your body forms an 'L' shape. Push off of the chair to return to your start position to complete a rep.",
                TextModule.fontFamily.sans_serif,
                Integer.toString(13),
                "#000000",
                50,
                1512,
                null,
                null));

        exercises.add(new Exercise(new ImageLayout(50,
                887,
                726,
                550,
                8,
                "https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisemusclegroups/tricep_dip.png",
                null,
                null),
                shapeTypes,
                "Tricep Dip",
                "Using a chair, put your weight onto your hands then lower yourself slowly down so that your legs are straight and your body forms an 'L' shape. Push off of the chair to return to your start position to complete a rep.",
                "number",
                5,
                5,
                1,
                30,
                Color.parseColor("#FFDB58"),
                new VideoLayout("https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Tricep-Dip.mp4",
                        980, // The '-100' accounts for the rectangle 'border' behind the video.
                        550,
                        50,
                        262,
                        "",
                        0,
                        true,
                        null,
                        null),
                textLayouts3,
                null));

        ArrayList<TextLayout> textLayouts4 = new ArrayList<>();
        textLayouts4.add(new TextLayout("Superman",
                TextModule.fontFamily.default_bold,
                Integer.toString(32),
                "#000000",
                50,
                50,
                null,
                null));
        textLayouts4.add(new TextLayout("Start by laying on your front. Raise your legs off of the floor while simultaneously raising your arms off of the floor using your shoulder and back muscles. Hold this position.",
                TextModule.fontFamily.sans_serif,
                Integer.toString(13),
                "#000000",
                50,
                1512,
                null,
                null));

        exercises.add(new Exercise(new ImageLayout(50,
                887,
                726,
                550,
                8,
                "https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisemusclegroups/superman.png",
                null,
                null),
                shapeTypes,
                "Superman",
                "Start by laying on your front. Raise your legs off of the floor while simultaneously raising your arms off of the floor using your shoulder and back muscles. Hold this position.",
                "number",
                5,
                4,
                1,
                30,
                Color.parseColor("#BFFF00"),
                new VideoLayout("https://www-users.york.ac.uk/~hew550/NimbleAssets/exercisevideos/Superman.mp4",
                        980, // The '-100' accounts for the rectangle 'border' behind the video.
                        550,
                        50,
                        262,
                        "",
                        0,
                        true,
                        null,
                        null),
                textLayouts4,
                null));

        return new Routine(R.drawable.upperbody,"Beginners Upper Body","This is a test routine to ensure all routine related pages take the correct information from this single object",3,4, 60, 4,0, exercises);
    }
}
