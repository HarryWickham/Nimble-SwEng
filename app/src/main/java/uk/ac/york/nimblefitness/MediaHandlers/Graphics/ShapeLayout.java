package uk.ac.york.nimblefitness.MediaHandlers.Graphics;

import android.content.Context;
import android.graphics.LinearGradient;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;

import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;

public class ShapeLayout implements Serializable, AbstractLayout {
    private int yStart;
    private int xStart;
    private int width;
    private int height;
    private int colour;
    private int xEnd;
    private int yEnd;
    private String shape_type;
    private LinearGradient shading;
    private int duration;
    private Context context;
    FrameLayout parentLayout;

    public ShapeLayout(int yStart, int xStart, int width, int height, int colour, int xEnd, int yEnd, String shape_type, LinearGradient shading, int duration, Context context, FrameLayout parentLayout) {
        this.yStart = yStart;
        this.xStart = xStart;
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.shape_type = shape_type;
        this.shading = shading;
        this.duration = duration;
        this.context = context;
        this.parentLayout = parentLayout;
    }


    @Override
    public void draw() {
        ShapeView shapeView = new ShapeView(context);

        parentLayout.addView(shapeView);

        if (shape_type.equals("RECTANGLE") | shape_type.equals("OVAL")) {
            if (colour == 0) {
                shapeView.addShape(xStart, yStart, height, width, shading, shape_type, duration);
            } else {
                shapeView.addShape(xStart, yStart, height, width, colour, shape_type, duration);
            }
        } else if (shape_type.equals("LINE")) {
            shapeView.addLine(xStart, yStart, xEnd, yEnd, colour, duration);
        }
    }

    @Override
    public String getMediaId() {
        return null;
    }
}
