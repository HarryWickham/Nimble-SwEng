package uk.ac.york.nimblefitness.Screens;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}