package uk.ac.york.nimblefitness.MediaHandlers.Graphics;

import android.graphics.LinearGradient;

import java.io.Serializable;

public class ShapeType implements Serializable {

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

    public ShapeType() {
    }

    public ShapeType(int xStart, int yStart, int width, int height, int colour, String shape_type,
                     LinearGradient shading, int duration) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.shape_type = shape_type;
        this.shading = shading;
        this.duration = duration;
    }

    public ShapeType(int xStart,int yStart,int xEnd,int yEnd,int colour,String shape,int duration){
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

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public int getxEnd() {
        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public int getyEnd() {
        return yEnd;
    }

    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
    }

    public String getShape_type() {
        return shape_type;
    }

    public void setShape_type(String shape_type) {
        this.shape_type = shape_type;
    }

    public LinearGradient getShading() {
        return shading;
    }

    public void setShading(LinearGradient shading) {
        this.shading = shading;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
