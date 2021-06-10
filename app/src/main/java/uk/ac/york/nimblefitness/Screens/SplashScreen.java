package uk.ac.york.nimblefitness.Screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

/**
 * A screen when the user first opens that app that allows for loading of data while making the
 * user think it just for aesthetic reasons although that is also true
 */

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        receiveData();
        //connectedToTheInternet();
    }

    //Starts the collection of data if the user is already logged in
    private void receiveData() {
        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser != null) {

            DatabaseReference rootReference =
                    rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
            rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserDetails userDetails =
                            snapshot.child("userDetails").getValue(UserDetails.class);
                    //Saves all the collected data to the phone for quick retrial in the future
                    if (userDetails != null) {
                        String userFullName = String.format("%s %s", userDetails.getFirstName(),
                                userDetails.getLastName());
                        SharedPreferences prefs =
                                getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(currentFirebaseUser + "membershipPlan",
                                userDetails.getMembershipPlan());
                        editor.putString(currentFirebaseUser + "userFullName", userFullName);
                        editor.putInt(currentFirebaseUser + "weeklyGoal",
                                userDetails.getWeeklyGoal());
                        editor.putInt(currentFirebaseUser + "currentMoves",
                                userDetails.getCurrentMoves());
                        editor.putInt(currentFirebaseUser + "completedRoutines",
                                userDetails.getCompletedRoutines());
                        editor.putBoolean(currentFirebaseUser + "acceptedTC",
                                userDetails.isAcceptedTC());
                        editor.putBoolean(currentFirebaseUser + "onBoarded",
                                userDetails.isOnBoarded());
                        editor.apply();
                        resetCompletedRoutines(userDetails, currentFirebaseUser, prefs);
                        resetWeeklyMoves(userDetails, currentFirebaseUser, prefs);

                    }
                    routing(currentFirebaseUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            //if the user is not logged in to an account they will see the splash screen for 1.5
            // seconds and then be taken to the signup page
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    routing(null);
                }
            }, 1500);
        }
    }

    //Routine of the user when they open the app is a key part of improving the user experience so
    // we ensure that the user is taken to the page that the were on when the left, or at least
    // if they haven't fully created their account yet.
    private void routing(FirebaseUser currentFirebaseUser) {
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        String userName = prefs.
                getString(currentFirebaseUser + "userFullName", "error");
        String membershipPlan = prefs.
                getString(currentFirebaseUser + "membershipPlan", "error");
        boolean acceptedTC = prefs.
                getBoolean(currentFirebaseUser + "acceptedTC", false);
        boolean onBoarded = prefs.getBoolean(currentFirebaseUser + "onBoarded", false);
        if (currentFirebaseUser == null) {
            startActivity(new Intent(SplashScreen.this, SignupActivity.class));
            finish();
        } else if (!acceptedTC) {
            startActivity(new Intent(SplashScreen.this, TermsAndConditionsActivity.class));
            finish();
        } else if (membershipPlan.equals("error")) {
            startActivity(new Intent(SplashScreen.this, PaymentActivity.class));
            finish();
        } else if ((userName.equals("error") || userName.equals("null null"))) {
            startActivity(new Intent(SplashScreen.this, UserDetailsActivity.class));
            finish();
        } else if (!onBoarded) {
            startActivity(new Intent(SplashScreen.this, OnBoardingActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        }


    }

    //The users Routines will get reset every month to allow them to use their subscriptions
    // amount. This method will reset the stored number of completed routines back to 0 the first
    // time they open the app each month.
    private void resetCompletedRoutines(UserDetails userDetails, FirebaseUser currentFirebaseUser
            , SharedPreferences prefs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootReference =
                rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
        if (Integer.parseInt(sdf.format(new Date())) > userDetails.getLastLogin()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(currentFirebaseUser + "completedRoutines", 0);
            editor.apply();
            rootReference.child("userDetails").child("completedRoutines").setValue(0);
        }

        rootReference.child("userDetails").child("lastLogin")
                .setValue(Integer.parseInt(sdf.format(new Date())));

    }

    //similar to the resetCompletedRoutines this method will reset the users moves the first time
    // they open the app each week
    private void resetWeeklyMoves(UserDetails userDetails, FirebaseUser currentFirebaseUser,
                                  SharedPreferences prefs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyww");
        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootReference =
                rootDatabase.getReference("users").child(currentFirebaseUser.getUid());
        if (Integer.parseInt(sdf.format(new Date())) > userDetails.getLastLoginWeek()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(currentFirebaseUser + "currentMoves", 0);
            editor.apply();
            rootReference.child("userDetails").child("currentMoves").setValue(0);
        }


        rootReference.child("userDetails").child("lastLoginWeek")
                .setValue(Integer.parseInt(sdf.format(new Date())));

    }

    //This method will add a popup to the screen if the user is not connected to the internet, it
    // is not currently being used due to the fact that when using an emulator this will be
    // triggered even if the emulator does have internet.
    private void connectedToTheInternet() {
        if (!isNetworkConnected() | !internetIsConnected()) {
            AlertDialog.Builder exitApp = new AlertDialog.Builder(this);
            exitApp.setTitle("An error has occurred");
            exitApp.setMessage("Please ensure you are connected to the internet");
            exitApp.setCancelable(false).setPositiveButton("Retry", (dialog, id) -> {
                if (isNetworkConnected() | internetIsConnected()) {
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

    //checks if the phone is connected to a network
    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    //checks if the network is connected to the internet
    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.co.uk";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}