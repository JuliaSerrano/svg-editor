package es.upm.pproject.geditor.view;

import javax.swing.*;

import es.upm.pproject.geditor.controller.ImageController;
import es.upm.pproject.geditor.model.ImageModel;

public class MainFrame extends JFrame {

    public MainFrame() {
        // Initialize the frame
        setTitle("SVG Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        ImageModel imageModel = new ImageModel();
        ImagePanel imagePanel = new ImagePanel();
        ImageController controller = new ImageController(this, imageModel, imagePanel);
        JMenuBar menuBar = new MenuBar(controller);

        setJMenuBar(menuBar);
        add(imagePanel);
        pack();

        // Center the frame
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}