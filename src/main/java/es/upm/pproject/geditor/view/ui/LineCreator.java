package es.upm.pproject.geditor.view.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGLine;

public class LineCreator implements ShapeCreator {
    private SVGEditorController controller;
    private Line2D.Double currentLine;
    private double startX;
    private double startY;

    public LineCreator(SVGEditorController controller) {
        this.controller = controller;
    }

    @Override
    public void startShape(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        currentLine = new Line2D.Double(startX, startY, startX, startY);
    }

    @Override
    public void updateShape(MouseEvent e) {
        if (currentLine != null) {
            currentLine.setLine(startX, startY, e.getX(), e.getY());
        }
    }

    @Override
    public void finishShape(MouseEvent e) {
        if (currentLine != null) {
            double x2 = e.getX();
            double y2 = e.getY();
            SVGLine line = new SVGLine(startX, startY, x2, y2);
            controller.addElement(line);
            currentLine = null;
        }
    }

    @Override
    public Shape getCurrentShape() {
        return currentLine;
    }
}
