package uk.ac.york.nimblefitness.Screens;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import uk.ac.york.nimblefitness.R;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SigninActivityTest {

    @Rule
    public ActivityTestRule<SigninActivity> signinActivityTestRule = new ActivityTestRule<>(SigninActivity.class);

    private SigninActivity mActivity;

    @Before
    public void seUp() throws Exception {
        mActivity = signinActivityTestRule.getActivity();
    }


    @Test
    public void testLaunchSigninActivity() {

        ImageView signInHeaderImageView = mActivity.findViewById(R.id.signInHeaderImageView);
        TextView signInHeaderTextView = mActivity.findViewById(R.id.signInHeaderTextView);
        TextInputLayout SignInEmailLayout = mActivity.findViewById(R.id.SignInEmailLayout);
        TextInputEditText SignInEmail = mActivity.findViewById(R.id.SignInEmail);
        TextInputLayout SignInPasswordLayout = mActivity.findViewById(R.id.SignInPasswordLayout);
        TextInputEditText SignInPassword = mActivity.findViewById(R.id.SignInPassword);
        TextView forgotten_password = mActivity.findViewById(R.id.forgotten_password);
        ProgressBar signin_progress_circular = mActivity.findViewById(R.id.signin_progress_circular);
        Button sign_in_button = mActivity.findViewById(R.id.sign_in_button);
        SignInButton googleSignIn = mActivity.findViewById(R.id.googleSignIn);
        TextView new_member = mActivity.findViewById(R.id.new_member);


        assertNotNull(signInHeaderImageView);
        assertNotNull(signInHeaderTextView);
        assertNotNull(SignInEmailLayout);
        assertNotNull(SignInEmail);
        assertNotNull(SignInPasswordLayout);
        assertNotNull(SignInPassword);
        assertNotNull(forgotten_password);
        assertNotNull(signin_progress_circular);
        assertNotNull(sign_in_button);
        assertNotNull(googleSignIn);
        assertNotNull(new_member);
    }

}