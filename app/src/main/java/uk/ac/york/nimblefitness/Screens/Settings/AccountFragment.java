package uk.ac.york.nimblefitness.Screens.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import uk.ac.york.nimblefitness.R;

public class AccountFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Account");

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //onOptionsItemSelected(actionBar);

        View view = inflater.inflate(R.layout.fragment_account, container, false); //shows the fragment_settings.xml file in the frame view of the activity_main.xml

        String[] account_options_list = {"Favourites", "Notifications", "Report Problem"}; //the text that goes in each different list view item

        ListView listView = (ListView) view.findViewById(R.id.account_options_list); //find the list view from the fragment_settings.xml file

        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, account_options_list); //need to make a 'simple_list_item_1' replacement -> 'settings_list_layout' allow the use of images in the list view like in settings activity example
        listView.setAdapter(ListViewAdapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {//watches for a user to click on the list view, then gives the program which position the click was in
            String itemValue = (String) listView.getItemAtPosition(position); //converts the position ID into the text that is written in that position

            Toast toast = Toast.makeText(getActivity(), itemValue, Toast.LENGTH_SHORT); //shows an alert with the text of the list item that has been clicked
            toast.show();


            switch (itemValue) {
                case "Favourites": { //if logout is clicked the user gets taken back to the login/signin screen will need to be changed to a case statement to allow for all items to be perform actions
                    FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                    fr.replace(R.id.main_frame, new FavouritesFragment());
                    fr.commit();
                    break;
                }
                case "Notifications": { //if logout is clicked the user gets taken back to the login/signin screen will need to be changed to a case statement to allow for all items to be perform actions
                    FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                    fr.replace(R.id.main_frame, new NotificationsFragment());
                    fr.commit();
                    break;
                }
                case "Report Problem": { //if logout is clicked the user gets taken back to the login/signin screen will need to be changed to a case statement to allow for all items to be perform actions
                    FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                    fr.replace(R.id.main_frame, new ReportProblemFragment());
                    fr.commit();
                    break;
                }
            }
        });
        return view;
    }
}
