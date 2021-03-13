package uk.ac.york.nimblefitness.Screens.Search;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.R;


public class SearchFragment extends Fragment {

    ListView list;
    SearchView search;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Search");

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        list = view.findViewById(R.id.list);
        search = view.findViewById(R.id.search);
        search.setActivated(true);
        search.setQueryHint("Search for exercises");
        search.onActionViewExpanded();
        search.setIconified(false);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Push Ups");
        arrayList.add("Sit Ups");
        arrayList.add("Crunches");
        arrayList.add("Squats");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                arrayList);
        list.setAdapter(arrayAdapter);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) list.getItemAtPosition(position);
                Toast.makeText(getContext(), clickedItem, Toast.LENGTH_LONG).show();
            }
        });
        // Inflate the layout for this fragment
        return view;


    }


}