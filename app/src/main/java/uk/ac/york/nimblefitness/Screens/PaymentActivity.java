package uk.ac.york.nimblefitness.Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.share.Share;

import uk.ac.york.nimblefitness.Adapters.PaymentListAdapter;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

//Importing

public class PaymentActivity extends AppCompatActivity implements PaymentListAdapter.MyActionCallback {

    //Runs when page is created (opened by user)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setTitle("Membership Plan");
        String[] planSubtitle = {"Basic Membership", "Intermediate Membership",
                "Advanced Membership"};
        String[] planTier = {"Bronze", "Silver", "Gold"};
        String[] membershipDetails = {"more details", "more details", "more details"};
        int[] planImage = {R.drawable.bronzerounded,
                R.drawable.silverrounded,
                R.drawable.goldrounded};
    /*int[] detailsIcon = {R.drawable.ic_baseline_keyboard_arrow_down_24,
            R.drawable.ic_baseline_keyboard_arrow_up_24,
            R.drawable.ic_baseline_keyboard_arrow_down_24,
            R.drawable.ic_baseline_keyboard_arrow_up_24,
            R.drawable.ic_baseline_keyboard_arrow_down_24,
            R.drawable.ic_baseline_keyboard_arrow_up_24};*/
        String[] moreDetailsButton = {"more details", "more details", "more details"};
        //String[] selectionButton = {"select1", "select2", "select3"};


        PaymentListAdapter listAdapter = new PaymentListAdapter(this, planSubtitle, planTier, planImage, moreDetailsButton, this);
        /**Context context, String [] planSubtitle, String [] planTier, int [] planImage,
         int [] moreDetailsButton, String[] membershipDetails,
         int [] selectionButton**/

        ListView list = findViewById(R.id.expanding_item);

        list.setAdapter(listAdapter);//setListViewHeightBasedOnChildren(list);





    }

    public void planBought(View view) {
        startActivity(new Intent(getApplicationContext(), UserDetailsActivity.class));//takes user to the main page
        finish();
    }

    @Override
    public void onActionPerformed(String position) {
        Button checkout = findViewById(R.id.checkout_button);
        checkout.setText(position);
    }
}