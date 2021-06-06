package uk.ac.york.nimblefitness.HelperClasses;

public class SavableExercise {
    private String exerciseName;
    private int reps;
    private float movesPerRep;
    private int colour;
    private String repType;
    private String dateCompleted;

    public SavableExercise(String exerciseName, int reps, float movesPerRep, int colour, String repType, String dateCompleted) {
        this.exerciseName = exerciseName;
        this.reps = reps;
        this.movesPerRep = movesPerRep;
        this.colour = colour;
        this.repType = repType;
        this.dateCompleted = dateCompleted;
    }

    public SavableExercise() {
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public float getMovesPerRep() {
        return movesPerRep;
    }

    public void setMovesPerRep(float movesPerRep) {
        this.movesPerRep = movesPerRep;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }
}
