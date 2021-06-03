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

    //Setters
    public void setXCoordinate(int xCoordinate){
        this.xCoordinate = xCoordinate;
    }

    public void setYCoordinate(int yCoordinate){
        this.yCoordinate = yCoordinate;
    }

    public void setImageWidth(int width){
        this.width = width;
    }

    public void setImageHeight(int height){
        this.height = height;
    }

    public void setImageDuration(int duration){ this.duration = duration; }

    public void setImageSource(String imageSource){
        this.imageSource = imageSource;
    }

    //Getters
    public int getXCoordinate(){
        return xCoordinate;
    }

    public int getYCoordinate(){
        return yCoordinate;
    }

    public int getImageWidth(){
        return width;
    }

    public int getImageHeight(){
        return height;
    }

    public int getImageDuration(){ return duration; }

    public String getImageSource(){
        return imageSource;
    }

}
