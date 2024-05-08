package es.upm.pproject.geditor.controller;

import javax.swing.JOptionPane;

import es.upm.pproject.geditor.model.ImageModel;
import es.upm.pproject.geditor.view.MainFrame;
import es.upm.pproject.geditor.view.ImagePanel;
import java.awt.Color;

public class ImageController {

    MainFrame mainFrame;
    private ImageModel model;
    private ImagePanel view;

    public ImageController(MainFrame mainFrame, ImageModel model, ImagePanel view) {
        this.mainFrame = mainFrame;
        this.model = model;
        this.view = view;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public ImagePanel getView() {
        return view;
    }

    public ImageModel getModel() {
        return model;
    }

    public void resizeImage(String widthText, String heightText, boolean restart) {
        try {
            int width = Integer.parseInt(widthText);
            int height = Integer.parseInt(heightText);
            if (width > 0 && height > 0) {

                if (restart) view.restart();
    
                view.resize(width, height);
                mainFrame.pack();
            } else {
                JOptionPane.showMessageDialog(null, "Dimensions must be positive numbers.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid numbers for width and height.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void changeBackgroundColor(Color color) {
        view.changeBackground(color);
    }

}