package uk.ac.york.nimblefitness.MediaHandlers.Audio;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.IOException;

public class Audio extends Service {
    MediaPlayer myPlayer;
    SharedPreferences prefs;

    @Override
    public void onCreate() {
        myPlayer = new MediaPlayer();
        myPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .build()
        );
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String URL = prefs.getString("url","");
        try {
            myPlayer.setDataSource(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myPlayer.prepare();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        myPlayer.setLooping(true); // Set looping, setting loop play to true
        Toast.makeText(this, "Service Successfully Created", Toast.LENGTH_LONG).show();
    }
   @Override
    public void onStart(Intent intent, int startid) {
       Boolean loop = prefs.getBoolean("loop",false);
       myPlayer.start();
       Toast.makeText(this, "Service Started and Playing Music", Toast.LENGTH_LONG).show();
       myPlayer.setLooping(loop);

    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped and Music Stopped", Toast.LENGTH_LONG).show();
        myPlayer.stop();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}