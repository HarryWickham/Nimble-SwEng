package uk.ac.york.nimblefitness.Screens;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import uk.ac.york.nimblefitness.R;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SignupActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> signUpActivityTestRule = new ActivityTestRule<>(SignupActivity.class);

    private SignupActivity mActivity;

    @Before
    public void seUp() throws Exception {
        mActivity  = signUpActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchSignupActivity() {
        ImageView signUpHeaderImageView = mActivity.findViewById(R.id.signUpHeaderImageView);
        TextView signUpHeaderTextView = mActivity.findViewById(R.id.signUpHeaderTextView);
        TextInputLayout SignUpEmailLayout = mActivity.findViewById(R.id.SignUpEmailLayout);
        TextInputEditText SignUpEmail = mActivity.findViewById(R.id.SignUpEmail);
        TextInputLayout SignUpPasswordLayout = mActivity.findViewById(R.id.SignUpPasswordLayout);
        TextInputEditText SignUpPassword = mActivity.findViewById(R.id.SignUpPassword);
        TextInputLayout SignUpPasswordConfirmLayout = mActivity.findViewById(R.id.SignUpPasswordConfirmLayout);
        TextInputEditText SignUpPasswordConfirm = mActivity.findViewById(R.id.SignUpPasswordConfirm);
        ProgressBar signup_progress_circular = mActivity.findViewById(R.id.signup_progress_circular);
        Button sign_up_button = mActivity.findViewById(R.id.sign_up_button);
        TextView login_button = mActivity.findViewById(R.id.login_button);

        assertNotNull(signUpHeaderImageView);
        assertNotNull(signUpHeaderTextView);
        assertNotNull(SignUpEmailLayout);
        assertNotNull(SignUpEmail);
        assertNotNull(SignUpPasswordLayout);
        assertNotNull(SignUpPassword);
        assertNotNull(SignUpPasswordConfirmLayout);
        assertNotNull(SignUpPasswordConfirm);
        assertNotNull(signup_progress_circular);
        assertNotNull(sign_up_button);
        assertNotNull(login_button);

    }

    @Test
    public void moveToSignIn(){

    }

}