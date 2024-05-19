package es.upm.pproject.geditor.view.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGCircle;

public class CircleCreator implements ShapeCreator {
    private SVGEditorController controller;
    private Ellipse2D.Double currentCircle;
    private int startX;
    private int startY;

    public CircleCreator(SVGEditorController controller) {
        this.controller = controller;
    }

    @Override
    public void startShape(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        currentCircle = new Ellipse2D.Double(startX, startY, 0, 0);
    }

    @Override
    public void updateShape(MouseEvent e) {
        if (currentCircle != null) {
            int radius = (int) Math.sqrt(Math.pow(e.getX() - startX, 2) + Math.pow(e.getY() - startY, 2));
            currentCircle.setFrameFromCenter(startX, startY, startX + radius, startY + radius);
        }
    }

    @Override
    public void finishShape(MouseEvent e) {
        if (currentCircle != null) {
            int radius = (int) Math.sqrt(Math.pow(e.getX() - startX, 2) + Math.pow(e.getY() - startY, 2));
            int x = startX - radius;
            int y = startY - radius;
            SVGCircle circle = new SVGCircle(startX, startY, radius, Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0);
            controller.addElement(circle);
            currentCircle = null;
        }
    }

    @Override
    public Shape getCurrentShape() {
        return currentCircle;
    }
}
