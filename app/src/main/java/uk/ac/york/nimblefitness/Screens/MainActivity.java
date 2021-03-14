package uk.ac.york.nimblefitness.Screens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Profile.CalendarFragment;
import uk.ac.york.nimblefitness.Screens.Profile.ProfileTabsFragment;
import uk.ac.york.nimblefitness.Screens.Routines.RoutinesFragment;
import uk.ac.york.nimblefitness.Screens.Search.SearchFragment;
import uk.ac.york.nimblefitness.Screens.Settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private ProfileTabsFragment profileTabsFragment;
    private RoutinesFragment routinesFragment;
    private SearchFragment searchFragment;
    private SettingsFragment settingsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileTabsFragment = new ProfileTabsFragment();
        routinesFragment = new RoutinesFragment();
        searchFragment = new SearchFragment();
        settingsFragment = new SettingsFragment();

        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        openFragment(new ProfileTabsFragment());
        bottomNav.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.profile_page:
                    openFragment(profileTabsFragment);
                    return true;
                case R.id.routines_page:
                    openFragment(routinesFragment);
                    return true;
                case R.id.search_page:
                    openFragment(searchFragment);
                    return true;
                case R.id.settings_page:
                    openFragment(settingsFragment);
                    return true;
            }
            return false;
        });



    }

    void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder exitApp = new AlertDialog.Builder(this);
        exitApp.setTitle("Would you like to close the app?");
        Log.i("TAG", "onBackPressed: ");
        exitApp.setCancelable(true)
                .setPositiveButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        exitApp.create().show();

    }
}