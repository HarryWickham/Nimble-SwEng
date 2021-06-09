package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.CreateNotification;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.LeaderBoardUserDetails;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.HelperClasses.SavableExercise;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

public class FinishFragment extends Fragment {

    FirebaseUser currentFirebaseUser;
    FirebaseDatabase rootDatabase;
    DatabaseReference rootReferenceUser;
    Routine routine = null;
    CountDownTimer restTimer;
    CountDownTimer restTimer2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish, container, false);

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
        TextView restTime = view.findViewById(R.id.finish_rest_remaining);
        TextView restText = view.findViewById(R.id.restText);
        finishListView.setEnabled(false);



        if(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType().equalsIgnoreCase("time")){
            finishText.setText(String.format(Locale.UK,"Congratulations you have completed %d seconds of %s",routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps(), routine.getExerciseArrayList().get(routine.getCurrentExercise()).getExerciseName()));
        }else if (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType().equalsIgnoreCase("number")){
            finishText.setText(String.format(Locale.UK,"Congratulations you have completed %d %s",routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps(), routine.getExerciseArrayList().get(routine.getCurrentExercise()).getExerciseName()));
        }

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(currentFirebaseUser + "currentMoves", (int) (prefs.getInt(currentFirebaseUser+"currentMoves", 0)+(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps()*routine.getExerciseArrayList().get(routine.getCurrentExercise()).getMovesPerRep())));
        editor.apply();

        if(prefs.getInt(currentFirebaseUser+"currentMoves",0)>prefs.getInt(currentFirebaseUser+
                "weeklyGoal",0)){
            CreateNotification createNotification = new CreateNotification(R.drawable.ic_stat_name, "Congratulations!", String.format("You have reached your weekly goal. Your current moves are: %s", String.format("%d/%d", prefs.getInt(currentFirebaseUser + "currentMoves", 0), prefs.getInt(currentFirebaseUser + "weeklyGoal",0))), MainActivity.class, "goalReachedChannelID", 1, getApplicationContext());
        }

        rootDatabase = FirebaseDatabase.getInstance();
        rootReferenceUser = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());

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

        InformationFragment informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("routine",routine);
        informationFragment.setArguments(getArguments());

        EndSummaryFragment endSummaryFragment = new EndSummaryFragment();
        endSummaryFragment.setArguments(getArguments());

        retrieveCompletedExerciseFromFirebase();

        if (routine.getSetsRemaining()==1 && remainingExercises.size()==0){

            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, endSummaryFragment);
            fragmentTransaction.commit();

        } else if (remainingExercises.size()==0){
            nextExercise.setText("Continue to next set");
            remainingListText.setText("");
            routine.setSetsRemaining(routine.getSetsRemaining()-1);
            restText.setText("Take a Rest! Time until next set: ");
            if(routine.getSetsRemaining()==1){
                finishText.setText(String.format(Locale.UK,"Congratulations you have completed all the exercises for this set. You have %d set remaining.", routine.getSetsRemaining()));
            } else {
                finishText.setText(String.format(Locale.UK,"Congratulations you have completed all the exercises for this set. You have %d sets remaining.", routine.getSetsRemaining()));
            }
            remainingListText.setText("");
            routine.setCurrentExercise(0);
            restTimer = new CountDownTimer(routine.getRestBetweenSets()*1000, 1000) {
                @Override
                public void onTick(long startTimeRemaining) {
                    restTime.setText(String.valueOf(Integer.parseInt((String) restTime.getText())-1));
                }

                @Override
                public void onFinish() {
                    FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                    fragmentTransaction.commit();
                }
            }.start();

            restTime.setText(String.valueOf(routine.getRestBetweenSets()));
        } else {

            restTimer2 = new CountDownTimer(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRestAfterFinish()*1000, 1000) {
                @Override
                public void onTick(long startTimeRemaining) {
                    restTime.setText(String.valueOf(Integer.parseInt((String) restTime.getText())-1));
                }

                @Override
                public void onFinish() {
                    FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                    fragmentTransaction.commit();
                }
            }.start();

            restTime.setText(String.valueOf(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRestAfterFinish()));
        }

        MovesListAdapter movesListAdapter = new MovesListAdapter(getContext(), remainingExercises);

        finishListView.setAdapter(movesListAdapter);
        setListViewHeightBasedOnChildren(finishListView);


        nextExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (remainingExercises.size()==0) {
                    restTimer.cancel();
                } else {
                    restTimer2.cancel();
                }
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                fragmentTransaction.commit();
            }
        });

        exitToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (remainingExercises.size()==0) {
                    restTimer.cancel();
                } else {
                    restTimer2.cancel();
                }
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();//takes user the main page
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

    public void retrieveCompletedExerciseFromFirebase(){
        Log.i("Retrieve", "retrieveCompletedExerciseFromFirebase: ");
        ArrayList<SavableExercise> exerciseArrayList = new ArrayList<>();
        rootReferenceUser.child("exercises").child(currentDayNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Retrieve", "onDataChange");
                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.i("Retrieve", "DataSnapshot");
                    if (ds.getValue(SavableExercise.class) != null) {
                        Log.i("Retrieve", "ds.getValue(Exercise.class)");
                        SavableExercise exercise = ds.getValue(SavableExercise.class);
                        exerciseArrayList.add(exercise);
                    }

                }
                addCompletedExerciseToFirebase(exerciseArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addCompletedExerciseToFirebase(ArrayList<SavableExercise> completedExercises){
        Exercise exercise;
        if(routine.getCurrentExercise() != 0) {
            exercise = routine.getExerciseArrayList().get(routine.getCurrentExercise()-1);
        } else{
            exercise = routine.getExerciseArrayList().get(routine.getExerciseArrayList().size()-1);
        }

        boolean alreadyDone = false;
        for(SavableExercise savableExercise : completedExercises){
            if(savableExercise.getExerciseName().equals(exercise.getExerciseName())){
                savableExercise.setReps(savableExercise.getReps() + exercise.getReps());
                alreadyDone = true;
                break;
            }
        }

        if(completedExercises.size() == 0 || !alreadyDone){
            completedExercises.add(new SavableExercise(exercise.getExerciseName(),exercise.getReps(), exercise.getMovesPerRep(),exercise.getColour(), exercise.getRepType(), currentDayNumber()));

        }


        rootReferenceUser.child("exercises").child(currentDayNumber()).setValue(completedExercises);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyDD");
        Log.i("SimpleDateFormat", sdf.format(new Date()));
        Log.i("CustomFormattedDay", currentDayNumber());
    }

    public String monthText(int month){
        switch (month){
            case 1: return("January");
            case 2: return("February");
            case 3: return("March");
            case 4: return("April");
            case 5: return("May");
            case 6: return("June");
            case 7: return("July");
            case 8: return("August");
            case 9: return("September");
            case 10: return("October");
            case 11: return("November");
            case 12: return("December");
            default: return("error");
        }
    }

    // This method is used to set which prefix is displayed after the date number.
    public String datePrefix(int dayOfMonth){
        switch (dayOfMonth){
            case 1:
            case 21:
            case 31:
                return ("st");
            case 2:
            case 22:
                return ("nd");
            case 3:
            case 23:
                return ("rd");
            default: return ("th");
        }
    }

    public String currentDayNumber() {
        SimpleDateFormat month = new SimpleDateFormat("M", Locale.UK);
        SimpleDateFormat day = new SimpleDateFormat("d", Locale.UK);
        Date currentTime = Calendar.getInstance().getTime();
        String monthString = month.format(currentTime);
        String dayString = day.format(currentTime);
        String currentDayNumber = String.format("%s %s%s", monthText(Integer.parseInt(monthString)),
                dayString, datePrefix(Integer.parseInt(dayString)));
        return currentDayNumber;
    }
}