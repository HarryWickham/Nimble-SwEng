package uk.ac.york.nimblefitness.HelperClasses;

import android.media.Image;

public class Routine {
    private String routineName;
    private int routineImage;

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
}
