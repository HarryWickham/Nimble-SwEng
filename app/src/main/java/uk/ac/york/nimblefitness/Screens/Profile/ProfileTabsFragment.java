package uk.ac.york.nimblefitness.Screens.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import uk.ac.york.nimblefitness.R;

public class ProfileTabsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchFragment(new CalendarFragment()); //the calendar tab is the first fragment to appear in the tab view
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        View view = inflater.inflate(R.layout.fragment_profile_tabs, container, false); //inflates the tab layout fragment so it's visible
        TabLayout profileTabs = (TabLayout) view.findViewById(R.id.profile_tabs); //finds where in the fragment the tab layout should go

        profileTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { //listens for when a user selects a new tab and shows the respective fragment
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
                    default:
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
                switchFragment(fragment); //this changes the visible fragment when a different tab is selected
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
    //this switches the current fragment displayed when the corresponding tab is selected
    void switchFragment(Fragment fragment) {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.profile_frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
