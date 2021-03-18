package uk.ac.york.nimblefitness.Screens.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import pl.pawelkleczkowski.customgauge.CustomGauge;
import uk.ac.york.nimblefitness.R;

/*
 This class initialises the 'Goal' tab within the profile page of the app. It includes the
 'goal gauge' which shows how many moves the user has completed for the day/week and the list below
 the gauge gives a breakdown of these moves in terms of the exercises. There is also a randomised
 motivational quote below the gauge which changes every time the user views the goal tab.
*/

public class GoalFragment extends Fragment {

    FirebaseDatabase rootDatabase;
    DatabaseReference rootReference;
    // This method initialises the fragment.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    // This method creates the appearance of the fragment and inflates the goal tab layout so it's
    // visible.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Profile"); // The title that appears at the top of the page
        View view = inflater.inflate(R.layout.fragment_goal, container, false);

        CustomGauge gauge = (CustomGauge) view.findViewById(R.id.gauge);
        TextView gaugeNumber = view.findViewById(R.id.moves_counter);
        updateGauge(gauge, 33, 100, gaugeNumber);

        String[] moves_to_do = {"Plank", "Squats", "Sit-Ups", "Press-ups"}; //A list of the moves completed/in progress for the current day.
        ListView listView = (ListView) view.findViewById(R.id.todays_moves); //Finds where the string array 'moves_to_do' should go.
        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, moves_to_do); //need to make a 'simple_list_item_1' replacement -> 'settings_list_layout' allow the use of images in the list view like in settings activity example
        listView.setAdapter(ListViewAdapter);

        String[] quotes = {"You can do it!","Feel the burn.","Time to make some gains.","Time to exercise!"}; //List of quotes which may appear on the 'goals' tab of the profile page.
        TextView motivationQuote = view.findViewById(R.id.motivation); //Finds where the quote should go in this layout.
        Random rand = new Random(); //The quote changes each time the user opens the goal tab.
        int n = rand.nextInt(quotes.length);
        String default_quote = "Improve your health today!";
        if ((n >= 0) && (n < quotes.length)) {//This 'if' statement always ensures a quote is shown.
            motivationQuote.setText(quotes[n]);
        } else {
            motivationQuote.setText(default_quote);
        }


        TextView userName = view.findViewById(R.id.user_name);
        rootDatabase = FirebaseDatabase.getInstance();
        rootReference = rootDatabase.getReference("users");
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (currentFirebaseUser != null) {
                    String user_firstName = snapshot.child(currentFirebaseUser.getUid()).child("firstName").getValue(String.class);
                    String user_lastName = snapshot.child(currentFirebaseUser.getUid()).child("lastName").getValue(String.class);
                    //The user's name is set in the section of the layout above the moves list.
                    userName.setText(String.format("%s %s", user_firstName, user_lastName));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }

    /*
     This method will update the value of the gauge when the user has completed a move/exercise.
     This will eventually be coded such that the user's number of completed moves is used in
     valueAdded and the moves to complete for a week/month is used in setEndValue.
    */
    void updateGauge(CustomGauge gauge, int valueAdded, int endValue, TextView gaugeNumber) {
        int startValue = 0;
        int gaugeValue = startValue + valueAdded;
        gauge.setValue(gaugeValue);
        gauge.setEndValue(endValue);
        gaugeNumber.setText(String.valueOf(gaugeValue));
    }

}
