package uk.ac.york.nimblefitness.Screens.Profile.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import uk.ac.york.nimblefitness.HelperClasses.ShareService;
import uk.ac.york.nimblefitness.R;

public class CalendarFragment extends Fragment implements CalendarContract.CalendarView{

    CalendarPresenter calendarPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendarPresenter = new CalendarPresenter(this, new CalendarModel());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Profile"); // The title that appears at the top of the page.
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        // The calendar has its minimum and maximum selectable date set and the default selected
        // date set which is displayed as text.
        CalendarView calendarView = view.findViewById(R.id.profile_calendar);
        calendarView.setMinDate(calendarPresenter.setStartDate());
        calendarView.setMaxDate(calendarView.getDate());
        TextView dayNumber = view.findViewById(R.id.profile_date);
        dayNumber.setText(calendarPresenter.displayCurrentDayNumber());
        // The displayed date text changes accordingly when another day is selected besides the
        // default.
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) ->
                dayNumber.setText(calendarPresenter.displaySelectedDay(month, dayOfMonth)));
        // The user's name is displayed above the list of completed moves.
        TextView userName = view.findViewById(R.id.user_name);
        userName.setText(calendarPresenter.displayUserName());
        // For the currently selected day, a list of the moves completed on that particular day is
        // displayed.
        ListView listView = (ListView) view.findViewById(R.id.completed_moves_list);
        listView.setAdapter(calendarPresenter.setCompletedMovesList());

        ImageButton shareButton = view.findViewById(R.id.share_icon);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new ShareService("Look at my workout", "I have just completed all of this exercise", "Share your workout - "+ dayNumber.getText()).ShareContent());
            }
        });

        calendarPresenter.setListViewHeightBasedOnChildren(listView);

        return view;
    }

    // This method is used to send the fragment's context to the Presenter so the list of completed
    // moves can be set.
    @Override
    public Context getContext() {
        return getActivity();
    }


}
