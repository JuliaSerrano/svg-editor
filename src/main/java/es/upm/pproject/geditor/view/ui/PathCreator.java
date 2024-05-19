package es.upm.pproject.geditor.view.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGPath;

public class PathCreator implements ShapeCreator {
    private SVGEditorController controller;
    private Path2D.Double currentPath;
    // private Point prevPoint;

    public PathCreator(SVGEditorController controller) {
        this.controller = controller;
        // currentPath = new Path2D.Double();
    }

    @Override
    public void startShape(MouseEvent e) {
        currentPath = new Path2D.Double();
        // currentPath.reset();
        currentPath.moveTo(e.getX(), e.getY());
        // this.prevPoint = e.getPoint();
    }

    @Override
    public void updateShape(MouseEvent e) {
        if (currentPath != null) {
            currentPath.lineTo(e.getX(), e.getY());
            // this.prevPoint = e.getPoint();
        }
    }

    @Override
    public void finishShape(MouseEvent e) {
        // Close the path when finishing the shape
        // currentPath.closePath();

        // Create the SVGPath object and add it to the controller
        if (currentPath != null) {
            currentPath.lineTo(e.getX(), e.getY());
            SVGPath svgPath = new SVGPath(0, 0, Color.WHITE, 1.0, Color.BLACK, 1.0, 1.0, currentPath);
            controller.addElement(svgPath);
            currentPath = null;
        }

    }

    @Override
    public Shape getCurrentShape() {
        return currentPath;
    }
}
