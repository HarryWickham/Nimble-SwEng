package uk.ac.york.nimblefitness.MediaHandlers.Graphics;

import android.graphics.Color;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.PaymentActivity;
import uk.ac.york.nimblefitness.Screens.Settings.HandlerTestActivity;

import static org.junit.jupiter.api.Assertions.*;

public class ShapeViewTest {

    @Rule
    public ActivityTestRule<HandlerTestActivity> PaymentActivityTestRule = new ActivityTestRule<>(HandlerTestActivity.class);

    private HandlerTestActivity mActivity;

    @BeforeEach
    public void setUp() {
        mActivity = PaymentActivityTestRule.getActivity();

    }

    @Test
    public void testAdditionOfRectangle(){
        ShapeView shapeView = new ShapeView(mActivity);
        shapeView.addShape(100, 200, 300, 150, Color.BLUE, ShapeType.Shape.RECTANGLE, 0);
        assertEquals(1,shapeView.getShapeTypeArray().size());
    }
}