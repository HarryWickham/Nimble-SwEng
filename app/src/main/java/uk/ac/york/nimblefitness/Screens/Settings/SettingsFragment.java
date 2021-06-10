package uk.ac.york.nimblefitness.Screens.Settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import uk.ac.york.nimblefitness.Adapters.SettingsListAdapter;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.PaymentActivity;
import uk.ac.york.nimblefitness.Screens.SigninActivity;
import uk.ac.york.nimblefitness.Screens.UserDetailsActivity;

/**
 * Fragment for the settings page from which the user can select several other pages. User can also
 * logout from this fragment.
 */
public class SettingsFragment extends Fragment {

    //instantiate token for firebase integration
    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //get a value for the firebase token
        firebaseAuth = FirebaseAuth.getInstance();

        //set the title for the screen
        getActivity().setTitle("Settings");

        View view; //shows the fragment_settings.xml file in the frame view of the activity_main.xml
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        //the text that go in each different list view item
        String[] settings_list_items = {"Account", "Membership Plan", "Billing Information",
                "Report a Problem", "Terms and Conditions", "Logout", "Load New Presentation"};

        //the icons that go in each different list view item
        int[] settingsListIcons = {R.drawable.ic_baseline_account_circle_24,
                R.drawable.ic_baseline_card_membership_24, R.drawable.ic_baseline_payment_24,
                R.drawable.ic_baseline_report_problem_24, R.drawable.ic_baseline_error_outline_24
                , R.drawable.ic_baseline_login_24, R.drawable.ic_baseline_download_24};

        //adapter to allow the text and icon of each item is be placed together
        SettingsListAdapter settings = new SettingsListAdapter(settings_list_items,
                settingsListIcons, getContext());

        //find the list view from the fragment_settings.xml file
        ListView listView = view.findViewById(R.id.settings_list);
        //need to make a 'simple_list_item_1' replacement -> 'settings_list_layout'
        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, settings_list_items);
        listView.setAdapter(settings);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            /** watches for a user to click on the list view, then gives the program which position
             * the click was in.
             */
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position
                    , long id) {

                switch (position) {
                    case 0: { //if account is clicked the user details will be loaded to the screen
                        Intent mIntent = new Intent(getActivity(), UserDetailsActivity.class);
                        startActivity(mIntent);
                        break;
                    }
                    case 1: { //if membership plan is clicked the payment activity starts
                        Intent mIntent = new Intent(getActivity(), PaymentActivity.class);
                        startActivity(mIntent);
                        break;
                    }
                    case 2: { //if billing information is clicked the play store is opened
                        String url = "https://play.google.com/store/account/subscriptions";
                        Intent mIntent = new Intent(Intent.ACTION_VIEW);
                        mIntent.setData(Uri.parse(url));
                        startActivity(mIntent);
                        break;
                    }
                    case 3: { //if report a problem is clicked a Google forum is loaded
                        String url = "https://forms.gle/QUjMeKqGW5W82RJN6";
                        Intent mIntent = new Intent(Intent.ACTION_VIEW);
                        mIntent.setData(Uri.parse(url));
                        startActivity(mIntent);
                        break;
                    }
                    case 4: { //if terms and conditions is clicked the user can view the T&Cs
                        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                        fr.replace(R.id.main_frame, new TermsAndConditionsFragment());
                        fr.commit();
                        break;
                    }
                    case 5: { //if logout is clicked the user gets taken back to the login/signin
                        // screen
                        FirebaseAuth.getInstance().signOut();
                        LoginManager.getInstance().logOut();
                        GoogleSignIn.getClient(getActivity(),
                                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .build()).signOut().addOnSuccessListener(aVoid ->
                                startActivity(new Intent(view.getContext(), SigninActivity.class)))
                                .addOnFailureListener(e -> Toast.makeText(getActivity(),
                                        "Sign-out Failed", Toast.LENGTH_SHORT).show());

                        Intent mIntent = new Intent(getActivity(), SigninActivity.class);
                        startActivity(mIntent);
                    /*closes this activity so when the user logs in again they are taken to the
                      profile page not settings (also conserves device memory) */
                        requireActivity().finish();

                    }
                    //if selected the user can select a downloadable xml file to load onto the app.
                    //this is for marking purposes.
                    case 6: {
                        Intent mIntent = new Intent(getActivity(),
                                LoadNewPresentationActivity.class);
                        startActivity(mIntent);
                        break;
                    }
                }
            }
        });

        return view;
    }

    //bottom navigation bar functionality
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
        fr.replace(R.id.main_frame, new SettingsFragment());
        fr.commit();
        return super.onOptionsItemSelected(item);

    }
}