package uk.ac.york.nimblefitness.MediaHandlers.Audio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.Serializable;

public class AudioType implements Serializable {
    String url;
    int starttime;
    boolean loop;
    String id;
    Context context;

    public AudioType(String url, int starttime, boolean loop, String id, Context context) {
        this.url = url;
        this.starttime = starttime;
        this.loop = loop;
        this.id = id;
        this.context = context;
    }

    public AudioType() {
    }

    public void play(){
        SharedPreferences prefs  = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Log.i("prefs putString URL", url);
        editor.putString("url",url);
        editor.putBoolean("loop",loop);
        editor.apply();
        context.startService(new Intent(context.getApplicationContext(), Audio.class));
    }

    public void stop(){
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
}
