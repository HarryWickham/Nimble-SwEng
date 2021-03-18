package uk.ac.york.nimblefitness.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.ac.york.nimblefitness.R;

public class PaymentActivity extends AppCompatActivity {

    private LinearLayout membershipDetails;
    private LinearLayout membershipDetails1;
    private LinearLayout membershipDetails2;

    private LinearLayout downArrow;
    private LinearLayout downArrow1;
    private LinearLayout downArrow2;

    Button pushMeButton;
    Button pushMeButton1;
    Button pushMeButton2;

    Button lessDetailsButton;
    Button lessDetailsButton1;
    Button lessDetailsButton2;

    Button chooseButton;
    Button chooseButton1;
    Button chooseButton2;

    TextView writingFeedback;
    TextView writingFeedback1;
    TextView writingFeedback2;

    TextView activationFeedback;
    TextView activationFeedback1;
    TextView activationFeedback2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        writingFeedback = findViewById(R.id.writing);
        pushMeButton = findViewById(R.id.pushme);

        membershipDetails = findViewById(R.id.dropdown_menu);
        membershipDetails1 = findViewById(R.id.dropdown_menu1);
        membershipDetails2 = findViewById(R.id.dropdown_menu2);

        downArrow = findViewById(R.id.arrow);
        downArrow1 = findViewById(R.id.arrow1);
        downArrow2 = findViewById(R.id.arrow2);

        writingFeedback1 = findViewById(R.id.writing1);
        writingFeedback2 = findViewById(R.id.writing2);
        pushMeButton1 = findViewById(R.id.pushme1);
        pushMeButton2 = findViewById(R.id.pushme2);

        lessDetailsButton = findViewById(R.id.lessdetailsbutton);
        lessDetailsButton1 = findViewById(R.id.lessdetailsbutton1);
        lessDetailsButton2 = findViewById(R.id.lessdetailsbutton2);

        chooseButton = findViewById(R.id.choosebutton);
        chooseButton1 = findViewById(R.id.choosebutton1);
        chooseButton2 = findViewById(R.id.choosebutton2);

        activationFeedback = findViewById(R.id.activationwriting);
        activationFeedback1 = findViewById(R.id.activationwriting1);
        activationFeedback2 = findViewById(R.id.activationwriting2);

        this.setTitle("Membership Page");

        pushMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                writingFeedback.setText("Basic Membership Plan (Viewed)");
                membershipDetails.setVisibility(View.VISIBLE);
                downArrow.setVisibility(View.GONE);
            }
        });

        pushMeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                writingFeedback1.setText("Intermediate Membership Plan (Viewed)");
                membershipDetails1.setVisibility(View.VISIBLE);
                downArrow1.setVisibility(View.GONE);
            }
        });

        pushMeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                writingFeedback2.setText("Advanced Membership Plan (Viewed)");
                membershipDetails2.setVisibility(View.VISIBLE);
                downArrow2.setVisibility(View.GONE);
            }
        });

        lessDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                membershipDetails.setVisibility(View.GONE);
                downArrow.setVisibility(View.VISIBLE);
            }
        });

        lessDetailsButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                membershipDetails1.setVisibility(View.GONE);
                downArrow1.setVisibility(View.VISIBLE);
            }
        });

        lessDetailsButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                membershipDetails2.setVisibility(View.GONE);
                downArrow2.setVisibility(View.VISIBLE);
            }
        });

        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activationFeedback.setText("Bronze Membership (Selected)");
                activationFeedback1.setText("Silver Membership");
                activationFeedback2.setText("Gold Membership");
                chooseButton.setVisibility(View.GONE);
                chooseButton1.setVisibility(View.VISIBLE);
                chooseButton2.setVisibility(View.VISIBLE);
            }
        });

        chooseButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activationFeedback1.setText("Silver Membership (Selected)");
                activationFeedback.setText("Bronze Membership");
                activationFeedback2.setText("Gold Membership");
                chooseButton1.setVisibility(View.GONE);
                chooseButton.setVisibility(View.VISIBLE);
                chooseButton2.setVisibility(View.VISIBLE);
            }
        });

        chooseButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activationFeedback2.setText("Gold Membership (Selected)");
                activationFeedback.setText("Bronze Membership");
                activationFeedback1.setText("Silver Membership");
                chooseButton2.setVisibility(View.GONE);
                chooseButton.setVisibility(View.VISIBLE);
                chooseButton1.setVisibility(View.VISIBLE);
            }
        });

    }
}