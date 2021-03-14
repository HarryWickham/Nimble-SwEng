package uk.ac.york.nimblefitness.Screens.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Settings.SettingsFragment;

public class ProfileTabsFragment extends Fragment {

    private FrameLayout profileFrame;
    private TabLayout profileTabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        View view = inflater.inflate(R.layout.fragment_profile_tabs, container, false);

        profileFrame.findViewById(R.id.profile_frame);
        profileTabs.findViewById(R.id.profile_tabs);

        profileTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = new CalendarFragment();
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new GoalFragment();
                        break;
                    case 1:
                        fragment = new FavesFragment();
                        break;
                    case 2:
                        fragment = new CalendarFragment();
                        break;
                }

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
