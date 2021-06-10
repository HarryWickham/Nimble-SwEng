package uk.ac.york.nimblefitness.Screens.Profile.Goal;

import android.content.SharedPreferences;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * This class is used to interface between the View and Model associated with the Goal tab.
 */
public class GoalPresenter implements GoalContract.Presenter {

    private final GoalFragment goalView;
    private final GoalModel goalModel;

    public GoalPresenter(GoalFragment goalView, GoalModel goalModel) {
        this.goalView = goalView;
        this.goalModel = goalModel;
    }

    /**
     * Displays the motivational quote on the screen.
     */
    @Override
    public String displayQuote() {
        return goalModel.motivationQuote();
    }

    /**
     * Display's the user's name on the screen.
     */
    @Override
    public String displayUserName() {
        return goalModel.currentUser();
    }

    /**
     * Displays the gauge values: current moves gained for the week, total goal amount of moves set
     * for the week.
     **/
    @Override
    public int displayGaugeInfo() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        int currentMoves = prefs.getInt(currentFirebaseUser + "currentMoves", 0);
        return goalModel.updateGauge(0, currentMoves);
    }

    /**
     * Sets the end value of the gauge which is the weekly goal set by the user.
     */
    @Override
    public int setGaugeEndValue() {
        return goalModel.gaugeEndValue();
    }

    /**
     * Displays the list of exercises the user has completed on the current day.
     */
    @Override
    public MovesListAdapter setTodaysMovesList(ListView listView) {
        return goalModel.todaysMoves(goalView.getContext(), listView);
    }

    /**
     * Sets the height of the list which contains the completed exercises.
     */
    @Override
    public void setListViewHeightBasedOnChildren(ListView listView) {
        goalModel.setListViewHeightBasedOnChildren(listView);
    }
}