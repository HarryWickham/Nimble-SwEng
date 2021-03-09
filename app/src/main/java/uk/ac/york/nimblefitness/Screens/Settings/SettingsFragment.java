package uk.ac.york.nimblefitness.Screens.Settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.SigninActivity;
import uk.ac.york.nimblefitness.Screens.UserDetailsActivity;

public class SettingsFragment extends Fragment {

    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();

        View view = inflater.inflate(R.layout.fragment_settings, container, false); //shows the fragment_settings.xml file in the frame view of the activity_main.xml

        String[] settings_list_items = {"Billing Information", "Membership Plan", "Account", "Terms and Conditions", "logout", "User Details"}; //the text that goes in each different list view item

        ListView listView = (ListView) view.findViewById(R.id.settings_list); //find the list view from the fragment_settings.xml file

        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, settings_list_items); //need to make a 'simple_list_item_1' replacement -> 'settings_list_layout' allow the use of images in the list view like in settings activity example
        listView.setAdapter(ListViewAdapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {//watches for a user to click on the list view, then gives the program which position the click was in
            String itemValue = (String) listView.getItemAtPosition(position); //converts the position ID into the text that is written in that position
            Toast toast = Toast.makeText(getActivity(), itemValue, Toast.LENGTH_SHORT); //shows an alert with the text of the list item that has been clicked
            toast.show();

                if (itemValue.equals("Logout")) { //if logout is clicked the user gets taken back to the login/signin screen will need to be changed to a case statement to allow for all items to be perform actions
                    FirebaseAuth.getInstance().signOut();
                    GoogleSignIn.getClient(getActivity(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(view.getContext(), SigninActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Signout Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent mIntent = new Intent(getActivity(), SigninActivity.class);
                    startActivity(mIntent);
                    requireActivity().finish();//closes this activity so when the user logs in again they are taken to the profile page not settings (also conserves device memory)
                } else if (itemValue.equals("Account")) { //if logout is clicked the user gets taken back to the login/signin screen will need to be changed to a case statement to allow for all items to be perform actions
                    FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                    fr.replace(R.id.main_frame, new AccountFragment());
                    fr.commit();
                } else if (itemValue.equals("Terms and Conditions")) { //if logout is clicked the user gets taken back to the login/signin screen will need to be changed to a case statement to allow for all items to be perform actions
                    FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                    fr.replace(R.id.main_frame, new TermsAndConditionsFragment());
                    fr.commit();
                } else if (itemValue.equals("User Details")) { //if logout is clicked the user gets taken back to the login/signin screen will need to be changed to a case statement to allow for all items to be perform actions
                    Intent mIntent = new Intent(getActivity(), UserDetailsActivity.class);
                    startActivity(mIntent);
                    requireActivity().finish();
                }

        });

        return view;
    }
}