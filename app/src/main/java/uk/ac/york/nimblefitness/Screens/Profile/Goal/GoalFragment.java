package uk.ac.york.nimblefitness.Screens.Profile.Goal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Locale;

import pl.pawelkleczkowski.customgauge.CustomGauge;
import uk.ac.york.nimblefitness.R;

/** This class initialises the 'Goal' tab within the profile page of the app. It includes the 'goal
 * gauge' which shows how many moves the user has completed for the day/week and the list below the
 * gauge gives a breakdown of these moves in terms of the exercises. There is also a randomised
 * motivational quote below the gauge which changes every time the user views the goal tab.
 */

public class GoalFragment extends Fragment implements GoalContract.GoalView {
    GoalPresenter goalPresenter;
    /** This method initialises the fragment. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goalPresenter = new GoalPresenter(this, new GoalModel());
    }
    /** This method creates the appearance of the fragment and inflates the goal tab layout so it's
     * visible.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Profile"); /* The title that appears at the top of the page. */
        View view = inflater.inflate(R.layout.fragment_goal, container, false);
        /* The gauge which shows the number of goals completed and to do. */
        CustomGauge gauge = view.findViewById(R.id.gauge);
        /* Sets the maximum value of the gauge. */
        gauge.setEndValue(goalPresenter.setGaugeEndValue());
        /* Sets the current value of the gauge and subsequently how much it's filled. */
        gauge.setValue(goalPresenter.displayGaugeInfo()>goalPresenter.setGaugeEndValue()?
                goalPresenter.setGaugeEndValue():goalPresenter.displayGaugeInfo());
        /* The value of the gauge is displayed as text. */
        TextView gaugeNumber = view.findViewById(R.id.moves_counter);
        gaugeNumber.setText(String.format(Locale.UK,"%d/%d",
                goalPresenter.displayGaugeInfo(), gauge.getEndValue()));
        /* Finds where the quote should go in this layout. */
        TextView motivationQuote = view.findViewById(R.id.motivation);
        motivationQuote.setText(goalPresenter.displayQuote()); // Sets the quote.
        /* Finds where in the layout the current user's name should go. */
        TextView userName = view.findViewById(R.id.user_name);
        userName.setText(goalPresenter.displayUserName()); // Displays the user's name.
        /* The list of exercises to do for today. */
        ListView listView = view.findViewById(R.id.todays_moves);
        /* Sets the list of today's moves. */
        listView.setAdapter(goalPresenter.setTodaysMovesList(listView));
        goalPresenter.setListViewHeightBasedOnChildren(listView);
        return view;
    }
    /** This method is used to send the fragment's context to the Presenter so the list of today's
     * moves can be set.
     */
    @Override
    public Context getContext(){
        return getActivity();
    }
}