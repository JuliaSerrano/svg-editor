package es.upm.pproject.geditor.view;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class ImagePanel extends JPanel {
	
	private Figure currentShape = null;
	private int startX, startY, endX, endY;
	private List<Rectangle> rectangles = new ArrayList<>();
	 


    MainFrame mainFrame;

    public ImagePanel() {
        setPreferredSize(new Dimension(800, 600));
        
     // Registrar listeners de ratón
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                endX = startX;
                endY = startY;
                currentShape = Figure.RECTANGLE;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                Rectangle rectangle = new Rectangle(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                rectangles.add(rectangle);
                repaint();
            }

        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
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
    
//*******************************************************************************   
  
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Rectangle rectangle : rectangles) {
            g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        if (currentShape == Figure.RECTANGLE) {
            int width = Math.abs(endX - startX);
            int height = Math.abs(endY - startY);
            int x = Math.min(startX, endX);
            int y = Math.min(startY, endY);
            g.drawRect(x, y, width, height);
        }
    }



}
