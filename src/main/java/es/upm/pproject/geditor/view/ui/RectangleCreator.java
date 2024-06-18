package es.upm.pproject.geditor.view.ui;

import java.awt.*;
import java.awt.event.MouseEvent;

import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGRectangle;

public class RectangleCreator implements ShapeCreator {
    private SVGEditorController controller;
    private Rectangle currentRectangle;

    public RectangleCreator(SVGEditorController controller) {
        this.controller = controller;
    }

    @Override
    public void startShape(MouseEvent e) {
        currentRectangle = new Rectangle(e.getX(), e.getY(), 0, 0);
    }

    @Override
    public void updateShape(MouseEvent e) {
        if (currentRectangle != null) {
            int width = Math.abs(e.getX() - currentRectangle.x);
            int height = Math.abs(e.getY() - currentRectangle.y);
            currentRectangle.width = width;
            currentRectangle.height = height;
        }
    }

    @Override
    public void finishShape(MouseEvent e) {
        if (currentRectangle != null) {
            int width = Math.abs(e.getX() - currentRectangle.x);
            int height = Math.abs(e.getY() - currentRectangle.y);
            int x = Math.min(e.getX(), currentRectangle.x);
            int y = Math.min(e.getY(), currentRectangle.y);
            SVGRectangle rect = new SVGRectangle(x, y, width, height);
            controller.addElement(rect);
            currentRectangle = null;
        }
    }

    @Override
    public Rectangle getCurrentShape() {
        return currentRectangle;
    }
}
