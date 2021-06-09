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

/**
 * This class extends an ExpandableListAdapter, and helps connect the code to the UI
 * Using an ArrayList<Routine>, it stores all the information needed to form the Expandable List
 * Overriden methods are needed to get certain values from the ArrayList
 * A method at the bottom is used to filter the data for the SearchView
 */
public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Routine> originalRoutineArrayList;
    private ArrayList<Routine> routineArrayList;

    public CustomExpandableListAdapter(Context context, ArrayList<Routine> routineArrayList) {
        this.context = context;
        this.originalRoutineArrayList = routineArrayList;
        this.routineArrayList = (ArrayList<Routine>) routineArrayList.clone();
    }

    /**
     * Returns number of routines
     * @return number of routines
     */
    @Override
    public int getGroupCount() {
        return this.routineArrayList.size()-1;
    }

    /**
     * Returns number of exercises in a given group
     * @param groupPosition
     * @return number of exercises
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return routineArrayList.get(groupPosition).getExerciseArrayList().size();
    }

    /**
     * Returns a specified routine
     * @param groupPosition
     * @return routine
     */
    @Override
    public Object getGroup(int groupPosition) { return routineArrayList.get(groupPosition); }

    /**
     * Returns a specified exercise from a routine
     * @param groupPosition
     * @param childPosition
     * @return exercise
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return routineArrayList.get(groupPosition).getExerciseArrayList().get(childPosition);
    }

    /**
     * Returns routine ID (position)
     * @param groupPosition
     * @return routine position
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Returns exercise ID (position)
     * @param groupPosition
     * @param childPosition
     * @return exercise position
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition; //Our child elements don't have IDs, so this returns the position
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Sets out the information needed for displaying a routine card in the expandable list
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return view for the routines
     */
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
                if(prefs.getInt(currentFirebaseUser+"completedRoutines", 0) <
                        getUserMembershipPlanRoutines(prefs, currentFirebaseUser)) {

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

    /**
     * Sets out the information needed for displaying the list of exercises in a routine
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return view for the exercises
     */
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

    /**
     * Function for searching the Routines with the SearchView input
     * Uses a copy of the routineArrayList, clears it, and fills it up with routines/exercises that match the search
     * @param query
     * @return boolean determining if a search yielded a valid response
     */
    public boolean filterData(String query) {
        boolean searched = true; //Sets search to true when instantiated
        query = query.toLowerCase(); //Can use contains() without worry of capitalisation
        routineArrayList.clear();

        //When nothing is searched
        if (query.isEmpty()) {
            routineArrayList.addAll(originalRoutineArrayList);
            searched = true;
        } else {
            for (Routine routine : originalRoutineArrayList) {
                ArrayList<Exercise> exerciseList = routine.getExerciseArrayList();

                //If routine is searched
                if (routine.getRoutineName().toLowerCase().contains(query)) {
                    routineArrayList.add(routine);
                }
                //Boolean for checking for doubles
                boolean alreadyAdded = false;
                //If exercise is searched
                for (Exercise exercise : exerciseList) {
                    if(exercise.getExerciseName().toLowerCase().contains(query)) {
                        // Check to see if routine is already added
                        for(Routine addRoutine : routineArrayList) {
                            //Compares routine about to be added to routines already added
                            if(routine.getRoutineName().equals(addRoutine.getRoutineName())) {
                                alreadyAdded = true;
                            }
                        }

                        //If routine isn't already added
                        if (!alreadyAdded) {
                            routineArrayList.add(routine);
                        }
                    }
                }
            }
        }
        //Updates data on the expandableList
        notifyDataSetChanged();
        //If nothing matched the search
        if (routineArrayList.isEmpty()) {
            searched = false;
        }
        return searched;
    }

    /**
     * Gets information on user's plan to indicate how many routines a user has remaining
     * @param prefs
     * @param currentFirebaseUser
     * @return number of routines a user is allowed in a month
     */
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

    /**
     * Sums the number of moves of each exercise in a routine
     * @param routine
     * @return total moves for a given routine
     */
    private int totalMoves(Routine routine){
        int routineTotalMoves = 0;

        //Adds moves for each routine in a recurring for loop by multiplying movesPerRep x number of reps
        for(int i = 0; i < routine.getExerciseArrayList().size(); i++){
            routineTotalMoves = routineTotalMoves +
                    (int) routine.getExerciseArrayList().get(i).getMovesPerRep()*routine.getExerciseArrayList().get(i).getReps();
        }

        //Multiply the total by the number of sets the routine has
        routineTotalMoves = routineTotalMoves*routine.getSets();

        return routineTotalMoves;
    }
}
