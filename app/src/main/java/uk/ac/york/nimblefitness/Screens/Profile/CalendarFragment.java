package uk.ac.york.nimblefitness.Screens.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uk.ac.york.nimblefitness.R;

public class CalendarFragment extends Fragment {

    private static final String TAG = "log";
    FirebaseDatabase rootDatabase;
    DatabaseReference rootReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Profile");
        View view = inflater.inflate(R.layout.fragment_calendar, container, false); //shows the fragment_settings.xml file in the frame view of the activity_main.xml

        String[] completed_activity_list = {"Press-ups", "Sit-ups", "Plank", "Crunches"}; //the text that goes in each different list view item

        ListView listView = (ListView) view.findViewById(R.id.completed_moves_list); //find the list view from the fragment_settings.xml file
        CalendarView calendarView = view.findViewById(R.id.profile_calendar);
        TextView dayNumber = view.findViewById(R.id.profile_date);
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
                     userName.setText(String.format("%s %s", user_firstName, user_lastName));
                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        SimpleDateFormat month = new SimpleDateFormat("M");
        SimpleDateFormat day = new SimpleDateFormat("d");
        Date currentTime = Calendar.getInstance().getTime();
        String monthString = month.format(currentTime);
        String dayString = day.format(currentTime);

        dayNumber.setText(String.format("%s %s%s", monthText(Integer.parseInt(monthString)), dayString, datePrefix(Integer.parseInt(dayString))));
        //the user can only select a date in the range from when they signed up to the app up to today's date
        long today = calendarView.getDate();
        if (currentFirebaseUser != null) {
            long startDate = currentFirebaseUser.getMetadata().getCreationTimestamp();
            calendarView.setMinDate(startDate);
        }
        calendarView.setMaxDate(today);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dayNumber.setText(String.format("%s %d%s", monthText(month + 1), dayOfMonth, datePrefix(dayOfMonth)));
            }
        });

        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, completed_activity_list); //need to make a 'simple_list_item_1' replacement -> 'settings_list_layout' allow the use of images in the list view like in settings activity example
        listView.setAdapter(ListViewAdapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {//watches for a user to click on the list view, then gives the program which position the click was in
            String itemValue = (String) listView.getItemAtPosition(position); //converts the position ID into the text that is written in that position

            Toast toast = Toast.makeText(getActivity(), itemValue, Toast.LENGTH_SHORT); //shows an alert with the text of the list item that has been clicked
            toast.show();

        });
        return view;
    }

    public String monthText(int month){
        switch (month){
            case 1: return("January");
            case 2: return("February");
            case 3: return("March");
            case 4: return("April");
            case 5: return("May");
            case 6: return("June");
            case 7: return("July");
            case 8: return("August");
            case 9: return("September");
            case 10: return("October");
            case 11: return("November");
            case 12: return("December");
            default: return("error");
        }
    }

    public String datePrefix(int dayOfMonth){
        switch (dayOfMonth){
            case 1:
            case 21:
            case 31:
                return ("st");
            case 2:
            case 22:
                return ("nd");
            case 3:
            case 23:
                return ("rd");
            default: return ("th");
        }
    }


}


