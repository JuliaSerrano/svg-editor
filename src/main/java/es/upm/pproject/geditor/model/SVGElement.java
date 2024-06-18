package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.awt.Shape;

public abstract class SVGElement {
    protected Shape shape;
    protected double x;
    protected double y;
    protected Color fillColor;
    protected double fillOpacity;
    protected Color strokeColor;
    protected double strokeWidth;
    protected double strokeOpacity;

    protected SVGElement(double x, double y) {
        this.x = x;
        this.y = y;
        this.fillColor = Color.BLACK;
        this.fillOpacity = 1.0;
        this.strokeColor = Color.BLACK;
        this.strokeOpacity = 1.0;
        this.strokeWidth = 1.0;
    }

    public abstract String toSVGString();

    // Getters and setters
    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public double getFillOpacity() {
        return fillOpacity;
    }

    public void setFillOpacity(double fillOpacity) {
        this.fillOpacity = fillOpacity;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public double getStrokeOpacity() {
        return strokeOpacity;
    }

    public void setStrokeOpacity(double strokeOpacity) {
        this.strokeOpacity = strokeOpacity;
    }

    public void setStyles(Color fillColor, double fillOpacity, Color strokeColor, double strokeWidth, double strokeOpacity) {
        this.fillColor = fillColor;
        this.fillOpacity = fillOpacity;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
        this.strokeOpacity = strokeOpacity;
    }

    public abstract void move(double dx, double dy);

    public abstract boolean isWithinBounds(int width, int height);

    protected String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
