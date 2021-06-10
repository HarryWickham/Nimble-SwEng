package uk.ac.york.nimblefitness.MediaHandlers.Video;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.Serializable;

public class VideoPlayer extends Activity implements Serializable {
    private MediaController controller;
    private volatile VideoView view;
    private int xstart;
    private int ystart;
    private String id;
    private int startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStop() {
        super.onStop();
        view.suspend();
    }

    public void loadAndPlayVideo(Uri path, Boolean loop, VideoView outsideView, int xStart,
                                 int yStart, String id, int startTime) {
        this.xstart = xStart;
        this.ystart = yStart;
        this.id = id;
        this.startTime = startTime;
        final Runnable runnable = () -> {
            try {
                try {
                    this.view = outsideView;
                    view.setOnPreparedListener(mp -> {
                        view.start();
                        mp.setLooping(loop);
                        mp.setOnVideoSizeChangedListener((mp1, width, height) -> {
                            view.setMediaController(controller);
                            controller.setAnchorView(view);

                            view.pause();
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                /** The start of the video is delayed by the value set by the user
                                 * in startTime.
                                 */
                                @Override
                                public void run() {
                                    view.start();
                                }
                            }, this.startTime * 1000);

                        });

                    });
                    view.setVideoURI(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                controller = new MediaController(view.getContext(), loop);
                controller.setEnabled(true);
                controller.show(2);
                controller.setVisibility(View.VISIBLE);

                view.setContentDescription("Video showing how a rep should be completed");

                view.setOnGenericMotionListener((v, event) -> {
                    controller.show(3);
                    return true;
                });

                view.setOnErrorListener((mp, what, extra) -> {
                    mp.release();
                    return true;
                });

            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("Video killed the Player Program");
                view.suspend();
            }


        };
        Thread videoThread = new Thread(runnable);
        videoThread.start();
    }

    public void playPause() {
        if (view.isPlaying()) {
            view.pause();
        } else {
            view.start();
        }
    }

    public int getXstart() {
        return xstart;
    }

    public void setXstart(int xStart) {
        this.xstart = xStart;
    }

    public int getYstart() {
        return ystart;
    }

    public void setYstart(int yStart) {
        this.ystart = yStart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
}