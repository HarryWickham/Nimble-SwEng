package uk.ac.york.nimblefitness.MediaHandlers.Graphics;

import android.graphics.LinearGradient;

public class ShapeType {

    public enum Shape {
        RECTANGLE,
        OVAL,
        LINE
    }

    private int yStart;
    private int xStart;
    private int width;
    private int height;
    private int colour;
    private int xEnd;
    private int yEnd;
    private Shape shape_type;
    private LinearGradient shading;
    private int duration;


    public ShapeType(int xStart, int yStart, int width, int height, int colour, Shape shape_type, LinearGradient shading, int duration) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.shape_type = shape_type;
        this.shading = shading;
        this.duration = duration;
    }

    public ShapeType(int xStart, int yStart, int xEnd, int yEnd, int colour, Shape shape, int duration){
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.colour = colour;
        this.duration = duration;
        this.shape_type = shape;
    }

    public int getyStart() {
        return yStart;
    }

    public int getxStart() {
        return xStart;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getColour() {
        return colour;
    }

    public int getxEnd() {
        return xEnd;
    }

    public int getyEnd() {
        return yEnd;
    }

    public Shape getShape_type() {
        return shape_type;
    }

    public LinearGradient getShading() {
        return shading;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}

