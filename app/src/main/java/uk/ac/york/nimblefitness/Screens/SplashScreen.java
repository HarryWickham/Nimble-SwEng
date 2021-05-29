package uk.ac.york.nimblefitness.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import uk.ac.york.nimblefitness.HelperClasses.UserHelperClass;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;

public class SplashScreen extends AppCompatActivity {

    FirebaseUser currentFirebaseUser;
    DatabaseReference rootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
            receiveData();

    }

    private void receiveData(){
        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentFirebaseUser != null) {
            Log.i("currentFirebaseUser", currentFirebaseUser.getUid());
            Log.i("currentFirebaseUser", " not null :)");
            rootReference = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
            rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserHelperClass userDetails = snapshot.child("userDetails").getValue(UserHelperClass.class);
                    Log.i("onDataChange", " not null :)");
                    Log.i("userDetails", String.valueOf(userDetails));
                    if (userDetails != null) {

                        String userFullName = String.format("%s %s", userDetails.getFirstName(), userDetails.getLastName());

                        Log.i("userFullName", userFullName);

                        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(currentFirebaseUser + "membershipPlan", userDetails.getMembershipPlan());
                        editor.putString(currentFirebaseUser + "userFullName", userFullName);
                        editor.putInt(currentFirebaseUser + "weeklyGoal", userDetails.getWeeklyGoal());
                        editor.putInt(currentFirebaseUser + "currentMoves", userDetails.getCurrentMoves());
                        editor.apply();

                        routing();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else{
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    routing();
                }
            }, 1500);
        }
    }

    private void routing(){
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        String userName = prefs.getString(currentFirebaseUser+"userFullName", "error");
        String membershipPlan = prefs.getString(currentFirebaseUser+"membershipPlan", "error");
        if(currentFirebaseUser == null){
            Log.i("routing FirebaseUser", String.valueOf(currentFirebaseUser));
            startActivity(new Intent(SplashScreen.this,SignupActivity.class));
        } else if(currentFirebaseUser != null && membershipPlan.equals("error")){
            Log.i("routing membershipPlan ", membershipPlan);
            startActivity(new Intent(SplashScreen.this,PaymentActivity.class));
        } else if(currentFirebaseUser != null && (userName.equals("error") || userName.equals("null null"))){
            Log.i("routing userName", userName);
            startActivity(new Intent(SplashScreen.this,UserDetailsActivity.class));
        } else {
            Log.i("routing FirebaseUser", String.valueOf(currentFirebaseUser));
            Log.i("routing membershipPlan", membershipPlan);
            Log.i("routing userName", userName);
            startActivity(new Intent(SplashScreen.this,MainActivity.class));
        }
    }
}