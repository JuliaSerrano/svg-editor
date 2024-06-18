package es.upm.pproject.geditor.utils;

import javax.swing.*;
import es.upm.pproject.geditor.model.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class DialogUtils {
    private DialogUtils() {
        throw new UnsupportedOperationException("Utility class");
    }
    private static final String X_LABEL = "X:";
    private static final String Y_LABEL = "Y:";
    private static final String WIDTH_LABEL = "Width:";
    private static final String HEIGHT_LABEL = "Height:";
    private static final String CENTER_X_LABEL = "Center X:";
    private static final String CENTER_Y_LABEL = "Center Y:";
    private static final String RADIUS_LABEL = "Radius:";
    private static final String START_X_LABEL = "Start X:";
    private static final String START_Y_LABEL = "Start Y:";
    private static final String END_X_LABEL = "End X:";
    private static final String END_Y_LABEL = "End Y:";

    private static SVGElement showShapeDialog(Component parent, String title, JPanel panel, String shapeType) {
        int result = JOptionPane.showConfirmDialog(parent, panel, title, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return createSVGElement(panel, shapeType);
        }
        return null;
    }

    private static SVGElement createSVGElement(JPanel panel, String shapeType) {
        Component[] components = panel.getComponents();
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;
        int radius = 0;
        int centerX = 0;
        int centerY = 0;
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;

        try {
            switch (shapeType) {
                case "Rectangle":
                    x = getIntValueFromComponent(components, X_LABEL);
                    y = getIntValueFromComponent(components, Y_LABEL);
                    width = getIntValueFromComponent(components, WIDTH_LABEL);
                    height = getIntValueFromComponent(components, HEIGHT_LABEL);
                    return new SVGRectangle(x, y, width, height);
                case "Circle":
                    centerX = getIntValueFromComponent(components, CENTER_X_LABEL);
                    centerY = getIntValueFromComponent(components, CENTER_Y_LABEL);
                    radius = getIntValueFromComponent(components, RADIUS_LABEL);
                    return new SVGCircle(centerX, centerY, radius);
                case "Ellipse":
                    centerX = getIntValueFromComponent(components, CENTER_X_LABEL);
                    centerY = getIntValueFromComponent(components, CENTER_Y_LABEL);
                    width = getIntValueFromComponent(components, WIDTH_LABEL);
                    height = getIntValueFromComponent(components, HEIGHT_LABEL);
                    return new SVGEllipse(centerX, centerY, width, height);
                case "Line":
                    startX = getIntValueFromComponent(components, START_X_LABEL);
                    startY = getIntValueFromComponent(components, START_Y_LABEL);
                    endX = getIntValueFromComponent(components, END_X_LABEL);
                    endY = getIntValueFromComponent(components, END_Y_LABEL);
                    return new SVGLine(startX, startY, endX, endY);
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private static int getIntValueFromComponent(Component[] components, String label) {
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JLabel && ((JLabel) components[i]).getText().equals(label)) {
                JTextField textField = (JTextField) components[i + 1];
                return Integer.parseInt(textField.getText());
            }
        }
        throw new IllegalArgumentException("Label not found: " + label);
    }

    
    public static SVGRectangle showRectangleDialog(Component parent, String title) {
        JPanel panel = createShapePanel(new String[] { X_LABEL, Y_LABEL, WIDTH_LABEL, HEIGHT_LABEL });
        return (SVGRectangle) showShapeDialog(parent, title, panel, "Rectangle");
    }

    
    public static SVGCircle showCircleDialog(Component parent, String title) {
        JPanel panel = createShapePanel(new String[] { CENTER_X_LABEL, CENTER_Y_LABEL, RADIUS_LABEL });
        return (SVGCircle) showShapeDialog(parent, title, panel, "Circle");
    }

   
   public static SVGEllipse showEllipseDialog(Component parent, String title) {
        JPanel panel = createShapePanel(new String[] { CENTER_X_LABEL, CENTER_Y_LABEL, WIDTH_LABEL, HEIGHT_LABEL });
        return (SVGEllipse) showShapeDialog(parent, title, panel, "Ellipse");
    }

    
   public static SVGLine showLineDialog(Component parent, String title) {
        JPanel panel = createShapePanel(new String[] { START_X_LABEL, START_Y_LABEL, END_X_LABEL, END_Y_LABEL });
        return (SVGLine) showShapeDialog(parent, title, panel, "Line");
    }

    private static JPanel createShapePanel(String[] labels) {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        for (String label : labels) {
            panel.add(new JLabel(label));
            panel.add(new JTextField(5));
        }
        return panel;
    }

    public static SVGPolyline showPolylineDialog(Component parent, String title) {
        return (SVGPolyline) showPointBasedShapeDialog(parent, title, "Polyline");
    }

    public static SVGPolygon showPolygonDialog(Component parent, String title) {
        return (SVGPolygon) showPointBasedShapeDialog(parent, title, "Polygon");
    }

    public static SVGPath showPathDialog(Component parent, String title) {
        return (SVGPath) showPointBasedShapeDialog(parent, title, "Path");
    }

    private static SVGElement showPointBasedShapeDialog(Component parent, String title, String shapeType) {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Point X:"));
        JTextField pointXField = new JTextField(5);
        panel.add(pointXField);
        panel.add(new JLabel("Point Y:"));
        JTextField pointYField = new JTextField(5);
        panel.add(pointYField);
        JButton addPointButton = new JButton("Add Point");
        panel.add(addPointButton);

        List<Point> points = new ArrayList<>();
        addPointButton.addActionListener(e -> {
            int pointX = Integer.parseInt(pointXField.getText());
            int pointY = Integer.parseInt(pointYField.getText());
            points.add(new Point(pointX, pointY));
            pointXField.setText("");
            pointYField.setText("");
        });

        int result = JOptionPane.showConfirmDialog(parent, panel, title, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return createPointBasedSVGElement(points, shapeType);
        }
        return null;
    }

    private static SVGElement createPointBasedSVGElement(List<Point> points, String shapeType) {
        List<Integer> xPoints = new ArrayList<>();
        List<Integer> yPoints = new ArrayList<>();
        for (Point point : points) {
            xPoints.add((int) point.getX());
            yPoints.add((int) point.getY());
        }

        switch (shapeType) {
            case "Polyline":
                return new SVGPolyline(xPoints, yPoints);
            case "Polygon":
                return new SVGPolygon(xPoints, yPoints, points.size());
            case "Path":
                Path2D path = new Path2D.Double();
                Point firstPoint = points.get(0);
                path.moveTo(firstPoint.getX(), firstPoint.getY());
                for (int i = 1; i < points.size(); i++) {
                    Point nextPoint = points.get(i);
                    path.lineTo(nextPoint.getX(), nextPoint.getY());
                }
                return new SVGPath(0, 0, path);
            default:
                return null;
        }
    }
}