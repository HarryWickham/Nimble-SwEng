package uk.ac.york.nimblefitness.MediaHandlers.Images;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class ImageModule extends AppCompatImageView implements Serializable {

    //Declaring the variables for the image
    private int xCoordinate;
    private int yCoordinate;
    private int width;
    private int height;
    private int duration;
    private String imageSource;
    public ImageType imageType = new ImageType();

    /**
     *
     * Constructor for the image module. Uses all these inputs to fulfill the PWS
     */

    public ImageModule(Context context) {
        super(context);
    }

    public ImageModule(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageModule(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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


    public void setAll(int xCoordinate, int yCoordinate, int width, int height,
                       int duration, String imageSource) {
        setXCoordinate(xCoordinate);
        setYCoordinate(yCoordinate);
        setImageWidth(width);
        setImageHeight(height);
        setImageDuration(duration);
        setImageSource(imageSource);
    }

    public void setImage(){
        //Instantiating image with glide

        ImageView view = this;
        if (getImageDuration() != 0 ){
            delay(view);
        }

        Glide.with(getContext()).load(imageSource).override(width, height).into(view);
        this.setContentDescription("Image of muscle groups targeted by this exercise");
    }

    /** Sets the duration the image is displayed on the slide. */
    public void delay(ImageView imageView){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(getContext()).clear(imageView);
            }
        }, this.getImageDuration());
    }
}
