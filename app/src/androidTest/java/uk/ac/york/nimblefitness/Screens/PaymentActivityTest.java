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