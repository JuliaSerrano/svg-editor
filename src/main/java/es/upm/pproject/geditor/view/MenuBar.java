package es.upm.pproject.geditor.view;

import javax.swing.*;

import java.awt.*;
import es.upm.pproject.geditor.controller.ImageController;

public class MenuBar extends JMenuBar {

    ImageController controller;
    ImagePanel image;

    private boolean drawRectanglesEnabled = false;
    private boolean drawCirclesEnabled = false;
    private boolean drawLinesEnabled = false;
    private boolean drawPolygonsEnabled = false;

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

    // ********************************************************************************

    public void setCurrentShape(Figure shape) {
        if (shape == Figure.RECTANGLE || shape == Figure.CIRCLE || shape == Figure.LINE || shape == Figure.POLYGON) {
            if (shape == Figure.RECTANGLE) {
                drawRectanglesEnabled = !drawRectanglesEnabled;
                drawCirclesEnabled = false;
                drawLinesEnabled = false;
                drawPolygonsEnabled = false;
            } else if (shape == Figure.CIRCLE) {
                drawCirclesEnabled = !drawCirclesEnabled;
                drawRectanglesEnabled = false;
                drawLinesEnabled = false;
                drawPolygonsEnabled = false;
            } else if (shape == Figure.LINE) {
                drawLinesEnabled = !drawLinesEnabled;
                drawRectanglesEnabled = false;
                drawCirclesEnabled = false;
                drawPolygonsEnabled = false;
            } else if (shape == Figure.POLYGON) {
                drawPolygonsEnabled = !drawPolygonsEnabled;
                drawRectanglesEnabled = false;
                drawCirclesEnabled = false;
                drawLinesEnabled = false;

            }
            controller.getView().setDrawRectanglesEnabled(drawRectanglesEnabled);
            controller.getView().setDrawCirclesEnabled(drawCirclesEnabled);
            controller.getView().setDrawLinesEnabled(drawLinesEnabled);
            controller.getView().setDrawPolygonsEnabled(drawPolygonsEnabled);
        }
    }

}