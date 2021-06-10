package uk.ac.york.nimblefitness.MediaHandlers.Audio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;

import java.io.Serializable;

import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;

public class AudioType implements Serializable, AbstractLayout {
    String url;
    int starttime;
    boolean loop, isPlaying = false;
    String id;
    Context context;
    Intent musicIntent;

    public AudioType(String url, int starttime, boolean loop, String id, Context context) {
        this.url = url;
        this.starttime = starttime;
        this.loop = loop;
        this.id = id;
        this.context = context;
    }

    public AudioType() {
    }

    public void stop() {
        context.stopService(new Intent(context, Audio.class));
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void draw() {
        musicIntent = new Intent(context.getApplicationContext(), Audio.class);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("url", url);
        editor.putBoolean("loop", loop);
        editor.apply();
        delay(this);
    }

    @Override
    public String getMediaId() {
        return id;
    }

    /**
     * This method delays when the audio starts as set by the user using setStartTime.
     */
    public void delay(AudioType audioType) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                context.startService(musicIntent);
                isPlaying = true;
            }
        }, audioType.getStarttime());
    }

    @Override
    public void playPause() {
        if (isPlaying) {
            context.stopService(musicIntent);
            isPlaying = false;
        } else {
            context.startService(musicIntent);
        }
    }
}
