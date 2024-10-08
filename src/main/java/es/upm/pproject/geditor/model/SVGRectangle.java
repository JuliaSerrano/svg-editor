package es.upm.pproject.geditor.model;

import java.awt.geom.Rectangle2D;

public class SVGRectangle extends SVGElement {
    private double width;
    private double height;

    public SVGRectangle(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.shape = new Rectangle2D.Double(x, y, width, height);
    }

  
    @Override
    public String toSVGString() {
        return String.format(
                "<rect x=\"%.2f\" y=\"%.2f\" width=\"%.2f\" height=\"%.2f\" style=\"fill:%s; fill-opacity:%.2f; stroke:%s; stroke-opacity:%.2f; stroke-width:%.2f\" />",
                x, y, width, height, colorToHex(fillColor), fillOpacity, colorToHex(strokeColor), strokeOpacity, strokeWidth);
    }

    // Getters and setters
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        this.shape = new Rectangle2D.Double(x, y, width, height);
    }

    public boolean isWithinBounds(int width, int height) {
        return x >= 0 && y >= 0 && x + this.width <= width && y + this.height <= height;
    }
}
