package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;
import uk.ac.york.nimblefitness.Screens.PaymentActivity;
import uk.ac.york.nimblefitness.Screens.RoutineAndExercise.RoutineAndExerciseActivity;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Routine> originalRoutineArrayList;
    private ArrayList<Routine> routineArrayList;

    public CustomExpandableListAdapter(Context context, ArrayList<Routine> routineArrayList) {
        this.context = context;
        this.originalRoutineArrayList = routineArrayList;
        this.routineArrayList = (ArrayList<Routine>) routineArrayList.clone();
    }

    @Override
    public int getGroupCount() {
        return this.routineArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return routineArrayList.get(groupPosition).getExerciseArrayList().size();
    }

    @Override
    public Object getGroup(int groupPosition) { return routineArrayList.get(groupPosition); }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return routineArrayList.get(groupPosition).getExerciseArrayList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_group, null);
        }

        Button expand_routines_button = convertView.findViewById(R.id.expand_routines_button);
        expand_routines_button.setFocusable(false);
        expand_routines_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getDefaultSharedPreferences(context);
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if(prefs.getInt(currentFirebaseUser+"completedRoutines", 0)<getUserMembershipPlanRoutines(prefs, currentFirebaseUser)) {

                    Exercise exercise = new Exercise();
                    exercise.setExerciseName("fake");

                    Intent intent = new Intent(context, RoutineAndExerciseActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("routine",(Serializable) routineArrayList.get(groupPosition));
                    bundle.putSerializable("exercise", (Serializable) exercise);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else{
                    AlertDialog.Builder noMoreRoutines = new AlertDialog.Builder(context);
                    noMoreRoutines.setTitle("You have used all of your routines for this month.");
                    noMoreRoutines.setMessage("Would you like to upgrade your plan to get access to more plans per month?");
                    noMoreRoutines.setCancelable(true)
                            .setPositiveButton("Upgrade", (dialog, id) -> {
                                // if this button is clicked, close
                                // current activity
                                context.startActivity(new Intent(context, PaymentActivity.class));

                            })
                            .setNegativeButton("Cancel", (dialog, id) -> {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            });

                    // create alert dialog
                    noMoreRoutines.create().show();
                }
            }
        });
        TextView routineName = convertView.findViewById(R.id.routines_activity_name);
        routineName.setText(routineArrayList.get(groupPosition).getRoutineName());
        TextView routineSets = convertView.findViewById(R.id.routine_sets);
        routineSets.setText(routineArrayList.get(groupPosition).getSets() + " sets");
        TextView routineTotalMoves = convertView.findViewById(R.id.routine_total_moves);
        routineTotalMoves.setText("Total Moves: " + totalMoves(routineArrayList.get(groupPosition)));
        ImageView routineImage = convertView.findViewById(R.id.routines_image);
        Glide.with(context).load(routineArrayList.get(groupPosition).getRoutineImage()).into(routineImage);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Exercise child = (Exercise) getChild(groupPosition, childPosition);
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.moves_list_layout, null);
        }
        convertView.setPadding(20,0,20,10);
        TextView exerciseName = convertView.findViewById(R.id.exercise_name);
        exerciseName.setText(child.getExerciseName());
        TextView exerciseMoves = convertView.findViewById(R.id.number_of_moves);
        exerciseMoves.setText("Moves/Set: " + child.getMovesPerRep()*child.getReps());
        TextView numberOfReps = convertView.findViewById(R.id.sets_of_reps);
        numberOfReps.setText(child.getReps() + " reps");
        View colourBar = convertView.findViewById(R.id.moves_list_colour_bar);
        colourBar.setBackgroundColor(child.getColour());


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public ArrayList<Routine> getRoutineArrayList() {
        return routineArrayList;
    }

    /*
        Function for searching the Routines with the SearchView input
        Uses a copy of the
         */
    public boolean filterData(String query) {
        boolean searched = true;
        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(routineArrayList.size()));
        routineArrayList.clear();

        if (query.isEmpty()) {
            routineArrayList.addAll(originalRoutineArrayList);
            searched = true;
        } else {
            for (Routine routine : originalRoutineArrayList) {
                ArrayList<Exercise> exerciseList = routine.getExerciseArrayList();
                ArrayList<Exercise> searchList = new ArrayList<>();
                boolean searchedRoutine = false;

                if (routine.getRoutineName().toLowerCase().contains(query)) {
                    routineArrayList.add(routine);
                }
                boolean alreadyAdded = false;
                for (Exercise exercise : exerciseList) {
                    if(exercise.getExerciseName().toLowerCase().contains(query)) {
                        // Check to see if routine is already added
                        for(Routine addRoutine : routineArrayList) {
                            if(routine.getRoutineName().equals(addRoutine.getRoutineName())) {
                                alreadyAdded = true;
                            }
                        }

                        if (!alreadyAdded) {
                            routineArrayList.add(routine);
                        }
                    }
                }
            }
        }
        notifyDataSetChanged();
        if (routineArrayList.isEmpty()) {
            searched = false;
        }
        return searched;
    }

    public int getUserMembershipPlanRoutines(SharedPreferences prefs, FirebaseUser currentFirebaseUser ){
        switch (prefs.getString(currentFirebaseUser+"membershipPlan", "bronze")){
            case "bronze":
                return 20;
            case "silver":
                return 40;
            case "gold":
                return 9999;
            default:
                return 0;
        }
    }

    private int totalMoves(Routine routine){
        int routineTotalMoves = 0;

        for(int i = 0; i < routine.getExerciseArrayList().size(); i++){
            routineTotalMoves = routineTotalMoves + (int) routine.getExerciseArrayList().get(i).getMovesPerRep()*routine.getExerciseArrayList().get(i).getReps();
        }

        routineTotalMoves = routineTotalMoves*routine.getSets();

        return routineTotalMoves;
    }
}
