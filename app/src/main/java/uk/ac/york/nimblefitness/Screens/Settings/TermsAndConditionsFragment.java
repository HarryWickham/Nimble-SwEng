package uk.ac.york.nimblefitness.Screens.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import uk.ac.york.nimblefitness.R;

/**
 * Fragment to present the terms and conditions to the user.
 */
public class TermsAndConditionsFragment extends Fragment {

    // To allow the user to go back to the settings fragment
    ImageButton back_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Shows the fragment_terms_and_conditions.xml file in the frame view of the
           activity_main.xml */
        View view = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);

        //set the title of the screen
        requireActivity().setTitle("Terms and Conditions");

        //to allow for navigation back to the settings fragment
        back_button = view.findViewById(R.id.back_button_terms_and_conditions);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                fr.replace(R.id.main_frame, new SettingsFragment());
                fr.commit();
            }
        });

        return view;
    }
}
