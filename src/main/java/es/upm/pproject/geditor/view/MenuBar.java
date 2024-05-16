package es.upm.pproject.geditor.view;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import es.upm.pproject.geditor.controller.ImageController;

public class MenuBar extends JMenuBar {

    ImageController controller;
    ImagePanel image;

    private boolean drawRectanglesEnabled = false;
    private boolean drawCirclesEnabled = false;
    private boolean drawLinesEnabled = false;
    private boolean drawPolygonsEnabled = false;
    private boolean drawPolylinesEnabled = false;
    private boolean drawPathsEnabled = false;
    

    public MenuBar(ImageController controller) {
        super();
        this.controller = controller;

        // Create the File menu
        JMenu fileMenu = new JMenu("File");
        this.add(fileMenu);

        // Add menu items to the File menu
        JMenuItem newImageItem = new JMenuItem("New Image");
        fileMenu.add(newImageItem);
        newImageItem.addActionListener(e -> showNewImageDialog());

        // Create the Edit menu
        JMenu editMenu = new JMenu("Edit");
        this.add(editMenu);

        // Add menu items to the Edit menu
        JMenuItem resizeItem = new JMenuItem("Resize");
        editMenu.add(resizeItem);
        resizeItem.addActionListener(e -> showResizeImageDialog());

        JMenuItem changeBgColorItem = new JMenuItem("Change Background Color");
        editMenu.add(changeBgColorItem);
        changeBgColorItem.addActionListener(e -> changeBackgroundColor());

        // Create the Figures Mouse menu
        JMenu figuresMouseMenu = new JMenu("Figures Mouse");
        this.add(figuresMouseMenu);

        JMenuItem rectangleMouseItem = new JMenuItem("Draw Rectangle");
        figuresMouseMenu.add(rectangleMouseItem);
        rectangleMouseItem.addActionListener(e -> setCurrentShape(Figure.RECTANGLE));

        JMenuItem circleMouseItem = new JMenuItem("Draw Circle/Ellipse");
        figuresMouseMenu.add(circleMouseItem);
        circleMouseItem.addActionListener(e -> setCurrentShape(Figure.CIRCLE));

        JMenuItem lineMouseItem = new JMenuItem("Draw Line");
        figuresMouseMenu.add(lineMouseItem);
        lineMouseItem.addActionListener(e -> setCurrentShape(Figure.LINE));

        JMenuItem polygonMouseItem = new JMenuItem("Draw Polygon");
        figuresMouseMenu.add(polygonMouseItem);
        polygonMouseItem.addActionListener(e -> setCurrentShape(Figure.POLYGON));
        
        JMenuItem polylineMouseItem = new JMenuItem("Draw Polyline");
        figuresMouseMenu.add(polylineMouseItem);
        polylineMouseItem.addActionListener(e -> setCurrentShape(Figure.POLYLINE));
        
        JMenuItem pathMouseItem = new JMenuItem("Draw Path");
        figuresMouseMenu.add(pathMouseItem);
        pathMouseItem.addActionListener(e -> setCurrentShape(Figure.PATH));

        JMenu figuresKeyMenu = new JMenu("Figures Key");
        this.add(figuresKeyMenu);

        JMenuItem rectangleKeyItem = new JMenuItem("Draw Rectangle");
        figuresKeyMenu.add(rectangleKeyItem);
        rectangleKeyItem.addActionListener(e -> showRectangleDialog());

        JMenuItem circleleKeyItem = new JMenuItem("Draw Circle");
        figuresKeyMenu.add(circleleKeyItem);
        circleleKeyItem.addActionListener(e -> showCircleDialog());

        JMenuItem ellipseKeyItem = new JMenuItem("Draw Ellipse");
        figuresKeyMenu.add(ellipseKeyItem);
        ellipseKeyItem.addActionListener(e -> showEllipseDialog());

        JMenuItem lineKeyItem = new JMenuItem("Draw Line");
        figuresKeyMenu.add(lineKeyItem);
        lineKeyItem.addActionListener(e -> showLineDialog());
        

        JMenuItem polygonKeyItem = new JMenuItem("Draw Polygon");
        figuresKeyMenu.add(polygonKeyItem);
        polygonKeyItem.addActionListener(e -> showPolygonDialog());
        
        
        JMenuItem polylineKeyItem = new JMenuItem("Draw Polyline");
        figuresKeyMenu.add(polylineKeyItem);
        polylineKeyItem.addActionListener(e -> showPolylineDialog());
        
        JMenuItem pathKeyItem = new JMenuItem("Draw Path");
        figuresKeyMenu.add(pathKeyItem);
        pathKeyItem.addActionListener(e -> showPathDialog());

    }

