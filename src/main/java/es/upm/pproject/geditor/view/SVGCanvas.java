package es.upm.pproject.geditor.view;

import javax.swing.*;
import java.awt.*;

import es.upm.pproject.geditor.model.SVGDocument;
import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.view.ui.ShapeCreator;
import es.upm.pproject.geditor.view.ui.PolygonCreator;
import es.upm.pproject.geditor.view.ui.PolylineCreator;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class SVGCanvas extends JPanel {
    private transient SVGDocument document;
    private transient ShapeCreator shapeCreator;
    private ArrayList<SVGElement> selectedElements;
    private Point initialMousePoint;

    public enum Mode {
        DEFAULT,
        SELECT
    }

    private Mode mode = Mode.DEFAULT;

    public void setMode(Mode mode) {
        this.mode = mode;
        if (mode == Mode.DEFAULT) {
            selectedElements.clear();
            repaint();
        }
    }

    public SVGCanvas() {
        setFocusable(true);
        selectedElements = new ArrayList<>();
        addMouseListeners();
        addKeyBindings();
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
        for (SVGElement element : selectedElements) {
            element.setFillColor(newColor);
        }
        repaint();
    }

    public void changeSelectedElementFillOpacity(double fillOpacity) {
        for (SVGElement element : selectedElements) {
            element.setFillOpacity(fillOpacity);
        }
        repaint();
    }

    public void changeSelectedElementStrokeColor(Color color) {
        for (SVGElement element : selectedElements) {
            element.setStrokeColor(color);
        }
        repaint();
    }

    public void changeSelectedElementStrokeWidth(double strokeWidth) {
        for (SVGElement element : selectedElements) {
            element.setStrokeWidth(strokeWidth);
        }
        repaint();
    }

    public void changeSelectedElementStrokeOpacity(double strokeOpacity) {
        for (SVGElement element : selectedElements) {
            element.setStrokeOpacity(strokeOpacity);
        }
        repaint();
    }

    public void removeSelectedElement() {
        for (SVGElement element : selectedElements) {
            document.removeElement(element);
        }
        selectedElements.clear();
        repaint();
    }

    private void addMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow(); // Request focus
                if (mode == Mode.SELECT) {
                    finishShape();
                    if (e.isControlDown()) {
                        toggleElementAt(e.getPoint());
                    } else {
                        selectElementAt(e.getPoint());
                    }
                    initialMousePoint = e.getPoint();
                } else {
                    shapeCreator.startShape(e);
                    if (isPolyCreator(shapeCreator)) {
                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // if (mode == Mode.SELECT) {
                initialMousePoint = null;
                if (mode != Mode.SELECT && !isPolyCreator(shapeCreator)) {
                    shapeCreator.finishShape(e);
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mode == Mode.SELECT && initialMousePoint != null) {
                    Point currentMousePoint = e.getPoint();
                    double dx = currentMousePoint.getX() - initialMousePoint.getX();
                    double dy = currentMousePoint.getY() - initialMousePoint.getY();
                    moveSelectedElements(dx, dy);
                    initialMousePoint = currentMousePoint;
                    repaint();
                } else if (mode != Mode.SELECT && !isPolyCreator(shapeCreator)) {
                    shapeCreator.updateShape(e);
                    repaint();
                }
            }
        });
    }

    private void moveSelectedElements(double dx, double dy) {
        for (SVGElement element : selectedElements) {
            element.move(dx, dy);
        }
    }

    private void addKeyBindings() {
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedElements(0, -5); // Move up by 5 pixels
                repaint();
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedElements(0, 5); // Move down by 5 pixels
                repaint();
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedElements(-5, 0); // Move left by 5 pixels
                repaint();
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedElements(5, 0); // Move right by 5 pixels
                repaint();
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
                g2d.draw(element.getShape());

            }

            // Draw selection handle points
            if (selectedElements.contains(element)) {
                g2d.setColor(Color.RED);
                Rectangle bounds = element.getShape().getBounds();
                int handleSize = 6;
                g2d.fillRect(bounds.x - handleSize / 2, bounds.y - handleSize / 2, handleSize, handleSize);
                g2d.fillRect(bounds.x + bounds.width - handleSize / 2, bounds.y - handleSize / 2, handleSize,
                        handleSize);
                g2d.fillRect(bounds.x - handleSize / 2, bounds.y + bounds.height - handleSize / 2, handleSize,
                        handleSize);
                g2d.fillRect(bounds.x + bounds.width - handleSize / 2, bounds.y + bounds.height - handleSize / 2,
                        handleSize, handleSize);
            }
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
        final double tolerance = 5.0;

        for (SVGElement element : document.getElements()) {
            if (element.getShape() instanceof Line2D) {
                Line2D line = (Line2D) element.getShape();
                if (line.ptSegDist(point) <= tolerance) {
                    selectedElements.clear();
                    selectedElements.add(element);
                    repaint();
                    return;
                }
            } else if (element.getShape() instanceof Path2D) {
                Path2D path = (Path2D) element.getShape();
                if (path.contains(point)) {
                    selectedElements.clear();
                    selectedElements.add(element);
                    repaint();
                    return;
                }
            } else if (element.getShape().contains(point)) {
                selectedElements.clear();
                selectedElements.add(element);
                repaint();
                return;
            }
        }
        selectedElements.clear();
        repaint();
    }

    private void toggleElementAt(Point point) {
        if (document == null) {
            return;
        }
        final double tolerance = 5.0;

        for (SVGElement element : document.getElements()) {
            if (element.getShape() instanceof Line2D) {
                Line2D line = (Line2D) element.getShape();
                if (line.ptSegDist(point) <= tolerance) {
                    if (selectedElements.contains(element)) {
                        selectedElements.remove(element);
                    } else {
                        selectedElements.add(element);
                    }
                    repaint();
                    return;
                }
            } else if (element.getShape() instanceof Path2D) {
                Path2D path = (Path2D) element.getShape();
                if (path.contains(point)) {
                    if (selectedElements.contains(element)) {
                        selectedElements.remove(element);
                    } else {
                        selectedElements.add(element);
                    }
                    repaint();
                    return;
                }
            } else if (element.getShape().contains(point)) {
                if (selectedElements.contains(element)) {
                    selectedElements.remove(element);
                } else {
                    selectedElements.add(element);
                }
                repaint();
                return;
            }
        }
    }

}