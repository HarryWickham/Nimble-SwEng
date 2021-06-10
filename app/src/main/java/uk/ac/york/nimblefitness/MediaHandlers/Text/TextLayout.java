package uk.ac.york.nimblefitness.MediaHandlers.Text;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.Serializable;

import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;

public class TextLayout implements Serializable, AbstractLayout {

    TextModule.styleFamily style;
    TextModule.fontFamily font;
    String text, fontSize, fontColour;
    int xStart, yStart;
    FrameLayout parentLayout;
    TextModule textModule;
    Context context;

    public TextLayout(String text, TextModule.fontFamily font, String fontSize, String fontColour
            , int xStart, int yStart, FrameLayout parentLayout, Context context) {
        this.font = font;
        this.text = text;
        this.fontSize = fontSize;
        this.fontColour = fontColour;
        this.xStart = xStart;
        this.yStart = yStart;
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
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = this.xStart;
        params.topMargin = this.yStart;
        textModule.setLayoutParams(params);
        parentLayout.addView(textModule);
        textModule.setAll(this.text, this.font, this.fontSize, this.fontColour, this.xStart,
                this.yStart);
        textModule.writeText();
    }

    @Override
    public String getMediaId() {
        return null;
    }

    @Override
    public void playPause() {
    }
}
