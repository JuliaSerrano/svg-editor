package es.upm.pproject.geditor.model;

import java.awt.geom.Line2D;

public class SVGLine extends SVGElement {
    private double x2;
    private double y2;

    public SVGLine(double x1, double y1, double x2, double y2) {
        super(x1, y1);
        this.x2 = x2;
        this.y2 = y2;
        this.shape = new Line2D.Double(x1, y1, x2, y2);
    }

    
    @Override
    public String toSVGString() {
        return String.format(
                "<line x1=\"%.2f\" y1=\"%.2f\" x2=\"%.2f\" y2=\"%.2f\" style=\"stroke:%s; stroke-opacity:%.2f; stroke-width:%.2f\" />",
                x, y, x2, y2, colorToHex(strokeColor), strokeOpacity, strokeWidth);
    }

    @Override
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        this.x2 += dx;
        this.y2 += dy;
        this.shape = new Line2D.Double(x, y, x2, y2);
    }

    // Getters and setters
    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public boolean isWithinBounds(int width, int height) {
        return x >= 0 && y >= 0 && x2 >= 0 && y2 >= 0 && x <= width && y <= height && x2 <= width && y2 <= height;
    }
}