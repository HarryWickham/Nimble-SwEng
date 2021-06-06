package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import uk.ac.york.nimblefitness.R;

public class SettingsListAdapter extends BaseAdapter {

    Context context;
    String[] settingsTitles;
    int[] settingsIcons;

    public SettingsListAdapter(String[] settingsTitles, int[] settingsIcons, Context context) {
        this.context = context;
        this.settingsIcons = settingsIcons;
        this.settingsTitles = settingsTitles;
    }

    @Override
    public int getCount() {
        return settingsTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SettingsListAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new SettingsListAdapter.ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.settings_list_layout, parent, false);

            viewHolder.settingsName = convertView.findViewById(R.id.settings_list_text_view);
            viewHolder.settingsIcon = convertView.findViewById(R.id.settings_list_image_view);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SettingsListAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.settingsName.setText(settingsTitles[position]);
        viewHolder.settingsIcon.setImageResource(settingsIcons[position]);

        return convertView;
    }

    private static class ViewHolder {
        TextView settingsName;
        ImageView settingsIcon;
    }
}
