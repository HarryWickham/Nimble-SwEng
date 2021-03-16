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


public class GoalFragment extends Fragment {

    FirebaseDatabase rootDatabase;
    DatabaseReference rootReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        View view = inflater.inflate(R.layout.fragment_goal, container, false); //inflates the goal tab layout so it's visible

        String[] moves_to_do = {"Plank", "Squats", "Sit-Ups", "Press-ups"}; //A list of the moves completed/in progress for the current day
        ListView listView = (ListView) view.findViewById(R.id.todays_moves); //finds where the string array 'moves_to_do' should go
        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, moves_to_do); //need to make a 'simple_list_item_1' replacement -> 'settings_list_layout' allow the use of images in the list view like in settings activity example
        listView.setAdapter(ListViewAdapter);

        CustomGauge gauge = (CustomGauge) view.findViewById(R.id.gauge);
        int gaugeValue = 33;
        gauge.setValue(gaugeValue);//This will eventually be coded such that the completed moves is used in setValue
        gauge.setEndValue(100);    // and the moves to complete for a day/week is used in setEndValue.
        TextView gaugeNumber = view.findViewById(R.id.moves_counter);
        gaugeNumber.setText(String.valueOf(gaugeValue));

        String[] quotes = {"You can do it!","Feel the burn.","Time to make some gains.","Time to exercise!"}; //List of quotes which may appear on the 'goals' tab of the profile page
        TextView motivationQuote = view.findViewById(R.id.motivation); //finds where the quote should go in this layout
        Random rand = new Random(); //The quote changes each time the user opens the goal tab
        int n = rand.nextInt(quotes.length);
        switch (n){
            case 0:
                motivationQuote.setText(quotes[0]);
                break;
            case 1:
                motivationQuote.setText(quotes[1]);
                break;
            case 2:
                motivationQuote.setText(quotes[2]);
                break;
            case 3:
                motivationQuote.setText(quotes[3]);
                break;
            default:
                motivationQuote.setText("Improve your health today!");
        }

        TextView userName = view.findViewById(R.id.user_name);
        rootDatabase = FirebaseDatabase.getInstance();
        rootReference = rootDatabase.getReference("users");
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user_firstName = snapshot.child(currentFirebaseUser.getUid()).child("firstName").getValue(String.class);
                String user_lastName = snapshot.child(currentFirebaseUser.getUid()).child("lastName").getValue(String.class);

                userName.setText(user_firstName + " " + user_lastName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }
}
