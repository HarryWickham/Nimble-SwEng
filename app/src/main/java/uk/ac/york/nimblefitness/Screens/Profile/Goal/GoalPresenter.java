package uk.ac.york.nimblefitness.Screens.Profile.Goal;

import android.widget.ListView;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;

/*
 This class is used to interface between the View and Model associated with the Goal tab.
 */
public class GoalPresenter implements GoalContract.Presenter{

    private GoalFragment goalView;
    private GoalModel goalModel;

    public GoalPresenter(GoalFragment goalView, GoalModel goalModel) {
        this.goalView = goalView;
        this.goalModel = goalModel;
    }

    @Override
    public String displayQuote() {
        return goalModel.motivationQuote();
    }

    @Override
    public String displayUserName() {
        return goalModel.currentUser();
    }

    @Override
    public int displayGaugeInfo() {
        return goalModel.updateGauge(0,68);
    }

    @Override
    public int setGaugeEndValue() {
        return goalModel.gaugeEndValue();
    }

    @Override
    public MovesListAdapter setTodaysMovesList() {
        return goalModel.todaysMoves(goalView.getContext());
    }

    @Override
    public void setListViewHeightBasedOnChildren(ListView listView) {
        goalModel.setListViewHeightBasedOnChildren(listView);
    }
}
