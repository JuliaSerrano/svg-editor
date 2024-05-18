package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class SVGEllipse extends SVGElement {
    private int width;
    private int height;

    public SVGEllipse(int x, int y, int width, int height, Color fillColor, double fillOpacity, Color strokeColor,
            double strokeOpacity, double strokeWidth) {
        super(x, y, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
        this.width = width;
        this.height = height;
        this.shape = new Ellipse2D.Double(x, y, width, height);
    }

    @Override
    public String toSVGString() {
        return String.format(
                "<ellipse cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\" style=\"fill:%s; fill-opacity:%.2f; stroke:%s; stroke-opacity:%.2f; stroke-width:%.2f\" />",
                x + width / 2, y + height / 2, width / 2, height / 2, fillColor, fillOpacity,
                strokeColor, strokeOpacity, strokeWidth);
    }

    // Getters and setters
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}