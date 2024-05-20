package es.upm.pproject.geditor.view;

import javax.swing.*;
import java.awt.*;

import es.upm.pproject.geditor.controller.SVGEditorController;
// import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGDocument;
import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.view.ui.ShapeCreator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SVGCanvas extends JPanel {
    private SVGDocument document;
    private SVGEditorController controller;
    private ShapeCreator shapeCreator;

    public SVGCanvas(SVGEditorController controller) {
        this.controller = controller;
    }

    public void setDocument(SVGDocument document) {
        this.document = document;
        if (document != null) {
            setPreferredSize(new Dimension(document.getWidth(), document.getHeight()));
        }
    }

    public void createShape(ShapeCreator shapeCreator) {
        this.shapeCreator = shapeCreator;
    }

    public void finishShape() {
        this.shapeCreator = null;
    }

    public void setController(SVGEditorController controller) {
        this.controller = controller;
        addMouseListeners();
    }

    private void addMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                shapeCreator.startShape(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                shapeCreator.finishShape(e);
                repaint(); // Ensure repaint after finishing the shape
                // finishShape();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                shapeCreator.updateShape(e);
                repaint(); // Repaint during dragging to show the shape being drawn

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (document == null) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(document.getBackgroundColor());
        g2d.fillRect(0, 0, document.getWidth(), document.getHeight());
        for (SVGElement element : document.getElements()) {
            g2d.setColor(element.getFillColor());
            // g2d.fill(element.getShape());
            g2d.setColor(element.getStrokeColor());
            g2d.setStroke(new BasicStroke((float) element.getStrokeWidth()));
            g2d.draw(element.getShape());
        }

        // Draw the current shape if it exists
        if (shapeCreator != null) {
            Shape currentShape = shapeCreator.getCurrentShape();
            if (currentShape != null) {
                g2d.setColor(Color.BLACK); // Temporary shape color
                g2d.draw(currentShape);
            }
        }
        g2d.dispose();

    }

}