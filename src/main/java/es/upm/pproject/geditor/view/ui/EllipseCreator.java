package es.upm.pproject.geditor.view.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGEllipse;

public class EllipseCreator implements ShapeCreator {
    private SVGEditorController controller;
    private Ellipse2D.Double currentEllipse;
    private double startX;
    private double startY;

    public EllipseCreator(SVGEditorController controller) {
        this.controller = controller;
    }

    @Override
    public void startShape(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        currentEllipse = new Ellipse2D.Double(startX, startY, 0, 0);
    }

    @Override
    public void updateShape(MouseEvent e) {
        if (currentEllipse != null) {
            double width = Math.abs(e.getX() - startX);
            double height = Math.abs(e.getY() - startY);
            double x = Math.min(e.getX(), startX);
            double y = Math.min(e.getY(), startY);
            currentEllipse.setFrame(x, y, width, height);
        }
    }

    @Override
    public void finishShape(MouseEvent e) {
        if (currentEllipse != null) {
            double width = Math.abs(e.getX() - startX);
            double height = Math.abs(e.getY() - startY);
            double x = Math.min(e.getX(), startX);
            double y = Math.min(e.getY(), startY);
            SVGEllipse ellipse = new SVGEllipse(x, y, width, height, Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0);
            controller.addElement(ellipse);
            currentEllipse = null;
        }
    }

    @Override
    public Shape getCurrentShape() {
        return currentEllipse;
    }
}
