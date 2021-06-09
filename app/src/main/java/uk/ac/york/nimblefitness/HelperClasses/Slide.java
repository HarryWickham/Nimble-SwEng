package uk.ac.york.nimblefitness.HelperClasses;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;

public class Slide {
    ArrayList<AbstractLayout> abstractLayouts;
    int duration;

    public Slide() {
    }

    public ArrayList<AbstractLayout> getAbstractLayouts() {
        return abstractLayouts;
    }

    public void setAbstractLayouts(ArrayList<AbstractLayout> abstractLayouts) {
        this.abstractLayouts = abstractLayouts;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
