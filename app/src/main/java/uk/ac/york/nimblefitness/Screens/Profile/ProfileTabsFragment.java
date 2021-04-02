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
import uk.ac.york.nimblefitness.Screens.Profile.Calendar.CalendarFragment;
import uk.ac.york.nimblefitness.Screens.Profile.Goal.GoalFragment;

/*
 This class initialises the Tab Layout within the profile page and allows the user to switch and
 view the different tabs: Calendar, Favourites & Goals.
*/
public class ProfileTabsFragment extends Fragment {
    // This method initialises the fragment.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //The calendar tab is the first fragment to appear in the tab view.
        switchFragment(new CalendarFragment());
    }
    // This method creates the appearance of the fragment and inflates the goal tab layout so it's
    // visible.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Profile");
        View view = inflater.inflate(R.layout.fragment_profile_tabs, container, false);
        TabLayout profileTabs = (TabLayout) view.findViewById(R.id.profile_tabs); //Finds where in the fragment the tab layout should go.
        //This listener listens for when a user selects a new tab and shows the respective fragment.
        profileTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                switchFragment(fragment); //This changes the visible fragment when a different tab is selected.
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
    //This switches the current fragment displayed when the corresponding tab is selected.
    void switchFragment(Fragment fragment) {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.profile_frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
