package uk.ac.york.nimblefitness.Screens.Exercises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
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


public class ExerciseFragment extends Fragment {

    ListView list;
    SearchView exercisesSearch;
    TextView nothingFound;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        requireActivity().setTitle("Search for exercise");

        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        list = view.findViewById(R.id.list);
        exercisesSearch = view.findViewById(R.id.search);
        exercisesSearch.setActivated(true);
        exercisesSearch.setQueryHint("Search for exercises");
        exercisesSearch.onActionViewExpanded();
        exercisesSearch.setIconified(false);

        RoutineData routineData = new RoutineData(getContext(), R.raw.exercise); // Object of routine data, that holds all the data from the routines.xml
        ArrayList<Exercise> exercises = (ArrayList<Exercise>) routineData.getRoutine().get(0).getExerciseArrayList().clone();

        ExerciseListAdapter arrayAdapter = new ExerciseListAdapter(getContext(),exercises);
        list.setAdapter(arrayAdapter);


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
                }
                else {
                    nothingFound.setVisibility(GONE);
                }
                return false;
            }
        });
        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) list.getItemAtPosition(position).;
                //Toast.makeText(getContext(), clickedItem, Toast.LENGTH_LONG).show();
            }
        });*/
        return view;


    }

}