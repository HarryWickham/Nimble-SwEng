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

import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.SigninActivity;

public class SettingsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        String[] settings_list_items = {"Billing Information","Membership Plan","Account","Terms and Conditions","logout"};

        ListView listView = (ListView) view.findViewById(R.id.settings_list);

        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, settings_list_items);
        listView.setAdapter(ListViewAdapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String itemValue = (String) listView.getItemAtPosition(position);

            Toast toast = Toast.makeText(getActivity(), itemValue , Toast.LENGTH_SHORT);
            toast.show();

            if(itemValue.equals("logout")){
                Intent mIntent = new Intent(getActivity(), SigninActivity.class);
                startActivity(mIntent);
                getActivity().finish();
            }
        });
        return view;
    }
}