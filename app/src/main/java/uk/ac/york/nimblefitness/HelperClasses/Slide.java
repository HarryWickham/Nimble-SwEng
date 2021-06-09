package uk.ac.york.nimblefitness.HelperClasses;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;

/**
 * Class for setting up a slide for the parsed xml data
 * abstractLayouts: a layout that the slide will show
 * duration: duration for the amount of time the slide should appear (if specified)
 */
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
