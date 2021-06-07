package uk.ac.york.nimblefitness.Screens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uk.ac.york.nimblefitness.HelperClasses.UserDetails;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class TermsAndConditionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        setTitle("Terms & Conditions");

        Button acceptTC = findViewById(R.id.acceptTC);
        acceptTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference rootReference = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
                rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserDetails userDetails = snapshot.child("userDetails").getValue(UserDetails.class);
                        rootReference.child("userDetails").child("acceptedTC").setValue(true);
                        if (userDetails != null) {
                            SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean(currentFirebaseUser + "acceptedTC", true);
                            editor.apply();
                        }
                        routing(currentFirebaseUser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }

    private void routing(FirebaseUser currentFirebaseUser){
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        String userName = prefs.getString(currentFirebaseUser+"userFullName", "error");
        String membershipPlan = prefs.getString(currentFirebaseUser+"membershipPlan", "error");

        if(membershipPlan.equals("error")){
            Log.i("routing membershipPlan ", membershipPlan);
            startActivity(new Intent(TermsAndConditionsActivity.this,PaymentActivity.class));
            finish();
        } else if((userName.equals("error") || userName.equals("null null"))){
            Log.i("routing userName", userName);
            startActivity(new Intent(TermsAndConditionsActivity.this,UserDetailsActivity.class));
            finish();
        } else {
            Log.i("routing FirebaseUser", String.valueOf(currentFirebaseUser));
            Log.i("routing membershipPlan", membershipPlan);
            Log.i("routing userName", userName);
            startActivity(new Intent(TermsAndConditionsActivity.this,MainActivity.class));
            finish();
        }
    }
}
/*

 */