package uk.ac.york.nimblefitness.HelperClasses;

public class Exercise {
    private String muscleGroupImage;
    private String exerciseVideo;
    private String exerciseName;
    private String exerciseDescription;
    private int reps;
    private int movesPerRep;
    private int restAfterFinish;
    private int icon;

    public Exercise(String muscleGroupImage, String exerciseVideo, String exerciseName, String exerciseDescription, int reps, int movesPerRep, int restAfterFinish, int icon) {
        this.muscleGroupImage = muscleGroupImage;
        this.exerciseVideo = exerciseVideo;
        this.exerciseName = exerciseName;
        this.exerciseDescription = exerciseDescription;
        this.reps = reps;
        this.movesPerRep = movesPerRep;
        this.restAfterFinish = restAfterFinish;
        this.icon = icon;
    }

    public String getMuscleGroupImage() {
        return muscleGroupImage;
    }

    public String getExerciseVideo() {
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

    public void setExerciseVideo(String exerciseVideo) {
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
}
