package uk.ac.york.nimblefitness.Screens.Routines;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.york.nimblefitness.Adapters.CustomExpandableListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextModule;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Exercises.ExerciseFragment;
/*
Fragment for displaying the routines in an expandable list view
Each Parent element is a routine, and the children elements are the exercises in said routine
The routine information is loaded in through xml, and is passed onto the RoutineExerciseActivity for when the user starts a routine

 */
public class RoutinesFragment extends Fragment {


    CustomExpandableListAdapter listAdapter;
    ArrayList<Routine> routineArrayList;
    TextView nothingFound;
    ExpandableListView routineListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Routines");
        View view = inflater.inflate(R.layout.fragment_routines, container, false); //shows the fragment_settings.xml file in the frame view of the activity_main.xml

        routineListView = view.findViewById(R.id.routine_exp_list);
        SearchView routineSearch = view.findViewById(R.id.routine_search);
        routineSearch.setActivated(true);
        routineSearch.setQueryHint("Search for Routines");
        routineSearch.onActionViewExpanded();
        routineSearch.setIconified(false);


        routineArrayList = setUpRoutines();
        listAdapter = new CustomExpandableListAdapter(getContext(), routineArrayList);

        // setting list adapter
        routineListView.setAdapter(listAdapter);

        // Listview on child click listener
        routineListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
                Toast.makeText(getContext(),routineArrayList.get(groupPosition).getRoutineName()
                        + " : "
                        + routineArrayList.get(groupPosition).getExerciseArrayList().get(childPosition).getExerciseName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        routineListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getContext(),routineArrayList.get(groupPosition).getRoutineName() + " Expanded",Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        routineListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(),routineArrayList.get(groupPosition).getRoutineName() + " Collapsed",Toast.LENGTH_SHORT).show();

            }
        });
        routineSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                nothingFound = view.findViewById(R.id.nothing_found_routines);
                boolean successfulSearch = listAdapter.filterData(query);
                if (!successfulSearch) {
                    nothingFound.setVisibility(View.VISIBLE);
                }
                else{
                    nothingFound.setVisibility(View.GONE);
                    expandAll();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                nothingFound = view.findViewById(R.id.nothing_found_routines);
                boolean successfulSearch = listAdapter.filterData(query);
                if (!successfulSearch) {
                    nothingFound.setVisibility(View.VISIBLE);
                }
                else if (query.isEmpty()) {
                    collapseAll();
                }
                else {
                    nothingFound.setVisibility(View.GONE);
                    expandAll();
                }
                return false;
            }
        });

        return view;
    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            routineListView.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            routineListView.collapseGroup(i);
        }
    }

    public ArrayList<Routine> setUpRoutines() {
        //Instantiate variables for collecting data of each routine to display
        ArrayList<Routine> listOfRoutines = new ArrayList<>();

        //Sets up an array of routines with the data loaded into each routine
        for (int i = 0; i < 12; i++) {
            Routine routine = new Routine();
            routine = routine.getExampleRoutine();
            listOfRoutines.add(routine);
        }
        return listOfRoutines;
    }
}