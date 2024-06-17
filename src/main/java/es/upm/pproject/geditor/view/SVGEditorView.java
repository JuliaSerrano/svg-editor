
package es.upm.pproject.geditor.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.*;
import es.upm.pproject.geditor.utils.DialogUtils;
import es.upm.pproject.geditor.view.ui.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

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
    private JMenuItem exitMenuItem;

    private JToolBar toolBar;

    private static final String ERROR_TITLE = "Error";

    public SVGEditorView(SVGModel model) {
        this.document = model.getDocument();
        controller = new SVGEditorController(model, this); // Initialize controller

        setTitle("SVG Editor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(this.document.getWidth(), this.document.getHeight()));

        canvas = new SVGCanvas(controller);
        add(canvas, BorderLayout.CENTER);

        // side panel
        JPanel optionsPanel = createToolBox();
        add(optionsPanel, BorderLayout.WEST);

        // add menu
        JMenuBar menuBar = new JMenuBar();

        // file section
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        exitMenuItem = new JMenuItem("Exit");

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
        optionsPanel.setPreferredSize(new Dimension(130, getHeight()));

        toolBar = new JToolBar(JToolBar.VERTICAL);
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

        // Fill color picker
        JButton fillColorButton = new JButton("Fill Color");
        fillColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose Fill Color", Color.BLACK);
            if (newColor != null) {
                // Set fill color to the selected color
                controller.changeSelectedElementFillColor(canvas.getSelectedElements(), newColor);
            }
        });
        toolBar.add(fillColorButton);

        // Fill opacity slider
        JSlider fillOpacitySlider = new JSlider(0, 100, 100); // From 0% to 100%
        fillOpacitySlider.setPaintTicks(true);
        fillOpacitySlider.setPaintLabels(true);
        fillOpacitySlider.setMajorTickSpacing(25);
        fillOpacitySlider.setMinorTickSpacing(5);
        fillOpacitySlider.setToolTipText("Fill Opacity");
        fillOpacitySlider.addChangeListener(e -> {
            int opacity = fillOpacitySlider.getValue();
            controller.changeSelectedElementFillOpacity(canvas.getSelectedElements(), opacity / 100.0);
        });
        toolBar.add(new JLabel("Fill Opacity"));
        toolBar.add(fillOpacitySlider);

        // Line color picker
        JButton lineColorButton = new JButton("Line Color");
        lineColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose Line Color", Color.BLACK);
            if (newColor != null) {
                controller.changeSelectedElementStrokeColor(canvas.getSelectedElements(), newColor);
            }
        });
        toolBar.add(lineColorButton);

        // Stroke width spinner
        JSpinner strokeWidthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        strokeWidthSpinner.setToolTipText("Line Width");
        strokeWidthSpinner.addChangeListener(e -> {
            int lineWidth = (int) strokeWidthSpinner.getValue();
            controller.changeSelectedElementStrokeWidth(canvas.getSelectedElements(), lineWidth);
        });
        toolBar.add(new JLabel("Line Width"));
        toolBar.add(strokeWidthSpinner);

        // Stroke opacity slider
        JSlider strokeOpacitySlider = new JSlider(0, 100, 100); // From 0% to 100%
        strokeOpacitySlider.setPaintTicks(true);
        strokeOpacitySlider.setPaintLabels(true);
        strokeOpacitySlider.setMajorTickSpacing(25);
        strokeOpacitySlider.setMinorTickSpacing(5);
        strokeOpacitySlider.setToolTipText("Stroke Opacity");
        strokeOpacitySlider.addChangeListener(e -> {
            int opacity = strokeOpacitySlider.getValue();
            controller.changeSelectedElementStrokeOpacity(canvas.getSelectedElements(), opacity / 100.0);
        });
        toolBar.add(new JLabel("Stroke Opacity"));
        toolBar.add(strokeOpacitySlider);

        // Delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> controller.removeSelectedElement(canvas.getSelectedElements()));
        toolBar.add(deleteButton);

        // Group button
        JButton groupButton = new JButton("Group");
        groupButton.addActionListener(e -> controller.groupSelectedElements(canvas.getSelectedElements()));
        toolBar.add(groupButton);

        // Ungroup button
        JButton ungroupButton = new JButton("Ungroup");
        ungroupButton.addActionListener(e -> controller.ungroupSelectedElements(canvas.getSelectedElements()));
        toolBar.add(ungroupButton);

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
        fileListeners();
        editListeners();
        shapeListeners();
    }

    private void fileListeners() {
        // New image action listener
        JMenuItem newMenuItem = getJMenuBar().getMenu(0).getItem(0);
        newMenuItem.addActionListener((ActionEvent e) -> {
            int newWidth = Integer.parseInt(JOptionPane.showInputDialog("Enter width:"));
            int newHeight = Integer.parseInt(JOptionPane.showInputDialog("Enter height:"));
            controller.createNewImage(newWidth, newHeight);
        });

        // Save image action listener
        JMenuItem saveMenuItem = getJMenuBar().getMenu(0).getItem(1);
        saveMenuItem.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save SVG Image");

            // Set the default filename to "Untitled.svg"
            fileChooser.setSelectedFile(new File("Untitled.svg"));

            // Add a file filter for SVG files
            FileNameExtensionFilter svgFilter = new FileNameExtensionFilter("SVG (*.svg)", "svg");
            fileChooser.setFileFilter(svgFilter);

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                if (filename != null && !filename.trim().isEmpty()) {
                    if (!filename.toLowerCase().endsWith(".svg")) {
                        filename += ".svg"; // Add .svg extension if not present
                    }
                    controller.saveImage(filename);
                    JOptionPane.showMessageDialog(null, "File saved successfully at: " + filename, "Save Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid filename.", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Open image action listener
        JMenuItem openMenuItem = getJMenuBar().getMenu(0).getItem(2);
        openMenuItem.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open SVG Image");

            // Add a file filter for SVG files
            FileNameExtensionFilter svgFilter = new FileNameExtensionFilter("SVG (*.svg)", "svg");
            fileChooser.setFileFilter(svgFilter);

            int userSelection = fileChooser.showOpenDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                controller.openSVGFile(filename);
            }
        });

        // Exit application action listener
        exitMenuItem = getJMenuBar().getMenu(0).getItem(3);
        exitMenuItem.addActionListener((ActionEvent e) -> controller.exit());
    }

    private void editListeners() {
        // resize an existing image action listener
        JMenuItem resizeMenuItem = getJMenuBar().getMenu(1).getItem(0);
        resizeMenuItem.addActionListener((ActionEvent e) -> {
            try {
                int newWidth = Integer.parseInt(JOptionPane.showInputDialog("Enter new width:"));
                int newHeight = Integer.parseInt(JOptionPane.showInputDialog("Enter new height:"));
                if (newWidth > 0 && newHeight > 0) {
                    controller.resizeImage(newWidth, newHeight);
                } else {
                    JOptionPane.showMessageDialog(null, "Dimensions must be positive numbers.", ERROR_TITLE,
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for width and height.", ERROR_TITLE,
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

        // Undo action listener
        JMenuItem undoMenuItem = getJMenuBar().getMenu(1).getItem(2);
        undoMenuItem.addActionListener((ActionEvent e) -> controller.undo());
    }

    private void shapeListeners() {

        // Action listeners for rectangle submenu items
        rectangleByKeyboardMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            SVGRectangle rect = DialogUtils.showRectangleDialog(this, "Create Rectangle");
            if (rect != null) {
                controller.addElement(rect);
            }
        });
        rectangleByMouseMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            RectangleCreator rectangleCreator = new RectangleCreator(controller);
            canvas.createShape(rectangleCreator);
        });

        // Action listeners for circle submenu items (keyboard and mouse)
        circleByKeyboardMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            SVGCircle circle = DialogUtils.showCircleDialog(this, "Create Circle");
            if (circle != null) {
                controller.addElement(circle);
            }
        });

        circleByMouseMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            CircleCreator circleCreator = new CircleCreator(controller);
            canvas.createShape(circleCreator);
        });

        // Action listeners for ellipse submenu items (keyboard and mouse)
        ellipseByKeyboardMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            SVGEllipse ellipse = DialogUtils.showEllipseDialog(this, "Create Ellipse");
            if (ellipse != null) {
                controller.addElement(ellipse);
            }
        });

        ellipseByMouseMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            EllipseCreator ellipseCreator = new EllipseCreator(controller);
            canvas.createShape(ellipseCreator);
        });

        // Action listeners for line submenu items (keyboard and mouse)
        lineByKeyboardMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            SVGLine line = DialogUtils.showLineDialog(this, "Create Line");
            if (line != null) {
                controller.addElement(line);
            }
        });

        lineByMouseMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            LineCreator lineCreator = new LineCreator(controller);
            canvas.createShape(lineCreator);
        });

        // Action listeners for polyline submenu items (keyboard and mouse)
        polylineByKeyboardMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            SVGPolyline polyline = DialogUtils.showPolylineDialog(this, "Create Polyline");
            if (polyline != null) {
                controller.addElement(polyline);
            }
        });

        polylineByMouseMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            PolylineCreator polylineCreator = new PolylineCreator(controller);
            canvas.createShape(polylineCreator);
        });

        // Action listeners for polygon submenu items (keyboard and mouse)
        polygonByKeyboardMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            SVGPolygon polygon = DialogUtils.showPolygonDialog(this, "Create Polygon");
            if (polygon != null) {
                controller.addElement(polygon);
            }
        });

        polygonByMouseMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            PolygonCreator polygonCreator = new PolygonCreator(controller);
            canvas.createShape(polygonCreator);
        });

        // Action listeners for path submenu items (keyboard and mouse)
        pathByKeyboardMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            SVGPath path = DialogUtils.showPathDialog(this, "Create Path");
            if (path != null) {
                controller.addElement(path);
            }
        });

        pathByMouseMenuItem.addActionListener(e -> {
            deselectSelectionMode();
            PathCreator pathCreator = new PathCreator(controller);
            canvas.createShape(pathCreator);
        });

    }

    private void deselectSelectionMode() {
        for (Component comp : toolBar.getComponents()) {
            if (comp instanceof JToggleButton) {
                JToggleButton toggleButton = (JToggleButton) comp;
                if (toggleButton.getText().equals("In Selection")) {
                    toggleButton.setSelected(false);
                    toggleButton.setText("Select");
                    canvas.setMode(SVGCanvas.Mode.DEFAULT);
                    break;
                }
            }
        }
    }

    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
    }
}
