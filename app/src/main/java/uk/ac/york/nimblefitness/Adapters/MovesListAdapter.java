package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import uk.ac.york.nimblefitness.R;

/*
 This adapter is used to display the list items in the goal and calendar tabs of the profile page so
 that each item contains an icon representing the exercise, its title and the exercise details along
 with the number of moves which will contributing towards the current goal amount and fill the goal
 gauge.
*/
public class MovesListAdapter extends BaseAdapter {

    Context context;
    private String[] exerciseTitle;
    private String[] exerciseDetails;
    private int[] exerciseIcon;

    public MovesListAdapter(Context context, String [] exerciseTitle,
                       String [] exerciseDetails, int [] exerciseIcon) {
        this.context = context;
        this.exerciseTitle = exerciseTitle;
        this.exerciseDetails = exerciseDetails;
        this.exerciseIcon = exerciseIcon;
    }

    @Override
    public int getCount() {
        return exerciseTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.moves_list_layout, parent, false);

            viewHolder.txtName = (TextView) convertView.findViewById(R.id.exercise_name);
            viewHolder.txtDetails = (TextView) convertView.findViewById(R.id.sets_of_reps);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.exercise_icon);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtName.setText(exerciseTitle[position]);
        viewHolder.txtDetails.setText(exerciseDetails[position]);
        viewHolder.icon.setImageResource(exerciseIcon[position]);
        return convertView;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtDetails;
        ImageView icon;
    }
}
