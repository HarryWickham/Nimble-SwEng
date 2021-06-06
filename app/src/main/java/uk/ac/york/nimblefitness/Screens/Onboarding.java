package uk.ac.york.nimblefitness.Screens;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import uk.ac.york.nimblefitness.R;

public class Onboarding extends AppCompatActivity {

    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.view_pager2);
        Log.i("viewpager", String.valueOf(viewPager2));


        List<String> title = new ArrayList<>();
        title.add("Calendar Tracker");
        title.add("Choose a Routine");
        title.add("Set a Goal");
        title.add("Leaderboard");

        List<String> description = new ArrayList<>();
        description.add("Automatically track your progress and view anytime");
        description.add("All our routines are created by industry professionals");
        description.add("Set a monthly goal to ensure you stay on track");
        description.add("Share your progress with others on our live leaderboard");

        List<Integer> image = new ArrayList<>();
        image.add(R.drawable.calendar);
        image.add(R.drawable.goal);
        image.add(R.drawable.medal);
        image.add(R.drawable.leaderboard);

        viewPager2.setAdapter(new ViewPagerAdapter(this, title, viewPager2, image, description));


    }
}