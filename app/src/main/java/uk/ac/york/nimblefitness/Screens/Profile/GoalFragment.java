package uk.ac.york.nimblefitness.Screens.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Random;

import uk.ac.york.nimblefitness.R;


public class GoalFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        View view = inflater.inflate(R.layout.fragment_goal, container, false);

        String[] moves_to_do = {"Plank", "Squats", "Sit-Ups", "Press-ups"}; //A list of the moves completed/in progress for the current day
        ListView listView = (ListView) view.findViewById(R.id.todays_moves);
        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, moves_to_do); //need to make a 'simple_list_item_1' replacement -> 'settings_list_layout' allow the use of images in the list view like in settings activity example
        listView.setAdapter(ListViewAdapter);

        String[] quotes = {"You can do it!","Feel the burn.","Time to make some gains.","Time to exercise!"}; //List of quotes which appear on the 'goals' tab of the profile page
        TextView motivationQuote = view.findViewById(R.id.motivation);
        Random rand = new Random(); //The quote changes each time the user opens the app (?)
        int n = rand.nextInt(2);
        switch (n){
            case 0:
                motivationQuote.setText(quotes[0]);
            case 1:
                motivationQuote.setText(quotes[1]);
            case 2:
                motivationQuote.setText(quotes[2]);
            default:
                motivationQuote.setText(quotes[3]);
        }
        return view;
    }
}

