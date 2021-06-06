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

        getActivity().setTitle("Settings");

        View view; //shows the fragment_settings.xml file in the frame view of the activity_main.xml
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        String[] settings_list_items = {"Account", "Membership Plan", "Billing Information", "Report a Problem", "Terms and Conditions", "Logout", "HandlerTestActivity"}; //the text that goes in each different list view item

        int[] settingsListIcons = {
                R.drawable.ic_baseline_account_circle_24,
                R.drawable.ic_baseline_card_membership_24,
                R.drawable.ic_baseline_payment_24,
                R.drawable.ic_baseline_report_problem_24,
                R.drawable.ic_baseline_error_outline_24,
                R.drawable.ic_baseline_login_24,
                R.drawable.ic_baseline_download_24
        };

        SettingsListAdapter settings = new SettingsListAdapter(settings_list_items, settingsListIcons, getContext());

        ListView listView = view.findViewById(R.id.settings_list); //find the list view from the fragment_settings.xml file
        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, settings_list_items); //need to make a 'simple_list_item_1' replacement -> 'settings_list_layout' allow the use of images in the list view like in settings activity example
        listView.setAdapter(settings);

        listView.setOnItemClickListener((parent, view1, position, id) -> {//watches for a user to click on the list view, then gives the program which position the click was in
            String itemValue = (String) listView.getItemAtPosition(position); //converts the position ID into the text that is written in that position
            Toast toast = Toast.makeText(getActivity(), itemValue, Toast.LENGTH_SHORT); //shows an alert with the text of the list item that has been clicked
            toast.show();

            switch (itemValue) {
                case "Billing Information": {
                    String url = "https://play.google.com/store/account/subscriptions";
                    Intent mIntent = new Intent(Intent.ACTION_VIEW);
                    mIntent.setData(Uri.parse(url));
                    startActivity(mIntent);
                    break;
                }
                case "Membership Plan": {
                    Intent mIntent = new Intent(getActivity(), PaymentActivity.class);
                    startActivity(mIntent);
                    requireActivity().finish();
                    break;
                }

                case "Terms and Conditions": { //if logout is clicked the user gets taken back to the login/signin screen will need to be changed to a case statement to allow for all items to be perform actions
                    FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                    fr.replace(R.id.main_frame, new TermsAndConditionsFragment());
                    fr.commit();
                    break;
                }

                case "Report a Problem":{
                    String url = "https://forms.gle/QUjMeKqGW5W82RJN6";
                    Intent mIntent = new Intent(Intent.ACTION_VIEW);
                    mIntent.setData(Uri.parse(url));
                    startActivity(mIntent);
                    break;
                }

                case "Logout": { //if logout is clicked the user gets taken back to the login/signin screen will need to be changed to a case statement to allow for all items to be perform actions
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    GoogleSignIn.getClient(getActivity(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut().addOnSuccessListener(aVoid ->
                            startActivity(new Intent(view.getContext(), SigninActivity.class))).addOnFailureListener(e ->
                            Toast.makeText(getActivity(), "Sign-out Failed", Toast.LENGTH_SHORT).show());

                    Intent mIntent = new Intent(getActivity(), SigninActivity.class);
                    startActivity(mIntent);
                    requireActivity().finish();//closes this activity so when the user logs in again they are taken to the profile page not settings (also conserves device memory)

                }

                case "Account": { //if logout is clicked the user gets taken back to the login/signin screen will need to be changed to a case statement to allow for all items to be perform actions
                    Intent mIntent = new Intent(getActivity(), UserDetailsActivity.class);
                    startActivity(mIntent);
                    break;
                }
                case "HandlerTestActivity": { //if logout is clicked the user gets taken back to the login/signin screen will need to be changed to a case statement to allow for all items to be perform actions
                    Intent mIntent = new Intent(getActivity(), HandlerTestActivity.class);
                    startActivity(mIntent);
                    break;
                }
            }
            
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
        fr.replace(R.id.main_frame, new SettingsFragment());
        fr.commit();
        return super.onOptionsItemSelected(item);

    }
}