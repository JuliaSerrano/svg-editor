package es.upm.pproject.geditor.view;

import javax.swing.*;
import java.awt.*;

import es.upm.pproject.geditor.controller.ImageController;

public class MenuBar extends JMenuBar {

    ImageController controller;

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
    }

    private void showNewImageDialog() {
        // Create a panel to hold the input fields
        JPanel panel = new JPanel(new GridLayout(0, 2));

        // Add text fields for width and height
        panel.add(new JLabel("Width:"));
        JTextField widthField = new JTextField(5);
        panel.add(widthField);

        panel.add(new JLabel("Height:"));
        JTextField heightField = new JTextField(5);
        panel.add(heightField);

        // Show the dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Width and Height", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            controller.createImage(widthField.getText(), heightField.getText());
        }
    }
}