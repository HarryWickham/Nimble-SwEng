package uk.ac.york.nimblefitness.Screens;

import androidx.appcompat.app.AppCompatActivity;

//Importing

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.ac.york.nimblefitness.R;

public class PaymentActivity extends AppCompatActivity {

    //Declare LinearLayouts which hold membership detail paragraphs for each plan

    private LinearLayout membershipDetailsBronze;
    private LinearLayout membershipDetailsSilver;
    private LinearLayout membershipDetailsGold;

    //Declare LinearLayouts which hold down arrows for the show details buttons for each plan

    private LinearLayout downArrowBronze;
    private LinearLayout downArrowSilver;
    private LinearLayout downArrowGold;

    //Declare boolean variables for the selection status of membership plans

    boolean selectionStatusBronze = false;
    boolean selectionStatusSilver = false;
    boolean selectionStatusGold = false;

    //Declare Buttons used to expand the membership details for each plan

    Button moreDetailsButtonBronze;
    Button moreDetailsButtonSilver;
    Button moreDetailsButtonGold;

    //Declare Buttons used to minimize the membership details for each plan

    Button lessDetailsButtonBronze;
    Button lessDetailsButtonSilver;
    Button lessDetailsButtonGold;

    //Declare Buttons used to select membership plans

    Button selectionButtonBronze;
    Button selectionButtonSilver;
    Button selectionButtonGold;

    //Declare Buttons used to checkout for each plan (at bottom of screen)

    Button checkoutButtonDefault;
    Button checkoutButtonBronze;
    Button checkoutButtonSilver;
    Button checkoutButtonGold;
    Button checkoutButtonNoSelection;

    //Declare TextView which displays viewed status text for each membership plan

    TextView viewedFeedbackBronze;
    TextView viewedFeedbackSilver;
    TextView viewedFeedbackGold;

    //Declare TextView which displays selection status text for each membership plan

    TextView selectionFeedbackBronze;
    TextView selectionFeedbackSilver;
    TextView selectionFeedbackGold;

    //Runs when page is created (opened by user)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //Links membershipDetails[plan] to the corresponding id in the xml

        membershipDetailsBronze = findViewById(R.id.membership_details_bronze);
        membershipDetailsSilver = findViewById(R.id.memership_details_silver);
        membershipDetailsGold = findViewById(R.id.membership_details_gold);

        //Links downArrow[plan] to the corresponding id in the xml

        downArrowBronze = findViewById(R.id.down_arrow_bronze);
        downArrowSilver = findViewById(R.id.down_arrow_silver);
        downArrowGold = findViewById(R.id.down_arrow_gold);

        //Links viewedFeedback[plan] to the corresponding id in the xml

        viewedFeedbackBronze = findViewById(R.id.viewed_feedback_bronze);
        viewedFeedbackSilver = findViewById(R.id.viewed_feedback_silver);
        viewedFeedbackGold = findViewById(R.id.viewed_feedback_gold);

        //Links moreDetailsButton[plan] to the corresponding id in the xml

        moreDetailsButtonBronze = findViewById(R.id.more_details_button_bronze);
        moreDetailsButtonSilver = findViewById(R.id.more_details_button_silver);
        moreDetailsButtonGold = findViewById(R.id.more_details_button_gold);

        //Links lessDetailsButton[plan] to the corresponding id in the xml

        lessDetailsButtonBronze = findViewById(R.id.less_details_button_bronze);
        lessDetailsButtonSilver = findViewById(R.id.less_details_button_silver);
        lessDetailsButtonGold = findViewById(R.id.less_details_button_gold);

        //Links selectionButton[plan] to the corresponding id in the xml

        selectionButtonBronze = findViewById(R.id.selection_button_bronze);
        selectionButtonSilver = findViewById(R.id.selection_button_silver);
        selectionButtonGold = findViewById(R.id.selection_button_gold);

        //Links selectionFeedback[plan] to the corresponding id in the xml

        selectionFeedbackBronze = findViewById(R.id.selection_feedback_bronze);
        selectionFeedbackSilver = findViewById(R.id.selection_feedback_silver);
        selectionFeedbackGold = findViewById(R.id.selection_feedback_gold);

        //Links checkout buttons to the corresponding ids in the xml

        checkoutButtonDefault = findViewById(R.id.checkout_button_default);
        checkoutButtonBronze = findViewById(R.id.checkout_button_bronze);
        checkoutButtonSilver = findViewById(R.id.checkout_button_silver);
        checkoutButtonGold = findViewById(R.id.checkout_button_gold);
        checkoutButtonNoSelection = findViewById(R.id.checkout_button_no_selection);

        //Set title displayed at the top of the screen

        this.setTitle("Membership Page");

        /*Upon clicking the more details button for each of the membership plans, set the
        * more details button to be gone, set the less details button for that plan
        * to be visible and the details paragraph to be visible.*/

        moreDetailsButtonBronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewedFeedbackBronze.setText("Basic Membership Plan (Viewed)");
                membershipDetailsBronze.setVisibility(View.VISIBLE);
                downArrowBronze.setVisibility(View.GONE);
            }
        });

        moreDetailsButtonSilver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewedFeedbackSilver.setText("Intermediate Membership Plan (Viewed)");
                membershipDetailsSilver.setVisibility(View.VISIBLE);
                downArrowSilver.setVisibility(View.GONE);
            }
        });

        moreDetailsButtonGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewedFeedbackGold.setText("Advanced Membership Plan (Viewed)");
                membershipDetailsGold.setVisibility(View.VISIBLE);
                downArrowGold.setVisibility(View.GONE);
            }
        });

        /*Upon clicking the less details button for each of the membership plans, set the
         * more details button to be visible, set the less details button to be gone, set
         * the details paragraph for that plan to be gone.*/

        lessDetailsButtonBronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                membershipDetailsBronze.setVisibility(View.GONE);
                downArrowBronze.setVisibility(View.VISIBLE);
            }
        });

        lessDetailsButtonSilver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                membershipDetailsSilver.setVisibility(View.GONE);
                downArrowSilver.setVisibility(View.VISIBLE);
            }
        });

        lessDetailsButtonGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                membershipDetailsGold.setVisibility(View.GONE);
                downArrowGold.setVisibility(View.VISIBLE);
            }
        });

        /*For each membership plan's selection buttons, if it is clicked, set that membership plan's
        * selection button to be gone and all other membership plan's selection buttons to be
        * visible, also set the checkout button at the bottom of the screen to a button in correspondence
        * to selected membership plan, if no plan is selected, a checkout button displaying text asking
        * the user to select a plan will be set to be visible. In any of these cases, one button is
        * set to visible and the rest set to be gone.*/

        selectionButtonBronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectionStatusBronze = true;
                selectionStatusSilver = false;
                selectionStatusGold = false;

                selectionFeedbackBronze.setText("Bronze Membership (Selected)");
                selectionFeedbackSilver.setText("Silver Membership");
                selectionFeedbackGold.setText("Gold Membership");

                selectionButtonBronze.setVisibility(View.INVISIBLE);
                selectionButtonSilver.setVisibility(View.VISIBLE);
                selectionButtonGold.setVisibility(View.VISIBLE);

                checkoutButtonDefault.setVisibility(View.GONE);
                checkoutButtonBronze.setVisibility(View.VISIBLE);
                checkoutButtonSilver.setVisibility(View.GONE);
                checkoutButtonGold.setVisibility(View.GONE);
                checkoutButtonNoSelection.setVisibility(View.GONE);
            }
        });

        selectionButtonSilver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectionStatusBronze = false;
                selectionStatusSilver = true;
                selectionStatusGold = false;

                selectionFeedbackBronze.setText("Bronze Membership");
                selectionFeedbackSilver.setText("Silver Membership (Selected)");
                selectionFeedbackGold.setText("Gold Membership");

                selectionButtonBronze.setVisibility(View.VISIBLE);
                selectionButtonSilver.setVisibility(View.INVISIBLE);
                selectionButtonGold.setVisibility(View.VISIBLE);

                checkoutButtonDefault.setVisibility(View.GONE);
                checkoutButtonBronze.setVisibility(View.GONE);
                checkoutButtonSilver.setVisibility(View.VISIBLE);
                checkoutButtonGold.setVisibility(View.GONE);
                checkoutButtonNoSelection.setVisibility(View.GONE);
            }
        });

        selectionButtonGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectionStatusBronze = false;
                selectionStatusSilver = false;
                selectionStatusGold = true;

                selectionFeedbackBronze.setText("Bronze Membership");
                selectionFeedbackSilver.setText("Silver Membership");
                selectionFeedbackGold.setText("Gold Membership (Selected)");

                selectionButtonBronze.setVisibility(View.VISIBLE);
                selectionButtonSilver.setVisibility(View.VISIBLE);
                selectionButtonGold.setVisibility(View.INVISIBLE);

                checkoutButtonDefault.setVisibility(View.GONE);
                checkoutButtonBronze.setVisibility(View.GONE);
                checkoutButtonSilver.setVisibility(View.GONE);
                checkoutButtonGold.setVisibility(View.VISIBLE);
                checkoutButtonNoSelection.setVisibility(View.GONE);
            }
        });

        checkoutButtonDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkoutButtonDefault.setVisibility(View.GONE);
                checkoutButtonBronze.setVisibility(View.GONE);
                checkoutButtonSilver.setVisibility(View.GONE);
                checkoutButtonGold.setVisibility(View.GONE);
                checkoutButtonNoSelection.setVisibility(View.VISIBLE);
            }
        });

    }
}