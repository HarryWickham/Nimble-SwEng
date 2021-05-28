package uk.ac.york.nimblefitness.HelperClasses;

import java.util.ArrayList;

public class Routine {
    private String routineImage;
    private String routineName;
    private String routineSummary;
    private int rating;
    private int sets;
    private int restBetweenSets;
    private ArrayList<Exercise> exerciseArrayList;

    public Routine(String routineImage, String routineName, String routineSummary, int rating, int sets, int restBetweenSets, ArrayList<Exercise> exerciseArrayList) {
        this.routineImage = routineImage;
        this.routineName = routineName;
        this.routineSummary = routineSummary;
        this.rating = rating;
        this.sets = sets;
        this.restBetweenSets = restBetweenSets;
        this.exerciseArrayList = exerciseArrayList;
    }

    public String getRoutineImage() {
        return routineImage;
    }

    public String getRoutineName() {
        return routineName;
    }

    public String getRoutineSummary() {
        return routineSummary;
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
}