    private void showNewImageDialog() {
        showImageDialog("Enter Width and Height for New Image", true);
    }

    private void showResizeImageDialog() {
        showImageDialog("Enter New Width and Height to Resize Image", false);
    }

    private void showImageDialog(String dialogTitle, boolean restart) {
        // Create a panel to hold the input fields
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Width:"));
        JTextField widthField = new JTextField(5);
        panel.add(widthField);
        panel.add(new JLabel("Height:"));
        JTextField heightField = new JTextField(5);
        panel.add(heightField);

        // Show the dialog
        int result = JOptionPane.showConfirmDialog(null, panel, dialogTitle, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            controller.resizeImage(widthField.getText(), heightField.getText(), restart);
        }
    }

    private void changeBackgroundColor() {
        Color newColor = JColorChooser.showDialog(null, "Choose Background Color",
                controller.getView().getBackground());
        controller.changeBackgroundColor(newColor);
    }

    // FIgures

    private void showRectangleDialog() {
        // Crear el cuadro de diálogo para ingresar las coordenadas y dimensiones del
        // rectángulo
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("X:"));
        JTextField xField = new JTextField(5);
        panel.add(xField);
        panel.add(new JLabel("Y:"));
        JTextField yField = new JTextField(5);
        panel.add(yField);
        panel.add(new JLabel("Width:"));
        JTextField widthField = new JTextField(5);
        panel.add(widthField);
        panel.add(new JLabel("Height:"));
        JTextField heightField = new JTextField(5);
        panel.add(heightField);

