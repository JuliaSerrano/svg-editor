package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SVGDocument {
    private int width;
    private int height;
    private Color backgroundColor;
    private List<SVGElement> elements;

    public SVGDocument(int width, int height) {
        this.width = width;
        this.height = height;
        this.backgroundColor = Color.WHITE;
        this.elements = new ArrayList<>();
    }

    // Getters and setters
    public List<SVGElement> getElements() {
        return elements;
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

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void addElement(SVGElement element) {
        elements.add(element);
    }

    public void removeElement(SVGElement element) {
        elements.remove(element);
    }
    public String toSVGString() {
        StringBuilder svg = new StringBuilder();
        svg.append(String.format("<svg width=\"%d\" height=\"%d\" xmlns=\"http://www.w3.org/2000/svg\">", width, height));
        svg.append(System.lineSeparator());
        svg.append(String.format("<rect width=\"100%%\" height=\"100%%\" fill=\"%s\" />", colorToHex(backgroundColor)));
        svg.append(System.lineSeparator());
        
        for (SVGElement element : elements) {
            svg.append(element.toSVGString()).append(System.lineSeparator());
        }

        svg.append("</svg>");
        return svg.toString();
    }

    private String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
