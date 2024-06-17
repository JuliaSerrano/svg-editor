package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class SVGEllipse extends SVGElement {
    private double width;
    private double height;

    public SVGEllipse(double x, double y, double width, double height, Color fillColor, double fillOpacity,
            Color strokeColor,
            double strokeOpacity, double strokeWidth) {
        super(x, y, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
        this.width = width;
        this.height = height;
        this.shape = new Ellipse2D.Double(x, y, width, height);
    }

    
   
    @Override
    public String toSVGString() {
        return String.format(
                "<ellipse cx=\"%.2f\" cy=\"%.2f\" rx=\"%.2f\" ry=\"%.2f\" style=\"fill:%s; fill-opacity:%.2f; stroke:%s; stroke-opacity:%.2f; stroke-width:%.2f\" />",
                x + width / 2, y + height / 2, width / 2, height / 2, colorToHex(fillColor), fillOpacity, colorToHex(strokeColor),
                strokeOpacity, strokeWidth);
    }

    @Override
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        this.shape = new Ellipse2D.Double(x, y, width, height);
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

    public boolean isWithinBounds(int width, int height) {
        double rx = this.width / 2;
        double ry = this.height / 2;
        return x - rx >= 0 && y - ry >= 0 && x + rx <= width && y + ry <= height;
    }
}