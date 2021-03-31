package uk.ac.york.nimblefitness.Screens.Profile;

import pl.pawelkleczkowski.customgauge.CustomGauge;

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
        return goalModel.updateGauge(0,33);
    }

    @Override
    public int setGaugeEndValue() {
        return goalModel.gaugeEndValue();
    }

}
