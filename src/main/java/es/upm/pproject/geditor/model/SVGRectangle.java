package es.upm.pproject.geditor.model;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class SVGRectangle extends SVGElement {
    private int width;
    private int height;

    public SVGRectangle(int x, int y, int width, int height) {
        super("rect", x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public Shape getShape() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    // Getters and setters for x, y, width, height

    @Override
    public String toSVGString() {
        return String.format(
                "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" style=\"fill:%s; fill-opacity:%.2f; stroke:%s; stroke-opacity:%.2f; stroke-width:%.2f\" />",
                x, y, width, height, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
    }
}