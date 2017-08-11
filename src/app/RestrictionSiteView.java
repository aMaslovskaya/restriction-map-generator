package app;

import com.sun.javafx.tk.Toolkit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class RestrictionSiteView {
    private Color color = Color.BLACK;
    private FontWeight fontWeight = FontWeight.NORMAL;
    private FontPosture fontPosture = FontPosture.REGULAR;
    private Integer fontSize = 12;
    private String fontFamily = "Serif";
    private Font font;
    private boolean isUnderline = false;
    private boolean isHorizontal = false;
    private double x;
    private  double y;
    private GraphicsContext context;
    private RestrictionSite restrictionSite;
    private boolean isOutline = false;

    public RestrictionSiteView(GraphicsContext context, RestrictionSite restrictionSite) {
        this.context = context;
        this.initFont();
        this.restrictionSite = restrictionSite;
    }

    public FontWeight getFontWeight() {
        return fontWeight;
    }

    public FontPosture getFontPosture() {
        return  fontPosture;
    }

    public Color getColor() {
        return color;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public Font getFont() {
        return font;
    }

    public RestrictionSite getRestrictionSite() {
        return restrictionSite;
    }

    public boolean isUnderline() {
        return isUnderline;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private void initFont() {
        font = Font.font(fontFamily, fontWeight, fontPosture, fontSize);
        context.setFont(font);
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        this.font = Font.font(fontFamily, fontWeight, fontPosture, fontSize);
    }

    public void setFontPosture(FontPosture fontPosture) {
        this.fontPosture = fontPosture;
        this.font = Font.font(fontFamily, fontWeight, fontPosture, fontSize);
    }

    public void setFontWeight(FontWeight fontWeight) {
        this.fontWeight = fontWeight;
        this.font = Font.font(fontFamily, fontWeight, fontPosture, fontSize);
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
        this.font = Font.font(fontFamily, fontWeight, fontPosture, fontSize);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setIsUnderline(boolean isUnderline) {
        this.isUnderline = isUnderline;
        this.font = Font.font(fontFamily, fontWeight, fontPosture, fontSize);
    }

    public void setIsHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    public void setOutline(boolean isOutline) {
        this.isOutline = isOutline;
    }

    public float getFontHeight() {
        Float fontHight = Toolkit.getToolkit().getFontLoader().getFontMetrics(font).getLineHeight();
        return fontHight;
    }

    public float getTextLength() {
        Float lineLength = Toolkit.getToolkit().getFontLoader().computeStringWidth(getLabelText(), font);
        return  lineLength;
    }

    public void render() {
        initFont();
        Float fontHight = this.getFontHeight();
        Float lineLength = this.getTextLength();
        context.setFont(font);
        context.setFill(this.getColor());
        context.setStroke(this.getColor());
        if(!isHorizontal) {
            context.save();
            context.translate(x, y);
            context.rotate(90);
            context.fillText(getLabelText(), 0 ,0);
            context.restore();
            if(isUnderline) {
                Double lineX = x, lineY = y;;
                context.strokeLine(lineX, lineY, lineX, lineY + lineLength);
            }
            if(isOutline) {
                context.strokeRect(x - fontHight / 4, y, fontHight, lineLength);
            }
        } else {
            context.fillText(getLabelText(), x ,y + fontHight);
            if(isUnderline) {
                Double lineX = x, lineY = y + fontHight;
                context.strokeLine(lineX, lineY, lineX + lineLength, lineY);
            }
            if(isOutline) {
                context.strokeRect(x, y  + fontHight / 4, lineLength, fontHight);
            }
        }
    }

    public boolean isInArea(double x, double y) {
        Float fontHight = this.getFontHeight();
        Float lineLength = this.getTextLength();
        if(isHorizontal) {
            if(x >= this.x && x <= this.x + lineLength && y >= this.y && y < this.y + fontHight)
                return  true;
        }
        else
            if(x >= this.x && x <= this.x + fontHight && y >= this.y && y < this.y + lineLength)
                return true;
        return false;
    }

    public String getLabelText() {
        RestrictionEnzyme enzyme = restrictionSite.getRestrictionEnzyme();
        return enzyme.getName() + ' ' + enzyme.getSequence();
    }

}
