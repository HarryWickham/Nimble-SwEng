package uk.ac.york.nimblefitness.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.RoutineAndExercise.RoutineAndExerciseActivity;

public class ExerciseListAdapter extends BaseAdapter{
    Context context;
    List<Exercise> originalExerciseList;
    List<Exercise> exerciseList;

    public ExerciseListAdapter(Context context, ArrayList<Exercise> exerciseList){
        this.context = context;
        this.exerciseList = exerciseList;
        this.originalExerciseList = (List<Exercise>) exerciseList.clone();
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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Routine routine = new Routine();
                routine.setRoutineName("fake");

                Intent intent = new Intent(context, RoutineAndExerciseActivity.class);
                intent.putExtra("fragment", "information");
                Bundle bundle = new Bundle();
                bundle.putSerializable("exercise", (Serializable) exerciseList.get(i));
                bundle.putSerializable("routine", (Serializable) routine);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public boolean filterData(String query) {
        boolean searched = true;
        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(exerciseList.size()));
        exerciseList.clear();

        if (query.isEmpty()) {
            exerciseList.addAll(originalExerciseList);
            searched = true;
        } else {
            for (Exercise exercise : originalExerciseList) {
                if (exercise.getExerciseName().toLowerCase().contains(query)) {
                    exerciseList.add(exercise);
                }
            }
        }
        notifyDataSetChanged();
        if (exerciseList.isEmpty()) {
            searched = false;
        }
        return searched;
    }

    public class ViewHolder{
        TextView mytitleView;
        View colourBar;

    }
}
