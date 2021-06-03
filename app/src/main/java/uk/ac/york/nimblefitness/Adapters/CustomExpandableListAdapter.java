package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
    List<Routine> originalRoutineArrayList;
    List<Routine> routineArrayList;

    public CustomExpandableListAdapter(Context context, ArrayList<Routine> routineArrayList) {
        this.context = context;
        this.routineArrayList = routineArrayList;
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

    public void filterData(String query) {
        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(originalRoutineArrayList.size()));
        routineArrayList.clear();

        if (query.isEmpty()) {
            routineArrayList = originalRoutineArrayList;
        } else {

        }


    }
}
