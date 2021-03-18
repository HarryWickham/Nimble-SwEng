package uk.ac.york.nimblefitness.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.ac.york.nimblefitness.R;

public class PaymentActivity extends AppCompatActivity {

    Button pushMeButton;
    Button pushMeButton1;
    Button pushMeButton2;

    TextView writingFeedback;
    TextView writingFeedback1;
    TextView writingFeedback2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        writingFeedback = findViewById(R.id.writing);
        pushMeButton = findViewById(R.id.pushme);

        writingFeedback1 = findViewById(R.id.writing1);
        writingFeedback2 = findViewById(R.id.writing2);
        pushMeButton1 = findViewById(R.id.pushme1);
        pushMeButton2 = findViewById(R.id.pushme2);

        this.setTitle("Membership Page");

        pushMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writingFeedback.setText("clicked");
            }
        });

        pushMeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writingFeedback1.setText("clicked1");
            }
        });

        pushMeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writingFeedback2.setText("clicked2");
            }
        });
    }
}