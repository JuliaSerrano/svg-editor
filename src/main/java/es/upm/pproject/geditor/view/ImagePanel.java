package es.upm.pproject.geditor.view;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class ImagePanel extends JPanel {

    private Figure currentShape = null;
    private int startX, startY, endX, endY;
    private List<Rectangle> rectangles = new ArrayList<>();
    private List<Ellipse2D.Double> circles = new ArrayList<>();
    private List<Line2D.Double> lines = new ArrayList<>();

    private List<Polygon> polygons = new ArrayList<>();
    private List<Point> polygonPoints = new ArrayList<>();
    private List<Polyline> polylines = new ArrayList<>();
    private List<Path2D> paths = new ArrayList<>();

    private boolean drawRectanglesEnabled = false;
    private boolean drawCirclesEnabled = false;
    private boolean drawLinesEnabled = false;
    private boolean drawPolygonsEnabled = false;
    private Polygon currentPolygon = new Polygon();

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
                } else if (drawLinesEnabled) {
                    startX = e.getX();
                    startY = e.getY();
                    endX = startX;
                    endY = startY;
                    currentShape = Figure.LINE;
                } else if (drawPolygonsEnabled) {
                    polygonPoints.add(new Point(e.getX(), e.getY()));
                    // Double-click to finish the polygon
                    if (e.getClickCount() == 2 && !polygonPoints.isEmpty()) {
                        for (Point p : polygonPoints) {
                            currentPolygon.addPoint(p.x, p.y);
                        }
                        polygonPoints.clear();
                        polygons.add(currentPolygon);
                        currentPolygon = new Polygon();
                    }
                    currentShape = Figure.POLYGON;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!drawPolygonsEnabled) {
                    endX = e.getX();
                    endY = e.getY();
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                if (currentShape == Figure.RECTANGLE) {
                    Rectangle rectangle = new Rectangle(Math.min(startX, endX), Math.min(startY, endY),
                            Math.abs(endX - startX), Math.abs(endY - startY));
                    rectangles.add(rectangle);
                } else if (currentShape == Figure.CIRCLE) {
                    Ellipse2D.Double circle = new Ellipse2D.Double(Math.min(startX, endX), Math.min(startY, endY),
                            Math.abs(endX - startX), Math.abs(endY - startY));
                    circles.add(circle);
                } else if (currentShape == Figure.LINE) {
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

    // *******************************************************************************

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
            g.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
        }

        for (Polygon polygon : polygons) {
            g.drawPolygon(polygon);
        }
        
        for (Polyline polyline : polylines) {
            polyline.draw(g);
        }
        
        for (Polyline polyline : polylines) {
            polyline.draw(g);
        }
        
        for (Path2D path : paths) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.BLACK);
            g2d.draw(path);
            g2d.dispose();
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
        } else if (currentShape == Figure.LINE) {
            g.drawLine(startX, startY, endX, endY);
        } else if (currentShape == Figure.POLYGON && !polygonPoints.isEmpty()) {
            Point start = polygonPoints.get(0);
            for (int i = 1; i < polygonPoints.size(); i++) {
                Point end = polygonPoints.get(i);
                g.drawLine(start.x, start.y, end.x, end.y);
                start = end;
            }
        }
    }

    public void drawRectangle(int x, int y, int width, int height) {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangles.add(rectangle);
        repaint();
    }

    public void drawCircle(int centerX, int centerY, int radius) {
        Circle circle = new Circle(centerX, centerY, radius);
        circles.add(circle);
        repaint();
    }

    public void drawEllipse(int centerX, int centerY, int width, int height) {
        Ellipse2D.Double ellipse = new Ellipse2D.Double(centerX - width / 2, centerY - height / 2, width, height);
        circles.add(ellipse);
        repaint();
    }

    public void drawLine(int startX, int startY, int endX, int endY) {
        Line2D.Double line = new Line2D.Double(startX, startY, endX, endY);
        lines.add(line);
        repaint();
    }
    
    public void drawPolygon(int[] xPoints, int[] yPoints, int numVertices) {
        Polygon polygon = new Polygon(xPoints, yPoints, numVertices);
        polygons.add(polygon);
        repaint();
    }
    
    public void drawPolyline(int[] xPoints, int[] yPoints, int numPoints) {
        Polyline polyline = new Polyline(xPoints, yPoints, numPoints);
        polylines.add(polyline);
        repaint();
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

    public void setDrawPolygonsEnabled(boolean enabled) {
        drawPolygonsEnabled = enabled;
    }
    
    public void drawPath(Path2D path) {
        paths.add(path);
        repaint();
    }


    // Class for drawing circle
    public class Circle extends Ellipse2D.Double {
        public Circle(int centerX, int centerY, int radius) {
            super(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }
    }
    
    //Class for drawing Polylines
    public class Polyline {
        private int[] xPoints;
        private int[] yPoints;
        private int numPoints;

        public Polyline(int[] xPoints, int[] yPoints, int numPoints) {
            this.xPoints = xPoints;
            this.yPoints = yPoints;
            this.numPoints = numPoints;
        }

        public void draw(Graphics g) {
            g.drawPolyline(xPoints, yPoints, numPoints);
        }
    }
}
