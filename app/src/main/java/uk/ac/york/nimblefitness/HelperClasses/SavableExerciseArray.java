package uk.ac.york.nimblefitness.HelperClasses;

import java.util.ArrayList;

public class SavableExerciseArray {
    ArrayList<SavableExercise> savableExercises = new ArrayList<>();

    public SavableExerciseArray(ArrayList<SavableExercise> savableExercises) {
        this.savableExercises = savableExercises;
    }

    public SavableExerciseArray() {
    }

    public ArrayList<SavableExercise> getSavableExercises() {
        return savableExercises;
    }

    public void setSavableExercises(ArrayList<SavableExercise> savableExercises) {
        this.savableExercises = savableExercises;
    }
}
