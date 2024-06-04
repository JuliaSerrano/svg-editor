package es.upm.pproject.geditor.utils;

import javax.swing.*;

import es.upm.pproject.geditor.model.SVGCircle;
import es.upm.pproject.geditor.model.SVGEllipse;
import es.upm.pproject.geditor.model.SVGLine;
import es.upm.pproject.geditor.model.SVGPath;
import es.upm.pproject.geditor.model.SVGPolygon;
import es.upm.pproject.geditor.model.SVGPolyline;
import es.upm.pproject.geditor.model.SVGRectangle;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

//TODO: refactor to remove duplications
public class DialogUtils {

    // Private constructor to prevent instantiation
    private DialogUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static SVGRectangle showRectangleDialog(Component parent, String title) {
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);
        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("X:"));
        panel.add(xField);
        panel.add(new JLabel("Y:"));
        panel.add(yField);
        panel.add(new JLabel("Width:"));
        panel.add(widthField);
        panel.add(new JLabel("Height:"));
        panel.add(heightField);

        int result = JOptionPane.showConfirmDialog(parent, panel, title, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            return new SVGRectangle(x, y, width, height, Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0);
        }
        return null;
    }

    public static SVGCircle showCircleDialog(Component parent, String title) {
        // Declare and initialize the UI components
        JTextField centerXField = new JTextField(5);
        JTextField centerYField = new JTextField(5);
        JTextField radiusField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 2));

        JLabel centerXLabel = new JLabel("Center X:");
        JLabel centerYLabel = new JLabel("Center Y:");
        JLabel radiusLabel = new JLabel("Radius:");

        // Add the components to the panel
        panel.add(centerXLabel);
        panel.add(centerXField);
        panel.add(centerYLabel);
        panel.add(centerYField);
        panel.add(radiusLabel);
        panel.add(radiusField);

        // Mostrar el cuadro de diálogo
        int result = JOptionPane.showConfirmDialog(parent, panel, title, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Obtener los valores ingresados por el usuario y convertirlos a enteros
            int centerX = Integer.parseInt(centerXField.getText());
            int centerY = Integer.parseInt(centerYField.getText());
            int radius = Integer.parseInt(radiusField.getText());
            // Dibujar el círculo utilizando el método drawCircle
            // Create the circle element
            return new SVGCircle(centerX, centerY, radius, Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0);
        }
        return null;

    }

    public static SVGEllipse showEllipseDialog(Component parent, String title) {
        // Create the panel to collect user input
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Center X:"));
        JTextField centerXField = new JTextField(5);
        panel.add(centerXField);
        panel.add(new JLabel("Center Y:"));
        JTextField centerYField = new JTextField(5);
        panel.add(centerYField);
        panel.add(new JLabel("Width:"));
        JTextField widthField = new JTextField(5);
        panel.add(widthField);
        panel.add(new JLabel("Height:"));
        JTextField heightField = new JTextField(5);
        panel.add(heightField);

        // Show the dialog
        int result = JOptionPane.showConfirmDialog(parent, panel, title, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Get the user input and convert it to integers
            int centerX = Integer.parseInt(centerXField.getText());
            int centerY = Integer.parseInt(centerYField.getText());
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            // Create and return the ellipse
            return new SVGEllipse(centerX, centerY, width, height, Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0);
        }
        return null;
    }

    public static SVGLine showLineDialog(Component parent, String title) {
        // Crear el cuadro de diálogo para ingresar las coordenadas de inicio y fin de
        // la línea
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Start X:"));
        JTextField startXField = new JTextField(5);
        panel.add(startXField);
        panel.add(new JLabel("Start Y:"));
        JTextField startYField = new JTextField(5);
        panel.add(startYField);
        panel.add(new JLabel("End X:"));
        JTextField endXField = new JTextField(5);
        panel.add(endXField);
        panel.add(new JLabel("End Y:"));
        JTextField endYField = new JTextField(5);
        panel.add(endYField);

        // Mostrar el cuadro de diálogo
        int result = JOptionPane.showConfirmDialog(null, panel, "Draw Line", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Obtener los valores ingresados por el usuario y convertirlos a enteros
            int startX = Integer.parseInt(startXField.getText());
            int startY = Integer.parseInt(startYField.getText());
            int endX = Integer.parseInt(endXField.getText());
            int endY = Integer.parseInt(endYField.getText());
            // Dibujar la línea utilizando el método drawLine
            return new SVGLine(startX, startY, endX, endY, Color.BLACK, 1.0, 1.0);

        }
        return null;
    }

    public static SVGPolyline showPolylineDialog(Component parent, String title) {
        // Crear el cuadro de diálogo para ingresar las coordenadas de los puntos de la
        // polilínea
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

        // Listener para agregar puntos a la polilínea
        addPointButton.addActionListener(e -> {
            int pointX = Integer.parseInt(pointXField.getText());
            int pointY = Integer.parseInt(pointYField.getText());
            points.add(new Point(pointX, pointY));
            pointXField.setText("");
            pointYField.setText("");
        });

        // Mostrar el cuadro de diálogo
        int result = JOptionPane.showConfirmDialog(null, panel, "Draw Polyline", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Convertir la lista de puntos a listas de coordenadas
            List<Integer> xPointsList = new ArrayList<>();
            List<Integer> yPointsList = new ArrayList<>();
            for (Point point : points) {
                xPointsList.add(point.x);
                yPointsList.add(point.y);
            }

            // Dibujar la polilínea utilizando el método drawPolyline
            return new SVGPolyline(xPointsList, yPointsList, Color.BLACK, 1.0, 1.0);
        }
        return null;
    }

    public static SVGPolygon showPolygonDialog(Component parent, String title) {
        // Create the panel to collect user input
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Vertex X:"));
        JTextField vertexXField = new JTextField(5);
        panel.add(vertexXField);
        panel.add(new JLabel("Vertex Y:"));
        JTextField vertexYField = new JTextField(5);
        panel.add(vertexYField);

        JButton addVertexButton = new JButton("Add Vertex");
        panel.add(addVertexButton);

        List<Point> vertices = new ArrayList<>();

        // Listener to add vertices to the polygon
        addVertexButton.addActionListener(e -> {
            int vertexX = Integer.parseInt(vertexXField.getText());
            int vertexY = Integer.parseInt(vertexYField.getText());
            vertices.add(new Point(vertexX, vertexY));
            vertexXField.setText("");
            vertexYField.setText("");
        });

        // Show the dialog box
        int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Convert the list of vertices to arrays of coordinates
            List<Integer> xPoints = new ArrayList<>();
            List<Integer> yPoints = new ArrayList<>();
            for (Point vertex : vertices) {
                xPoints.add((int) vertex.getX());
                yPoints.add((int) vertex.getY());
            }

            // Get the number of points
            int numPoints = vertices.size();

            // Draw the polygon using the SVGPolygon constructor
            return new SVGPolygon(xPoints, yPoints, numPoints, Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0);
        }
        return null;
    }

    public static SVGPath showPathDialog(Component parent, String title) {
        // Create the panel to collect user input
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Point X:"));
        JTextField pointXField = new JTextField(5);
        panel.add(pointXField);
        panel.add(new JLabel("Point Y:"));
        JTextField pointYField = new JTextField(5);
        panel.add(pointYField);

        JButton addPointButton = new JButton("Add Point");
        panel.add(addPointButton);

        // List to store the points of the path
        List<Point> points = new ArrayList<>();

        // Listener to add points to the path
        addPointButton.addActionListener(e -> {
            int pointX = Integer.parseInt(pointXField.getText());
            int pointY = Integer.parseInt(pointYField.getText());
            points.add(new Point(pointX, pointY));
            pointXField.setText("");
            pointYField.setText("");
        });

        // Show the dialog box
        int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Create a new Path2D object to store the path
            Path2D path = new Path2D.Double();

            // Set the first point of the path
            Point firstPoint = points.get(0);
            path.moveTo(firstPoint.getX(), firstPoint.getY());

            // Add the remaining points to the path
            for (int i = 1; i < points.size(); i++) {
                Point nextPoint = points.get(i);
                path.lineTo(nextPoint.getX(), nextPoint.getY());
            }

            // Return the SVGPath object
            return new SVGPath(0, 0, Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0, path);
        }
        return null;
    }

}
