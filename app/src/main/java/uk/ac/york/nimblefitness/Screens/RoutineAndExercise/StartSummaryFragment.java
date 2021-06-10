package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

/**This is the page the user is first taken to when they click to start a routine. The information of the routine is displayed on this page such as:
 * The exercises in the routine, the reps for each exercise and the sets for the routine
 * The routines rating is also displayed with an overview of the routine
 * The information about the exercises is displayed in a custom list
 * To start the users information is gathered from the firebase to get their unique user id which is used to store the users upcoming points**/

public class StartSummaryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_summary, container, false);

        // Gathering the current routine
        Intent intent = getActivity().getIntent();

        // Gathering the information to send to the next screen
        Bundle bundle = intent.getExtras();

        // Gathering the current routine to load the information needed
        Routine routine = (Routine) bundle.getSerializable("routine");

        // Gathering the users information from the firebase
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // Gathering the users unique code
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        // Setting the users current points to 0 for the routine start
        editor.putInt(currentFirebaseUser + "totalPoints",0);
        editor.apply();

        // Setting the page title
        getActivity().setTitle(routine.getRoutineName());

        // Finding the text box to output the routine summary
        TextView routineSummary = view.findViewById(R.id.routine_summary);
        routineSummary.setText(routine.getRoutineSummary());

        // Finding the text box to output the routine rating
        TextView starRating = view.findViewById(R.id.star_rating);
        starRating.setText(String.format("Routine Rating: %d/5", routine.getRating()));

        // Finding the text box to output the amount of sets in a the routine
        TextView routineSets = view.findViewById(R.id.sets);
        routineSets.setText(String.format("Total sets to complete: %d", routine.getSets()));

        // Finding the list view for displaying the exercises in the routine
        ListView listView = view.findViewById(R.id.start_summary_list_view);
        listView.setEnabled(false);

        // Getting the custom made adapter for the exercise list
        MovesListAdapter movesListAdapter = new MovesListAdapter(getContext(), routine.getExerciseArrayList());

        // Setting the list to be in the form of the adapter
        listView.setAdapter(movesListAdapter);
        setListViewHeightBasedOnChildren(listView);

        // Finding and implementing a button for the user to start the routine
        Button toInfoPage = view.findViewById(R.id.toInfoPage);
        InformationFragment informationFragment = new InformationFragment(); // Creating the next page fragment and passing it the information needed in the bundle
        informationFragment.setArguments(bundle);
        toInfoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Once the button has been clicked, the users id is recieved and the profile is updated to having started the routine
                SharedPreferences prefs = getDefaultSharedPreferences(getContext());
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(currentFirebaseUser + "completedRoutines", prefs.getInt(currentFirebaseUser+"completedRoutines", 0)+1);
                editor.apply();
                FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
                DatabaseReference rootReferenceUser = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
                rootReferenceUser.child("userDetails").child("completedRoutines").setValue(prefs.getInt(currentFirebaseUser+"completedRoutines", 0));

                // Once the button is clicked to move to the next screen, this below gathers the information from this page and sends it to the next one
                // In this example, the next page is the screen which explains the exercise
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    /**Ensures that the view height is sufficient based on the content of the page
     * This checks for how many exercised are in the routine, which need to be displayed in the list
     * It then makes the box big enough to display the full list **/
    public void setListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
