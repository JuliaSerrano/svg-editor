package es.upm.pproject.geditor.view.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGPolyline;

public class PolylineCreator implements ShapeCreator {
    private SVGEditorController controller;
    private List<Integer> xPoints;
    private List<Integer> yPoints;
    private Path2D.Double currentPolyline;
    boolean drawing;

    public PolylineCreator(SVGEditorController controller) {
        this.controller = controller;
        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();
        drawing = false;
        currentPolyline = new Path2D.Double();
    }

    @Override
    public void startShape(MouseEvent e) {
        if (drawing) {
            updateShape(e);
        } else {
            createShape(e);
        }
    }

    private void createShape(MouseEvent e) {
        xPoints.clear();
        yPoints.clear();
        currentPolyline.reset();

        currentPolyline.moveTo(e.getX(), e.getY());
        xPoints.add(e.getX());
        yPoints.add(e.getY());
        drawing = true;
    }

    @Override
    public void updateShape(MouseEvent e) {
        if (e.getClickCount() == 2) {
            finishShape(e);
        } else {
            currentPolyline.lineTo(e.getX(), e.getY());
            xPoints.add(e.getX());
            yPoints.add(e.getY());
        }
    }

    @Override
    public void finishShape(MouseEvent e) {
        SVGPolyline polyline = new SVGPolyline(xPoints, yPoints);
        controller.addElement(polyline);
        xPoints.clear();
        yPoints.clear();
        currentPolyline.reset();
        drawing = false;
    }

    @Override
    public Shape getCurrentShape() {
        return currentPolyline;
    }
}
