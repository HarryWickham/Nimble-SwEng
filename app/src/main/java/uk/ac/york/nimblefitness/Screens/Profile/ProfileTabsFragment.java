package uk.ac.york.nimblefitness.Screens.Profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Profile.Calendar.CalendarFragment;
import uk.ac.york.nimblefitness.Screens.Profile.Goal.GoalFragment;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

/** This class initialises the Tab Layout within the profile page and allows the user to switch and
 * view the different tabs: Calendar, Favourites & Goals.
 */
public class ProfileTabsFragment extends Fragment {
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchFragment(new CalendarFragment());
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Profile");
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        Log.i("onCreateView", prefs.getString("membershipPlan", "bronze"));

        if(prefs.getString(currentFirebaseUser+"membershipPlan", "bronze").
                equals("gold")) {
            view = inflater.
                    inflate(R.layout.fragment_profile_tabs_gold, container, false);
        }else{
            view = inflater.inflate(R.layout.fragment_profile_tabs, container, false);
        }
        // Finds where in the fragment the tab layout should go. //
        TabLayout profileTabs = view.findViewById(R.id.profile_tabs);

        profileTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            /** This listener listens for when a user selects a new tab and shows the respective
             * fragment.
             */
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
                    case 3:
                        fragment = new LeaderBoardFragment();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
                // This changes the visible fragment when a different tab is selected. //
                switchFragment(fragment);
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
    /** This switches the current fragment displayed when the corresponding tab is selected. */
    void switchFragment(Fragment fragment) {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.profile_frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}