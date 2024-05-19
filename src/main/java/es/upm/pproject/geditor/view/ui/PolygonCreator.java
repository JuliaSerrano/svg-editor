package es.upm.pproject.geditor.view.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGPolygon;

public class PolygonCreator implements ShapeCreator {
    private SVGEditorController controller;
    private List<Integer> xPoints;
    private List<Integer> yPoints;
    private Path2D.Double currentPolygon;

    public PolygonCreator(SVGEditorController controller) {
        this.controller = controller;
        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();
        currentPolygon = new Path2D.Double();
    }

    @Override
    public void startShape(MouseEvent e) {
        xPoints.clear();
        yPoints.clear();
        currentPolygon.reset();
        currentPolygon.moveTo(e.getX(), e.getY());
        xPoints.add(e.getX());
        yPoints.add(e.getY());
    }

    @Override
    public void updateShape(MouseEvent e) {
        currentPolygon.lineTo(e.getX(), e.getY());
        xPoints.add(e.getX());
        yPoints.add(e.getY());
    }

    @Override
    public void finishShape(MouseEvent e) {
        SVGPolygon polygon = new SVGPolygon(xPoints, yPoints, xPoints.size(), Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0);
        controller.addElement(polygon);
        xPoints.clear();
        yPoints.clear();
        currentPolygon.reset();
    }

    @Override
    public Shape getCurrentShape() {
        return currentPolygon;
    }
}
