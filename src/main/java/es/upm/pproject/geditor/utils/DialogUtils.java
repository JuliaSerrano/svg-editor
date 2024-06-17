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

        switch (shapeType) {
            case "Rectangle":
                int x = Integer.parseInt(((JTextField) components[1]).getText());
                int y = Integer.parseInt(((JTextField) components[3]).getText());
                int width = Integer.parseInt(((JTextField) components[5]).getText());
                int height = Integer.parseInt(((JTextField) components[7]).getText());
                return new SVGRectangle(x, y, width, height, Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0);
            case "Circle":
                int centerXCircle = Integer.parseInt(((JTextField) components[1]).getText());
                int centerYCircle = Integer.parseInt(((JTextField) components[3]).getText());
                int radius = Integer.parseInt(((JTextField) components[5]).getText());
                return new SVGCircle(centerXCircle, centerYCircle, radius, Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0);
            case "Ellipse":
                int centerXEll = Integer.parseInt(((JTextField) components[1]).getText());
                int centerYEll = Integer.parseInt(((JTextField) components[3]).getText());
                int widthEll = Integer.parseInt(((JTextField) components[5]).getText());
                int heightEll = Integer.parseInt(((JTextField) components[7]).getText());
                return new SVGEllipse(centerXEll, centerYEll, widthEll, heightEll, Color.BLACK, 1.0, Color.BLACK, 1.0,
                        1.0);
            case "Line":
                int startX = Integer.parseInt(((JTextField) components[1]).getText());
                int startY = Integer.parseInt(((JTextField) components[3]).getText());
                int endX = Integer.parseInt(((JTextField) components[5]).getText());
                int endY = Integer.parseInt(((JTextField) components[7]).getText());
                return new SVGLine(startX, startY, endX, endY, Color.BLACK, 1.0, 1.0);
            default:
                return null;
        }
    }

    public static SVGRectangle showRectangleDialog(Component parent, String title) {
        JPanel panel = createShapePanel(new String[] { "X:", "Y:", "Width:", "Height:" });
        return (SVGRectangle) showShapeDialog(parent, title, panel, "Rectangle");
    }

    public static SVGCircle showCircleDialog(Component parent, String title) {
        JPanel panel = createShapePanel(new String[] { "Center X:", "Center Y:", "Radius:" });
        return (SVGCircle) showShapeDialog(parent, title, panel, "Circle");
    }

    public static SVGEllipse showEllipseDialog(Component parent, String title) {
        JPanel panel = createShapePanel(new String[] { "Center X:", "Center Y:", "Width:", "Height:" });
        return (SVGEllipse) showShapeDialog(parent, title, panel, "Ellipse");
    }

    public static SVGLine showLineDialog(Component parent, String title) {
        JPanel panel = createShapePanel(new String[] { "Start X:", "Start Y:", "End X:", "End Y:" });
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
                return new SVGPolyline(xPoints, yPoints, Color.BLACK, 1.0, 1.0);
            case "Polygon":
                return new SVGPolygon(xPoints, yPoints, points.size(), Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0);
            case "Path":
                Path2D path = new Path2D.Double();
                Point firstPoint = points.get(0);
                path.moveTo(firstPoint.getX(), firstPoint.getY());
                for (int i = 1; i < points.size(); i++) {
                    Point nextPoint = points.get(i);
                    path.lineTo(nextPoint.getX(), nextPoint.getY());
                }
                return new SVGPath(0, 0, null, 1.0, Color.BLACK, 1.0, 1.0, path);
            default:
                return null;
        }
    }
}