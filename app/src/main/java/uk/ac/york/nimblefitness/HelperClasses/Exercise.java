package uk.ac.york.nimblefitness.HelperClasses;

import uk.ac.york.nimblefitness.MediaHandlers.Audio.AudioType;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeType;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeView;
import uk.ac.york.nimblefitness.MediaHandlers.Images.ImageLayout;
import java.io.Serializable;
import java.util.ArrayList;

import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;

public class Exercise implements Serializable {
    private ImageLayout muscleGroupImage;
    private ArrayList<ShapeType> backgroundShapes;
    private String exerciseName;
    private String exerciseDescription;
    private String repType; //"time" or "number"
    private int reps;
    private int timePerRep;
    private int movesPerRep;
    private int restAfterFinish;
    private int colour;
    private VideoLayout exerciseVideo;
    private ArrayList<TextLayout> textLayouts;
    private int currentExercise;
    private ArrayList<AudioType> audioTypes;

    public Exercise(ImageLayout muscleGroupImage, ArrayList<ShapeType> backgroundShapes, String exerciseName, String exerciseDescription, String repType, int reps, int timePerRep, int movesPerRep, int restAfterFinish, int colour, VideoLayout exerciseVideo, ArrayList<TextLayout> textLayouts) {
        this.muscleGroupImage = muscleGroupImage;
        this.backgroundShapes = backgroundShapes;
        this.exerciseName = exerciseName;
        this.exerciseDescription = exerciseDescription;
        this.repType = repType;
        this.reps = reps;
        this.timePerRep = timePerRep;
        this.movesPerRep = movesPerRep;
        this.restAfterFinish = restAfterFinish;
        this.colour = colour;
        this.exerciseVideo = exerciseVideo;
        this.textLayouts = textLayouts;
        this.currentExercise = currentExercise;
    }

    public ImageLayout getMuscleGroupImage() {
        return muscleGroupImage;
    }

    public void setMuscleGroupImage(ImageLayout muscleGroupImage) {
        this.muscleGroupImage = muscleGroupImage;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getTimePerRep() {
        return timePerRep;
    }

    public void setTimePerRep(int timePerRep) {
        this.timePerRep = timePerRep;
    }

    public int getMovesPerRep() {
        return movesPerRep;
    }

    public void setMovesPerRep(int movesPerRep) {
        this.movesPerRep = movesPerRep;
    }

    public int getRestAfterFinish() {
        return restAfterFinish;
    }

    public void setRestAfterFinish(int restAfterFinish) {
        this.restAfterFinish = restAfterFinish;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public VideoLayout getExerciseVideo() {
        return exerciseVideo;
    }

    public void setExerciseVideo(VideoLayout exerciseVideo) {
        this.exerciseVideo = exerciseVideo;
    }

    public ArrayList<TextLayout> getTextLayouts() {
        return textLayouts;
    }

    public void setTextLayouts(ArrayList<TextLayout> textLayouts) {
        this.textLayouts = textLayouts;
    }

    public int getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentExercise(int currentExercise) {
        this.currentExercise = currentExercise;
    }

    public ArrayList<ShapeType> getBackgroundShapes() {
        return backgroundShapes;
    }

    public void setBackgroundShapes(ArrayList<ShapeType> backgroundShapes) {
        this.backgroundShapes = backgroundShapes;
    }
}
