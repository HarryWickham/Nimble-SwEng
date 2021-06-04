package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
import android.content.Intent;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.RoutineAndExercise.RoutineAndExerciseActivity;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Routine> originalRoutineArrayList;
    private ArrayList<Routine> routineArrayList;

    public CustomExpandableListAdapter(Context context, ArrayList<Routine> routineArrayList) {
        this.context = context;
        this.routineArrayList = routineArrayList;
        this.originalRoutineArrayList = (ArrayList<Routine>) routineArrayList.clone();
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
        Routine group = (Routine) getGroup(groupPosition);
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_group, null);
        }

        Button expand_routines_button = convertView.findViewById(R.id.expand_routines_button);
        expand_routines_button.setFocusable(false);
        expand_routines_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, RoutineAndExerciseActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("routine", routineArrayList.get(groupPosition));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        TextView routineName = convertView.findViewById(R.id.routines_activity_name);
        routineName.setText(group.getRoutineName());
        ImageView routineImage = convertView.findViewById(R.id.routines_image);
        routineImage.setImageResource(group.getRoutineImage());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Exercise child = (Exercise) getChild(groupPosition, childPosition);
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_item, null);
        }
        TextView exerciseName = convertView.findViewById(R.id.exercise_name);
        exerciseName.setText(child.getExerciseName());
        TextView exerciseMoves = convertView.findViewById(R.id.number_of_moves);
        exerciseMoves.setText("Moves: " + child.getMovesPerRep()*child.getReps());

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
        Log.v("MyListAdapter", String.valueOf(originalRoutineArrayList.size()));
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

                for (Exercise exercise : exerciseList) {
                    if(exercise.getExerciseName().toLowerCase().contains(query)) {
                        searchList.add(exercise);
                    }
                }
                if (searchList.size() > 0) {
                    Routine filteredRoutine = new Routine();
                    filteredRoutine = filteredRoutine.getExampleRoutine();
                    filteredRoutine.setExerciseArrayList(searchList);
                    routineArrayList.add(filteredRoutine);
                }
            }
        }
        notifyDataSetChanged();
        if (routineArrayList.size() == 0) {
            searched = false;
        }
        return searched;
    }
}
