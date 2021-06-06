package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

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

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        Routine routine = (Routine) bundle.getSerializable("routine");

        getActivity().setTitle(routine.getRoutineName());

        TextView routineSummary = view.findViewById(R.id.routine_summary);
        routineSummary.setText(routine.getRoutineSummary());

        TextView starRating = view.findViewById(R.id.star_rating);
        starRating.setText(String.format("Routine Rating: %d/5", routine.getRating()));

        TextView routineSets = view.findViewById(R.id.sets);
        routineSets.setText(String.format("Total sets to complete: %d", routine.getSets()));

        ListView listView = view.findViewById(R.id.start_summary_list_view);
        listView.setEnabled(false);

        MovesListAdapter movesListAdapter = new MovesListAdapter(getContext(), routine.getExerciseArrayList());

        listView.setAdapter(movesListAdapter);

        Button toInfoPage = view.findViewById(R.id.toInfoPage);
        InformationFragment informationFragment = new InformationFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("routine",routine);
        informationFragment.setArguments(bundle);
        toInfoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getDefaultSharedPreferences(getContext());
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                SharedPreferences.Editor editor = prefs.edit();
                Log.i("completedRoutines", String.valueOf(prefs.getInt(currentFirebaseUser+"completedRoutines", 0)));
                editor.putInt(currentFirebaseUser + "completedRoutines", prefs.getInt(currentFirebaseUser+"completedRoutines", 0)+1);
                editor.apply();
                FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
                DatabaseReference rootReferenceUser = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
                rootReferenceUser.child("userDetails").child("completedRoutines").setValue(prefs.getInt(currentFirebaseUser+"completedRoutines", 0));
                Log.i("completedRoutines saved", String.valueOf(prefs.getInt(currentFirebaseUser+"completedRoutines", 0)));

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RoutineAndExerciseFrame, informationFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}