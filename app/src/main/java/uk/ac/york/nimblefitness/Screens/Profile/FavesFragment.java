package uk.ac.york.nimblefitness.Screens.Profile;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

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

import uk.ac.york.nimblefitness.Adapters.CustomExpandableListAdapter;
import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.Routines.RoutineData;

// NOTE
// For this iteration the favourites list only loads a single favourited routine from the firebase
// This load is account based and the favourited routine must be added manually
// This tests only purpose is to show a routine being loaded from the firebase
// This favourited routine is a child under a users unique code, called favouriteRoutines with the value being the routine
// An example of this is under user ez6SMLl19sUlhfUJ46mRXJDdGSf2

public class FavesFragment extends Fragment {

    DatabaseReference mDatabase;

    String favourite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle("Profile");
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        ExpandableListView FavouritesList = view.findViewById(R.id.routine_exp_list);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();  // Getting the unique ID for the current user for their information

        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentFirebaseUser.getUid()); // Finding the "users" child in the firebase

        // Listener to gather information out of the firebase
        mDatabase.child("favorites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> favourites = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    favourites.add(dataSnapshot.getValue(String.class));
                }

                RoutineData routineData = new RoutineData(getContext(), R.raw.routines); // Object of routine data, that holds all the data from the routines.xml
                ArrayList<Routine> routines = routineData.getRoutine();

                ArrayList<Routine> favouriteRoutines = new ArrayList<>();

                for(Routine routine : routines){
                    for(String favs: favourites){
                        if(routine.getRoutineName().equals(favs)){
                            favouriteRoutines.add(routine);
                        }
                    }
                }

                CustomExpandableListAdapter listAdapter = new CustomExpandableListAdapter(getContext(), favouriteRoutines); // Assigns the listAdapter with the

                FavouritesList.setAdapter(listAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });


        return view;
    }
}