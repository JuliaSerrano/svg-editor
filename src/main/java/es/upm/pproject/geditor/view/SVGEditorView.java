package es.upm.pproject.geditor.view;

import javax.imageio.ImageIO;
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
import es.upm.pproject.geditor.view.ui.CircleCreator;
import es.upm.pproject.geditor.view.ui.EllipseCreator;
import es.upm.pproject.geditor.view.ui.LineCreator;
import es.upm.pproject.geditor.view.ui.PathCreator;
import es.upm.pproject.geditor.view.ui.PolygonCreator;
import es.upm.pproject.geditor.view.ui.PolylineCreator;
import es.upm.pproject.geditor.view.ui.RectangleCreator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class SVGEditorView extends JFrame {
    private SVGCanvas canvas;
    private transient SVGEditorController controller;
    private transient SVGDocument document;

    // Instance variables for menu items
    private JMenuItem rectangleByKeyboardMenuItem;
    private JMenuItem rectangleByMouseMenuItem;
    private JMenuItem circleByKeyboardMenuItem;
    private JMenuItem circleByMouseMenuItem;
    private JMenuItem ellipseByKeyboardMenuItem;
    private JMenuItem ellipseByMouseMenuItem;
    private JMenuItem lineByKeyboardMenuItem;
    private JMenuItem lineByMouseMenuItem;
    private JMenuItem polylineByKeyboardMenuItem;
    private JMenuItem polylineByMouseMenuItem;
    private JMenuItem polygonByKeyboardMenuItem;
    private JMenuItem polygonByMouseMenuItem;
    private JMenuItem pathByKeyboardMenuItem;
    private JMenuItem pathByMouseMenuItem;

    public SVGEditorView(SVGModel model) {
        this.document = model.getDocument();
        controller = new SVGEditorController(model, this); // Initialize
        // controller

        setTitle("SVG Editor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(this.document.getWidth(), this.document.getHeight()));

        canvas = new SVGCanvas();
        add(canvas, BorderLayout.CENTER);

        // Create the side panel with options
        JPanel optionsPanel = createToolBox();
        add(optionsPanel, BorderLayout.WEST);

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

        // Rectangle submenu
        final String byKeyboard = "By Keyboard";
        final String byMouse = "By Mouse";
        JMenu rectangleSubMenu = new JMenu("Rectangle");
        rectangleByKeyboardMenuItem = new JMenuItem(byKeyboard);
        rectangleByMouseMenuItem = new JMenuItem(byMouse);
        rectangleSubMenu.add(rectangleByKeyboardMenuItem);
        rectangleSubMenu.add(rectangleByMouseMenuItem);
        shapeMenu.add(rectangleSubMenu);

        // Circle submenu
        JMenu circleSubMenu = new JMenu("Circle");
        circleByKeyboardMenuItem = new JMenuItem(byKeyboard);
        circleByMouseMenuItem = new JMenuItem(byMouse);
        circleSubMenu.add(circleByKeyboardMenuItem);
        circleSubMenu.add(circleByMouseMenuItem);
        shapeMenu.add(circleSubMenu);

        // Ellipse submenu
        JMenu ellipseSubMenu = new JMenu("Ellipse");
        ellipseByKeyboardMenuItem = new JMenuItem(byKeyboard);
        ellipseByMouseMenuItem = new JMenuItem(byMouse);
        ellipseSubMenu.add(ellipseByKeyboardMenuItem);
        ellipseSubMenu.add(ellipseByMouseMenuItem);
        shapeMenu.add(ellipseSubMenu);

        // Line submenu
        JMenu lineSubMenu = new JMenu("Line");
        lineByKeyboardMenuItem = new JMenuItem(byKeyboard);
        lineByMouseMenuItem = new JMenuItem(byMouse);
        lineSubMenu.add(lineByKeyboardMenuItem);
        lineSubMenu.add(lineByMouseMenuItem);
        shapeMenu.add(lineSubMenu);

        // Polyline submenu
        JMenu polylineSubMenu = new JMenu("Polyline");
        polylineByKeyboardMenuItem = new JMenuItem(byKeyboard);
        polylineByMouseMenuItem = new JMenuItem(byMouse);
        polylineSubMenu.add(polylineByKeyboardMenuItem);
        polylineSubMenu.add(polylineByMouseMenuItem);
        shapeMenu.add(polylineSubMenu);

        // Polygon submenu
        JMenu polygonSubMenu = new JMenu("Polygon");
        polygonByKeyboardMenuItem = new JMenuItem(byKeyboard);
        polygonByMouseMenuItem = new JMenuItem(byMouse);
        polygonSubMenu.add(polygonByKeyboardMenuItem);
        polygonSubMenu.add(polygonByMouseMenuItem);
        shapeMenu.add(polygonSubMenu);

        // Path submenu
        JMenu pathSubMenu = new JMenu("Path");
        pathByKeyboardMenuItem = new JMenuItem(byKeyboard);
        pathByMouseMenuItem = new JMenuItem(byMouse);
        pathSubMenu.add(pathByKeyboardMenuItem);
        pathSubMenu.add(pathByMouseMenuItem);
        shapeMenu.add(pathSubMenu);

        menuBar.add(shapeMenu);

        setJMenuBar(menuBar);
        addActionListeners();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createToolBox() {
        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Tools"));
        optionsPanel.setPreferredSize(new Dimension(115, getHeight()));

        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setFloatable(false);

        JToggleButton selectButton = new JToggleButton("Select");
        selectButton.setToolTipText("Select");
        selectButton.addActionListener(e -> {
            if (selectButton.isSelected()) {
                selectButton.setText("In Selection");
                // Activate select mode in canvas
                canvas.setMode(SVGCanvas.Mode.SELECT);
            } else {
                selectButton.setText("Select");
                // Deactivate select mode in canvas or revert to default mode
                canvas.setMode(SVGCanvas.Mode.DEFAULT);
            }
        });

        toolBar.add(selectButton);
        optionsPanel.add(toolBar, BorderLayout.NORTH);

        return optionsPanel;
    }

    public void setController(SVGEditorController controller) {
        this.controller = controller;
    }

    public void updateCanvas(SVGDocument document) {
        canvas.setDocument(document);
        canvas.repaint();

    }

    public void resizeEditor(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        pack();
        setLocationRelativeTo(null);
    }

    private void addActionListeners() {
        // New image action listener
        JMenuItem newMenuItem = getJMenuBar().getMenu(0).getItem(0);
        newMenuItem.addActionListener((ActionEvent e) -> {
            int newWidth = Integer.parseInt(JOptionPane.showInputDialog("Enter width:"));
            int newHeight = Integer.parseInt(JOptionPane.showInputDialog("Enter height:"));
            controller.createNewImage(newWidth, newHeight);
        });

        editListeners();
        shapeListeners();
    }

    public void editListeners() {
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
    }

    public void shapeListeners() {

        // Action listeners for rectangle submenu items
        rectangleByKeyboardMenuItem.addActionListener(e -> {
            SVGRectangle rect = DialogUtils.showRectangleDialog(this, "Create Rectangle");
            if (rect != null) {
                controller.addElement(rect);
            }
        });
        rectangleByMouseMenuItem.addActionListener(e -> {
            RectangleCreator rectangleCreator = new RectangleCreator(controller);
            canvas.createShape(rectangleCreator);
        });

        // Action listeners for circle submenu items (keyboard and mouse)
        circleByKeyboardMenuItem.addActionListener(e -> {
            SVGCircle circle = DialogUtils.showCircleDialog(this, "Create Circle");
            if (circle != null) {
                controller.addElement(circle);
            }
        });

        circleByMouseMenuItem.addActionListener(e -> {
            CircleCreator circleCreator = new CircleCreator(controller);
            canvas.createShape(circleCreator);
        });

        // Action listeners for ellipse submenu items (keyboard and mouse)
        ellipseByKeyboardMenuItem.addActionListener(e -> {
            SVGEllipse ellipse = DialogUtils.showEllipseDialog(this, "Create Ellipse");
            if (ellipse != null) {
                controller.addElement(ellipse);
            }
        });

        ellipseByMouseMenuItem.addActionListener(e -> {
            EllipseCreator ellipseCreator = new EllipseCreator(controller);
            canvas.createShape(ellipseCreator);
        });

        // Action listeners for line submenu items (keyboard and mouse)
        lineByKeyboardMenuItem.addActionListener(e -> {
            SVGLine line = DialogUtils.showLineDialog(this, "Create Line");
            if (line != null) {
                controller.addElement(line);
            }
        });

        lineByMouseMenuItem.addActionListener(e -> {
            LineCreator lineCreator = new LineCreator(controller);
            canvas.createShape(lineCreator);
        });

        // Action listeners for polyline submenu items (keyboard and mouse)
        polylineByKeyboardMenuItem.addActionListener(e -> {
            SVGPolyline polyline = DialogUtils.showPolylineDialog(this, "Create Polyline");
            if (polyline != null) {
                controller.addElement(polyline);
            }
        });

        polylineByMouseMenuItem.addActionListener(e -> {
            PolylineCreator polylineCreator = new PolylineCreator(controller);
            canvas.createShape(polylineCreator);
        });

        // Action listeners for polygon submenu items (keyboard and mouse)
        polygonByKeyboardMenuItem.addActionListener(e -> {
            SVGPolygon polygon = DialogUtils.showPolygonDialog(this, "Create Polygon");
            if (polygon != null) {
                controller.addElement(polygon);
            }
        });

        polygonByMouseMenuItem.addActionListener(e -> {
            PolygonCreator polygonCreator = new PolygonCreator(controller);
            canvas.createShape(polygonCreator);
        });

        // Action listeners for path submenu items (keyboard and mouse)
        pathByKeyboardMenuItem.addActionListener(e -> {
            SVGPath path = DialogUtils.showPathDialog(this, "Create Path");
            if (path != null) {
                controller.addElement(path);
            }
        });

        pathByMouseMenuItem.addActionListener(e -> {
            PathCreator pathCreator = new PathCreator(controller);
            canvas.createShape(pathCreator);
        });

    }

}
