package uk.ac.york.nimblefitness.MediaHandlers.Graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class ShapeView extends View implements Serializable {

    public ArrayList<ShapeType> shapeTypeArray = new ArrayList<>();

    //Defines the rectangle, oval and paint objects that might be used in later methods
    Rect mRect;
    RectF mOval;
    Paint mPaint;


    /** The following 4 creators are implemented due to extending View */
    public ShapeView(Context context) {
        super(context);
        init(null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public ShapeView(Context context,@Nullable AttributeSet attrs,int defStyleAttr,int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    /** instantiates the previously defined objects with their constructors */
    public void init(@Nullable AttributeSet set){
        mRect = new Rect();
        mOval = new RectF();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    /** adding a shapeType to the shape array when the shape has a linear gradient */
    public void addShape(int xStart, int yStart, int height, int width,
                         LinearGradient linearGradient, String shape, int duration){
        shapeTypeArray.add(new ShapeType(xStart, yStart, width, height, Color.RED, shape,
                            linearGradient, duration));
    }

    /** adding a shapeType to the shape array when the shape has a solid colour */
    public void addShape(int xStart, int yStart, int height, int width, int colour,
                         String shape, int duration){
        shapeTypeArray.add(new ShapeType(xStart, yStart, width, height, colour, shape,
                    null, duration));
    }

    /** adding a shapeType to the shape array when the shape is a line */
    public void addLine(int xStart, int yStart, int xEnd, int yEnd, int colour, int duration){
        shapeTypeArray.add(new ShapeType(xStart,yStart,xEnd,yEnd,colour,"LINE",duration));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //looping through the shapeTypeArray to draw the shapes
        for(ShapeType shapeType : shapeTypeArray){
            if(shapeType.getDuration() != 0) {
                delay(shapeType);
            }
            //drawing the shapes that have a linear gradient
            if (shapeType.getShading() != null) {
                //drawing a rectangle using the drawRect method of canvas
                if (shapeType.getShape_type().equals("RECTANGLE")) {
                    mRect.left = shapeType.getxStart();
                    mRect.top = shapeType.getyStart();
                    mRect.right = mRect.left + shapeType.getWidth();
                    mRect.bottom = mRect.top + shapeType.getHeight();
                    mPaint.setShader(shapeType.getShading());
                    canvas.drawRect(mRect, mPaint);

                    //drawing an oval using the drawOval method of canvas
                } else if (shapeType.getShape_type().equals("OVAL")) {
                    mOval.left = shapeType.getxStart();
                    mOval.top = shapeType.getyStart();
                    mOval.right = mOval.left + shapeType.getWidth();
                    mOval.bottom = mOval.top + shapeType.getHeight();
                    mPaint.setShader(shapeType.getShading());
                    canvas.drawOval(mOval, mPaint);
                }
                //drawing the shapes that have a solid fill
            } else{
                //drawing a rectangle using the drawRect method of canvas
                switch (shapeType.getShape_type()) {
                    case "RECTANGLE":
                        mRect.left = shapeType.getxStart();
                        mRect.top = shapeType.getyStart();
                        mRect.right = mRect.left + shapeType.getWidth();
                        mRect.bottom = mRect.top + shapeType.getHeight();
                        mPaint.setShader(shapeType.getShading());
                        mPaint.setColor(shapeType.getColour());
                        canvas.drawRect(mRect, mPaint);

                        //drawing an oval using the drawOval method of canvas
                        break;
                    case "OVAL":
                        mOval.left = shapeType.getxStart();
                        mOval.top = shapeType.getyStart();
                        mOval.right = mOval.left + shapeType.getWidth();
                        mOval.bottom = mOval.top + shapeType.getHeight();
                        mPaint.setShader(shapeType.getShading());
                        mPaint.setColor(shapeType.getColour());
                        canvas.drawOval(mOval, mPaint);

                        //drawing a line using the drawLine method of canvas
                        break;
                    case "LINE":
                        mPaint.setShader(shapeType.getShading());
                        mPaint.setColor(shapeType.getColour());
                        canvas.drawLine(shapeType.getxStart(), shapeType.getyStart(),
                                        shapeType.getxEnd(), shapeType.getyEnd(), mPaint);
                        break;
                }
            }
        }
    }

    //A method to allow the developer to clear the canvas of all shapes
    public void clearAll(){
        shapeTypeArray.clear();
    }

    /** Runs a delay before removing a shape from the array list dependent on the duration parameter
     *  defined by the user.
     */
    public void delay(ShapeType shapeType){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shapeTypeArray.remove(shapeType);
                postInvalidate();
            }
        }, shapeType.getDuration());
    }
}
