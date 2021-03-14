package uk.ac.york.nimblefitness.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.ac.york.nimblefitness.R;

public class PaymentActivity extends AppCompatActivity {

    Button pushMeButton;
    TextView writingFeedback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        writingFeedback = findViewById(R.id.writing);
        pushMeButton = findViewById(R.id.pushme);

        this.setTitle("Membership Page");

        pushMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writingFeedback.setText("clicked");
            }
        });
    }
}