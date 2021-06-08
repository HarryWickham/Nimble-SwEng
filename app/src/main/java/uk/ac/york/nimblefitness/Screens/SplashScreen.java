package uk.ac.york.nimblefitness.Screens;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import uk.ac.york.nimblefitness.HelperClasses.UserDetails;
import uk.ac.york.nimblefitness.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        connectedToTheInternet();
    }

    private void receiveData(){
        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentFirebaseUser != null) {
            Log.i("currentFirebaseUser", currentFirebaseUser.getUid());
            Log.i("currentFirebaseUser", " not null :)");

            DatabaseReference rootReference = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
            rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserDetails userDetails = snapshot.child("userDetails").getValue(UserDetails.class);
                    if (userDetails != null) {
                        String userFullName = String.format("%s %s", userDetails.getFirstName(), userDetails.getLastName());
                        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(currentFirebaseUser + "membershipPlan", userDetails.getMembershipPlan());
                        editor.putString(currentFirebaseUser + "userFullName", userFullName);
                        editor.putInt(currentFirebaseUser + "weeklyGoal", userDetails.getWeeklyGoal());
                        editor.putInt(currentFirebaseUser + "currentMoves", userDetails.getCurrentMoves());
                        editor.putInt(currentFirebaseUser + "completedRoutines", userDetails.getCompletedRoutines());
                        editor.putBoolean(currentFirebaseUser + "acceptedTC", userDetails.isAcceptedTC());
                        editor.putBoolean(currentFirebaseUser + "onBoarded", userDetails.isOnBoarded());
                        editor.apply();
                        resetCompletedRoutines(userDetails, currentFirebaseUser, prefs);

                    }
                    routing(currentFirebaseUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.i("onCancelled", String.valueOf(error));
                }
            });
        } else{
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    routing(null);
                }
            }, 1500);
        }
    }

    private void routing(FirebaseUser currentFirebaseUser){
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        String userName = prefs.getString(currentFirebaseUser+"userFullName", "error");
        String membershipPlan = prefs.getString(currentFirebaseUser+"membershipPlan", "error");
        boolean acceptedTC = prefs.getBoolean(currentFirebaseUser+"acceptedTC", false);
        boolean onBoarded = prefs.getBoolean(currentFirebaseUser+"onBoarded", false);
        if(currentFirebaseUser == null){
            startActivity(new Intent(SplashScreen.this,SignupActivity.class));
            finish();
        } else if(!acceptedTC) {
            startActivity(new Intent(SplashScreen.this,TermsAndConditionsActivity.class));
            finish();
        } else if(membershipPlan.equals("error")){
            Log.i("routing membershipPlan ", membershipPlan);
            startActivity(new Intent(SplashScreen.this,PaymentActivity.class));
            finish();
        } else if((userName.equals("error") || userName.equals("null null"))){
            Log.i("routing userName", userName);
            startActivity(new Intent(SplashScreen.this,UserDetailsActivity.class));
            finish();
        }else if(!onBoarded){
            startActivity(new Intent(SplashScreen.this, OnBoardingActivity.class));
            finish();
        }
        else {
            Log.i("routing FirebaseUser", String.valueOf(currentFirebaseUser));
            Log.i("routing membershipPlan", membershipPlan);
            Log.i("routing userName", userName);
            startActivity(new Intent(SplashScreen.this,MainActivity.class));
            finish();
        }


    }

    private void resetCompletedRoutines(UserDetails userDetails, FirebaseUser currentFirebaseUser, SharedPreferences prefs){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Log.i("getLastLogin", String.valueOf(userDetails.getLastLogin()));
        Log.i("SimpleDateFormat", sdf.format(new Date()));

        if(Integer.parseInt(sdf.format(new Date()))>userDetails.getLastLogin()){
            Log.i("resetCompletedRoutines", String.valueOf(userDetails.getLastLogin()));
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(currentFirebaseUser + "completedRoutines", 0);
            editor.apply();
        }
        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootReference = rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
        rootReference.child("userDetails").child("lastLogin").setValue(Integer.parseInt(sdf.format(new Date())));
    }

    private void connectedToTheInternet(){
        if(!isNetworkConnected() | !internetIsConnected()){
            AlertDialog.Builder exitApp = new AlertDialog.Builder(this);
            exitApp.setTitle("An error has occurred");
            exitApp.setMessage("Please ensure you are connected to the internet");
            Log.i("TAG", "onBackPressed: ");
            exitApp.setCancelable(false)
                    .setPositiveButton("Retry", (dialog, id) -> {
                        if(isNetworkConnected() | internetIsConnected()) {
                            receiveData();
                        } else {
                            connectedToTheInternet();
                        }
                    });

            // create alert dialog
            exitApp.create().show();
        } else {
            receiveData();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.co.uk";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}