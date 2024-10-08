package es.upm.pproject.geditor.model;

import java.awt.geom.Ellipse2D;

public class SVGCircle extends SVGElement {
    private double radius;

    public SVGCircle(double x, double y, double radius) {
        super(x, y);
        this.radius = radius;
        this.shape = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public String toSVGString() {
        return String.format(
                "<circle cx=\"%.2f\" cy=\"%.2f\" r=\"%.2f\" style=\"fill:%s; fill-opacity:%.2f; stroke:%s; stroke-opacity:%.2f; stroke-width:%.2f\" />",
                x, y, radius, colorToHex(fillColor), fillOpacity, colorToHex(strokeColor), strokeOpacity, strokeWidth);
    }

    @Override
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        this.shape = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    public boolean isWithinBounds(int width, int height) {
        return x - radius >= 0 && y - radius >= 0 && x + radius <= width && y + radius <= height;
    }

    // Getters and setters
    public double getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getCx() {
        return this.x;
    }

    public double getCy() {
        return this.y;
    }
}