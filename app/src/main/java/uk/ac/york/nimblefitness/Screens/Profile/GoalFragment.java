package uk.ac.york.nimblefitness.Screens.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import pl.pawelkleczkowski.customgauge.CustomGauge;
import uk.ac.york.nimblefitness.R;

/*
 This class initialises the 'Goal' tab within the profile page of the app. It includes the
 'goal gauge' which shows how many moves the user has completed for the day/week and the list below
 the gauge gives a breakdown of these moves in terms of the exercises. There is also a randomised
 motivational quote below the gauge which changes every time the user views the goal tab.
*/

public class GoalFragment extends Fragment implements GoalContract.GoalView {
    GoalPresenter goalPresenter;
    // This method initialises the fragment.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goalPresenter = new GoalPresenter(this, new GoalModel());
    }
    // This method creates the appearance of the fragment and inflates the goal tab layout so it's
    // visible.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Profile"); // The title that appears at the top of the page
        View view = inflater.inflate(R.layout.fragment_goal, container, false);
        // The gauge which shows the number of goals completed and to do.
        CustomGauge gauge = (CustomGauge) view.findViewById(R.id.gauge);
        // Sets the current value of the gauge and subsequently how much it's filled.
        gauge.setValue(goalPresenter.displayGaugeInfo());
        // Sets the maximum value of the gauge.
        gauge.setEndValue(goalPresenter.setGaugeEndValue());
        // The value of the gauge is displayed as text.
        TextView gaugeNumber = view.findViewById(R.id.moves_counter);
        gaugeNumber.setText(String.valueOf(goalPresenter.displayGaugeInfo()));
        // Finds where the quote should go in this layout.
        TextView motivationQuote = view.findViewById(R.id.motivation);
        motivationQuote.setText(goalPresenter.displayQuote()); // Sets the quote.

        TextView userName = view.findViewById(R.id.user_name);
        userName.setText(goalPresenter.displayUserName()); // Displays the user's name.

        ListView listView = (ListView) view.findViewById(R.id.todays_moves); //Finds where the string array 'moves_to_do' should go.

        String[] moves_to_do = {"Plank", "Squats", "Sit-Ups", "Press-ups"}; //A list of the moves completed/in progress for the current day.
        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, moves_to_do); //need to make a 'simple_list_item_1' replacement -> 'settings_list_layout' allow the use of images in the list view like in settings activity example
        listView.setAdapter(ListViewAdapter);

        return view;
    }
}
