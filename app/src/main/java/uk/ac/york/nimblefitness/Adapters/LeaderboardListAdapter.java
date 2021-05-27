package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.HelperClasses.LeaderBoardUserDetails;
import uk.ac.york.nimblefitness.R;

public class LeaderboardListAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<LeaderBoardUserDetails> leaderBoardUserDetails;

    public LeaderboardListAdapter(ArrayList<LeaderBoardUserDetails> leaderBoardUserDetails, Context context) {
        this.context = context;
        this.leaderBoardUserDetails = leaderBoardUserDetails;
    }

    @Override
    public int getCount() {
        return leaderBoardUserDetails.size();
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
        LeaderboardListAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new LeaderboardListAdapter.ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.leaderboard_list_layout, parent, false);

            viewHolder.rankTV = convertView.findViewById(R.id.leaderboard_rank);
            viewHolder.firstNameTV = convertView.findViewById(R.id.leaderboard_first_name);
            viewHolder.scoreTV = convertView.findViewById(R.id.leaderboard_score);
            viewHolder.leaderBoardLayout = convertView.findViewById(R.id.leader_board_layout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LeaderboardListAdapter.ViewHolder) convertView.getTag();
        }
        if(leaderBoardUserDetails.get(position).isUser()){
            viewHolder.leaderBoardLayout.setBackgroundColor(Color.parseColor("#edb964"));
        }else{
            viewHolder.leaderBoardLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        viewHolder.rankTV.setText(String.valueOf(position+1));
        viewHolder.firstNameTV.setText(leaderBoardUserDetails.get(position).getName());
        viewHolder.scoreTV.setText(String.valueOf(leaderBoardUserDetails.get(position).getScore()));

        return convertView;
    }

    private static class ViewHolder {
        TextView rankTV;
        TextView firstNameTV;
        TextView scoreTV;
        LinearLayout leaderBoardLayout;
    }
}
