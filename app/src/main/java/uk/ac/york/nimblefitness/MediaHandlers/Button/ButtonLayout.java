package uk.ac.york.nimblefitness.MediaHandlers.Button;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import uk.ac.york.nimblefitness.R;

public class ButtonLayout {
    int xstart, ystart, width, height;
    String slideid, mediaid, fontsize, fontcolour, text, urlname;
    TextButtonType.fontFamily font;
    FrameLayout parentLayout;
    Context context;

    public ButtonLayout(int xstart, int ystart, int width, int height, String slideid, String mediaid, String fontsize, String fontcolour, String text, TextButtonType.fontFamily font, FrameLayout parentLayout, Context context) {
        this.xstart = xstart;
        this.ystart = ystart;
        this.width = width;
        this.height = height;
        this.slideid = slideid;
        this.mediaid = mediaid;
        this.fontsize = fontsize;
        this.fontcolour = fontcolour;
        this.text = text;
        this.font = font;
        this.parentLayout = parentLayout;
        this.context = context;
    }

    public ButtonLayout(int xstart, int ystart, int width, int height, String slideid, String mediaid, String urlname, FrameLayout parentLayout, Context context) {
        this.xstart = xstart;
        this.ystart = ystart;
        this.width = width;
        this.height = height;
        this.slideid = slideid;
        this.mediaid = mediaid;
        this.urlname = urlname;
        this.parentLayout = parentLayout;
        this.context = context;
    }

    public void drawTextButton(){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = this.xstart;
        params.topMargin = this.ystart;
        TextButtonType textButtonType = new TextButtonType(xstart,ystart,width,height,slideid,mediaid,fontsize,fontcolour,text,font,context);
        Button textButton = textButtonType.createButton();
        textButton.setLayoutParams(params);
        parentLayout.addView(textButton);

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(slideid != null){
                    changeSlide();
                }else if (mediaid != null){
                    startStopMedia();
                }
            }
        });

    }

    public void drawImageButton(){
        ImageButtonType imageButtonType = new ImageButtonType(xstart,ystart,width,height,slideid,mediaid,urlname,context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = this.xstart;
        params.topMargin = this.ystart;
        ImageButton imageButton = imageButtonType.createButton();
        imageButton.setLayoutParams(params);
        parentLayout.addView(imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(slideid != null){
                    changeSlide();
                }else if (mediaid != null){
                    startStopMedia();
                }
            }
        });
    }

    public Typeface updateTextFont(TextButtonType.fontFamily font) {
        Typeface family;
        switch (font) {
            case default_bold:
                family = Typeface.DEFAULT_BOLD;
                break;
            case monospace:
                family = Typeface.MONOSPACE;
                break;
            case sans_serif:
                family = Typeface.SANS_SERIF;
                break;
            case serif:
                family = Typeface.SERIF;
                break;
            default:
                family = Typeface.DEFAULT;
                break;
        }
        return family;
    }

    private void changeSlide() {
    }

    private void startStopMedia() {
    }
}
