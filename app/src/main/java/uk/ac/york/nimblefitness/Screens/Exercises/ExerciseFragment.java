package uk.ac.york.nimblefitness.Screens.Exercises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.Adapters.ExerciseListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Routines.RoutineData;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/*
Fragment for displaying the available exercises and allowing the user to search for them.
The ExerciseListAdapter is used to allow the exercises to be selected and loaded onto the screen.
SearchView allows the exercises to be searched for by the user.
*/
public class ExerciseFragment extends Fragment {

    ListView list; //list displays the exercises that can be searched for
    SearchView exercisesSearch; //exercisesSearch establishes the SearchView
    TextView nothingFound; //nothingFound displays a message if not items are found from a search

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //sets the title of the screen
        requireActivity().setTitle("Search for exercise");

        //loads the correct xml file for the layout
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        list = view.findViewById(R.id.list);
        exercisesSearch = view.findViewById(R.id.search);
        exercisesSearch.setActivated(true);
        exercisesSearch.setQueryHint("Search for exercises");
        exercisesSearch.onActionViewExpanded();
        exercisesSearch.setIconified(false);
        exercisesSearch.clearFocus();

        // Object of routine data, that holds all the data from the routines.xml
        RoutineData routineData = new RoutineData(getContext(), R.raw.exercise);
        ArrayList<Exercise> exercises =
                (ArrayList<Exercise>) routineData.getRoutine().get(0).getExerciseArrayList().clone();

        ExerciseListAdapter arrayAdapter = new ExerciseListAdapter(getContext(), exercises);
        list.setAdapter(arrayAdapter);

        //the method for the searching functionality
        exercisesSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                nothingFound = view.findViewById(R.id.nothingfoundmessage);
                boolean successfulSearch = arrayAdapter.filterData(query);
                list.setVisibility(VISIBLE);
                if (!successfulSearch) {
                    nothingFound.setVisibility(VISIBLE);
                    list.setVisibility(GONE);
                } else {
                    nothingFound.setVisibility(GONE);
                }
                return false;
            }
        });
        return view;
    }
}