package uk.ac.york.nimblefitness.Screens.Routines;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import uk.ac.york.nimblefitness.Adapters.RoutineListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;

public class RoutinesFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Routines");

        View view = inflater.inflate(R.layout.fragment_routines, container, false); //shows the fragment_settings.xml file in the frame view of the activity_main.xml

        ListView routineListView = view.findViewById(R.id.routines_list);

        Routine routine1 = new Routine("Routine1", R.drawable.final_logo);
        Routine routine2 = new Routine("Routine2", R.drawable.final_logo);
        Routine routine3 = new Routine("Routine3", R.drawable.final_logo);

        ArrayList<Routine> routineArrayList = new ArrayList<>();
        routineArrayList.add(routine1);
        routineArrayList.add(routine2);
        routineArrayList.add(routine3);

        RoutineListAdapter adapter = new RoutineListAdapter(getContext(), R.layout.routines_list_layout, routineArrayList);
        routineListView.setAdapter(adapter);


        return view;
    }
}