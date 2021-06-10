package uk.ac.york.nimblefitness.MediaHandlers.Graphics;

import android.content.Context;
import android.graphics.LinearGradient;
import android.widget.FrameLayout;

import java.io.Serializable;

import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;

public class ShapeLayout implements Serializable, AbstractLayout {
    FrameLayout parentLayout;
    private final int yStart;
    private final int xStart;
    private final int width;
    private final int height;
    private final int colour;
    private final int xEnd;
    private final int yEnd;
    private final String shape_type;
    private final LinearGradient shading;
    private final int duration;
    private final Context context;

    public ShapeLayout(int yStart, int xStart, int width, int height, int colour, int xEnd,
                       int yEnd, String shape_type, LinearGradient shading, int duration,
                       Context context, FrameLayout parentLayout) {
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

    @Override
    public void playPause() {
    }
}
