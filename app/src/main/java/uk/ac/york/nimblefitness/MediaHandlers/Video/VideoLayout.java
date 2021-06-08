package uk.ac.york.nimblefitness.MediaHandlers.Video;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.Serializable;

import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextModule;

public class VideoLayout implements Serializable, AbstractLayout {
    String uriPath, id;
    int width, height, xstart, ystart, starttime;
    boolean loop;
    FrameLayout parentLayout;
    Context context;

    public VideoLayout(String uriPath, int width, int height, int xstart, int ystart, String id, int starttime, boolean loop, FrameLayout parentLayout, Context context) {
        this.uriPath = uriPath;
        this.width = width;
        this.height = height;
        this.xstart = xstart;
        this.ystart = ystart;
        this.id = id;
        this.starttime = starttime;
        this.loop = loop;
        this.parentLayout = parentLayout;
        this.context = context;
    }

    public void setParentLayout(FrameLayout parentLayout) {
        this.parentLayout = parentLayout;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getXstart() {
        return this.xstart;
    }

    public void setXstart(int xstart) {
        this.xstart = xstart;
    }

    public int getYstart() {
        return this.ystart;
    }

    public void setYstart(int ystart) {
        this.ystart = ystart;
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

    @Override
    public void draw() {
        VideoPlayer videoPlayer = new VideoPlayer();
        Uri UrlPath=Uri.parse(this.uriPath);

        CustomVideoView videoView = new CustomVideoView(this.context);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin=this.xstart;
        params.topMargin=this.ystart;
        videoView.setLayoutParams(params);

        parentLayout.addView(videoView);
        if(this.width != 0 && this.height != 0) {
            videoView.resizeVideo(this.width, this.height);
        }
        videoPlayer.loadAndPlayVideo(UrlPath,this.loop,videoView,this.xstart,this.ystart,this.id,this.starttime);
    }

    @Override
    public String getMediaId() {
        return id;
    }
}
