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
    private SVGElement selectedElement;

    public enum Mode {
        DEFAULT,
        SELECT
    }

    private Mode mode = Mode.DEFAULT;

    public void setMode(Mode mode) {
        this.mode = mode;
        if (mode == Mode.DEFAULT) {
            selectedElement = null;
            repaint();
        }
    }

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

    public void changeSelectedElementFillColor(Color newColor) {
        if (selectedElement != null) {
            selectedElement.setFillColor(newColor);
            repaint();
        }
    }

    public void changeSelectedElementFillOpacity(double fillOpacity) {
        if (selectedElement != null) {
            selectedElement.setFillOpacity(fillOpacity);
            repaint();
        }
    }

    public void changeSelectedElementStrokeColor(Color color) {
        if (selectedElement != null) {
            selectedElement.setStrokeColor(color);
            repaint();
        }
    }

    public void changeSelectedElementStrokeWidth(double strokeWidth) {
        if (selectedElement != null) {
            selectedElement.setStrokeWidth(strokeWidth);
            repaint();
        }
    }

    public void changeSelectedElementStrokeOpacity(double strokeOpacity) {
        if (selectedElement != null) {
            selectedElement.setStrokeOpacity(strokeOpacity);
            repaint();
        }
    }

    private void addMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (mode == Mode.SELECT) {
                    selectElementAt(e.getPoint());
                } else {
                    shapeCreator.startShape(e);
                    if (isPolyCreator(shapeCreator)) {
                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (mode != Mode.SELECT && !isPolyCreator(shapeCreator)) {
                    shapeCreator.finishShape(e);
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mode != Mode.SELECT && !isPolyCreator(shapeCreator)) {
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

            // Fill color, opacity
            if (element.getFillColor() != null) {
                Color fillColor = new Color(
                        element.getFillColor().getRed(),
                        element.getFillColor().getGreen(),
                        element.getFillColor().getBlue(),
                        (int) (element.getFillOpacity() * 255));
                g2d.setColor(fillColor);
                g2d.fill(element.getShape());
            }

            // Stroke color, stroke opacity, and stroke width
            if (element.getStrokeColor() != null) {
                System.out.println("Stroke opacity: " + element.getStrokeOpacity());
                Color strokeColor = new Color(
                        element.getStrokeColor().getRed(),
                        element.getStrokeColor().getGreen(),
                        element.getStrokeColor().getBlue(),
                        (int) (element.getStrokeOpacity() * 255));
                g2d.setColor(strokeColor);
                g2d.setStroke(new BasicStroke(
                        (float) element.getStrokeWidth(),
                        BasicStroke.CAP_BUTT, // TODO:?
                        BasicStroke.JOIN_MITER, // TODO:?
                        10.0f // TODO:? Miter limit (default value)
                ));
            }

            // TODO: otra forma de mostrar que un elemento esta seleccionado para que se vea
            // si se cambia stroke
            if (element == selectedElement) {
                // Draw selection highlight
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(3));
                g2d.draw(element.getShape());
            }

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

    private void selectElementAt(Point point) {
        if (document == null) {
            return;
        }

        for (SVGElement element : document.getElements()) {
            if (element.getShape().contains(point)) {
                selectedElement = element;
                repaint();
                return;
            }
        }
        selectedElement = null;
        repaint();
    }
}