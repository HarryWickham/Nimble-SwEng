package uk.ac.york.nimblefitness.Screens.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import uk.ac.york.nimblefitness.Adapters.LeaderboardListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.LeaderBoardUserDetails;
import uk.ac.york.nimblefitness.R;

/**
 * This class loads the data and appearance for the leaderboard tab which is only available/visible
 * to users who have a gold subscription plan.
 */
public class LeaderBoardFragment extends Fragment {

    ArrayList<LeaderBoardUserDetails> leaderBoardUserDetails = new ArrayList<>();
    LeaderBoardUserDetails leaderBoardUserDetail;
    ListView listView;
    ProgressBar leaderBoardProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leader_board, container, false);
        listView = view.findViewById(R.id.leader_board_list_view);
        leaderBoardProgressBar = view.findViewById(R.id.leaderboard_progress_circular);
        leaderBoardProgressBar.setVisibility(View.VISIBLE);
        fetchData();

        return view;
    }

    private void fetchData() {

        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference rootReference = rootDatabase.getReference("scoreBoard");

        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    leaderBoardUserDetail = ds.getValue(LeaderBoardUserDetails.class);
                    leaderBoardUserDetail.setUser(ds.child("uuid").getValue()
                            .equals(currentFirebaseUser.getUid()));
                    leaderBoardUserDetails.add(leaderBoardUserDetail);
                    updateListView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateListView() {
        leaderBoardProgressBar.setVisibility(View.GONE);
        Collections.sort(leaderBoardUserDetails);
        LeaderboardListAdapter listAdapter = new LeaderboardListAdapter(leaderBoardUserDetails,
                getContext());
        listView.setAdapter(listAdapter);
    }

}