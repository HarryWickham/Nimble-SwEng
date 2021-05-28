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
}
