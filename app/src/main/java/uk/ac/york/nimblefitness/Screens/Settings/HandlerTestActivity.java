package uk.ac.york.nimblefitness.Screens.Settings;

import android.graphics.Color;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeType;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeView;
import uk.ac.york.nimblefitness.R;

public class HandlerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);

        ShapeView shapeView = findViewById(R.id.shapeView);

        shapeView.addShape(100, 200, 300, 150, Color.BLUE, ShapeType.Shape.RECTANGLE, 2000);
        shapeView.addShape(0, 0, 300, 150, 0,0,75,150,Color.RED,Color.YELLOW, Shader.TileMode.MIRROR, ShapeType.Shape.RECTANGLE, 0);
        shapeView.addLine(0, 0, 300, 150, Color.RED, 0);

    }
}