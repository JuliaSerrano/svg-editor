package es.upm.pproject.geditor.view;

import javax.swing.*;
import java.awt.*;

import es.upm.pproject.geditor.model.SVGDocument;
import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.view.ui.ShapeCreator;
import es.upm.pproject.geditor.view.ui.PolygonCreator;
import es.upm.pproject.geditor.view.ui.PolylineCreator;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SVGCanvas extends JPanel {
    private transient SVGDocument document;
    private transient ShapeCreator shapeCreator;

    public SVGCanvas() {
        addMouseListeners();
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

    private void addMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                shapeCreator.startShape(e);
                if (isPolyCreator(shapeCreator)) {
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!isPolyCreator(shapeCreator)) {
                    shapeCreator.finishShape(e);
                    repaint(); 
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!isPolyCreator(shapeCreator)) {
                    shapeCreator.updateShape(e);
                    repaint();
                }
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

    private boolean isPolyCreator(ShapeCreator shapeCreator) {
        return (shapeCreator instanceof PolygonCreator || shapeCreator instanceof PolylineCreator);
    }

}