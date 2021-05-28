package uk.ac.york.nimblefitness.MediaHandlers.Images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageModule extends AppCompatImageView {

    //Declaring the variables for the image
    private int xCoordinate;
    private int yCoordinate;
    private int width;
    private int height;
    private int duration;
    private String imageSource;

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


    public void setAll(int xCoordinate, int yCoordinate, int width, int height, int duration, String imageSource){
        setXCoordinate(xCoordinate);
        setYCoordinate(yCoordinate);
        setImageWidth(width);
        setImageHeight(height);
        setImageDuration(duration);
        setImageSource(imageSource);
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("TAG", "Error getting bitmap", e);
        }
        return bm;
    }

    public void setImage(){

        String fileSource = imageSource;
        //Sets Image Source
        Glide.with(getContext()).load(imageSource).override(width, height).into(this);


        //Sets the image height and width
        //setImageWidth(width);
        //setImageHeight(height);


    }

    //public void removeImage()
}
