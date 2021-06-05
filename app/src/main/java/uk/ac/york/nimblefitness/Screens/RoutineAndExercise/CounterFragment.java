package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

public class CounterFragment extends Fragment {

    // Buttons used throughout
    private Button StartButton;
    private Button PauseButton;

    // Text outputs used throughout
    private TextView TimeRemaining;
    private TextView RepCounter;
    private TextView PointsEarntOutput;

    // Variable for the time taken per rep/hold
    private long ExerciseTime = 2000;

    // The current countdown time
    private CountDownTimer mCountDownTimer;

    // Variables for if the timer has been activated and if it is currently running
    private boolean mTimerRunning;
    private boolean TimerStarted = false;

    // Time left in the counter
    private long mTimeLeftInMillis;

    // Counting the total reps and current reps of the exercise
    private int repCount = 0;
    private int repCountTotal = 0;

    // Points which have been added for this exercise
    private int pointsAdded = 0;

    Bundle bundle;

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter, container, false);

        // Within this page there are two functions for two different types of exercise
        // One of these are for exercises which have an amount of reps, such as pushups
        // The other is used for exercises which are stress holds which have no reps, but only a time such as planking
        // The timer used in this code counts down in milli seconds, in 1000's

        // Retrieving the information about the routine
        Routine routine = (Routine) getArguments().getSerializable("routine");

        // Creating a bundle for transferring information between pages
        bundle = new Bundle();
        bundle.putSerializable("routine",routine);

        // Retrieving the text outputs from the XML file to output text
        RepCounter = view.findViewById(R.id.RepCounter); // Text output for the rep counter
        TimeRemaining = view.findViewById(R.id.TimeRemaining); // Text output for the Timer
        PointsEarntOutput = view.findViewById(R.id.PointsEarntOutput); // Text output for the points earnt

        // Retrieving the total points for this routine from the users unique code from the firebase
        // First finds the unique code and retrieves the points from the app
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        PointsEarntOutput.setText("Points earnt: " + prefs.getInt(currentFirebaseUser+"totalPoints", 0));

        pointsAdded = 0;

        // The code for the return button
        // First it finds the button on the XML file and gives it a function
        // To start it finds the screen the button will change to upon press, and uses the bundle of information to prepare the screen
        // This button is used to go to the previous screen where it explains the current exercise
        // Once the button is pressed, the timer is paused if it is running and the points which have been earnt in that exercise is removed from the total as the user is going back a screen
        // Upon changing the points, the screen is then changed
        Button returnButton = view.findViewById(R.id.ReturnButton);
        InformationFragment informationFragment = new InformationFragment();
        informationFragment.setArguments(bundle);
        SharedPreferences.Editor editor = prefs.edit();
        returnButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (mTimerRunning) {
                    pauseTimer(); // If the timer is running, pause it
                }
                if (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType().equals("number")) {
                    editor.putInt(currentFirebaseUser + "totalPoints", prefs.getInt(currentFirebaseUser+"totalPoints", 0)-(pointsAdded * routine.getExerciseArrayList().get(routine.getCurrentExercise()).getMovesPerRep()));
                    editor.apply(); // Removing the points
                } else {
                    editor.putInt(currentFirebaseUser + "totalPoints", prefs.getInt(currentFirebaseUser+"totalPoints", 0)-(pointsAdded * routine.getExerciseArrayList().get(routine.getCurrentExercise()).getMovesPerRep()));
                    editor.apply(); // Removing the points
                }
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                fragmentTransaction.commit(); // Changing the screen
            }
        });

        // Mute button for the audio beep
        Button muteButton = view.findViewById(R.id.MuteButton);
        muteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

            }
        });

        // Pause button for the timer
        // First it retrieves the button from the XML file
        // Then upon clicking it first checks to see if the timer has been started
        // If the timer has been started, it then checks to see what current state it is in
        // If it is running, then a function is used to pause the timer at its current time
        // If the timer is not running, then a function is used to restart the time it was paused
        PauseButton = view.findViewById(R.id.PauseButton);
        PauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TimerStarted) { // If the timer has been started
                    if (mTimerRunning) {
                        pauseTimer(); // Pause the timer
                    } else { // Start the timer using one of the two functions
                        if (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType().equals("number")) { // If the routine has reps
                            startRepCounterTimer(view, routine); // Use the rep exercise layout
                        } else {
                            editor.putInt(currentFirebaseUser + "totalPoints", prefs.getInt(currentFirebaseUser+"totalPoints", 0)-1);
                            editor.apply(); // Removing the points
                            exerciseCountdown(view, routine); // Use the timer exercise layout
                        }
                    }
                }
            }
        });

        // Setting the title of the page with the set number
        getActivity().setTitle(routine.getRoutineName() + "       Set: " + String.valueOf(routine.getSets() - routine.getSetsRemaining() + 1));

        // Starting the exercise
        startExercise(view, routine);
        return view;
    }

    public void startExercise(View view, Routine routine) {

       // Retrieving the text outputs from the XML file
        TextView ExerciseTitle = view.findViewById(R.id.ExerciseTitle);
        ImageView MuscleImage = view.findViewById(R.id.MuscleImage);

        // Setting the title of the current exercise
        ExerciseTitle.setText(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getExerciseName());

        // Retrieving and setting the muscle image for the current exercise
        Glide.with(getContext()).load(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getMuscleGroupImage().getImageSource()).into(MuscleImage);

        // First setting of the "rep counter" function
        // If the exercise has an amount of reps, it will first display the amount of reps the user is currently on (0 since just starting) and the total amount in the exercise
        // If it is a stress hold, then it just dispays the message "hold the (exercise)" where the rep counter is usually
        if (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType().equals("number")) { // Check for reps
            repCountTotal = routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps(); // Collecting the amount of reps for the exercise
            RepCounter.setText("Rep: " + (repCount) + " out of: " + repCountTotal); // Updating the rep counter with amount
        } else {
            RepCounter.setText("Hold the " + routine.getExerciseArrayList().get(routine.getCurrentExercise()).getExerciseName()); // Setting the rep counter to be the hold message
        }

        // The start button for the exercise
        // This start button is in place for the timer countdown
        // So as the button is pressed, it is removed from the screen and the timer is then displayed
        // As there are two functions for the two types of exercise, a check is done to see which layout is going to be used
        // Once the button has been pressed, a variable is updated to true for the counter now running
        StartButton = view.findViewById(R.id.StartButton);
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartButton.setVisibility(View.GONE);
                TimeRemaining.setVisibility(View.VISIBLE); // Changing the screen
                TimerStarted = true; // Timer now running
                if (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType().equals("number")) { // If there are reps in the exercise
                    ExerciseTime = routine.getExerciseArrayList().get(routine.getCurrentExercise()).getTimePerRep(); // Retrieving time taken per rep
                    RepCounter.setText("Rep: " + (repCount + 1) + " out of: " + repCountTotal); // Updating the rep counter so state they are on the first rep
                    mTimeLeftInMillis = ExerciseTime * 1000; // Inputting the time taken per rep in milli seconds
                    startRepCounterTimer(view, routine); // Starting the timer for the reps exercise
                } else { // If it is a hold exercise
                    ExerciseTime = routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps(); // Gather time for the hold
                    repCountTotal = 1; // Setting total reps to be 1, as the user will only hold it once
                    mTimeLeftInMillis = ExerciseTime * 1000; // Time in millis
                    exerciseCountdown(view, routine); // Starting the timer for the hold exercise
                }
            }
        });
    }

    // Timer control for the reps exercise
    // This is also the function which is used for restarting the timer after it has been paused for the reps exercise
    private void startRepCounterTimer(View view, Routine routine){

        // The count down timer using the inputted total time per rep
        // Will count down in intervals of 1000 until it reaches 0, where it will move to the next function to check how many reps are left
        // Once the timer reaches 0, it indicates that a rep should have been completed
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) { // The count down timer
            @Override
            public void onTick(long millisUntilFinished) { // If timer does not equal 0
                mTimeLeftInMillis = millisUntilFinished; // Sets new value once the timer has counted down
                updateCountDownText(); // Function to update the timer on screen
            }

            @Override
            public void onFinish() { // If the timer has reached 0
                mTimerRunning = false; // States the timer is no longer running
                repCounter(view, routine); // Function to check if there are more reps
            }
        }.start(); // Starting the timer

        mTimerRunning = true; // Stating the timer is now running
        updateButtons(); // Function to update the buttons at the bottom of the screen
    }

    // Here is the function which updates the text on screen to show the correct value of the timer
    private void updateCountDownText(){
       // Gathering the time from the counter and converting it to seconds
       int seconds = (int) (mTimeLeftInMillis / 1000);

       // Converting the time into string so it can be outputted on screen, in seconds rather than milliseconds
       String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds);
       TimeRemaining.setText(timeLeftFormatted); // Outputting the time remaining
    }

    // Function to pause the timer
    private void pauseTimer(){
       mCountDownTimer.cancel(); // Stops the timer from running
       mTimerRunning = false; // States that the timer is no longer running
       updateButtons(); // Updates the buttons at the bottom of the screen
    }

    // Function to update the buttons at the bottom of the screen
    private void updateButtons(){
       // Updating the pause/resume button
       if (mTimerRunning) {
           PauseButton.setText("Pause"); // If the timer is running, have the button used for "pause"
       } else {
           PauseButton.setText("Resume"); // If the timer is not running, have the button used for "resume"
       }
    }

    // Function which controls the incrimentation of the reps for rep exercises
    // First it adds one to the rep, indicated by the counter reaching 0
    // It then checks to see if the total amount of reps has been achieved by the user
    // If total reps have been achieved then it will move to the next screen, the summary screen
    // If more reps are needed, the reps counter is incrimented, updated and the timer is reset
    // Also the points achieved is updated per amount of reps, a function is called to update the amount of points the user has earnt
    private void repCounter(View view, Routine routine) {
        repCount ++; // Adding rep count
        updatePoints(view, routine); // Updating the points for the rep exercised
        if (repCount != repCountTotal){
            RepCounter.setText("Rep: " + (1 + repCount) + " out of: " + repCountTotal); // Updating the rep counter on screen
            mTimeLeftInMillis = ExerciseTime * 1000; // Resetting the countdown timer
            startRepCounterTimer(view, routine); // Starting the rep exercise timer again
        } else {
            nextScreen(); // Function to move the next screen
        }
    }

    // Here is the function for the next screen
    // As with the return button, it puts all the information into the bundle and uses it to swap screens
    // This is to move to the finish screen once all the reps, or time has been achieved in an exercise
    private void nextScreen(){
        FinishFragment finishFragment = new FinishFragment();
        finishFragment.setArguments(bundle); // Inputting the information
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, finishFragment);
        fragmentTransaction.commit(); // Moving to the next screen
    }

    // A function for a timer if the current exercise has no reps and only a timer to hold
    // It works in the same way as the rep counter, however it does not add to a rep counter and just proceeds to the next page once it reaches 0
    // It also adds to the point counter every second the position is held
    private void exerciseCountdown (View view, Routine routine) {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updatePoints(view, routine); // Updating the points
                updateCountDownText(); // Updating the text on screen
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                nextScreen(); // goes to next screen
            }
        }.start();

        mTimerRunning = true;
        updateButtons();
    }

    // The function for updating the points
    // Everytime this is called, it finds the user unique code and gathers their current points earnt for the routine
    // Once it has this, every time a rep is completed or a second is held, it adds the points for that exercise
    private void updatePoints (View view, Routine routine) {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // Gathering the users unique code
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(currentFirebaseUser + "totalPoints", prefs.getInt(currentFirebaseUser+"totalPoints", 0)+(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getMovesPerRep()));
        editor.apply(); // Applying the points
        pointsAdded ++; // Amount of points added per exercise

        PointsEarntOutput.setText("Points earnt: " + prefs.getInt(currentFirebaseUser+"totalPoints", 0)); // Updating the text output for the points
    }
}