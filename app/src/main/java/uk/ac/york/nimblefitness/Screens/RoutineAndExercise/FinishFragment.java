package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.LeaderBoardUserDetails;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

public class FinishFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish, container, false);

        Routine routine = null;
        if (getArguments() != null) {
            routine = (Routine) getArguments().getSerializable("routine");
        }

        getActivity().setTitle(routine.getRoutineName());
        Button toEndSummary = view.findViewById(R.id.toEndSummary);
        Button nextExercise = view.findViewById(R.id.continue_button);
        Button exitToProfile = view.findViewById(R.id.exit_button);
        TextView finishText = view.findViewById(R.id.finish_text);
        TextView remainingListText = view.findViewById(R.id.remaining_exercises);
        ListView finishListView = view.findViewById(R.id.finish_list_view);
        finishListView.setEnabled(false);

        if(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType().equalsIgnoreCase("time")){
            finishText.setText(String.format(Locale.UK,"Congratulations you have completed %d seconds of %s",routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps(), routine.getExerciseArrayList().get(routine.getCurrentExercise()).getExerciseName()));
        }else if (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType().equalsIgnoreCase("number")){
            finishText.setText(String.format(Locale.UK,"Congratulations you have completed %d %s",routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps(), routine.getExerciseArrayList().get(routine.getCurrentExercise()).getExerciseName()));
        }

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(currentFirebaseUser + "currentMoves", prefs.getInt(currentFirebaseUser+"currentMoves", 0)+(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps()*routine.getExerciseArrayList().get(routine.getCurrentExercise()).getMovesPerRep()));
        editor.apply();

        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootReferenceUser = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
        DatabaseReference rootReferenceScoreBoard = rootDatabase.getReference("scoreBoard").child(currentFirebaseUser.getUid());

        rootReferenceUser.child("userDetails").child("currentMoves").setValue(prefs.getInt(currentFirebaseUser+"currentMoves", 0));

        if(prefs.getString(currentFirebaseUser+"membershipPlan","error").equals("gold")) {
            LeaderBoardUserDetails leaderBoardUserDetails = new LeaderBoardUserDetails(prefs.getString(currentFirebaseUser + "userFullName", "error"), prefs.getInt(currentFirebaseUser + "currentMoves", 0), currentFirebaseUser.getUid());
            rootReferenceScoreBoard.setValue(leaderBoardUserDetails);
        }

        ArrayList<Exercise> allExercises;
        allExercises = (ArrayList<Exercise>) routine.getExerciseArrayList().clone();

        routine.setCurrentExercise(routine.getCurrentExercise() + 1);

        ArrayList<Exercise> remainingExercises = remainingExerciseList(allExercises, routine);


        if (routine.getSetsRemaining()==1 && remainingExercises.size()==0){
            nextExercise.setVisibility(View.GONE);
            finishText.setText(String.format(Locale.UK,"Congratulations you have completed all the sets for this routine."));
            toEndSummary.setVisibility(View.VISIBLE);
            remainingListText.setText("");
        } else if (remainingExercises.size()==0){
            nextExercise.setText("Continue to next set");
            remainingListText.setText("");
            routine.setSetsRemaining(routine.getSetsRemaining()-1);
            if(routine.getSetsRemaining()==1){
                finishText.setText(String.format(Locale.UK,"Congratulations you have completed all the exercises for this set. You have %d set remaining.",routine.getSetsRemaining()));
            } else {
                finishText.setText(String.format(Locale.UK, "Congratulations you have completed all the exercises for this set. You have %d sets remaining.", routine.getSetsRemaining()));
            }
            remainingListText.setText("");
            routine.setCurrentExercise(0);
        }

        MovesListAdapter movesListAdapter = new MovesListAdapter(getContext(), remainingExercises);

        finishListView.setAdapter(movesListAdapter);
        setListViewHeightBasedOnChildren(finishListView);

        exitToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();//takes user the main page
            }
        });

        InformationFragment informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("routine",routine);
        informationFragment.setArguments(bundle);
        nextExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                fragmentTransaction.commit();
            }
        });


        EndSummaryFragment endSummaryFragment = new EndSummaryFragment();
        endSummaryFragment.setArguments(bundle);
        toEndSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, endSummaryFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

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

    public ArrayList<Exercise> remainingExerciseList(ArrayList<Exercise> remainingExercises, Routine routine){
        for (int i = 0; i < routine.getCurrentExercise(); i++){
            remainingExercises.remove(0);
        }
        return remainingExercises;
    }
}