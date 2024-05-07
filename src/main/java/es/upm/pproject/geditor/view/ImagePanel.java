package es.upm.pproject.geditor.view;

import javax.swing.JPanel;
import java.awt.Dimension;

public class ImagePanel extends JPanel {

    MainFrame mainFrame;

    public ImagePanel() {
        setPreferredSize(new Dimension(800, 600));
    }

    public void clean() {
        removeAll();
        repaint();
    }

    public void resize(int width, int height) {
        // Set the new size of the panel
        setPreferredSize(new Dimension(width, height));
    }
}
