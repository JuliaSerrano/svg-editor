package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SVGDocument {
    private static final Logger logger = LoggerFactory.getLogger(SVGDocument.class);
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
        if (element.isWithinBounds(width, height)) {
            elements.add(element);
            logger.info("Element {} added to the document", element.getShape());
        } else {
            logger.warn("Element is out of bounds and will not be added to the document");
        }
    }

    public void removeElement(SVGElement element) {
        elements.remove(element);
    }

    public String toSVGString() {
        StringBuilder svg = new StringBuilder();

        String indent = "  "; // Two spaces
        String newLine = System.lineSeparator() + System.lineSeparator();

        svg.append(
                String.format("<svg width=\"%d\" height=\"%d\" xmlns=\"http://www.w3.org/2000/svg\">", width, height));
        svg.append(newLine);
        svg.append(indent
                + String.format("<rect width=\"100%%\" height=\"100%%\" fill=\"%s\" />", colorToHex(backgroundColor)));
        svg.append(newLine);

        for (SVGElement element : elements) {
            svg.append(indent + element.toSVGString()).append(newLine);
        }

        svg.append("</svg>");
        return svg.toString();
    }

    private String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
