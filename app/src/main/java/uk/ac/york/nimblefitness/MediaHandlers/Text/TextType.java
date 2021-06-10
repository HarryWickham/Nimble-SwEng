package uk.ac.york.nimblefitness.MediaHandlers.Text;

/**
 * Text type Object to help with XML parsing
 */

public class TextType {
    TextModule.styleFamily style;
    TextModule.fontFamily font;
    String text = "", fontSize, fontColour;
    int xStart, yStart;

    public TextType() {
    }

    public TextModule.styleFamily getStyle() {
        return style;
    }

    public void setStyle(TextModule.styleFamily style) {
        this.style = style;
    }

    public TextModule.fontFamily getFont() {
        return font;
    }

    public void setFont(TextModule.fontFamily font) {
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void addText(String text) {
        this.text = this.text + text;
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

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }
}
