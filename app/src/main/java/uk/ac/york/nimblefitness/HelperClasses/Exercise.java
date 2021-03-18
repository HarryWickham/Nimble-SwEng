package uk.ac.york.nimblefitness.HelperClasses;

public class Exercise {
    private String exerciseName;
    private int exerciseImage;

    public Exercise(String exerciseName, int exerciseImage) {
        this.exerciseName = exerciseName;
        this.exerciseImage = exerciseImage;
    }

    public String getExerciseName() { return exerciseName; }

    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public int getExerciseImage() { return exerciseImage; }

    public void setExerciseImage(int exerciseImage) { this.exerciseImage = exerciseImage; }
}
