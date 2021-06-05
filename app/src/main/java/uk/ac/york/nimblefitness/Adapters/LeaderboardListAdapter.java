package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.HelperClasses.LeaderBoardUserDetails;
import uk.ac.york.nimblefitness.HelperClasses.ShareService;
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
            viewHolder.leaderBoardShareButton = convertView.findViewById(R.id.leaderboard_share_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LeaderboardListAdapter.ViewHolder) convertView.getTag();
        }
        if(leaderBoardUserDetails.get(position).isUser()){
            viewHolder.leaderBoardLayout.setBackgroundColor(Color.parseColor("#edb964"));
            viewHolder.leaderBoardShareButton.setVisibility(View.VISIBLE);
        }else{
            viewHolder.leaderBoardLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            viewHolder.leaderBoardShareButton.setVisibility(View.GONE);
        }
        viewHolder.rankTV.setText(String.valueOf(position+1));
        viewHolder.firstNameTV.setText(leaderBoardUserDetails.get(position).getName());
        viewHolder.scoreTV.setText(String.valueOf(leaderBoardUserDetails.get(position).getScore()));

        viewHolder.leaderBoardShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new ShareService("Look at where I am on the leader board", "I have completed " + String.valueOf(leaderBoardUserDetails.get(position).getScore()) + " moves this week. I am in " + String.valueOf(position+1) + positionPrefix(position+1) + " place. Can you beat me next week? \n Download the Nimble Fitness Companion app now: https://www-users.york.ac.uk/~hew550/", "Share your position on the leader board - " ).ShareContent());
            }
        });


        return convertView;
    }

    private static class ViewHolder {
        TextView rankTV;
        TextView firstNameTV;
        TextView scoreTV;
        LinearLayout leaderBoardLayout;
        ImageView leaderBoardShareButton;
    }

    public String positionPrefix(int dayOfMonth){
        switch (dayOfMonth){
            case 1:
            case 21:
            case 31:
                return ("st");
            case 2:
            case 22:
                return ("nd");
            case 3:
            case 23:
                return ("rd");
            default: return ("th");
        }
    }
}
