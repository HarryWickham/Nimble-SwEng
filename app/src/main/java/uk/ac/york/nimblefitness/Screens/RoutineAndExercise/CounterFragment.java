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

    private Button StartButton;
    private Button PauseButton;

    private TextView TimeRemaining;
    private TextView RepCounter;
    private TextView PointsEarntOutput;

    private long ExerciseTime = 2000;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;
    private boolean TimerStarted = false;

    private long mTimeLeftInMillis;

    private int repCount = 0;
    private int repCountTotal = 0;

    Bundle bundle;

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter, container, false);

        Routine routine = (Routine) getArguments().getSerializable("routine");

        bundle = new Bundle();
        bundle.putSerializable("routine",routine);

        RepCounter = view.findViewById(R.id.RepCounter);
        TimeRemaining = view.findViewById(R.id.TimeRemaining);
        PointsEarntOutput = view.findViewById(R.id.PointsEarntOutput);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        PointsEarntOutput.setText("Points earnt: " + prefs.getInt(currentFirebaseUser+"totalPoints", 0));

        Button returnButton = view.findViewById(R.id.ReturnButton);
        InformationFragment informationFragment = new InformationFragment();
        informationFragment.setArguments(bundle);
        returnButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                pauseTimer();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(currentFirebaseUser + "totalPoints", prefs.getInt(currentFirebaseUser+"totalPoints", 0)-(repCount * routine.getExerciseArrayList().get(routine.getCurrentExercise()).getMovesPerRep()));
                editor.apply();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                fragmentTransaction.commit();
            }
        });

        Button muteButton = view.findViewById(R.id.MuteButton);
        muteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

            }
        });

        PauseButton = view.findViewById(R.id.PauseButton);
        PauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TimerStarted) {
                    if (mTimerRunning) {
                        pauseTimer();
                    } else {
                        if (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType().equals("number")) {
                            startRepCounterTimer(view, routine);
                        } else {
                            exerciseCountdown(view, routine);
                        }
                    }
                }
            }
        });

        getActivity().setTitle(routine.getRoutineName() + "       Set: " + String.valueOf(routine.getSets() - routine.getSetsRemaining() + 1));

        startExercise(view, routine);
        return view;
    }

    public void startExercise(View view, Routine routine) {

        TextView ExerciseTitle = view.findViewById(R.id.ExerciseTitle);
        ImageView MuscleImage = view.findViewById(R.id.MuscleImage);

        ExerciseTitle.setText(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getExerciseName());

        Glide.with(getContext()).load(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getMuscleGroupImage().getImageSource()).into(MuscleImage);

        if (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType().equals("number")) {
            repCountTotal = routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps();
            RepCounter.setText("Rep: " + (repCount) + " out of: " + repCountTotal);
        } else {
            RepCounter.setText("Hold the " + routine.getExerciseArrayList().get(routine.getCurrentExercise()).getExerciseName());
        }

        StartButton = view.findViewById(R.id.StartButton);
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartButton.setVisibility(View.GONE);
                TimeRemaining.setVisibility(View.VISIBLE);
                TimerStarted = true;
                if (routine.getExerciseArrayList().get(routine.getCurrentExercise()).getRepType().equals("number")) {
                    ExerciseTime = routine.getExerciseArrayList().get(routine.getCurrentExercise()).getTimePerRep();
                    RepCounter.setText("Rep: " + (repCount + 1) + " out of: " + repCountTotal);
                    mTimeLeftInMillis = ExerciseTime * 1000;
                    startRepCounterTimer(view, routine);
                } else {
                    ExerciseTime = routine.getExerciseArrayList().get(routine.getCurrentExercise()).getReps();
                    repCountTotal = 1;
                    mTimeLeftInMillis = ExerciseTime * 1000;
                    exerciseCountdown(view, routine);
                }
            }
        });
    }

    private void startRepCounterTimer(View view, Routine routine){

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                repCounter(view, routine);
            }
        }.start();

        mTimerRunning = true;
        updateButtons();
    }

    private void updateCountDownText(){
       int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

       String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds);
       TimeRemaining.setText(timeLeftFormatted);
    }

    private void pauseTimer(){
       mCountDownTimer.cancel();
       mTimerRunning = false;
       updateButtons();
    }

    private void updateButtons(){
       if (mTimerRunning) {
           PauseButton.setText("Pause");
       } else {
           PauseButton.setText("Resume");
       }
    }

    private void repCounter(View view, Routine routine) {
        repCount ++;
        if (repCount != repCountTotal){
            RepCounter.setText("Rep: " + (1 + repCount) + " out of: " + repCountTotal);
            mTimeLeftInMillis = ExerciseTime * 1000;
            updatePoints(view, routine);
            startRepCounterTimer(view, routine);
        } else {
            updatePoints(view, routine);
            nextScreen();
        }
    }

    private void nextScreen(){
        FinishFragment finishFragment = new FinishFragment();
        finishFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, finishFragment);
        fragmentTransaction.commit();
    }

    private void exerciseCountdown (View view, Routine routine) {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updatePoints(view, routine);
                nextScreen();
            }
        }.start();

        mTimerRunning = true;
        updateButtons();
    }

    private void updatePoints (View view, Routine routine) {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(currentFirebaseUser + "totalPoints", prefs.getInt(currentFirebaseUser+"totalPoints", 0)+(routine.getExerciseArrayList().get(routine.getCurrentExercise()).getMovesPerRep()));
        editor.apply();

        PointsEarntOutput.setText("Points earnt: " + prefs.getInt(currentFirebaseUser+"totalPoints", 0));
    }
}