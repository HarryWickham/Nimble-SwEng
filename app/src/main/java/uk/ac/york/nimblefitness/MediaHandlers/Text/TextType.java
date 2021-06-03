package uk.ac.york.nimblefitness.MediaHandlers.Text;

public class TextType {
    TextModule.styleFamily style;
    TextModule.fontFamily font;
    String text = "", fontsize, fontcolour;
    int xstart, ystart;

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
}
