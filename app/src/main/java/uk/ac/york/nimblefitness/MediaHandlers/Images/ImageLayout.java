package uk.ac.york.nimblefitness.MediaHandlers.Images;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.Serializable;

public class ImageLayout implements Serializable {

    //Declaring the variables for the image
    private int xCoordinate;
    private int yCoordinate;
    private int width;
    private int height;
    private int duration;
    private String imageSource;

    FrameLayout parentLayout;
    ImageModule imageModule;
    Context context;


    public ImageLayout(int xCoordinate, int yCoordinate, int width, int height, int duration, String imageSource, FrameLayout parentLayout, Context context) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.imageSource = imageSource;
        this.parentLayout = parentLayout;
        this.context = context;
    }

    public void setImage(){
        imageModule = new ImageModule(this.context);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = this.xCoordinate;
        params.topMargin = this.yCoordinate;
        imageModule.setLayoutParams(params);

        parentLayout.addView(imageModule);
        imageModule.setAll(this.xCoordinate, this.yCoordinate, this.width, this.height, this.duration, this.imageSource);
        imageModule.setImage();

        Log.i("TAG", "setImage: ");

    }

    public void setParentLayout(FrameLayout parentLayout) {
        this.parentLayout = parentLayout;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}