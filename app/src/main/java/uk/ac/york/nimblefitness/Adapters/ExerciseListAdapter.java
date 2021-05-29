package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
