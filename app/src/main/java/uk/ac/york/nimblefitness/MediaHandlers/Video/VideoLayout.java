package uk.ac.york.nimblefitness.MediaHandlers.Video;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.Serializable;

import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;

public class VideoLayout implements Serializable, AbstractLayout {
    String uriPath, id;
    int width, height, xStart, yStart, startTime;
    boolean loop;
    FrameLayout parentLayout;
    Context context;

    public VideoLayout(String uriPath, int width, int height, int xStart, int yStart, String id,
                       int startTime, boolean loop, FrameLayout parentLayout, Context context) {
        this.uriPath = uriPath;
        this.width = width;
        this.height = height;
        this.xStart = xStart;
        this.yStart = yStart;
        this.id = id;
        this.startTime = startTime;
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

    public int getxStart() {
        return this.xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getyStart() {
        return this.yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
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

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin=this.xStart;
        params.topMargin=this.yStart;
        videoView.setLayoutParams(params);

        parentLayout.addView(videoView);
        if(this.width != 0 && this.height != 0) {
            videoView.resizeVideo(this.width, this.height);
        }
        videoPlayer.loadAndPlayVideo(UrlPath,this.loop,videoView, this.xStart,this.yStart,
                this.id,this.startTime);
    }

    @Override
    public String getMediaId() {
        return id;
    }
}
