package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.R;

/*
 This adapter is used to display the list items in the goal and calendar tabs of the profile page so
 that each item contains an icon representing the exercise, its title and the exercise details along
 with the number of moves which will contributing towards the current goal amount and fill the goal
 gauge.
*/
public class MovesListAdapter extends BaseAdapter {

    Context context;
    private ArrayList<Exercise> exercise;

    public MovesListAdapter(Context context, ArrayList<Exercise> exercise) {
        this.context = context;
        this.exercise = exercise;
    }

    @Override
    public int getCount() {
        return exercise.size();
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
            viewHolder.txtMoves = (TextView) convertView.findViewById(R.id.number_of_moves);
            viewHolder.colourBar = convertView.findViewById(R.id.moves_list_colour_bar);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtName.setText(exercise.get(position).getExerciseName());
        if(exercise.get(position).getRepType().equalsIgnoreCase("time")){
            viewHolder.txtDetails.setText(String.format(Locale.UK,"%d seconds", exercise.get(position).getReps()));
        }else if (exercise.get(position).getRepType().equalsIgnoreCase("number")){
            viewHolder.txtDetails.setText(String.format(Locale.UK,"%d reps", exercise.get(position).getReps()));
        }
        viewHolder.txtMoves.setText(String.format(Locale.UK,"Moves: %d", exercise.get(position).getMovesPerRep()*exercise.get(position).getReps()));
        viewHolder.colourBar.setBackgroundColor(exercise.get(position).getColour());
        return convertView;
    }

    public String getExerciseTitleAtPosition(int position){
        return exercise.get(position).getExerciseName();
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtDetails;
        TextView txtMoves;
        View colourBar;
    }
}
