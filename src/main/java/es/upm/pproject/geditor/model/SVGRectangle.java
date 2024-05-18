package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class SVGRectangle extends SVGElement {
    private int width;
    private int height;

    // public SVGRectangle() {

    // }

    public SVGRectangle(int x, int y, int width, int height, Color fillColor, double fillOpacity, Color strokeColor,
            double strokeOpacity, double strokeWidth) {
        super(x, y, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
        this.width = width;
        this.height = height;
        this.shape = new Rectangle2D.Double(x, y, width, height);
    }

    // TODO: adapt Color to a color code SVG would understand
    @Override
    public String toSVGString() {
        return String.format(
                "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" style=\"fill:%s; fill-opacity:%.2f; stroke:%s; stroke-opacity:%.2f; stroke-width:%.2f\" />",
                x, y, width, height, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
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
