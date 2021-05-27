package uk.ac.york.nimblefitness.HelperClasses;

import java.util.ArrayList;

public class Routine {
    private String routineName;
    private int routineImage;
    private int rating;
    private boolean favourite;
    private ArrayList<Exercise> exerciseArrayList;

    public Routine(String routineName, int routineImage) {
        this.routineName = routineName;
        this.routineImage = routineImage;
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public int getRoutineImage() {
        return routineImage;
    }

    public void setRoutineImage(int routineImage) {
        this.routineImage = routineImage;
    }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public boolean isFavourite() { return favourite; }

    public void setFavourite(boolean favourite) { this.favourite = favourite; }

    public int getNumberOfExercises() { return exerciseArrayList.size(); }

    public Exercise getExercise(int i) { return exerciseArrayList.get(i); }


}
