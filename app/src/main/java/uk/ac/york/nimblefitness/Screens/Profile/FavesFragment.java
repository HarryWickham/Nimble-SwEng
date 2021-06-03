package uk.ac.york.nimblefitness.Screens.Profile;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import uk.ac.york.nimblefitness.Adapters.MovesListAdapter;
import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.R;

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

        ListView FavouritesList = view.findViewById(R.id.FavouritesList);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();  // Getting the unique ID for the current user for their information

        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentFirebaseUser.getUid()); // Finding the "users" child in the firebase



        // Listener to gather information out of the firebase
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (currentFirebaseUser != null) { // If the firebase is detected
                    // Importing the favourited routine name into the favourites list to be displayed
                    favourite = snapshot.child("favouriteRoutines").getValue(String.class);
                    Log.i("TAG", "" + favourite); // Console output
                    // Outputs the found name of the favourite routine in a list using the pre made routine card
                    ListView FavouritesListView = view.findViewById(R.id.FavouritesList);

                    // If there is a saved routine in favourites
                    if (favourite != null) {
                        Log.i("TAG", "" + currentFirebaseUser.getUid()); // Console output
                        // Makes a new routine and names it the name of the saved favourite routine
                        //Routine routine1 = new Routine(favourite, R.drawable.final_logo);
                        //Routine routine2 = new Routine("Routine2", R.drawable.final_logo);
                        //Routine routine3 = new Routine("Routine3", R.drawable.final_logo);
                        //Routine routine4 = new Routine("Routine4", R.drawable.final_logo);

                        ArrayList<Exercise> exercises = new ArrayList<>();

                        Routine routine = new Routine().getExampleRoutine();

                        // Creates an array for the favourite routines to go into
                        //ArrayList<Routine> FavouritesArrayList = new ArrayList<>();
                        //FavouritesArrayList.add(routine1); // Adding the routine to the list for favourites
                        //FavouritesArrayList.add(routine2);
                        //FavouritesArrayList.add(routine3);
                        //FavouritesArrayList.add(routine4);


                        /*RoutineListAdapter adapter = new RoutineListAdapter(requireContext(), R.layout.routines_list_layout, FavouritesArrayList);
                        FavouritesListView.setAdapter(adapter);*/
                    } else { // If there are no favourite routines found
                        String[] settings_list_items = {"Favourites"}; //Adds a simple list with the title "favourites" to show that is where they will go when favourited

                        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, settings_list_items); //need to make a 'simple_list_item_1' replacement -> 'settings_list_layout' allow the use of images in the list view like in settings activity example
                        FavouritesList.setAdapter(ListViewAdapter); // Changing the layout of the list in favourites if there are no favourite routines
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });


        return view;
    }
}