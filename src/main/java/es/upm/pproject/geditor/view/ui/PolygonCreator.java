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
    boolean drawing;

    public PolygonCreator(SVGEditorController controller) {
        this.controller = controller;
        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();
        drawing = false;
        currentPolygon = new Path2D.Double();
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
        currentPolygon.reset();

        currentPolygon.moveTo(e.getX(), e.getY());
        xPoints.add(e.getX());
        yPoints.add(e.getY());
        drawing = true;
    }

    @Override
    public void updateShape(MouseEvent e) {
        if (e.getClickCount() == 2 && xPoints.size() > 2){
            finishShape(e);
        } else {
            currentPolygon.lineTo(e.getX(), e.getY());
            xPoints.add(e.getX());
            yPoints.add(e.getY());
        }
    }

    @Override
    public void finishShape(MouseEvent e) {
        SVGPolygon polygon = new SVGPolygon(xPoints, yPoints, xPoints.size());
        controller.addElement(polygon);
        xPoints.clear();
        yPoints.clear();
        currentPolygon.reset();
        drawing = false;
    }

    @Override
    public Shape getCurrentShape() {
        return currentPolygon;
    }
}
