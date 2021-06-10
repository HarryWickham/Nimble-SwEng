package uk.ac.york.nimblefitness.Screens.Profile.Calendar;

import android.widget.ListView;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;

/**
 * This class is used to interface between the View and Model associated with the Calendar tab.
 */
public class CalendarPresenter implements CalendarContract.Presenter {

    private final CalendarFragment calendarView;
    private final CalendarModel calendarModel;

    public CalendarPresenter(CalendarFragment calendarView, CalendarModel calendarModel) {
        this.calendarView = calendarView;
        this.calendarModel = calendarModel;
    }

    /**
     * Displays the current
     */
    @Override
    public String displayCurrentDayNumber() {
        return calendarModel.currentDayNumber();
    }

    /**
     * Sets the start date of selectable days on the calendar view.
     */
    @Override
    public long setStartDate() {
        return calendarModel.userStartDate();
    }

    /**
     * The selected day on the calendar is displayed as text below it.
     */
    @Override
    public String displaySelectedDay(int month, int dayOfMonth) {
        return calendarModel.selectedDay(month, dayOfMonth);
    }

    /**
     * Displays the user's name between the calendar and list of completed moves.
     */
    @Override
    public String displayUserName() {
        return calendarModel.currentUser();
    }

    /**
     * The list of completed moves is set with respect to the day they were completed on when that
     * day is selected in the calendar.
     */
    @Override
    public MovesListAdapter setCompletedMovesList(String dayNumber, ListView listView) {
        return calendarModel.completedMoves(calendarView.getContext(), dayNumber, listView);
    }

    /**
     * Sets the height of the list which contains the completed exercises.
     */
    @Override
    public void setListViewHeightBasedOnChildren(ListView listView) {
        calendarModel.setListViewHeightBasedOnChildren(listView);
    }
}