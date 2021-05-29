package uk.ac.york.nimblefitness.Screens.Routines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.york.nimblefitness.Adapters.CustomExpandableListAdapter;
import uk.ac.york.nimblefitness.R;

public class RoutinesFragment extends Fragment {


    CustomExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    List<Integer> listImageHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Routines");
        View view = inflater.inflate(R.layout.fragment_routines, container, false); //shows the fragment_settings.xml file in the frame view of the activity_main.xml

        ExpandableListView routineListView = view.findViewById(R.id.routine_exp_list);

        prepareListData();

        listAdapter = new CustomExpandableListAdapter(getContext(), listImageHeader, listDataHeader, listDataChild);

        // setting list adapter
        routineListView.setAdapter(listAdapter);

        // Listview on child click listener
        routineListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
                Toast.makeText(getContext(),listDataHeader.get(groupPosition)
                        + " : "
                        + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        routineListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getContext(),listDataHeader.get(groupPosition) + " Expanded",Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        routineListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(),listDataHeader.get(groupPosition) + " Collapsed",Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listImageHeader = new ArrayList<>();

        // Adding parent data
        listDataHeader.add("Beginners Upper Body");
        listDataHeader.add("Beginners Lower Body");
        listDataHeader.add("Beginners Core");
        listDataHeader.add("Easy Upper Body");
        listDataHeader.add("Easy Lower Body");
        listDataHeader.add("Easy Core");
        listDataHeader.add("Intermediate Upper Body");
        listDataHeader.add("Intermediate Lower Body");
        listDataHeader.add("Intermediate Core");
        listDataHeader.add("Hard Upper Body");
        listDataHeader.add("Hard Lower Body");
        listDataHeader.add("Hard Core");

        listImageHeader.add(R.drawable.upperbody);
        listImageHeader.add(R.drawable.lowerbody);
        listImageHeader.add(R.drawable.core);
        listImageHeader.add(R.drawable.upperbody);
        listImageHeader.add(R.drawable.lowerbody);
        listImageHeader.add(R.drawable.core);
        listImageHeader.add(R.drawable.upperbody);
        listImageHeader.add(R.drawable.lowerbody);
        listImageHeader.add(R.drawable.core);
        listImageHeader.add(R.drawable.upperbody);
        listImageHeader.add(R.drawable.lowerbody);
        listImageHeader.add(R.drawable.core);

        // Adding child data
        List<String> routine1 = new ArrayList<String>();
        routine1.add("5 Push Ups");
        routine1.add("20 second Plank");
        routine1.add("5 Tricep Dips over Chair");
        routine1.add("5 Superman Raises");

        List<String> routine2 = new ArrayList<String>();
        routine2.add("5 Bodyweight Squats");
        routine2.add("10 Bodyweight Lunges");
        routine2.add("10 Calf Raises");
        routine2.add("5 Hip Thrusters from Floor");

        List<String> routine3 = new ArrayList<String>();
        routine3.add("20 second Plank");
        routine3.add("15 second Side Plank (right side)");
        routine3.add("15 second Side Plank (left side)");
        routine3.add("10 Russian Twists (feet planted)");

        List<String> routine4 = new ArrayList<String>();
        routine4.add("10 Push Ups");
        routine4.add("25 second Plank");
        routine4.add("10 Tricep Dips Over Chair");
        routine4.add("10 Superman Raises");

        List<String> routine5 = new ArrayList<String>();
        routine5.add("10 Bodyweight Squats");
        routine5.add("10 Bodyweight Lunges");
        routine5.add("20 Calf Raises");
        routine5.add("10 Hip Thrusters from Floor");
        routine5.add("10 Step Up-Downs");

        List<String> routine6 = new ArrayList<String>();
        routine6.add("30 second Plank");
        routine6.add("15 second Side Plank (right side)");
        routine6.add("15 second Side Plank (left side)");
        routine6.add("Flutter Kicks for 30 seconds");
        routine6.add("10 Russian Twists (feet planted)");

        List<String> routine7 = new ArrayList<String>();
        routine7.add("10 Push Ups");
        routine7.add("10 Wide Push Ups");
        routine7.add("5 Closed Push Ups");
        routine7.add("10 Superman Raises");
        routine7.add("10 Tricep Dips Over Chair");

        List<String> routine8 = new ArrayList<String>();
        routine8.add("10 Bodyweight Squats");
        routine8.add("10 Bodyweight Lunges");
        routine8.add("20 Calf Raises");
        routine8.add("10 Hip Thrusters from Floor");
        routine8.add("10 Step Up-Downs");
        routine8.add("5 Side Raises from Side Plank (right side)");
        routine8.add("5 Side Raises from Side Plank (left side)");

        List<String> routine9 = new ArrayList<String>();
        routine9.add("45 second Plank");
        routine9.add("20 second Side Plank (right side)");
        routine9.add("20 second Side Plank (left side)");
        routine9.add("Cross Kicks for 30 seconds");
        routine9.add("10 Russian Twists (feet planted)");
        routine9.add("10 Ankle Taps from Laying Down");
        routine9.add("10 Sit Ups");

        List<String> routine10 = new ArrayList<String>();
        routine10.add("10 Push Ups");
        routine10.add("10 Wide Push Ups");
        routine10.add("10 Closed Push Ups");
        routine10.add("15 Superman Raises");
        routine10.add("10 Tricep Dips Over Chair");
        routine10.add("5 Spiderman Push Ups");

        List<String> routine11 = new ArrayList<String>();
        routine11.add("20 Bodyweight Squats");
        routine11.add("15 Bodyweight Lunges");
        routine11.add("25 Calf Raises");
        routine11.add("15 Hip Thrusters from Floor");
        routine11.add("5 Side Raises from Side Plank (right side)");
        routine11.add("5 Side Raises from Side Plank (left side)");
        routine11.add("10 Burpees");

        List<String> routine12 = new ArrayList<String>();
        routine12.add("60 second Plank");
        routine12.add("20 second Side Plank (right side)");
        routine12.add("20 second Side Plank (left side)");
        routine12.add("Cross Kicks for 30 seconds");
        routine12.add("20 Russian Twists (feet planted)");
        routine12.add("20 Ankle Taps from Laying Down");
        routine12.add("10 Sit Ups");
        routine12.add("Flutter Kicks for 30 seconds");

        listDataChild.put(listDataHeader.get(0), routine1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), routine2);
        listDataChild.put(listDataHeader.get(2), routine3);
        listDataChild.put(listDataHeader.get(3), routine4);
        listDataChild.put(listDataHeader.get(4), routine5);
        listDataChild.put(listDataHeader.get(5), routine6);
        listDataChild.put(listDataHeader.get(6), routine7);
        listDataChild.put(listDataHeader.get(7), routine8);
        listDataChild.put(listDataHeader.get(8), routine9);
        listDataChild.put(listDataHeader.get(9), routine10);
        listDataChild.put(listDataHeader.get(10), routine11);
        listDataChild.put(listDataHeader.get(11), routine12);
    }
}