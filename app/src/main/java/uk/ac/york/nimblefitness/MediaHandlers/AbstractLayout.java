package uk.ac.york.nimblefitness.MediaHandlers;

/**
 * Interface used by all media handlers.
 */
public interface AbstractLayout {
    void draw();

    String getMediaId();

    void playPause();
}
