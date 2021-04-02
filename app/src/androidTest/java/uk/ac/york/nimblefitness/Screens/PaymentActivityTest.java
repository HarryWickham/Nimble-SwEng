package uk.ac.york.nimblefitness.Screens;

import android.widget.TextView;

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
    public void seUp() throws Exception {
        mActivity = PaymentActivityTestRule.getActivity();
    }

    /*Test if the user clicks on the view more button, test to see if the view set to gone is visible afterward
    * Check to see what button */

    @Test
    public void testLaunchPaymentActivity() {
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        /*TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);
        TextView view = (TextView) mActivity.findViewById(R.id.tipbar);*/
        assertNotNull(view);
    }
}