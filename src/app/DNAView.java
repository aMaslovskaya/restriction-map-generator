package app;

import com.sun.javafx.tk.Toolkit;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DNAView {
    private GraphicsContext context;
    private DNA DNA;
    private ArrayList<RestrictionSiteView> restrictionViews = new ArrayList<>();
    private final double width = 1000;
    private final double height = 30;
    private final double left = 10;
    private double bottom;
    private double top;
    private double step;
    private Color backgroundColor = Color.web("0xB3CCFC");
    private Color DNAColor = Color.web("0x80334D");
    private ArrayList<RestrictionSiteView> activeViews = new ArrayList<>();
    private double mapTop;
    private String text = "";
    private float scale = 1;
    private boolean isNucliotidesColorized = false;
    private double canvasX = 0;
    private double canvasY = 0;
    private double mouseX = 0;
    private double mouseY = 0;


    public DNAView(DNA DNA, GraphicsContext context) {
        this.context = context;
        this.bottom = context.getCanvas().getHeight();
        this.DNA = DNA;
        this.mapTop = 0;
        this.initializeView();
        this.onClick();
        this.text = "new DNA entry: " + Integer.toString(DNA.getNucleotidesCount()) + " bp";
        this.render();
        this.onDrag();
    }

    public GraphicsContext getContext() {
        return context;
    }

    public void setCanvasX(double canvasX) {
        this.canvasX = canvasX;
    }

    public void setCanvasY(double canvasY) {
        this.canvasY = canvasY;
    }

    public void setNucliotidesColorized(boolean isNucliotidesColorized) {
        this.isNucliotidesColorized = isNucliotidesColorized;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    private void initializeView() {
        sortRestrictionEnzymes();
        for (RestrictionSite restrictionSite : DNA.getRestrictionSites()) {
            restrictionViews.add(new RestrictionSiteView(context, restrictionSite));
        }
        context.setLineWidth(1);
        top = bottom - height;
        step = (width - 2 * left)  / (double) DNA.getNucleotidesCount();
    }

    public void update() {
        context.clearRect(0, mapTop, context.getCanvas().getWidth() * scale, context.getCanvas().getHeight() * scale);
        render();
    }

    public void render() {
        context.save();
        context.translate(this.canvasX, this.canvasY);
        context.scale(this.scale, this.scale);
        renderBackground();
        renderDNA();
        renderRestrictionMarks();
        renderRestrictionSitesLabels();
        context.restore();
    }

    private void renderBackground() {
        context.setFill(backgroundColor);
        context.fillRect(0, mapTop, context.getCanvas().getWidth(), bottom - mapTop);
    }

    private void renderDNA() {
        if(isNucliotidesColorized) {
            for(int i = 0; i < DNA.getNucleotides().size(); i++) {
                Character nucleotide = DNA.getNucleotides().get(i);
                switch (nucleotide) {
                    case 'A':
                        context.setStroke(Color.RED);
                        break;
                    case 'T':
                        context.setStroke(Color.BLUE);
                        break;
                    case 'G':
                        context.setStroke(Color.GREEN);
                        break;
                    default:
                        context.setStroke(Color.YELLOW);
                }
                context.setLineWidth(step);
                context.strokeLine(left + i * step, bottom, left + i * step, top);
            }
        }
        else {
            context.setFill(DNAColor);
            context.fillRect(left, top, width - 2 * left, height);
        }
        context.setFill(DNAColor);
        context.setFont(new Font("Arial", 15));
        context.fillText(text, 50, mapTop + height * 2);
    }

    public String getText() {
        return text;
    }

    public double getCanvasX() {
        return canvasX;
    }

    public double getCanvasY() {
        return canvasY;
    }

    public Integer getInitFontSize() {
        return this.restrictionViews.get(0).getFontSize();
    }

    public Color getInitFontColor() {
        return this.restrictionViews.get(0).getColor();
    }

    public boolean isInitUnderline() {
        return this.restrictionViews.get(0).isUnderline();
    }

    public String getInitFontFamily() {
        return this.restrictionViews.get(0).getFontFamily();
    }



    private void renderRestrictionMarks() {
        for (RestrictionSiteView restrictionSiteView : restrictionViews) {
            context.setStroke(restrictionSiteView.getColor());
            final double x = step * restrictionSiteView.getRestrictionSite().getNucleotideNumber() + left;
            context.strokeLine(x, bottom, x, top);
        }
    }

    private void renderRestrictionSitesLabels() {
        double minX = 0;
        for (RestrictionSiteView restrictionSiteView : restrictionViews) {
            RestrictionSite restrictionSite = restrictionSiteView.getRestrictionSite();
            Float labelLength = restrictionSiteView.getTextLength();
            Float fontHeight = restrictionSiteView.getFontHeight();
            context.setStroke(restrictionSiteView.getColor());
            double y = (mapTop - bottom) / 3 + bottom;
            context.setLineWidth(1);
            if(restrictionSiteView.isHorizontal()) {
                minX += labelLength / 2;
                final double bottomX = step * restrictionSite.getNucleotideNumber() + left;
                final double topX = bottomX > minX ? bottomX : minX;
                context.strokeLine(bottomX, this.top, topX, y);
                restrictionSiteView.setX(topX - labelLength / 2);
                restrictionSiteView.setY(y - fontHeight);
                restrictionSiteView.render();
                minX = topX + labelLength / 2;
            } else {
                minX += fontHeight / 2;
                final double bottomX = step *restrictionSite.getNucleotideNumber() + left;
                final double topX = bottomX > minX ? bottomX : minX;
                context.strokeLine(bottomX, this.top, topX, y);
                restrictionSiteView.setX(topX - fontHeight / 4);
                restrictionSiteView.setY(y - labelLength );
                restrictionSiteView.render();
                minX = topX + fontHeight / 2;
            }
        }
    }

    private void sortRestrictionEnzymes() {
        Collections.sort(this.DNA.getRestrictionSites(), new Comparator<RestrictionSite>() {
            @Override
            public int compare(RestrictionSite o1, RestrictionSite o2) {
                return Integer.compare(o1.getNucleotideNumber(), o2.getNucleotideNumber());
            }
        });
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getDNAColor() {
        return DNAColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setDNAColor(Color DNAColor) {
        this.DNAColor = DNAColor;
    }

    public void setFontColor(Color color) {
        if(this.activeViews.isEmpty())
            for(RestrictionSiteView restrictionSiteView : this.restrictionViews)
                restrictionSiteView.setColor(color);
        else
            for(RestrictionSiteView restrictionSiteView : this.activeViews)
                restrictionSiteView.setColor(color);
    }

    public void setFontSize(Integer fontSize) {
        if(this.activeViews.isEmpty())
            for(RestrictionSiteView restrictionSiteView : this.restrictionViews)
                restrictionSiteView.setFontSize(fontSize);
        else
            for(RestrictionSiteView restrictionSiteView : this.activeViews)
                restrictionSiteView.setFontSize(fontSize);
    }

    public void setIsHorizontalDirection(boolean isHorizontalDirection) {
        if(this.activeViews.isEmpty())
            for(RestrictionSiteView restrictionSiteView : this.restrictionViews)
                restrictionSiteView.setIsHorizontal(isHorizontalDirection);
        else
            for(RestrictionSiteView restrictionSiteView : this.activeViews)
                restrictionSiteView.setIsHorizontal(isHorizontalDirection);
    }

    public void setIsUnderline(boolean isUnderline) {
        if(this.activeViews.isEmpty())
            for(RestrictionSiteView restrictionSiteView : this.restrictionViews)
                restrictionSiteView.setIsUnderline(isUnderline);
        else
            for(RestrictionSiteView restrictionSiteView : this.activeViews)
                restrictionSiteView.setIsUnderline(isUnderline);
    }

    public void setPosture(FontPosture posture) {
        if(this.activeViews.isEmpty())
            for(RestrictionSiteView restrictionSiteView : this.restrictionViews)
                restrictionSiteView.setFontPosture(posture);
        else
            for(RestrictionSiteView restrictionSiteView : this.activeViews)
                restrictionSiteView.setFontPosture(posture);
    }

    public void setFontFamily(String fontFamily) {
        if(this.activeViews.isEmpty())
            for(RestrictionSiteView restrictionSiteView : this.restrictionViews)
                restrictionSiteView.setFontFamily(fontFamily);
        else
            for(RestrictionSiteView restrictionSiteView : this.activeViews)
                restrictionSiteView.setFontFamily(fontFamily);
    }

    public void deleteSites () {
        for (RestrictionSiteView restrictionSiteView : activeViews) {
            restrictionViews.remove(restrictionSiteView);
        }
        activeViews.clear();
    }

    public void setWeight(FontWeight weight) {
        if(this.activeViews.isEmpty())
            for(RestrictionSiteView restrictionSiteView : this.restrictionViews)
                restrictionSiteView.setFontWeight(weight);
        else
            for(RestrictionSiteView restrictionSiteView : this.activeViews)
                restrictionSiteView.setFontWeight(weight);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void highlight(double x, double y) {
        if(x >= left && x <= left + width - 2 * left && y >= top && y <= top + height) {
            double startX = left;
            double startNumber = 0;
            double endNumber = 0;
            double endX = 0;
            for (RestrictionSiteView restrictionSiteView : restrictionViews) {
                final double markX = step * restrictionSiteView.getRestrictionSite().getNucleotideNumber() + left;
                if(x >= markX) {
                    startX = markX;
                    startNumber = restrictionSiteView.getRestrictionSite().getNucleotideNumber();
                }
                else {
                    endNumber = restrictionSiteView.getRestrictionSite().getNucleotideNumber();
                    endX = markX;
                    break;
                }
            }
            endX = endX > 0 ? endX :  left + width - 2 * left;
            context.setFill(Color.AZURE);
            context.fillRect(startX, top, endX - startX, height);
            Font font = new Font("sans", 8);
            context.setFont(font);
            context.strokeText(Double.toString(endNumber - startNumber),
                    startX + (endX - startX) / 2 - Toolkit.getToolkit().getFontLoader().computeStringWidth(Double.toString(endNumber - startNumber),
                            font) / 2, bottom - 15);
        }
    }

    private void onDrag() {

        context.getCanvas().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DNAView.this.mouseX = mouseEvent.getX();
                DNAView.this.mouseY = mouseEvent.getY();
            }
        });

        context.getCanvas().addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Canvas canvas = DNAView.this.context.getCanvas();
                float scale = DNAView.this.scale;
                double currentX = mouseEvent.getX();
                double currentY = mouseEvent.getY();
                DNAView.this.canvasX += (mouseEvent.getX() - DNAView.this.mouseX) / canvas.getWidth() * 20 * scale;
                DNAView.this.canvasY += (mouseEvent.getY() - DNAView.this.mouseY) / canvas.getHeight() * 20 * scale;
                if(canvas.getWidth() * scale + canvasX < canvas.getWidth())  {
                    canvasX = - canvas.getWidth() * (scale - 1);
                }
                if(canvas.getHeight()  * scale + canvasY < canvas.getHeight()) {
                    canvasY = - canvas.getHeight() * (scale - 1);
                }
                canvasX = canvasX < 0 ? canvasX : 0;
                canvasY = canvasY < 0 ? canvasY : 0;
                DNAView.this.update();
            }
        });
    }

    private void onClick() {
        context.getCanvas().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boolean isClickOnSite = false;
                for(RestrictionSiteView view : restrictionViews) {
                    if(view.isInArea(event.getX(), event.getY())) {
                        isClickOnSite = true;
                        if(activeViews.contains(view)) {
                            activeViews.remove(view);
                            view.setOutline(false);
                        }
                        else {
                            if(event.getButton() == MouseButton.PRIMARY) {
                                for (RestrictionSiteView activeView : restrictionViews)
                                    activeView.setOutline(false);
                                activeViews.clear();
                                activeViews.add(view);
                                view.setOutline(true);
                            }
                            else if(event.getButton() == MouseButton.SECONDARY) {
                                activeViews.add(view);
                                view.setOutline(true);
                            }
                        }
                        break;
                    }

                }
                if(!isClickOnSite) {
                    for (RestrictionSiteView activeView : restrictionViews)
                        activeView.setOutline(false);
                    activeViews.clear();
                }
                render();
                highlight(event.getX(), event.getY());
            }
        });
    }
}
