package uk.ac.york.nimblefitness.Screens.Exercises;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.Adapters.ExerciseListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.R;


public class ExerciseFragment extends Fragment {

    ListView list;
    SearchView exercises;
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
        exercises = view.findViewById(R.id.search);
        exercises.setActivated(true);
        exercises.setQueryHint("Search for exercises");
        exercises.onActionViewExpanded();
        exercises.setIconified(false);

        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<Exercise> exersiseList = new ArrayList<>();
        Exercise exercise = new Exercise();
        exercise.setColour(Color.parseColor("#ff6200"));
        exercise.setExerciseName("Normal Push Up");
        arrayList.add("Normal Push Up");
        arrayList.add("Wide Push Up");
        arrayList.add("Closed Push Up");
        arrayList.add("Spiderman Push Up");
        arrayList.add("Tricep Dip");
        arrayList.add("Plank");
        arrayList.add("Side Plank");
        arrayList.add("Flutter Kicks");
        arrayList.add("Cross Kicks");
        arrayList.add("Russian Twist");
        arrayList.add("Ankle Taps");
        arrayList.add("Sit Ups");
        arrayList.add("Superman");
        arrayList.add("Squats");
        arrayList.add("Lunges");
        arrayList.add("Calf Raises");
        arrayList.add("Hip Thruster");
        arrayList.add("Side Plank Kicks");
        arrayList.add("Burpees");
        arrayList.add("Step Ups");

        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                //android.R.layout.simple_list_item_1,
                //arrayList);
        exersiseList.add(exercise);
        ExerciseListAdapter arrayAdapter = new ExerciseListAdapter(getContext(),exersiseList);
        list.setAdapter(arrayAdapter);


        /*exercises.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("exercises", newText);
                nothingFound = view.findViewById(R.id.nothingfoundmessage);
                arrayAdapter.getFilter().filter(newText, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int i) {
                        if (i == 0) {
                            nothingFound.setVisibility(View.VISIBLE);
                            }
                        else{
                            nothingFound.setVisibility(View.GONE);
                        }
                    }
                });
                return false;
            }
        });*/
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) list.getItemAtPosition(position);
                Toast.makeText(getContext(), clickedItem, Toast.LENGTH_LONG).show();
            }
        });
        // Inflate the layout for this fragment
        return view;


    }

}