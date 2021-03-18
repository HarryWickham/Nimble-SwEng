package uk.ac.york.nimblefitness.Adapters;

/*public class RoutineListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    int mResource;
    ArrayList<Routine> routineList;

    public RoutineListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Routine> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        objects = routineList;
    }

   /* @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String routineName = getItem(position).getRoutineName();
        int routineImage = getItem(position).getRoutineImage();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvRoutineName = (TextView) convertView.findViewById(R.id.routines_activity_name);
        ImageView ivRoutineImage = (ImageView) convertView.findViewById(R.id.routines_image);

        tvRoutineName.setText(routineName);
        ivRoutineImage.setImageResource(routineImage);

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }*/

    //Above is the previous code, below is the code for using a BaseExpandableListAdapter

    /*@Override
    public int getGroupCount() {
        //Return no. of routines
        return routineList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        //Return no. of exercises in given routine
        return routineList.get(i).getNumberOfExercises();
    }

    @Override
    public Object getGroup(int i) {
        //Get a specified routine
        return routineList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        //Get a specified exercise
        return routineList.get(i).getExercise(i1);
    }

    @Override
    public long getGroupId(int i) {
        return routineList.get(i).getRoutineImage(); //Using image as an ID for now
    }

    @Override
    public long getChildId(int i, int i1) {
        return routineList.get(i).getExercise(i1).getExerciseImage(); //Using image as an ID for now
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        //Initialise the view
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.routines_list_layout, viewGroup, false);
        //Initialise and assign variables

        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;

public class RoutineListAdapter extends ArrayAdapter<Routine> {

    private Context mContext;
    int mResource;

    public RoutineListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Routine> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String routineName = getItem(position).getRoutineName();
        int routineImage = getItem(position).getRoutineImage();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvRoutineName = (TextView) convertView.findViewById(R.id.routines_activity_name);
        ImageView ivRoutineImage = (ImageView) convertView.findViewById(R.id.routines_image);

        tvRoutineName.setText(routineName);
        ivRoutineImage.setImageResource(routineImage);

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
