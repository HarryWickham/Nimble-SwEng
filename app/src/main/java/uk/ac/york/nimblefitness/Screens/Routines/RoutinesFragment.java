package uk.ac.york.nimblefitness.Screens.Routines;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import uk.ac.york.nimblefitness.Adapters.CustomExpandableListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Fragment for displaying the routines in an expandable list view
 * Each Parent element is a routine, and the children elements are the exercises in said routine
 * The routine information is loaded in through xml, and is passed onto the
 * RoutineExerciseActivity for when the user starts a routine
 * There is also a SearchView that can search for exercises or routines in the ExpandableListView
 */
public class RoutinesFragment extends Fragment {


    CustomExpandableListAdapter listAdapter; // listAdapter to mediate between the source code
    // and the screen
    TextView nothingFound; // TextView used to alert user when their search yields no results
    ExpandableListView routineListView; // Instantiated ExpandableListView to show the code as an
    // expandable list


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_routines, container, false); // shows the
        // fragment_routines.xml file in the frame view of the activity_main.xml

        RoutineData routineData = new RoutineData(getContext(), R.raw.routines); // Object of
        // routine data, that holds all the data from the routines.xml
        ArrayList<Routine> routine = routineData.getRoutine();

        SharedPreferences prefs = getDefaultSharedPreferences(getContext()); // Sets up firebase
        // data for routines for the user
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // If statement to display how many routines a user has left, if they're not on the gold
        // plan
        if (getUserMembershipPlanRoutines(prefs, currentFirebaseUser) > 40) {
            requireActivity().setTitle("Routines - Remaining: " + DecimalFormatSymbols.getInstance()
                    .getInfinity());
        } else {
            requireActivity().setTitle("Routines - Remaining: " +
                    (getUserMembershipPlanRoutines(prefs, currentFirebaseUser) -
                            prefs.getInt(currentFirebaseUser + "completedRoutines", 0)));
        }

        routineListView = view.findViewById(R.id.routine_exp_list); // Assigns the ListView to
        // the layout 'routine_exp_list'
        SearchView routineSearch = view.findViewById(R.id.routine_search); // Assigns the
        // SearchView to the layout 'routine_search'
        routineSearch.setActivated(true);
        routineSearch.setQueryHint("Search for Routines and Exercises");
        routineSearch.onActionViewExpanded();
        routineSearch.setIconified(false);
        routineSearch.clearFocus();

        listAdapter = new CustomExpandableListAdapter(getContext(), routine); // Assigns the
        // listAdapter with the context and routine list

        // setting list adapter
        routineListView.setAdapter(listAdapter);

        // Listview listener for when an exercise is clicked
        routineListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                return false;
            }
        });

        // Listview listener for when a routine is expanded
        routineListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        // Listview listener for when a routine is collapsed
        routineListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        // SearchView listener for when a user starts typing
        routineSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // When query is submitted
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // When query is being typed
            @Override
            public boolean onQueryTextChange(String query) {
                //TextView to show text to user that no routines were found with a given search
                // input
                nothingFound = view.findViewById(R.id.nothing_found_routines);
                //Calls the function to filter the data and set up a new routineArrayList to display
                boolean successfulSearch = listAdapter.filterData(query);

                routineListView.setVisibility(VISIBLE);
                //
                if (!successfulSearch) {
                    nothingFound.setVisibility(VISIBLE);
                    routineListView.setVisibility(GONE);
                } else if (query.isEmpty()) {
                    collapseAll();
                } else {
                    nothingFound.setVisibility(GONE);
                    expandAll();
                }
                return false;
            }
        });

        return view;
    }

    /**
     * Method to expand all groups
     */
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            routineListView.expandGroup(i);
        }
    }

    /**
     * Method to collapse all groups
     */
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            routineListView.collapseGroup(i);
        }
    }

    /**
     * Gets information on user's plan to indicate how many routines a user has remaining
     *
     * @param prefs
     * @param currentFirebaseUser
     * @return number of routines a user is allowed in a month
     */
    public int getUserMembershipPlanRoutines(SharedPreferences prefs,
                                             FirebaseUser currentFirebaseUser) {
        switch (prefs.getString(currentFirebaseUser + "membershipPlan", "bronze")) {
            case "bronze":
                return 20;
            case "silver":
                return 40;
            case "gold":
                return 9999;
            default:
                return 0;
        }
    }
}