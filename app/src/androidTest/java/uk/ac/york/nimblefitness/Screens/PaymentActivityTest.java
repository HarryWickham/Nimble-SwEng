package uk.ac.york.nimblefitness.Screens;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import uk.ac.york.nimblefitness.R;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentActivityTest {

    @Rule
    public ActivityTestRule<PaymentActivity> PaymentActivityTestRule = new ActivityTestRule<>(PaymentActivity.class);

    private PaymentActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = PaymentActivityTestRule.getActivity();
    }


    //Tests to see if all visual elements are instantiated
    @Test
    public void testLaunchPaymentActivity() {
        TextView tipBar = (TextView) mActivity.findViewById(R.id.tipbar);
        ListView expandingItem = (ListView) mActivity.findViewById(R.id.expanding_item);
        Button checkoutButton = (Button) mActivity.findViewById(R.id.checkout_button);
        CardView planCard = (CardView) mActivity.findViewById(R.id.plan_card);
        TextView planSubtitle = (TextView) mActivity.findViewById(R.id.plan_subtitle);
        TextView planTier = (TextView) mActivity.findViewById(R.id.plan_tier);
        ImageView planImage = (ImageView) mActivity.findViewById(R.id.plan_image);
        LinearLayout downArrowBronze = (LinearLayout) mActivity.findViewById(R.id.down_arrow_bronze);
        Button moreLessButton = (Button) mActivity.findViewById(R.id.more_less_button);
        LinearLayout membershipDetails = (LinearLayout) mActivity.findViewById(R.id.membership_details);
        TextView membershipDetailsText = (TextView) mActivity.findViewById(R.id.membership_details_text);
        Button selectionButton = (Button) mActivity.findViewById(R.id.selection_button);

        //Asserts that they are displayed
        assertNotNull(tipBar);
        assertNotNull(expandingItem);
        assertNotNull(checkoutButton);
        assertNotNull(planCard);
        assertNotNull(planSubtitle);
        assertNotNull(planTier);
        assertNotNull(planImage);
        assertNotNull(downArrowBronze);
        assertNotNull(moreLessButton);
        assertNotNull(membershipDetails);
        assertNotNull(membershipDetailsText);
        assertNotNull(selectionButton);
        
    }

}