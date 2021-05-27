package uk.ac.york.nimblefitness.Screens.Profile.Calendar;

import android.widget.ListView;
import android.widget.Toast;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;

public class CalendarPresenter implements CalendarContract.Presenter{

    private CalendarFragment calendarView;
    private CalendarModel calendarModel;

    public CalendarPresenter(CalendarFragment calendarView, CalendarModel calendarModel) {
        this.calendarView = calendarView;
        this.calendarModel = calendarModel;
    }

    @Override
    public String displayCurrentDayNumber() {
        return calendarModel.currentDayNumber();
    }

    @Override
    public long setStartDate() {
        return calendarModel.userStartDate();
    }

    @Override
    public String displaySelectedDay(int month, int dayOfMonth) {
        return calendarModel.selectedDay(month, dayOfMonth);
    }

    @Override
    public String displayUserName() {
        return calendarModel.currentUser();
    }

    @Override
    public MovesListAdapter setCompletedMovesList() {
        return calendarModel.completedMoves(calendarView.getContext());
    }

    @Override
    public void setListViewHeightBasedOnChildren (ListView listView){
        calendarModel.setListViewHeightBasedOnChildren(listView);
    }
}
