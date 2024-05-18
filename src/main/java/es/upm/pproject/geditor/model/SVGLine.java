package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.awt.geom.Line2D;

public class SVGLine extends SVGElement {
    private int x2;
    private int y2;

    public SVGLine(int x1, int y1, int x2, int y2, Color strokeColor, double strokeOpacity, double strokeWidth) {
        super(x1, y1, strokeColor, 1.0, strokeColor, strokeOpacity, strokeWidth);
        this.x2 = x2;
        this.y2 = y2;
        this.shape = new Line2D.Double(x1, y1, x2, y2);
    }

    @Override
    public String toSVGString() {
        return String.format(
                "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:%s; stroke-opacity:%.2f; stroke-width:%.2f\" />",
                x, y, x2, y2, strokeColor, strokeOpacity, strokeWidth);
    }

    // Getters and setters
    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
}