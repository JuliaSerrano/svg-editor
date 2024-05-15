package es.upm.pproject.geditor.model;

import java.awt.Shape;

public abstract class SVGElement {
    public String type;
    public int x;
    public int y;
    public int width;
    public int height;
    public String fillColor;
    public double fillOpacity;
    public String strokeColor;
    public double strokeWidth;
    public double strokeOpacity;

    public SVGElement(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    // Getters and setters

    public abstract String toSVGString();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

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

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public double getFillOpacity() {
        return fillOpacity;
    }

    public void setFillOpacity(double fillOpacity) {
        this.fillOpacity = fillOpacity;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
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

    public abstract Shape getShape();

}
