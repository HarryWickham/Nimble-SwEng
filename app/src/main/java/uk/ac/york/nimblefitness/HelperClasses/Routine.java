package uk.ac.york.nimblefitness.HelperClasses;

import java.util.ArrayList;

public class Routine {
    private Integer routineImage;
    private String routineName;
    private int rating;
    private int sets;
    private int restBetweenSets;
    private ArrayList<Exercise> exerciseArrayList;

    public Routine(Integer routineImage, String routineName, int rating, int sets, int restBetweenSets, ArrayList<Exercise> exerciseArrayList) {
        this.routineImage = routineImage;
        this.routineName = routineName;
        this.rating = rating;
        this.sets = sets;
        this.restBetweenSets = restBetweenSets;
        this.exerciseArrayList = exerciseArrayList;
    }

    public Routine(Integer routineImage, String routineName, int sets, ArrayList<Exercise> exerciseArrayList) {
        this.routineImage = routineImage;
        this.routineName = routineName;
        this.sets = sets;
        this.exerciseArrayList = exerciseArrayList;
    }

    public Integer getRoutineImage() {
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

    public void setRoutineImage(String routineImage) {
        this.routineImage = routineImage;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public void setRoutineSummary(String routineSummary) {
        this.routineSummary = routineSummary;
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
}
