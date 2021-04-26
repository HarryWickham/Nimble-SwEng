package uk.ac.york.nimblefitness.MediaHandlers.Graphics;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ShapeView extends View {

    public ArrayList<ShapeType> shapeTypeArray = new ArrayList<>();

    Rect mRect;
    RectF mOval;
    Paint mPaint;

    public ArrayList<ShapeType> getShapeTypeArray() {
        return shapeTypeArray;
    }

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

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void init(@Nullable AttributeSet set){
        mRect = new Rect();
        mOval = new RectF();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    public void addShape(int xStart, int yStart, int height, int width, float shadingX1, float shadingY1, float shadingX2, float shadingY2, int colour1, int colour2, Shader.TileMode shadingStyle, ShapeType.Shape shape, int duration){
        shapeTypeArray.add(new ShapeType(xStart, yStart, width, height, Color.RED, shape, new LinearGradient(shadingX1,shadingY1,shadingX2,shadingY2,colour1,colour2, shadingStyle), duration));
    }

    public void addShape(int xStart, int yStart, int height, int width, int colour, ShapeType.Shape shape, int duration){
        shapeTypeArray.add(new ShapeType(xStart, yStart, width, height, colour, shape, null, duration));
    }

    public void addLine(int xStart, int yStart, int xEnd, int yEnd, int colour, int duration){
        shapeTypeArray.add(new ShapeType(xStart, yStart, xEnd, yEnd, colour, ShapeType.Shape.LINE, duration));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for(ShapeType shapeType : shapeTypeArray){
            if(shapeType.getDuration() != 0) {
                delay(shapeType);
            }
            if (shapeType.getShading() != null) {
                if (shapeType.getShape_type().equals(ShapeType.Shape.RECTANGLE)) {
                    mRect.left = shapeType.getxStart();
                    mRect.top = shapeType.getyStart();
                    mRect.right = mRect.left + shapeType.getWidth();
                    mRect.bottom = mRect.top + shapeType.getHeight();
                    mPaint.setShader(shapeType.getShading());
                    canvas.drawRect(mRect, mPaint);

                } else if (shapeType.getShape_type().equals(ShapeType.Shape.OVAL)) {
                    mOval.left = shapeType.getxStart();
                    mOval.top = shapeType.getyStart();
                    mOval.right = mOval.left + shapeType.getWidth();
                    mOval.bottom = mOval.top + shapeType.getHeight();
                    mPaint.setShader(shapeType.getShading());
                    canvas.drawOval(mOval, mPaint);
                }
            } else{
                if (shapeType.getShape_type().equals(ShapeType.Shape.RECTANGLE)) {
                    mRect.left = shapeType.getxStart();
                    mRect.top = shapeType.getyStart();
                    mRect.right = mRect.left + shapeType.getWidth();
                    mRect.bottom = mRect.top + shapeType.getHeight();
                    mPaint.setShader(shapeType.getShading());
                    mPaint.setColor(shapeType.getColour());
                    canvas.drawRect(mRect, mPaint);
                } else if (shapeType.getShape_type().equals(ShapeType.Shape.OVAL)) {
                    mOval.left = shapeType.getxStart();
                    mOval.top = shapeType.getyStart();
                    mOval.right = mOval.left + shapeType.getWidth();
                    mOval.bottom = mOval.top + shapeType.getHeight();
                    mPaint.setShader(shapeType.getShading());
                    mPaint.setColor(shapeType.getColour());
                    canvas.drawOval(mOval, mPaint);
                } else if (shapeType.getShape_type().equals(ShapeType.Shape.LINE)) {
                    mPaint.setShader(shapeType.getShading());
                    mPaint.setColor(shapeType.getColour());
                    canvas.drawLine(shapeType.getxStart(), shapeType.getyStart(), shapeType.getxEnd(), shapeType.getyEnd(), mPaint);
                }
            }
        }
    }

    public void clearAll(){
        shapeTypeArray.clear();
    }

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
