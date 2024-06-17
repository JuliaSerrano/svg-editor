package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.geditor.controller.SVGEditorController;

public class SVGDocument {
    private static final Logger logger = LoggerFactory.getLogger(SVGEditorController.class);
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
        if (isWithinBounds(element)) {
            elements.add(element);
        } else {
        	logger.info("Element {} is out of bounds and cannot be added", element);
        	//logger.info("Creating new image with width {} and height {}", width, height);
        }
    }
    
    private boolean isWithinBounds(SVGElement element) {
        if (element instanceof SVGCircle) {
            SVGCircle circle = (SVGCircle) element;
            return circle.getCx() - circle.getRadius() >= 0 &&
                   circle.getCy() - circle.getRadius() >= 0 &&
                   circle.getCx() + circle.getRadius() <= width &&
                   circle.getCy() + circle.getRadius() <= height;
        } else if (element instanceof SVGEllipse) {
            SVGEllipse ellipse = (SVGEllipse) element;
            double rx = ellipse.getWidth() / 2;
            double ry = ellipse.getHeight() / 2;
            return ellipse.getX() - rx >= 0 &&
                   ellipse.getY() - ry >= 0 &&
                   ellipse.getX() + rx <= width &&
                   ellipse.getY() + ry <= height;
        } else if (element instanceof SVGLine) {
            SVGLine line = (SVGLine) element;
            return line.getX() >= 0 && line.getX() <= width &&
                   line.getY() >= 0 && line.getY() <= height &&
                   line.getX2() >= 0 && line.getX2() <= width &&
                   line.getY2() >= 0 && line.getY2() <= height;
        } else if (element instanceof SVGRectangle) {
            SVGRectangle rectangle = (SVGRectangle) element;
            return rectangle.getX() >= 0 &&
                   rectangle.getY() >= 0 &&
                   rectangle.getX() + rectangle.getWidth() <= width &&
                   rectangle.getY() + rectangle.getHeight() <= height;
        } else if (element instanceof SVGPolygon) {
            SVGPolygon polygon = (SVGPolygon) element;
            for (int i = 0; i < polygon.getNumPoints(); i++) {
                if (polygon.getXPoints().get(i) < 0 || polygon.getXPoints().get(i) > width ||
                    polygon.getYPoints().get(i) < 0 || polygon.getYPoints().get(i) > height) {
                    return false;
                }
            }
            return true;
        } else if (element instanceof SVGPolyline) {
            SVGPolyline polyline = (SVGPolyline) element;
            List<Integer> xPoints = polyline.getXPoints();
            List<Integer> yPoints = polyline.getYPoints();
            for (int i = 0; i < xPoints.size(); i++) {
                if (xPoints.get(i) < 0 || xPoints.get(i) > width ||
                    yPoints.get(i) < 0 || yPoints.get(i) > height) {
                    return false;
                }
            }
            return true;
        } else if (element instanceof SVGPath) {
            SVGPath svgPath = (SVGPath) element;
            PathIterator iterator = svgPath.getPath().getPathIterator(null);
            float[] coords = new float[6];
            while (!iterator.isDone()) {
                int type = iterator.currentSegment(coords);
                switch (type) {
                    case PathIterator.SEG_MOVETO:
                    case PathIterator.SEG_LINETO:
                        if (coords[0] < 0 || coords[0] > width || coords[1] < 0 || coords[1] > height) {
                            return false;
                        }
                        break;
                    case PathIterator.SEG_QUADTO:
                        if (coords[0] < 0 || coords[0] > width || coords[1] < 0 || coords[1] > height ||
                            coords[2] < 0 || coords[2] > width || coords[3] < 0 || coords[3] > height) {
                            return false;
                        }
                        break;
                    case PathIterator.SEG_CUBICTO:
                        if (coords[0] < 0 || coords[0] > width || coords[1] < 0 || coords[1] > height ||
                            coords[2] < 0 || coords[2] > width || coords[3] < 0 || coords[3] > height ||
                            coords[4] < 0 || coords[4] > width || coords[5] < 0 || coords[5] > height) {
                            return false;
                        }
                        break;
                }
                iterator.next();
            }
            return true;
        }
        
        return true;
    }



    public void removeElement(SVGElement element) {
        elements.remove(element);
    }
    public String toSVGString() {
        StringBuilder svg = new StringBuilder();

        String indent = "  "; // Two spaces
        String newLine = System.lineSeparator() + System.lineSeparator();

        svg.append(String.format("<svg width=\"%d\" height=\"%d\" xmlns=\"http://www.w3.org/2000/svg\">", width, height));
        svg.append(newLine);
        svg.append(indent + String.format("<rect width=\"100%%\" height=\"100%%\" fill=\"%s\" />", colorToHex(backgroundColor)));
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
