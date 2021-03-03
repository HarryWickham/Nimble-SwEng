package uk.ac.york.nimblefitness.Adapters;

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
import java.util.List;

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
