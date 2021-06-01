package uk.ac.york.nimblefitness;

import org.junit.Test;

import uk.ac.york.nimblefitness.Screens.SignupActivity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    SignupActivity activity = mock(SignupActivity.class);

    @Test
    public void addition_isCorrect() {
        when(activity.getText(1)).thenReturn("stuff");


        verify(activity).getText(1);
    }
}