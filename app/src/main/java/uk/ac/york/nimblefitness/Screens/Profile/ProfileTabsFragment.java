package uk.ac.york.nimblefitness.Screens.Profile;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Settings.SettingsFragment;

public class ProfileTabsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //the calendar tab is the first view to appear
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.profile_frame, new CalendarFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        // ^maybe could refactor this bit so it doesn't need to appear twice
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        View view = inflater.inflate(R.layout.fragment_profile_tabs, container, false); //inflates the tab layout fragment so it's visible
        TabLayout profileTabs = (TabLayout) view.findViewById(R.id.profile_tabs); //finds where in the fragment the tab layout should go

        profileTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { //listens for when a user selects a new tab and shows the respective tab
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new CalendarFragment();
                        break;
                    case 1:
                        fragment = new FavesFragment();
                        break;
                    case 2:
                        fragment = new GoalFragment();
                        break;
                }
                //this changes the visible fragment when a different tab is selected
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.profile_frame, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    return view;
    }
}
