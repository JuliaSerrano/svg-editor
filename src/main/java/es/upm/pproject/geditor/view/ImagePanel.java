package es.upm.pproject.geditor.view;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class ImagePanel extends JPanel {
	
	private Figure currentShape = null;
	private int startX, startY, endX, endY;
	private List<Rectangle> rectangles = new ArrayList<>();
	private List<Ellipse2D.Double> circles = new ArrayList<>();
	private List<Line2D.Double> lines = new ArrayList<>();
    private boolean drawRectanglesEnabled = false;
    private boolean drawCirclesEnabled = false;
    private boolean drawLinesEnabled = false;


    MainFrame mainFrame;

    public ImagePanel() {
        setPreferredSize(new Dimension(800, 600));
        drawRectanglesEnabled = false;
        
     // Registrar listeners de ratón
        MouseAdapter mouseAdapter = new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        	    if (drawRectanglesEnabled) {
        	        startX = e.getX();
        	        startY = e.getY();
        	        endX = startX;
        	        endY = startY;
        	        currentShape = Figure.RECTANGLE;
        	    } else if (drawCirclesEnabled) {
        	        startX = e.getX();
        	        startY = e.getY();
        	        endX = startX;
        	        endY = startY;
        	        currentShape = Figure.CIRCLE;
        	    }else if (drawLinesEnabled) {
                    startX = e.getX();
                    startY = e.getY();
                    endX = startX;
                    endY = startY;
                    currentShape = Figure.LINE;
                }
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
                if (currentShape == Figure.RECTANGLE) {
                    Rectangle rectangle = new Rectangle(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    rectangles.add(rectangle);
                }else if (currentShape == Figure.CIRCLE) {
                    Ellipse2D.Double circle = new Ellipse2D.Double(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    circles.add(circle);
                }else if (currentShape == Figure.LINE) {
                	Line2D.Double line = new Line2D.Double(startX, startY, endX, endY);
                    lines.add(line);
                }
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
         for (Ellipse2D.Double circle : circles) {
             g.drawOval((int) circle.getX(), (int) circle.getY(), (int) circle.getWidth(), (int) circle.getHeight());
         }
         
         for (Line2D.Double line : lines) {
        	 g.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(),(int) line.getY2());
         }
         if (currentShape == Figure.RECTANGLE) {
             int width = Math.abs(endX - startX);
             int height = Math.abs(endY - startY);
             int x = Math.min(startX, endX);
             int y = Math.min(startY, endY);
             g.drawRect(x, y, width, height);
         } else if (currentShape == Figure.CIRCLE) {
             int width = Math.abs(endX - startX);
             int height = Math.abs(endY - startY);
             int x = Math.min(startX, endX);
             int y = Math.min(startY, endY);
             g.drawOval(x, y, width, height);
         } else if(currentShape == Figure.LINE) {
             g.drawLine(startX, startY, endX, endY);
         }
     }

    
  
    
    
    public void setDrawRectanglesEnabled(boolean enabled) {
        drawRectanglesEnabled = enabled;
    }
    
    public void setDrawCirclesEnabled(boolean enabled) {
    	drawCirclesEnabled = enabled;
    }
    
    public void setDrawLinesEnabled(boolean enabled) {
    	drawLinesEnabled = enabled;
    }


}
