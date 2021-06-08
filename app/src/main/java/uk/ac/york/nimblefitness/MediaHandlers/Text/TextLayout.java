package uk.ac.york.nimblefitness.MediaHandlers.Text;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.Serializable;

import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;

public class TextLayout implements Serializable, AbstractLayout {

    TextModule.styleFamily style;
    TextModule.fontFamily font;
    String text, fontsize, fontcolour;
    int xstart, ystart;
    FrameLayout parentLayout;
    TextModule textModule;
    Context context;

    public TextLayout(String text, TextModule.fontFamily font, String fontsize, String fontcolour, int xstart, int ystart, FrameLayout parentLayout, Context context) {
        this.font = font;
        this.text = text;
        this.fontsize = fontsize;
        this.fontcolour = fontcolour;
        this.xstart = xstart;
        this.ystart = ystart;
        this.parentLayout = parentLayout;
        this.context = context;
    }

    public void setParentLayout(FrameLayout parentLayout) {
        this.parentLayout = parentLayout;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void draw() {
        textModule = new TextModule(this.context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin=this.xstart;
        params.topMargin=this.ystart;
        textModule.setLayoutParams(params);
        parentLayout.addView(textModule);
        textModule.setall(this.text,this.font,this.fontsize,this.fontcolour,this.xstart,this.ystart);
        textModule.writeText();
    }


    @Override
    public String getMediaId() {
        return null;
    }
}
