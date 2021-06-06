package uk.ac.york.nimblefitness.MediaHandlers.Button;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.google.gson.internal.$Gson$Preconditions;

import uk.ac.york.nimblefitness.MediaHandlers.Text.TextModule;

public class TextButtonType {
    int xStart, yStart, width, height;
    String slideId, mediaId, fontSize, fontColour, text;
    fontFamily font;
    Context context;

    public enum fontFamily {
        default_bold, monospace, sans_serif, serif
    }

    public TextButtonType(int xStart, int yStart, int width, int height, String slideId, String mediaId, String fontSize, String fontColour, String text, fontFamily font, Context context) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.width = width;
        this.height = height;
        this.slideId = slideId;
        this.mediaId = mediaId;
        this.fontSize = fontSize;
        this.fontColour = fontColour;
        this.text = text;
        this.font = font;
        this.context = context;
    }

    public Button createButton(){
        Button button = new Button(context);
        button.setText(text);
        button.setTextColor(Color.parseColor(fontColour));
        button.setTypeface(updateTextFont(font));
        button.setWidth(width);
        button.setHeight(height);

        return button;
    }

    public Typeface updateTextFont(fontFamily font) {
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

    public int getXStart() {
        return xStart;
    }

    public void setXStart(int xStart) {
        this.xStart = xStart;
    }

    public int getYStart() {
        return yStart;
    }

    public void setYStart(int yStart) {
        this.yStart = yStart;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSlideId() {
        return slideId;
    }

    public void setSlideId(String slideId) {
        this.slideId = slideId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColour() {
        return fontColour;
    }

    public void setFontColour(String fontColour) {
        this.fontColour = fontColour;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public fontFamily getFont() {
        return font;
    }

    public void setFont(fontFamily font) {
        this.font = font;
    }
}
