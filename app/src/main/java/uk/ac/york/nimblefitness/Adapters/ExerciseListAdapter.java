package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.R;

public class ExerciseListAdapter extends BaseAdapter{
    Context context;
    List<Exercise> exerciseList;

    public ExerciseListAdapter(Context context, List<Exercise> exerciseList){
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @Override
    public int getCount() { return exerciseList.size(); }

    @Override
    public Object getItem(int i) { return exerciseList.get(i); }

    @Override
    public long getItemId(int i) { return 0; }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ExerciseListAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ExerciseListAdapter.ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.exercise_list_layout, viewGroup, false);

            viewHolder.colourBar =  convertView.findViewById(R.id.exercise_list_colour_bar);
            viewHolder.mytitleView = convertView.findViewById(R.id.exercise_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ExerciseListAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.colourBar.setBackgroundColor(exerciseList.get(i).getColour());
        viewHolder.mytitleView.setText(exerciseList.get(i).getExerciseName());
        return convertView;
    }

    public class ViewHolder{
        TextView mytitleView;
        View colourBar;

    }
}
