package uk.ac.york.nimblefitness.MediaHandlers.Images;

public class ImageType {
    //Declaring the variables for the image
    private int xCoordinate;
    private int yCoordinate;
    private int width;
    private int height;
    private int duration;
    private String imageSource;

    public ImageType() {
    }

    //Getters
    public int getXCoordinate() {
        return xCoordinate;
    }

    //Setters
    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getImageWidth() {
        return width;
    }

    public void setImageWidth(int width) {
        this.width = width;
    }

    public int getImageHeight() {
        return height;
    }

    public void setImageHeight(int height) {
        this.height = height;
    }

    public int getImageDuration() {
        return duration;
    }

    public void setImageDuration(int duration) {
        this.duration = duration;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

}
