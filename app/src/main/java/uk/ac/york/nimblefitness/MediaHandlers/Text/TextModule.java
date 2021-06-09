package uk.ac.york.nimblefitness.MediaHandlers.Text;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;

import java.io.Serializable;

public class TextModule extends androidx.appcompat.widget.AppCompatTextView implements Serializable {

    public enum fontFamily {
        default_bold, monospace, sans_serif, serif
    }

    public enum styleFamily {
        bold, italic, bold_italic, normal
    }

    //Declaring variables needed for the text
    styleFamily style;
    fontFamily font;
    String text, fontsize, fontcolour;
    int xstart, ystart;

    /**
     * Constructor for the text module. Uses all these inputs to fulfill the PWS
     */
    public TextModule(Context context) {
        super(context);
    }

    public TextModule(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextModule(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
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

    public String getFontsize() {
        return fontsize;
    }

    public void setFontsize(String fontsize) {
        this.fontsize = fontsize;
    }

    public String getFontcolour() {
        return fontcolour;
    }

    public void setFontcolour(String fontcolour) {
        this.fontcolour = fontcolour;
    }

    public styleFamily getStyle() {
        return style;
    }

    public void setStyle(styleFamily style) {
        this.style = style;
    }

    public int getXstart() {
        return xstart;
    }

    public void setXstart(int xstart) {
        this.xstart = xstart;
    }

    public int getYstart() {
        return ystart;
    }

    public void setYstart(int ystart) {
        this.ystart = ystart;
    }

    public void setall(String text, fontFamily font, String fontsize, String fontcolour, int xstart, int ystart) {
        this.setText(Html.fromHtml(text));
        setFont(font);
        setFontsize(fontsize);
        setFontcolour(fontcolour);
        setXstart(xstart);
        setYstart(ystart);
    }

    /**
     * Method which calls for an update in Typeface and then appends the text
     */
    public void writeText() {
        updateTypeface();
    }

    /**
     * Updates text colour by taking the String from the constructor and converting it into an integer for setTextColor method
     */
    public void updateTextColour() {
        int colour = Color.parseColor(fontcolour);
        setTextColor(colour);
        System.out.println(colour);
    }

    /**
     * Updates text size by taking the String from the constructor and converting it into an integer for setTextSize method
     */
    public void updateTextSize() {
        int size = Integer.parseInt(fontsize);
        setTextSize(size);
        System.out.println(size);
    }

    /**
     * Updates text font using a switch case for default, default bold, monospace, sans serif and serif
     * @return family: used for setting up the typeface in updateTypeface
     */
    public Typeface updateTextFont() {
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

    /**
     * Calls the other updates to update the whole typeface
     */
    public void updateTypeface() {
        updateTextColour();
        updateTextSize();
        setTypeface(updateTextFont());
    }
}