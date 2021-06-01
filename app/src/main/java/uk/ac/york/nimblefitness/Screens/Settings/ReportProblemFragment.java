package uk.ac.york.nimblefitness.Screens.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import uk.ac.york.nimblefitness.R;

public class ReportProblemFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Report a Problem");

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        View view = inflater.inflate(R.layout.fragment_report_problem, container, false); //shows the fragment_settings.xml file in the frame view of the activity_main.xml

        WebView googleForum;

        googleForum = view.findViewById(R.id.report_a_problem_id);
        googleForum.loadUrl("https://forms.gle/QUjMeKqGW5W82RJN6");
        googleForum.getSettings().setJavaScriptEnabled(true);

        return view;
    }
}
