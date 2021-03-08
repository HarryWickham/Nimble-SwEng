package uk.ac.york.nimblefitness.Screens.Settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Objects;

import uk.ac.york.nimblefitness.R;

public class TermsAndConditions extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false); //shows the fragment_settings.xml file in the frame view of the activity_main.xml

        String[] terms_and_conditions_options_list = {"T&Cs","Membership Plan","Account","Terms and Conditions","Logout"}; //the text that goes in each different list view item

        ListView listView = (ListView) view.findViewById(R.id.terms_and_conditions_options_list); //find the list view from the fragment_settings.xml file

        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, terms_and_conditions_options_list); //need to make a 'simple_list_item_1' replacement -> 'settings_list_layout' allow the use of images in the list view like in settings activity example
        listView.setAdapter(ListViewAdapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {//watches for a user to click on the list view, then gives the program which position the click was in
            String itemValue = (String) listView.getItemAtPosition(position); //converts the position ID into the text that is written in that position

            Toast toast = Toast.makeText(getActivity(), itemValue, Toast.LENGTH_SHORT); //shows an alert with the text of the list item that has been clicked
            toast.show();
        });
        return view;
    }
}
