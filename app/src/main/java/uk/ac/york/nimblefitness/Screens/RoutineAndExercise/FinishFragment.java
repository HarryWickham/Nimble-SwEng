package uk.ac.york.nimblefitness.Screens.RoutineAndExercise;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Locale;

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;
import uk.ac.york.nimblefitness.Screens.PaymentActivity;

public class FinishFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish, container, false);

        getActivity().setTitle("Finish Page");

        ArrayList<Exercise> exercises = new ArrayList<>();

        exercises.add(new Exercise("Image","Video","Press-up","Description",10,15,10, R.drawable.ic_baseline_accessibility_24));
        exercises.add(new Exercise("Image","Video","Sit-up","Description",10,15,10, R.drawable.ic_baseline_accessibility_24));

        Routine routine = new Routine("Image","Name","Summary",0,5,10,exercises);

        Button toEndSummary = view.findViewById(R.id.toEndSummary);
        Button NextExercise = view.findViewById(R.id.continue_button);
        Button ExitToProfile = view.findViewById(R.id.exit_button);
        TextView finishText = view.findViewById(R.id.finish_text);
        ListView finishListView = view.findViewById(R.id.finish_list_view);


        exercises.add(new Exercise("","","Plank","",60,1,0, R.drawable.ic_baseline_accessibility_24));
        exercises.add(new Exercise("","","Squats","",20,1,0, R.drawable.ic_baseline_accessibility_24));
        exercises.add(new Exercise("","","Sit-ups","",15,1,0, R.drawable.ic_baseline_accessibility_24));
        exercises.add(new Exercise("","","Press-ups","",10,1,0, R.drawable.ic_baseline_accessibility_24));

        MovesListAdapter movesListAdapter = new MovesListAdapter(getContext(), exercises);

        finishListView.setAdapter(movesListAdapter);
        setListViewHeightBasedOnChildren(finishListView);
        finishText.setText(String.format(Locale.UK,"Congratulations you have completed %d %ss",routine.getExerciseArrayList().get(0).getReps(), routine.getExerciseArrayList().get(0).getExerciseName()));

        ExitToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));//takes user the main page
            }
        });

        EndSummaryFragment endSummaryFragment = new EndSummaryFragment();
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
}