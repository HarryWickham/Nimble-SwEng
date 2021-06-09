package uk.ac.york.nimblefitness.HelperClasses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A routine is a class that holds all the data for a whole routine
 * One of the main fields in this class is the exerciseArrayList,
 *  it has data for all exercises in the routine
 * The routine is Serializable so that all the information can get transferred
 *  between presentation pages
 * The data for all the fields is set up in the routines.xml and loaded in routineFragment using RoutineData
 * routineImage: Image for the routine, displayed in the RoutineFragment
 * routineName: Name for the routine
 * routineSummary: Short descriptive tag that shows in StartSummaryFragment
 * rating: A rating for the routine that the user can give at the end of a routine
 * sets: Number of sets the routine has
 * restBetweenSets: Amount of seconds a user can rest between each set
 * setsRemaining: A count for number of sets the user has gone through,
 *  used to get to FinishFragment from EndSummaryFragment
 * currentExercise: The current exercise the user is completing
 * exerciseArrayList: List of all exercises in the routine
 */
public class Routine implements Serializable {
    private String routineImage;
    private String routineName;
    private String routineSummary;
    private int rating;
    private int sets;
    private int restBetweenSets;
    private int setsRemaining;
    private int currentExercise;
    private ArrayList<Exercise> exerciseArrayList;

    public Routine(String routineImage, String routineName, String routineSummary, int rating, int sets,
                   int restBetweenSets, int setsRemaining, int currentExercise, ArrayList<Exercise> exerciseArrayList) {
        this.routineImage = routineImage;
        this.routineName = routineName;
        this.routineSummary = routineSummary;
        this.rating = rating;
        this.sets = sets;
        this.restBetweenSets = restBetweenSets;
        this.setsRemaining = setsRemaining;
        this.currentExercise = currentExercise;
        this.exerciseArrayList = exerciseArrayList;
    }

    public Routine() {
    }

    public String getRoutineImage() {
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

    public String getRoutineSummary() {
        return routineSummary;
    }

    public void setRoutineSummary(String routineSummary) {
        this.routineSummary = routineSummary;
    }

    public int getSetsRemaining() {
        return setsRemaining;
    }

    public void setSetsRemaining(int setsRemaining) {
        this.setsRemaining = setsRemaining;
    }

    public int getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentExercise(int currentExercise) {
        this.currentExercise = currentExercise;
    }
}