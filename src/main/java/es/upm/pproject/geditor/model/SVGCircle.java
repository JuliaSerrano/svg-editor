package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class SVGCircle extends SVGElement {
    private int radius;

    public SVGCircle(int x, int y, int radius, Color fillColor, double fillOpacity, Color strokeColor,
            double strokeOpacity, double strokeWidth) {
        super(x, y, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
        this.radius = radius;
        this.shape = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public String toSVGString() {
        return String.format(
                "<circle cx=\"%d\" cy=\"%d\" r=\"%d\" style=\"fill:%s; fill-opacity:%.2f; stroke:%s; stroke-opacity:%.2f; stroke-width:%.2f\" />",
                x, y, radius, fillColor, fillOpacity, strokeColor, strokeOpacity,
                strokeWidth);
    }

    // Getters and setters
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}