package es.upm.pproject.geditor.view;

import javax.swing.*;
import java.awt.*;

import es.upm.pproject.geditor.controller.ImageController;

public class MenuBar extends JMenuBar {

    ImageController controller;
    private boolean drawRectanglesEnabled = false;
    private boolean drawCirclesEnabled = false;

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
        
        JMenuItem rectangleItem = new JMenuItem("Draw Rectangle");
        editMenu.add(rectangleItem);
        rectangleItem.addActionListener(e -> setCurrentShape(Figure.RECTANGLE));
        
        JMenuItem circleItem = new JMenuItem("Draw Circle");
        editMenu.add(circleItem);
        circleItem.addActionListener(e -> setCurrentShape(Figure.CIRCLE));


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
        int result = JOptionPane.showConfirmDialog(null, panel, dialogTitle, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            controller.resizeImage(widthField.getText(), heightField.getText(), restart);
        }
    }

    private void changeBackgroundColor() {
        Color newColor = JColorChooser.showDialog(null, "Choose Background Color", controller.getView().getBackground());
        controller.getView().setBackground(newColor);
    }
   
//********************************************************************************
   
    
    
    public void setCurrentShape(Figure shape) {
        if (shape == Figure.RECTANGLE || shape == Figure.CIRCLE) {
            if (shape == Figure.RECTANGLE) {
                drawRectanglesEnabled = !drawRectanglesEnabled;
                drawCirclesEnabled = false;
            } else if (shape == Figure.CIRCLE) {
                drawCirclesEnabled = !drawCirclesEnabled;
                drawRectanglesEnabled = false;
            }
            controller.getView().setDrawRectanglesEnabled(drawRectanglesEnabled);
            controller.getView().setDrawCirclesEnabled(drawCirclesEnabled);
        }
    }


    
    

    
    
    
    

}