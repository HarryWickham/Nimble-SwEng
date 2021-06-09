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

/**This class adds content to the arrays used in PaymentListAdapter in accordance to the
 * different membership tiers, this class also contains the firebase integration for the membership
 * system. The selected membership tier is saved to firebase, and is read from firebase to rename the
 * corresponding buttons such as the checkout button. This works in conjunction with the other payment
 * related classes and the payment and membership related xml files to produce a working membership
 * selection system.**/

public class PaymentActivity extends AppCompatActivity implements PaymentListAdapter.MyActionCallback {

    Button checkout;
    String checkoutTier;

    /**Runs when the page is opened by the user**/
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
        String[] moreDetailsButton = {"more details", "more details", "more details"};
        String[] selectionButton = {"select this plan (£1.99)", "select this plan (£3.99)", "select this plan (£5.99)"};

        checkout = findViewById(R.id.checkout_button);

        PaymentListAdapter listAdapter = new PaymentListAdapter(this, planSubtitle, planTier, planImage, membershipDetails, this, selectionButton);

        ListView list = findViewById(R.id.expanding_item);

        list.setAdapter(listAdapter);//setListViewHeightBasedOnChildren(list);

        /**Adds user's membershipPlan to firebase databse under UserDetails**/

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

    /**Uses firebase integration to get currently selected membership plan and replace the checkout button
     * text accordingly**/

    @Override
    public void onActionPerformed(String position) {
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        editor.putString(currentFirebaseUser+"membershipPlan", position);
        editor.apply();
        checkout.setText("Check out with the " + position + " plan");
        setCheckoutTier(position);
    }

    public void setCheckoutTier(String checkoutTier) {
        this.checkoutTier = checkoutTier;
    }


}