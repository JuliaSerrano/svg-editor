package es.upm.pproject.geditor.view;

import javax.swing.*;

import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGCircle;
import es.upm.pproject.geditor.model.SVGDocument;
import es.upm.pproject.geditor.model.SVGEllipse;
import es.upm.pproject.geditor.model.SVGLine;
import es.upm.pproject.geditor.model.SVGModel;
import es.upm.pproject.geditor.model.SVGPath;
import es.upm.pproject.geditor.model.SVGPolygon;
import es.upm.pproject.geditor.model.SVGPolyline;
import es.upm.pproject.geditor.model.SVGRectangle;
import es.upm.pproject.geditor.utils.DialogUtils;

import java.awt.*;
import java.awt.event.ActionEvent;

public class SVGEditorView extends JFrame {
    private SVGCanvas canvas;
    private SVGEditorController controller;
    // private SVGModel model;
    private SVGDocument document;

    public SVGEditorView(SVGModel model) {
        // this.model = model;
        this.document = model.getDocument();
        controller = new SVGEditorController(model, this); // Initialize
                                                           // controller

        setTitle("SVG Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(this.document.getWidth(), this.document.getHeight()));

        canvas = new SVGCanvas();
        canvas.setController(controller); // Set controller for canvas
        add(canvas, BorderLayout.CENTER);

        // add menu
        JMenuBar menuBar = new JMenuBar();

        // file section
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        // edit section
        JMenu editMenu = new JMenu("Edit");
        JMenuItem resizeMenuItem = new JMenuItem("Resize");
        JMenuItem bgColorMenuItem = new JMenuItem("Change Background Color");
        JMenuItem undoMenuItem = new JMenuItem("Undo");

        editMenu.add(resizeMenuItem);
        editMenu.add(bgColorMenuItem);
        editMenu.add(undoMenuItem);
        menuBar.add(editMenu);

        // shape section
        JMenu shapeMenu = new JMenu("Add Shape");
        JMenuItem rectangleMenuItem = new JMenuItem("Rectangle");
        JMenuItem circleMenuItem = new JMenuItem("Circle");
        JMenuItem ellipseMenuItem = new JMenuItem("Ellipse");
        JMenuItem lineMenuItem = new JMenuItem("Line");
        JMenuItem polylineMenuItem = new JMenuItem("Polyline");
        JMenuItem polygonMenuItem = new JMenuItem("Polygon");
        JMenuItem pathMenuItem = new JMenuItem("Path");

        shapeMenu.add(rectangleMenuItem);
        shapeMenu.add(circleMenuItem);
        shapeMenu.add(ellipseMenuItem);
        shapeMenu.add(lineMenuItem);
        shapeMenu.add(polylineMenuItem);
        shapeMenu.add(polygonMenuItem);
        shapeMenu.add(pathMenuItem);
        menuBar.add(shapeMenu);

        setJMenuBar(menuBar);
        addListeners();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setController(SVGEditorController controller) {
        this.controller = controller;
    }

    public void updateCanvas(SVGDocument document) {
        canvas.setDocument(document);
        canvas.repaint();
    }

    private void addListeners() {
        // New image action listener
        JMenuItem newMenuItem = getJMenuBar().getMenu(0).getItem(0);
        newMenuItem.addActionListener((ActionEvent e) -> {
            int newWidth = Integer.parseInt(JOptionPane.showInputDialog("Enter width:"));
            int newHeight = Integer.parseInt(JOptionPane.showInputDialog("Enter height:"));
            controller.createNewImage(newWidth, newHeight);
        });

        // resize an existing image action listener
        JMenuItem resizeMenuItem = getJMenuBar().getMenu(1).getItem(0);
        resizeMenuItem.addActionListener((ActionEvent e) -> {
            try {
                int newWidth = Integer.parseInt(JOptionPane.showInputDialog("Enter new width:"));
                int newHeight = Integer.parseInt(JOptionPane.showInputDialog("Enter new height:"));
                if (newWidth > 0 && newHeight > 0) {
                    controller.resizeImage(newWidth, newHeight);
                } else {
                    JOptionPane.showMessageDialog(null, "Dimensions must be positive numbers.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for width and height.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Background color change action listener
        JMenuItem backgroundColorMenuItem = getJMenuBar().getMenu(1).getItem(1);
        backgroundColorMenuItem.addActionListener((ActionEvent e) -> {
            Color currentColor = this.document.getBackgroundColor();
            Color newColor = JColorChooser.showDialog(null, "Choose Background Color", currentColor);
            if (newColor != null) {
                controller.changeBackgroundColor(newColor);
            }
        });

        // Shapes listeners
        // Rectangle listener
        JMenuItem rectangleShapeMenuItem = getJMenuBar().getMenu(2).getItem(0);
        rectangleShapeMenuItem.addActionListener((ActionEvent e) -> {
            // showRectangleDialog();
            SVGRectangle rect = DialogUtils.showRectangleInputDialog(this, "Create Rectangle");
            if (rect != null) {
                // Call the controller to add the rectangle
                controller.addElement(rect);
            }
        });

        // Circle listener
        JMenuItem circleMenuItem = getJMenuBar().getMenu(2).getItem(1);
        circleMenuItem.addActionListener((ActionEvent e) -> {
            SVGCircle circle = DialogUtils.showCircleDialog(this, "Create Circle");
            if (circle != null) {
                // Call the controller to add the circle
                controller.addElement(circle);
            }
        });

        // Ellipse listener
        // Ellipse listener
        JMenuItem ellipseMenuItem = getJMenuBar().getMenu(2).getItem(2);
        ellipseMenuItem.addActionListener((ActionEvent e) -> {
            SVGEllipse ellipse = DialogUtils.showEllipseDialog(this, "Create Ellipse");
            if (ellipse != null) {
                controller.addElement(ellipse);
            }
        });

        // Line listener
        JMenuItem lineMenuItem = getJMenuBar().getMenu(2).getItem(3);
        lineMenuItem.addActionListener((ActionEvent e) -> {
            SVGLine line = DialogUtils.showLineDialog(this, "Create Line");
            if (line != null) {
                controller.addElement(line);
            }
        });

        // Polyline listener
        JMenuItem polylineMenuItem = getJMenuBar().getMenu(2).getItem(4);
        polylineMenuItem.addActionListener((ActionEvent e) -> {
            SVGPolyline polyline = DialogUtils.showPolylineDialog(this, "Create Polyline");
            if (polyline != null) {
                controller.addElement(polyline);
            }
        });

        // Polygon listener
        JMenuItem polygonMenuItem = getJMenuBar().getMenu(2).getItem(5);
        polygonMenuItem.addActionListener((ActionEvent e) -> {
            SVGPolygon polygon = DialogUtils.showPolygonDialog(this, "Create Polygon");
            if (polygon != null) {
                controller.addElement(polygon);
            }
        });

        // Path listener
        JMenuItem pathMenuItem = getJMenuBar().getMenu(2).getItem(6);
        pathMenuItem.addActionListener((ActionEvent e) -> {
            SVGPath path = DialogUtils.showPathDialog(this, "Create Path");
            if (path != null) {
                controller.addElement(path);
            }
        });

    }
}
