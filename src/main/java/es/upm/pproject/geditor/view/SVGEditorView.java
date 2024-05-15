package es.upm.pproject.geditor.view;

import javax.swing.*;

import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGDocument;

import java.awt.*;
import java.awt.event.ActionEvent;

public class SVGEditorView extends JFrame {
    private SVGCanvas canvas;
    private SVGEditorController controller;
    private SVGDocument model;

    public SVGEditorView() {
        setTitle("SVG Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        canvas = new SVGCanvas();
        add(canvas, BorderLayout.CENTER);

        // add menu
        JMenuBar menuBar = new JMenuBar();

        // file section
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem resizeMenuItem = new JMenuItem("Resize");
        JMenuItem bgColorMenuItem = new JMenuItem("Change Background Color");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem undoMenuItem = new JMenuItem("Undo");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(newMenuItem);
        fileMenu.add(resizeMenuItem);
        fileMenu.add(bgColorMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(undoMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        // shape section
        JMenu shapeMenu = new JMenu("Add Shape");
        JMenuItem rectangleMenuItem = new JMenuItem("Rectangle");
        JMenuItem circleMenuItem = new JMenuItem("Circle");
        JMenuItem lineMenuItem = new JMenuItem("Line");

        shapeMenu.add(rectangleMenuItem);
        shapeMenu.add(circleMenuItem);
        shapeMenu.add(lineMenuItem);
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

        // resize an existing image
        JMenuItem resizeMenuItem = getJMenuBar().getMenu(0).getItem(1);
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
        JMenuItem backgroundColorMenuItem = getJMenuBar().getMenu(0).getItem(2);
        backgroundColorMenuItem.addActionListener((ActionEvent e) -> {
            // TODO: fix bg color retrieval
            // Color currentColor = model.getBackgroundColor();

            Color newColor = JColorChooser.showDialog(null, "Choose Background Color", Color.BLUE);
            if (newColor != null) {
                controller.changeBackgroundColor(newColor);
            }
        });

    }
}
