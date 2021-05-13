package uk.ac.york.nimblefitness.MediaHandlers.Video;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import uk.ac.york.nimblefitness.MediaHandlers.Text.TextModule;

public class VideoLayout {
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

    public void PlayVideo(){
        VideoPlayer videoPlayer = new VideoPlayer();
        Uri UrlPath=Uri.parse(this.uriPath);

        CustomVideoView videoView = new CustomVideoView(this.context);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin=this.xstart;
        params.topMargin=this.ystart;
        videoView.setLayoutParams(params);

        parentLayout.addView(videoView);
        videoView.resizeVideo(this.width,this.height);
        videoPlayer.loadAndPlayVideo(UrlPath,this.loop,videoView,this.xstart,this.ystart,this.id,this.starttime);
    }
}
