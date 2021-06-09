package uk.ac.york.nimblefitness.MediaHandlers.Button;

import android.content.Context;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;

public class ImageButtonType {
    int xstart, ystart, width, height;
    String slideid, mediaid, urlname;
    Context context;

    public ImageButtonType(int xstart, int ystart, int width, int height, String slideid,
                           String mediaid, String urlname, Context context) {
        this.xstart = xstart;
        this.ystart = ystart;
        this.width = width;
        this.height = height;
        this.slideid = slideid;
        this.mediaid = mediaid;
        this.urlname = urlname;
        this.context = context;
    }

    public ImageButton createButton(){
        ImageButton imageButton = new ImageButton(context);
        imageButton.setMaxWidth(width);
        imageButton.setMaxHeight(height);
        Glide.with(context).load(urlname).override(width, height).into(imageButton);
        return imageButton;
    }


    public int getXStart() {
        return xstart;
    }

    public void setXStart(int xstart) {
        this.xstart = xstart;
    }

    public int getYStart() {
        return ystart;
    }

    public void setYStart(int ystart) {
        this.ystart = ystart;
    }


    public void setWidth(int width) {
        this.width = width;
    }


    public void setHeight(int height) {
        this.height = height;
    }

    public String getSlideId() {
        return slideid;
    }

    public void setSlideId(String slideid) {
        this.slideid = slideid;
    }

    public String getMediaId() {
        return mediaid;
    }

    public void setMediaId(String mediaid) {
        this.mediaid = mediaid;
    }

    public String getUrlName() {
        return urlname;
    }

    public void setUrlName(String urlname) {
        this.urlname = urlname;
    }
}
