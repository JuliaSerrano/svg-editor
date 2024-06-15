package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class SVGPolyline extends SVGElement {
    private List<Integer> xPoints;
    private List<Integer> yPoints;

    public SVGPolyline(List<Integer> xPoints, List<Integer> yPoints, Color strokeColor, double strokeOpacity,
            double strokeWidth) {
        super(xPoints.get(0), yPoints.get(0), null, 1.0, strokeColor, strokeOpacity, strokeWidth);
        this.xPoints = new ArrayList<>(xPoints);
        this.yPoints = new ArrayList<>(yPoints);
        this.shape = createPath(xPoints, yPoints);
    }

    private Path2D createPath(List<Integer> xPoints, List<Integer> yPoints) {
        Path2D path = new Path2D.Double();
        path.moveTo(xPoints.get(0), yPoints.get(0));
        for (int i = 1; i < xPoints.size(); i++) {
            path.lineTo(xPoints.get(i), yPoints.get(i));
        }
        return path;
    }

    
    @Override
    public String toSVGString() {
        StringBuilder pointsBuilder = new StringBuilder();
        for (int i = 0; i < xPoints.size(); i++) {
            pointsBuilder.append(String.format("%d,%d ", xPoints.get(i), yPoints.get(i)));
        }

        return String.format(
                "<polyline points=\"%s\" style=\"fill:none; stroke:%s; stroke-opacity:%.2f; stroke-width:%.2f\" />",
                pointsBuilder.toString().trim(), colorToHex(strokeColor), strokeOpacity, strokeWidth);
    }

    @Override
    public void move(double dx, double dy) {
        if (xPoints.size() != yPoints.size()) {
            throw new IllegalStateException("The number of xPoints and yPoints must be equal.");
        }

        for (int i = 0; i < xPoints.size(); i++) {
            xPoints.set(i, xPoints.get(i) + (int) dx);
            yPoints.set(i, yPoints.get(i) + (int) dy);
        }
        this.shape = createPath(xPoints, yPoints);
    }

}
