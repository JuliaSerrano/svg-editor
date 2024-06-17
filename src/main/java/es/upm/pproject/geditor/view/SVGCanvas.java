package es.upm.pproject.geditor.view;

import javax.swing.*;
import java.awt.*;

import es.upm.pproject.geditor.model.SVGDocument;
import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.model.SVGGroup;
import es.upm.pproject.geditor.controller.SVGEditorController;
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
import java.util.List;

public class SVGCanvas extends JPanel {
    private transient SVGEditorController controller;
    private transient SVGDocument document;
    private transient ShapeCreator shapeCreator;
    private transient ArrayList<SVGElement> selectedElements;
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

    public SVGCanvas(SVGEditorController controller) {
        this.controller = controller;
        setFocusable(true);
        selectedElements = new ArrayList<>();
        addMouseListeners();
        addKeyBindings();
    }

    public List<SVGElement> getSelectedElements() {
        return selectedElements;
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
                    controller.moveSelectedElements(selectedElements, dx, dy);
                    initialMousePoint = currentMousePoint;
                } else if (mode != Mode.SELECT && !isPolyCreator(shapeCreator)) {
                    shapeCreator.updateShape(e);
                    repaint();
                }
            }
        });
    }

    private void addKeyBindings() {
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.moveSelectedElements(selectedElements, 0, -5); // Move up by 5 pixels
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.moveSelectedElements(selectedElements, 0, 5); // Move down by 5 pixels
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.moveSelectedElements(selectedElements, -5, 0); // Move left by 5 pixels
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.moveSelectedElements(selectedElements, 5, 0); // Move right by 5 pixels
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
            paintElement(g2d, element);
        }

        // Draw selection handle points for selected elements or groups
        for (SVGElement element : selectedElements) {
            if (element instanceof SVGGroup) {
                drawGroupSelectionHandles(g2d, (SVGGroup) element);
            } else {
                drawSelectionHandles(g2d, element);
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

    private void paintElement(Graphics2D g2d, SVGElement element) {
        if (element instanceof SVGGroup) {
            SVGGroup group = (SVGGroup) element;
            for (SVGElement child : group.getElements()) {
                paintElement(g2d, child);
            }
        } else {
            if (element.getFillColor() != null) {
                Color fillColor = new Color(
                        element.getFillColor().getRed(),
                        element.getFillColor().getGreen(),
                        element.getFillColor().getBlue(),
                        (int) (element.getFillOpacity() * 255));
                g2d.setColor(fillColor);
                g2d.fill(element.getShape());
            }

            if (element.getStrokeColor() != null) {
                Color strokeColor = new Color(
                        element.getStrokeColor().getRed(),
                        element.getStrokeColor().getGreen(),
                        element.getStrokeColor().getBlue(),
                        (int) (element.getStrokeOpacity() * 255));
                g2d.setColor(strokeColor);
                g2d.setStroke(new BasicStroke(
                        (float) element.getStrokeWidth(),
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f));
                g2d.draw(element.getShape());
            }
        }
    }

    private void drawSelectionHandles(Graphics2D g2d, SVGElement element) {
        Rectangle bounds = element.getShape().getBounds();
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] { 5 }, 0));
        g2d.draw(bounds);

        int handleSize = 6;
        g2d.setColor(Color.BLUE);
        g2d.fillRect(bounds.x - handleSize / 2, bounds.y - handleSize / 2, handleSize, handleSize);
        g2d.fillRect(bounds.x + bounds.width - handleSize / 2, bounds.y - handleSize / 2, handleSize, handleSize);
        g2d.fillRect(bounds.x - handleSize / 2, bounds.y + bounds.height - handleSize / 2, handleSize, handleSize);
        g2d.fillRect(bounds.x + bounds.width - handleSize / 2, bounds.y + bounds.height - handleSize / 2, handleSize,
                handleSize);
    }

    private void drawGroupSelectionHandles(Graphics2D g2d, SVGGroup group) {
        Rectangle groupBounds = getGroupBounds(group);
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] { 5 }, 0));
        g2d.draw(groupBounds);

        int handleSize = 6;
        g2d.setColor(Color.BLUE);
        g2d.fillRect(groupBounds.x - handleSize / 2, groupBounds.y - handleSize / 2, handleSize, handleSize);
        g2d.fillRect(groupBounds.x + groupBounds.width - handleSize / 2, groupBounds.y - handleSize / 2, handleSize,
                handleSize);
        g2d.fillRect(groupBounds.x - handleSize / 2, groupBounds.y + groupBounds.height - handleSize / 2, handleSize,
                handleSize);
        g2d.fillRect(groupBounds.x + groupBounds.width - handleSize / 2,
                groupBounds.y + groupBounds.height - handleSize / 2, handleSize, handleSize);
    }

    private Rectangle getGroupBounds(SVGGroup group) {
        Rectangle groupBounds = null;
        for (SVGElement element : group.getElements()) {
            if (groupBounds == null) {
                groupBounds = element.getShape().getBounds();
            } else {
                groupBounds = groupBounds.union(element.getShape().getBounds());
            }
        }
        return groupBounds;
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
            if (element instanceof SVGGroup) {
                SVGGroup group = (SVGGroup) element;
                for (SVGElement groupElement : group.getElements()) {
                    if (elementContainsPoint(groupElement, point, tolerance)) {
                        selectGroup(group);
                        repaint();
                        return;
                    }
                }
            } else if (elementContainsPoint(element, point, tolerance)) {
                selectedElements.clear();
                selectedElements.add(element);
                repaint();
                return;
            }
        }
        selectedElements.clear();
        repaint();
    }

    private void selectGroup(SVGGroup group) {
        selectedElements.clear();
        selectedElements.add(group);
        repaint();
    }

    private boolean elementContainsPoint(SVGElement element, Point point, double tolerance) {
        if (element.getShape() instanceof Line2D) {
            Line2D line = (Line2D) element.getShape();
            return line.ptSegDist(point) <= tolerance;
        } else if (element.getShape() instanceof Path2D) {
            Path2D path = (Path2D) element.getShape();
            return path.contains(point);
        } else {
            return element.getShape().contains(point);
        }
    }

    private void toggleElementAt(Point point) {
        if (document == null) {
            return;
        }
        final double tolerance = 5.0;

        for (SVGElement element : document.getElements()) {
            if (element instanceof SVGGroup) {
                SVGGroup group = (SVGGroup) element;
                for (SVGElement groupElement : group.getElements()) {
                    if (elementContainsPoint(groupElement, point, tolerance)) {
                        toggleGroupSelection(group);
                        repaint();
                        return;
                    }
                }
            } else if (elementContainsPoint(element, point, tolerance)) {
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

    private void toggleGroupSelection(SVGGroup group) {
        if (selectedElements.contains(group)) {
            selectedElements.remove(group);
        } else {
            selectedElements.add(group);
        }
        repaint();
    }
}