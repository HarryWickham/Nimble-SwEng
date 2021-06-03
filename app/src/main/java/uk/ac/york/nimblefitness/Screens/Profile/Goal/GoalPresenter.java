package uk.ac.york.nimblefitness.Screens.Profile.Goal;

import android.content.SharedPreferences;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.CreateNotification;
import uk.ac.york.nimblefitness.HelperClasses.ShareService;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

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
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        int currentMoves = prefs.getInt(currentFirebaseUser+"currentMoves", 0);
        return goalModel.updateGauge(0,currentMoves);
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
