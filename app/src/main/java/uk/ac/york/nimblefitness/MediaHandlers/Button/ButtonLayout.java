package uk.ac.york.nimblefitness.MediaHandlers.Button;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;
import uk.ac.york.nimblefitness.Screens.Settings.LoadNewPresentationActivity;

public class ButtonLayout implements AbstractLayout {
    int xstart, ystart, width, height;
    String slideid, mediaid, fontsize, fontcolour, text, urlname;
    TextButtonType.fontFamily font;
    FrameLayout parentLayout;
    Context context;
    LoadNewPresentationActivity activity;

    public ButtonLayout(int xstart, int ystart, int width, int height, String slideid,
                        String mediaid, String urlname, String fontsize, String fontcolour,
                        String text, TextButtonType.fontFamily font, FrameLayout parentLayout,
                        Context context, LoadNewPresentationActivity activity) {
        this.xstart = xstart;
        this.ystart = ystart;
        this.width = width;
        this.height = height;
        this.slideid = slideid;
        this.mediaid = mediaid;
        this.urlname = urlname;
        this.fontsize = fontsize;
        this.fontcolour = fontcolour;
        this.text = text;
        this.font = font;
        this.parentLayout = parentLayout;
        this.context = context;
        this.activity = activity;
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

    @Override
    public void draw() {
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = this.xstart;
        params.topMargin = this.ystart;
        if (urlname == null) {
            TextButtonType textButtonType = new TextButtonType(xstart, ystart, width, height,
                    slideid, mediaid, fontsize, fontcolour, text, font, context);
            Button textButton = textButtonType.createButton();
            textButton.setLayoutParams(params);
            parentLayout.addView(textButton);

            textButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (slideid != null) {
                        changeSlide();
                    } else if (mediaid != null) {
                        startStopMedia();
                    }
                }
            });
        } else {
            ImageButtonType imageButtonType = new ImageButtonType(xstart, ystart, width, height,
                    slideid, mediaid, urlname, context);
            ImageButton imageButton = imageButtonType.createButton();
            imageButton.setLayoutParams(params);
            parentLayout.addView(imageButton);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (slideid != null) {
                        changeSlide();
                    } else if (mediaid != null) {
                        startStopMedia();
                    }
                }
            });
        }
    }

    private void changeSlide() {
        if (slideid != null) {
            Toast.makeText(context, slideid, Toast.LENGTH_SHORT).show();
            this.activity.changeSlide(slideid);
        }
    }

    private void startStopMedia() {
        if (mediaid != null) {
            Toast.makeText(context, mediaid, Toast.LENGTH_SHORT).show();
            this.activity.changeMedia(mediaid);
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
