package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

public class SVGPolygon extends SVGElement {
    private List<Integer> xPoints;
    private List<Integer> yPoints;
    private int numPoints;

    public SVGPolygon(List<Integer> xPoints, List<Integer> yPoints, int numPoints, Color fillColor, double fillOpacity,
            Color strokeColor, double strokeOpacity, double strokeWidth) {

        super(xPoints.get(0), yPoints.get(0), fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
        if (xPoints == null || yPoints == null) {
            throw new IllegalArgumentException("Point lists cannot be null.");
        }
        this.xPoints = new ArrayList<>(xPoints);
        this.yPoints = new ArrayList<>(yPoints);
        this.numPoints = numPoints;
        this.shape = createPolygon(xPoints, yPoints, numPoints);
    }

    private Polygon createPolygon(List<Integer> xPoints, List<Integer> yPoints, int numPoints) {
        Polygon polygon = new Polygon();
        for (int i = 0; i < numPoints; i++) {
            polygon.addPoint(xPoints.get(i), yPoints.get(i));
        }
        return polygon;
    }  
    
    
    @Override
    public String toSVGString() {
        StringBuilder svgString = new StringBuilder("<polygon points=\"");
        for (int i = 0; i < numPoints; i++) {
            svgString.append(String.format("%d,%d ", xPoints.get(i), yPoints.get(i)));
        }
        svgString.append("\" style=\"fill:").append(colorToHex(fillColor)).append(";fill-opacity:").append(fillOpacity)
                .append(";stroke:").append(colorToHex(strokeColor)).append(";stroke-opacity:").append(strokeOpacity)
                .append(";stroke-width:").append(strokeWidth).append("\" />");
        return svgString.toString();
    }
    @Override
    public void move(double dx, double dy) {

        for (int i = 0; i < numPoints; i++) {
            xPoints.set(i, xPoints.get(i) + (int) dx);
            yPoints.set(i, yPoints.get(i) + (int) dy);
        }
        this.shape = createPolygon(xPoints, yPoints, numPoints);
    }

    // Getters and setters
    public List<Integer> getXPoints() {
        return xPoints;
    }

    public void setXPoints(List<Integer> xPoints) {
        this.xPoints = xPoints;
    }

    public List<Integer> getYPoints() {
        return yPoints;
    }

    public void setYPoints(List<Integer> yPoints) {
        this.yPoints = yPoints;
    }

    public int getNumPoints() {
        return numPoints;
    }

    public void setNumPoints(int numPoints) {
        this.numPoints = numPoints;
    }
}
