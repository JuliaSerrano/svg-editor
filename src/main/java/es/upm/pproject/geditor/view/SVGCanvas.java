package es.upm.pproject.geditor.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import es.upm.pproject.geditor.controller.SVGEditorController;
// import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGDocument;
import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.model.SVGRectangle;

public class SVGCanvas extends JPanel {
    private SVGDocument document;
    private SVGEditorController controller;

    public void setDocument(SVGDocument document) {
        this.document = document;
        if (document != null) {
            setPreferredSize(new Dimension(document.getWidth(), document.getHeight()));
        }
    }

    public void setController(SVGEditorController controller) {
        this.controller = controller;
        // Add mouse listeners here, since controller is now available
        // addMouseListeners();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (document == null) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(document.getBackgroundColor());
        g2d.fillRect(0, 0, document.getWidth(), document.getHeight());
        for (SVGElement element : document.getElements()) {
            g2d.setColor(element.getFillColor());
            g2d.fill(element.getShape());
            g2d.setColor(element.getStrokeColor());
            g2d.setStroke(new BasicStroke((float) element.getStrokeWidth()));
            g2d.draw(element.getShape());
        }
        g2d.dispose();
    }

}