        // Mostrar el cuadro de diálogo
        int result = JOptionPane.showConfirmDialog(null, panel, "Draw Rectangle", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Obtener los valores ingresados por el usuario y convertirlos a enteros
            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            // Dibujar el rectángulo utilizando el método drawRectangle
            controller.getView().drawRectangle(x, y, width, height);
        }
    }

    private void showCircleDialog() {
        // Crear el cuadro de diálogo para ingresar las coordenadas y el radio del
        // círculo
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Center X:"));
        JTextField centerXField = new JTextField(5);
        panel.add(centerXField);
        panel.add(new JLabel("Center Y:"));
        JTextField centerYField = new JTextField(5);
        panel.add(centerYField);
        panel.add(new JLabel("Radius:"));
        JTextField radiusField = new JTextField(5);
        panel.add(radiusField);

        // Mostrar el cuadro de diálogo
        int result = JOptionPane.showConfirmDialog(null, panel, "Draw Circle", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Obtener los valores ingresados por el usuario y convertirlos a enteros
            int centerX = Integer.parseInt(centerXField.getText());
            int centerY = Integer.parseInt(centerYField.getText());
            int radius = Integer.parseInt(radiusField.getText());
            // Dibujar el círculo utilizando el método drawCircle
            controller.getView().drawCircle(centerX, centerY, radius);
        }
    }

    private void showEllipseDialog() {
        // Crear el cuadro de diálogo para ingresar las coordenadas del centro, ancho y
        // altura de la elipse
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

        // Mostrar el cuadro de diálogo
        int result = JOptionPane.showConfirmDialog(null, panel, "Draw Ellipse", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Obtener los valores ingresados por el usuario y convertirlos a enteros
            int centerX = Integer.parseInt(centerXField.getText());
            int centerY = Integer.parseInt(centerYField.getText());
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            // Dibujar la elipse utilizando el método drawEllipse
            controller.getView().drawEllipse(centerX, centerY, width, height);
        }
    }

    private void showLineDialog() {
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
            controller.getView().drawLine(startX, startY, endX, endY);
        }
    }
    
    
    private void showPolygonDialog() {
        // Crear el cuadro de diálogo para ingresar las coordenadas de los vértices del polígono
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
        

        // Listener para agregar vértices al polígono
        addVertexButton.addActionListener(e -> {
            int vertexX = Integer.parseInt(vertexXField.getText());
            int vertexY = Integer.parseInt(vertexYField.getText());
            vertices.add(new Point(vertexX, vertexY));
            vertexXField.setText("");
            vertexYField.setText("");
        });

        // Mostrar el cuadro de diálogo
        int result = JOptionPane.showConfirmDialog(null, panel, "Draw Polygon", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Convertir la lista de vértices a un array de coordenadas
            int[] xPoints = vertices.stream().mapToInt(point -> (int)point.getX()).toArray();
            int[] yPoints = vertices.stream().mapToInt(point -> (int)point.getY()).toArray();

            int numVertices = vertices.size();
            // Dibujar el polígono utilizando el método drawPolygon
            controller.getView().drawPolygon(xPoints, yPoints, numVertices);
        }
    }
    
    
    private void showPolylineDialog() {
        // Crear el cuadro de diálogo para ingresar las coordenadas de los puntos de la polilínea
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
        int result = JOptionPane.showConfirmDialog(null, panel, "Draw Polyline", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Convertir la lista de puntos a un array de coordenadas
        	int[] xPoints = points.stream().mapToInt(point -> (int) point.getX()).toArray();
        	int[] yPoints = points.stream().mapToInt(point -> (int) point.getY()).toArray();

            int numPoints = points.size();
            // Dibujar la polilínea utilizando el método drawPolyline
            controller.getView().drawPolyline(xPoints, yPoints, numPoints);
        }
    }
    
    
    private void showPathDialog() {
        // Crear el cuadro de diálogo para ingresar las coordenadas de los puntos del path
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Point X:"));
        JTextField pointXField = new JTextField(5);
        panel.add(pointXField);
        panel.add(new JLabel("Point Y:"));
        JTextField pointYField = new JTextField(5);
        panel.add(pointYField);

        JButton addPointButton = new JButton("Add Point");
        panel.add(addPointButton);

        // Lista para almacenar los puntos del path
        List<Point> points = new ArrayList<>();

        // Listener para agregar puntos al path
        addPointButton.addActionListener(e -> {
            int pointX = Integer.parseInt(pointXField.getText());
            int pointY = Integer.parseInt(pointYField.getText());
            points.add(new Point(pointX, pointY));
            pointXField.setText("");
            pointYField.setText("");
        });

        // Mostrar el cuadro de diálogo
        int result = JOptionPane.showConfirmDialog(null, panel, "Draw Path", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Crear un nuevo objeto Path2D para almacenar el path
            Path2D path = new Path2D.Double();
            
            // Establecer el primer punto del path
            Point firstPoint = points.get(0);
            path.moveTo(firstPoint.getX(), firstPoint.getY());
            
            // Agregar los demás puntos al path
            for (int i = 1; i < points.size(); i++) {
                Point nextPoint = points.get(i);
                path.lineTo(nextPoint.getX(), nextPoint.getY());
            }

            // Dibujar el path utilizando el método drawPath
            controller.getView().drawPath(path);
        }
    }


    // ********************************************************************************

    public void setCurrentShape(Figure shape) {
        if (shape == Figure.RECTANGLE || shape == Figure.CIRCLE || shape == Figure.LINE ||
        		shape == Figure.POLYGON || shape == Figure.POLYLINE || shape == Figure.PATH) {
            if (shape == Figure.RECTANGLE) {
                drawRectanglesEnabled = !drawRectanglesEnabled;
                drawCirclesEnabled = false;
                drawLinesEnabled = false;
                drawPolygonsEnabled = false;
                drawPolylinesEnabled = false;
                drawPathsEnabled = false;
            } else if (shape == Figure.CIRCLE) {
                drawCirclesEnabled = !drawCirclesEnabled;
                drawRectanglesEnabled = false;
                drawLinesEnabled = false;
                drawPolygonsEnabled = false;
                drawPolylinesEnabled = false;
                drawPathsEnabled = false;
            } else if (shape == Figure.LINE) {
                drawLinesEnabled = !drawLinesEnabled;
                drawRectanglesEnabled = false;
                drawCirclesEnabled = false;
                drawPolygonsEnabled = false;
                drawPolylinesEnabled = false;
                drawPathsEnabled = false;
            } else if (shape == Figure.POLYGON) {
                drawPolygonsEnabled = !drawPolygonsEnabled;
                drawRectanglesEnabled = false;
                drawCirclesEnabled = false;
                drawLinesEnabled = false;
                drawPolylinesEnabled = false;
                drawPathsEnabled = false;
            }else if (shape == Figure.POLYLINE) {
            	drawPolylinesEnabled = !drawPolylinesEnabled;
                drawRectanglesEnabled = false;
                drawCirclesEnabled = false;
                drawLinesEnabled = false;
                drawPolygonsEnabled = false;
                drawPathsEnabled = false;
            } else if (shape == Figure.PATH) {
            	drawPathsEnabled = !drawPathsEnabled;
                drawRectanglesEnabled = false;
                drawCirclesEnabled = false;
                drawLinesEnabled = false;
                drawPolygonsEnabled = false;
                drawPolylinesEnabled = false;
                

            }
            controller.getView().setDrawRectanglesEnabled(drawRectanglesEnabled);
            controller.getView().setDrawCirclesEnabled(drawCirclesEnabled);
            controller.getView().setDrawLinesEnabled(drawLinesEnabled);
            controller.getView().setDrawPolygonsEnabled(drawPolygonsEnabled);
            controller.getView().setDrawPolylinesEnabled(drawPolylinesEnabled);
            controller.getView().setDrawPathsEnabled(drawPathsEnabled);
        }
    }

}