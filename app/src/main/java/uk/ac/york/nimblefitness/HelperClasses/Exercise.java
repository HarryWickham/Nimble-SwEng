package uk.ac.york.nimblefitness.HelperClasses;

import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;

public class Exercise {
    private String muscleGroupImage;
    private String exerciseName;
    private String exerciseDescription;
    private int reps;
    private int movesPerRep;
    private int restAfterFinish;
    private int icon;
    private VideoLayout exerciseVideo;
    private TextLayout exerciseNameLayout;
    private TextLayout exerciseDescriptionLayout;

    public Exercise(String muscleGroupImage, String exerciseName, String exerciseDescription, int reps, int movesPerRep, int restAfterFinish, int icon, VideoLayout exerciseVideo, TextLayout exerciseNameLayout, TextLayout exerciseDescriptionLayout) {
        this.muscleGroupImage = muscleGroupImage;
        this.exerciseName = exerciseName;
        this.exerciseDescription = exerciseDescription;
        this.reps = reps;
        this.movesPerRep = movesPerRep;
        this.restAfterFinish = restAfterFinish;
        this.icon = icon;
        this.exerciseVideo = exerciseVideo;
        this.exerciseNameLayout = exerciseNameLayout;
        this.exerciseDescriptionLayout = exerciseDescriptionLayout;
    }

    public String getMuscleGroupImage() {
        return muscleGroupImage;
    }

    public VideoLayout getExerciseVideo() {
        return exerciseVideo;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public int getReps() {
        return reps;
    }

    public int getMovesPerRep() {
        return movesPerRep;
    }

    public int getRestAfterFinish() {
        return restAfterFinish;
    }

    public int getIcon() {
        return icon;
    }

    public void setMuscleGroupImage(String muscleGroupImage) {
        this.muscleGroupImage = muscleGroupImage;
    }

    public void setExerciseVideo(VideoLayout exerciseVideo) {
        this.exerciseVideo = exerciseVideo;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setMovesPerRep(int movesPerRep) {
        this.movesPerRep = movesPerRep;
    }

    public void setRestAfterFinish(int restAfterFinish) {
        this.restAfterFinish = restAfterFinish;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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
