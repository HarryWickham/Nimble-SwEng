package uk.ac.york.nimblefitness.Screens.Profile.Goal;

/*
 This contract interface file allows the Model, View and Presenter for the Goal tab of the profile
 page to interact with each other.
*/

public interface GoalContract {

    interface GoalView {
        // some view methods
    }

    interface Model {
        int updateGauge(int currentValue, int valueAdded);
        String motivationQuote();
        String currentUser();
        int gaugeEndValue();
        //void todaysMoves();
    }

    interface Presenter {
        String displayQuote();
        String displayUserName();
        int displayGaugeInfo();
        int setGaugeEndValue();
    }
}
