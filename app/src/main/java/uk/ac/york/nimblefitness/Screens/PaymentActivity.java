package uk.ac.york.nimblefitness.Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.ac.york.nimblefitness.Adapters.PaymentListAdapter;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

//Importing

public class PaymentActivity extends AppCompatActivity implements PaymentListAdapter.MyActionCallback {

    Button checkout;
    String checkoutTier;

    //Runs when page is created (opened by user)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setTitle("Membership Plan");
        String[] planSubtitle = {"Basic Membership", "Intermediate Membership",
                "Advanced Membership"};
        String[] planTier = {"Bronze", "Silver", "Gold"};
        String[] membershipDetails = {"20 routines/month", "40 routines/month", "Unlimited routines/month & Leaderboard Access"};
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
        String[] selectionButton = {"select this plan (£1.99)", "select this plan (£3.99)", "select this plan (£5.99)"};

        checkout = findViewById(R.id.checkout_button);

        PaymentListAdapter listAdapter = new PaymentListAdapter(this, planSubtitle, planTier, planImage, membershipDetails, this, selectionButton);
        /**Context context, String [] planSubtitle, String [] planTier, int [] planImage,
         int [] moreDetailsButton, String[] membershipDetails,
         int [] selectionButton**/

        ListView list = findViewById(R.id.expanding_item);

        list.setAdapter(listAdapter);//setListViewHeightBasedOnChildren(list);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkoutTier != null) {
                    FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference rootReference = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
                    rootReference.child("userDetails").child("membershipPlan").setValue(checkoutTier);
                    SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
                    String userName = prefs.getString(currentFirebaseUser+"userFullName", "Error Getting Name");
                    if(!userName.equals("Error Getting Name")) {
                        startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(PaymentActivity.this, UserDetailsActivity.class));
                    }
                    finish();
                }
            }
        });
    }

    @Override
    public void onActionPerformed(String position) {
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("membershipPlan", position);
        editor.apply();
        checkout.setText("Check out with the " + position + " plan");
        setCheckoutTier(position);
    }

    public void setCheckoutTier(String checkoutTier) {
        this.checkoutTier = checkoutTier;
    }


}