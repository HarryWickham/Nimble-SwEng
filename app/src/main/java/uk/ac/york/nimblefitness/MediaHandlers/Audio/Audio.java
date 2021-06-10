package uk.ac.york.nimblefitness.MediaHandlers.Audio;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.preference.PreferenceManager;

import java.io.IOException;

public class Audio extends Service {
    MediaPlayer myPlayer;
    SharedPreferences prefs;

    @Override
    public void onCreate() {
        //Set up media player
        myPlayer = new MediaPlayer();
        myPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME).build());
        //Set up shared preferences to allow URL and Looping function to be passed to service
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String URL = prefs.getString("url", "");
        //To catch an invalid URL
        try {
            myPlayer.setDataSource(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //To catch if not fully downloaded audio file
        try {
            myPlayer.prepare();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        myPlayer.setLooping(true); // Set looping, setting loop play to true
    }

    /**
     * This sets the looping value
     */
    @Override
    public void onStart(Intent intent, int startID) {
        //Set the looping value
        Boolean loop = prefs.getBoolean("loop", false);
        myPlayer.start();
        myPlayer.setLooping(loop);

    }

    /**
     * This method is triggered when the app stops.
     */
    @Override
    public void onDestroy() {
        myPlayer.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}