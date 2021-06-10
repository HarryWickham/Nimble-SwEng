package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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

    // In this fragment it creates the page which the routine goes too once you have completed an
    // exercise in a routine
    // When the user completes an exercise a page is opened
    // On this page it states what exercise has just been completed, with a list of remaining
    // exercises in the current set
    // There is a timer for the user to have a rest, then the next exercise is automatically
    // loaded. This timer length is determinded by the exercise
    // There are also two buttons for the user to click, to either go to the next exercise in the
    // routine or to go to the main menu

    // At the end of a set, the list of remaining exercised in a set is empty and a message is
    // used to indicate the end of a set
    // There is a different, longer rest time for the user and the next set is loaded when the
    // timer runs out or the next button is clicked

    // Once all the sets have been completed in a routine, instead of going to this page, the end
    // summary fragment is used

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

        // Collecting information from the XML
        getActivity().setTitle(routine.getRoutineName());
        Button nextExercise = view.findViewById(R.id.continue_button);
        Button exitToProfile = view.findViewById(R.id.exit_button);
        TextView finishText = view.findViewById(R.id.finish_text);
        TextView remainingListText = view.findViewById(R.id.remaining_exercises);
        ListView finishListView = view.findViewById(R.id.finish_list_view);
        TextView restTime = view.findViewById(R.id.finish_rest_remaining);
        TextView restText = view.findViewById(R.id.restText);
        finishListView.setEnabled(false);


        // Message once completing  an exercise
        // A check is used to see if a rep exercise has just occurred or if it was a holding
        // exercise
        // Depending on which exercise it was, a different message displays for the amount of
        // reps achieved or the time held for
        if (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType()
                .equalsIgnoreCase("time")) { // If the exercise was a hold exercise
            finishText.setText(String.format(Locale.UK, "Congratulations you have completed %d " +
                    "seconds of %s",
                    routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps(),
                    routine.getExerciseArrayList().get(routine.getCurrentExercise()).getExerciseName()));
        } else if (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType()
                .equalsIgnoreCase("number")) { // If the exercise was a rep exercise
            finishText.setText(String.format(Locale.UK, "Congratulations you have completed %d " +
                    "%s",
                    routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps(),
                    routine.getExerciseArrayList().get(routine.getCurrentExercise()).getExerciseName()));
        }

        // Gathering the users unique ID to load and store the routine information for the user
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        // Adding the users completed moves to their collective for the day, so that the total
        // amount of exercise can be tracked
        editor.putInt(currentFirebaseUser + "currentMoves",
                (int) (prefs.getInt(currentFirebaseUser + "currentMoves", 0) +
                        (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps()
                                * routine.getExerciseArrayList().get(routine.getCurrentExercise())
                                .getMovesPerRep())));
        editor.apply();

        // A check to see if the user has achieved their total moves for the week, if they have
        // then a notification is sent to notify the user
        if (prefs.getInt(currentFirebaseUser + "currentMoves", 0) >
                prefs.getInt(currentFirebaseUser + "weeklyGoal", 0)) {
            @SuppressLint("DefaultLocale") CreateNotification createNotification =
                    new CreateNotification(R.drawable.ic_stat_name, "Congratulations!",
                            String.format("You have reached your weekly goal. Your current moves " +
                                    "are: %s", String.format("%d/%d",
                                    prefs.getInt(currentFirebaseUser + "currentMoves", 0),
                                    prefs.getInt(currentFirebaseUser + "weeklyGoal", 0))),
                            MainActivity.class, "goalReachedChannelID", 1,
                            getApplicationContext());
        }

        // Collecting more data from the firebase using the user id
        rootDatabase = FirebaseDatabase.getInstance();
        rootReferenceUser = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());

        // Loading the leaderboard information
        DatabaseReference rootReferenceScoreBoard =
                rootDatabase.getReference("scoreBoard").child(currentFirebaseUser.getUid());

        // Adding to the current moves
        rootReferenceUser.child("userDetails").child("currentMoves")
                .setValue(prefs.getInt(currentFirebaseUser + "currentMoves", 0));

        // If the user has the "gold" membership of the app, then they have access to the
        // leaderboard
        // If they do, then it loads the leaderboard information and adds the current points the
        // user has earnt to it
        if (prefs.getString(currentFirebaseUser + "membershipPlan", "error").equals("gold")) {
            LeaderBoardUserDetails leaderBoardUserDetails =
                    new LeaderBoardUserDetails(prefs.getString(currentFirebaseUser +
                            "userFullName", "error"), prefs.getInt(currentFirebaseUser +
                            "currentMoves", 0), currentFirebaseUser.getUid());
            rootReferenceScoreBoard.setValue(leaderBoardUserDetails);
        }

        // Gaining the remaining list of the exercises
        ArrayList<Exercise> allExercises;
        allExercises = (ArrayList<Exercise>) routine.getExerciseArrayList().clone();

        // Seting the exercise to be loaded to the next one in the routine
        routine.setCurrentExercise(routine.getCurrentExercise() + 1);

        // Setting the exercises which are remaining in the routine
        ArrayList<Exercise> remainingExercises = remainingExerciseList(allExercises, routine);

        // Bundling the information in this fragment so it can be passed to the nxt screen
        Bundle bundle = new Bundle();
        bundle.putSerializable("routine", routine);

        // Creating a new fragment so that the page can be swapped to this new one
        InformationFragment informationFragment = new InformationFragment();
        informationFragment.setArguments(getArguments());

        // Creating a new fragment so that the page can be swapped to this new one
        EndSummaryFragment endSummaryFragment = new EndSummaryFragment();
        endSummaryFragment.setArguments(getArguments());

        retrieveCompletedExerciseFromFirebase();

        // If all the sets are completed in the routine and the last exercise has just been
        // completed
        if (routine.getSetsRemaining() == 1 && remainingExercises.size() == 0) {

            // Goes straight to the end summary page so the user can see the routine is over and
            // see their stats for this routine
            FragmentTransaction fragmentTransaction =
                    requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, endSummaryFragment);
            fragmentTransaction.commit();

            // If there are still sets remaining in the routine but the user has just completed
            // the last exercise in a set
        } else if (remainingExercises.size() == 0) {
            nextExercise.setText("Continue to next set"); // Changing the button text for next
            // set rather than next exercise
            remainingListText.setText(""); // Clearing the exercise list as no more exercise in
            // the set
            routine.setSetsRemaining(routine.getSetsRemaining() - 1); // Counting down remaining
            // sets
            restText.setText("Take a Rest! Time until next set: "); // Changing the rest timer
            // text to be set instead of exercise
            if (routine.getSetsRemaining() == 1) { // If there is only one set remaining
                finishText.setText(String.format(Locale.UK, "Congratulations you have completed " +
                        "all the exercises for this set. You have %d set remaining.",
                        routine.getSetsRemaining()));
            } else { // If there are more than 1 sets remaining
                finishText.setText(String.format(Locale.UK, "Congratulations you have completed " +
                        "all the exercises for this set. You have %d sets remaining.",
                        routine.getSetsRemaining()));
            }
            routine.setCurrentExercise(0);

            // The Count down timer used to control how long the user rests for between sets
            restTimer = new CountDownTimer(routine.getRestBetweenSets() * 1000, 1000) {
                @Override
                public void onTick(long startTimeRemaining) {
                    // Updating the timer on screen for every tick the timer counts down
                    restTime.setText(String.valueOf(Integer.parseInt((String) restTime.getText()) - 1));
                }

                @Override
                public void onFinish() {
                    // Once the timer reaches 0, automatically goes to the next page where it
                    // explains the next exercise in the routine, which is in a new set for this
                    // example
                    FragmentTransaction fragmentTransaction =
                            requireActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                    fragmentTransaction.commit();
                }
            }.start();

            // First setting of the rest time
            restTime.setText(String.valueOf(routine.getRestBetweenSets()));

            // When there are still exercises remaining in the current set
        } else {
            // Another Count down timer to control how long the user waits for between exercises
            restTimer2 =
                    new CountDownTimer(routine.getExerciseArrayList()
                            .get(routine.getCurrentExercise()).getRestAfterFinish() * 1000, 1000) {
                @Override
                public void onTick(long startTimeRemaining) {
                    // Updating the timer on screen for every tick the timer counts down
                    restTime.setText(String.valueOf(Integer.parseInt((String) restTime.getText()) - 1));
                }

                @Override
                public void onFinish() {
                    // Once the timer reaches 0, automatically goes to the next page where it
                    // explains the next exercise in the routine, which is in the same set for
                    // this timer
                    FragmentTransaction fragmentTransaction =
                            requireActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                    fragmentTransaction.commit();
                }
            }.start();

            // First setting of the rest time
            restTime.setText(String.valueOf(routine.getExerciseArrayList()
                    .get(routine.getCurrentExercise()).getRestAfterFinish()));
        }

        // Retrieving the remaining exercises in this set
        MovesListAdapter movesListAdapter = new MovesListAdapter(getContext(), remainingExercises);

        // Updating the list of remaining exercises for this screen
        finishListView.setAdapter(movesListAdapter);
        setListViewHeightBasedOnChildren(finishListView);

        // Setting up the button which can take to user to the next exercise earlier than the timer
        nextExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Does a check to see if any of the end counters are running, and cancels them
                if (remainingExercises.size() == 0) {
                    restTimer.cancel();
                } else {
                    restTimer2.cancel();
                }
                // Moving the screen to be the information screen for the next exercise
                FragmentTransaction fragmentTransaction =
                        requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                fragmentTransaction.commit();
            }
        });

        // Setting up the button which takes the user back to the main menu, exiting the routine
        exitToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Does a check to see if any of the end counters are running, and cancels them
                if (remainingExercises.size() == 0) {
                    restTimer.cancel();
                } else {
                    restTimer2.cancel();
                }
                // Goes to the main menu
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();//takes user the main page
            }
        });

        return view;
    }

    // Function for setting the height of the list of exercises of the remaining routines
    // As the list changes size, this function just changes it depending on how many exercises
    // are remaining
    // To work, it creates the amount of boxes needed to map all the remaining exercises in the
    // routine
    // It then increases the size of the box holding this list by the amount of boxes being created
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    // A function for tracking how many exercises are remaining in a routine
    // Once the exercise has been completed it is removed from the list
    public ArrayList<Exercise> remainingExerciseList(ArrayList<Exercise> remainingExercises,
                                                     Routine routine) {
        for (int i = 0; i < routine.getCurrentExercise(); i++) {
            remainingExercises.remove(0);
        }
        return remainingExercises;
    }

    // A function to retrieve the completed exercises from a routine from the firebase
    // To do this it first creates an array to put any/all of the routines which have been saved
    // to the firebase
    // It then finds where this information is being stored in the firebase
    // It then runs a for loop so it can access every piece of information in the child, which
    // are the names of the completed exercises
    // Once it has the name, it adds it to the new array
    public void retrieveCompletedExerciseFromFirebase() {
        ArrayList<SavableExercise> exerciseArrayList = new ArrayList<>();
        rootReferenceUser.child("exercises").child(currentDayNumber())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getValue(SavableExercise.class) != null) {
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

    // Function for adding the recently completed exercise to the list in the firebase
    // First a check is made and the current exercise is retrieved
    // Then a check is done to find if the exercise has already been added to the competed list
    public void addCompletedExerciseToFirebase(ArrayList<SavableExercise> completedExercises) {
        Exercise exercise;
        if (routine.getCurrentExercise() != 0) {
            exercise = routine.getExerciseArrayList().get(routine.getCurrentExercise() - 1);
        } else {
            exercise =
                    routine.getExerciseArrayList().get(routine.getExerciseArrayList().size() - 1);
        }

        // Checking the array of current exercises with the completed exercises
        boolean alreadyDone = false;
        for (SavableExercise savableExercise : completedExercises) {
            if (savableExercise.getExerciseName().equals(exercise.getExerciseName())) {
                savableExercise.setReps(savableExercise.getReps() + exercise.getReps());
                alreadyDone = true;
                break;
            }
        }

        // Adding the exercise to the firebase when completed
        if (completedExercises.size() == 0 || !alreadyDone) {
            completedExercises.add(new SavableExercise(exercise.getExerciseName(),
                    exercise.getReps(), exercise.getMovesPerRep(), exercise.getColour(),
                    exercise.getRepType(), currentDayNumber()));

        }


        // Finding the child in the firebase for storing the amount of completed exercises
        rootReferenceUser.child("exercises").child(currentDayNumber()).setValue(completedExercises);

        // Setting the date of this completed exercise with the selected format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyDD");
    }

    // A function to change the month from being a number to a string format for better readability
    // Simply it gets the month value and returns the name of the month
    public String monthText(int month) {
        switch (month) {
            case 1:
                return ("January");
            case 2:
                return ("February");
            case 3:
                return ("March");
            case 4:
                return ("April");
            case 5:
                return ("May");
            case 6:
                return ("June");
            case 7:
                return ("July");
            case 8:
                return ("August");
            case 9:
                return ("September");
            case 10:
                return ("October");
            case 11:
                return ("November");
            case 12:
                return ("December");
            default:
                return ("error");
        }
    }

    // This method is used to set which prefix is displayed after the date number.
    public String datePrefix(int dayOfMonth) {
        switch (dayOfMonth) {
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
            default:
                return ("th");
        }
    }

    // This function is for setting the current date in a readable format
    // It collects the current information for the users machine, such as day and month
    // Then it processes these numbers into a readable format using the previous function
    // It then saves the values in this format
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