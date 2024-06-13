package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.awt.Polygon;
import java.util.List;

public class SVGPolygon extends SVGElement {
    private List<Integer> xPoints;
    private List<Integer> yPoints;
    private int numPoints;

    public SVGPolygon(List<Integer> xPoints, List<Integer> yPoints, int numPoints, Color fillColor, double fillOpacity,
            Color strokeColor, double strokeOpacity, double strokeWidth) {
        super(xPoints.get(0), yPoints.get(0), fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
        this.xPoints = xPoints;
        this.yPoints = yPoints;
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
            svgString.append(xPoints.get(i)).append(",").append(yPoints.get(i)).append(" ");
        }
        svgString.append("\" style=\"fill:").append(fillColor).append(";fill-opacity:").append(fillOpacity)
                .append(";stroke:").append(strokeColor).append(";stroke-opacity:").append(strokeOpacity)
                .append(";stroke-width:").append(strokeWidth).append("\" />");
        return svgString.toString();
    }

    @Override
    public void move(double dx, double dy) {
        // TODO: first fix polygon creation
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
