package uk.ac.york.nimblefitness.HelperClasses;

import uk.ac.york.nimblefitness.MediaHandlers.Images.ImageLayout;
import java.io.Serializable;

import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;

public class Exercise implements Serializable {
    private ImageLayout muscleGroupImage;
    private String exerciseName;
    private String exerciseDescription;
    private String repType; //"time" or "number"
    private int reps;
    private int timePerRep;
    private int movesPerRep;
    private int restAfterFinish;
    private int colour;
    private VideoLayout exerciseVideo;
    private TextLayout exerciseNameLayout;
    private TextLayout exerciseDescriptionLayout;
    private int currentExercise;

    public Exercise(ImageLayout muscleGroupImage, String exerciseName, String exerciseDescription, String repType, int reps, int timePerRep, int movesPerRep, int restAfterFinish, int colour, VideoLayout exerciseVideo, TextLayout exerciseNameLayout, TextLayout exerciseDescriptionLayout) {
        this.muscleGroupImage = muscleGroupImage;
        this.exerciseName = exerciseName;
        this.exerciseDescription = exerciseDescription;
        this.repType = repType;
        this.reps = reps;
        this.timePerRep = timePerRep;
        this.movesPerRep = movesPerRep;
        this.restAfterFinish = restAfterFinish;
        this.colour = colour;
        this.exerciseVideo = exerciseVideo;
        this.exerciseNameLayout = exerciseNameLayout;
        this.exerciseDescriptionLayout = exerciseDescriptionLayout;
        this.currentExercise = currentExercise;
    }

    public ImageLayout getMuscleGroupImage() {
        return muscleGroupImage;
    }

    public void setMuscleGroupImage(ImageLayout muscleGroupImage) {
        this.muscleGroupImage = muscleGroupImage;
    }

    public void setExerciseVideo(VideoLayout exerciseVideo) {
        this.exerciseVideo = exerciseVideo;
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

    public TextLayout getExerciseNameLayout() {
        return exerciseNameLayout;
    }

    public void setExerciseNameLayout(TextLayout exerciseNameLayout) {
        this.exerciseNameLayout = exerciseNameLayout;
    }

    public TextLayout getExerciseDescriptionLayout() {
        return exerciseDescriptionLayout;
    }

    public void setExerciseDescriptionLayout(TextLayout exerciseDescriptionLayout) {
        this.exerciseDescriptionLayout = exerciseDescriptionLayout;
    }
}
