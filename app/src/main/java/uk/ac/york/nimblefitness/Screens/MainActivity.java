package uk.ac.york.nimblefitness.Screens;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.sentry.Sentry;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Exercises.ExerciseFragment;
import uk.ac.york.nimblefitness.Screens.Profile.ProfileTabsFragment;
import uk.ac.york.nimblefitness.Screens.Routines.RoutinesFragment;
import uk.ac.york.nimblefitness.Screens.Settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    AlertDialog internetChecker;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10000;
    private ProfileTabsFragment profileTabsFragment;
    private RoutinesFragment routinesFragment;
    private ExerciseFragment exerciseFragment;
    private SettingsFragment settingsFragment;

    /*@Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                connectedToTheInternet();
            }
        }, delay);
        super.onResume();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        internetChecker = new AlertDialog.Builder(MainActivity.this).create();
        profileTabsFragment = new ProfileTabsFragment();
        routinesFragment = new RoutinesFragment();
        exerciseFragment = new ExerciseFragment();
        settingsFragment = new SettingsFragment();

        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        openFragment(new ProfileTabsFragment());
        bottomNav.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.profile_page:
                    openFragment(profileTabsFragment);
                    return true;
                case R.id.routines_page:
                    openFragment(routinesFragment);
                    return true;
                case R.id.exercises_page:
                    openFragment(exerciseFragment);
                    return true;
                case R.id.settings_page:
                    openFragment(settingsFragment);
                    return true;
            }
            return false;
        });

        Sentry.captureMessage("testing SDK setup");


    }

    void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder exitApp = new AlertDialog.Builder(this);
        exitApp.setTitle("Would you like to close the app?");
        exitApp.setCancelable(true).setPositiveButton("Close", (dialog, id) -> {
            // if this button is clicked, close
            // current activity
            MainActivity.this.finish();
        }).setNegativeButton("Cancel", (dialog, id) -> {
            // if this button is clicked, just close
            // the dialog box and do nothing
            dialog.cancel();
        });

        // create alert dialog
        exitApp.create().show();

    }

    private void connectedToTheInternet() {
        if (!isNetworkConnected() | !internetIsConnected()) {
            internetChecker.setTitle("An error has occurred");
            internetChecker.setMessage("Please ensure you are connected to the internet");
            internetChecker.setCancelable(false);
            internetChecker.setButton(DialogInterface.BUTTON_NEGATIVE, "Retry", (dialog, id) -> {
                if (!isNetworkConnected() | !internetIsConnected()) {
                    connectedToTheInternet();
                    internetChecker.cancel();
                }
            });

            if (!internetChecker.isShowing()) {
                // create alert dialog
                internetChecker.show();
            }
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.co.uk";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}