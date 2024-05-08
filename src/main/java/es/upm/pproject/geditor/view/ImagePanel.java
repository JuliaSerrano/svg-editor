package es.upm.pproject.geditor.view;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

public class ImagePanel extends JPanel {

    MainFrame mainFrame;

    public ImagePanel() {
        setPreferredSize(new Dimension(800, 600));
    }

    public void restart() {
        removeAll();
        setBackground(Color.WHITE);
        repaint();
    }

    public void resize(int width, int height) {
        // Set the new size of the panel
        setPreferredSize(new Dimension(width, height));
    }

    public void changeBackground(Color color) {
        setBackground(color);
        repaint(); // Ensure the new background color is shown
    }
}
