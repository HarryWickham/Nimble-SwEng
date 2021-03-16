package uk.ac.york.nimblefitness.Screens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Profile.ProfileFragment;
import uk.ac.york.nimblefitness.Screens.Routines.RoutinesFragment;
import uk.ac.york.nimblefitness.Screens.Search.SearchFragment;
import uk.ac.york.nimblefitness.Screens.Settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private ProfileFragment profileFragment;
    private RoutinesFragment routinesFragment;
    private SearchFragment searchFragment;
    private SettingsFragment settingsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileFragment = new ProfileFragment();
        routinesFragment = new RoutinesFragment();
        searchFragment = new SearchFragment();
        settingsFragment = new SettingsFragment();

        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        openFragment(new ProfileFragment());
        bottomNav.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.profile_page:
                    openFragment(profileFragment);
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