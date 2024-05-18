package es.upm.pproject.geditor.view;

import javax.swing.*;
import java.awt.*;

// import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGDocument;
import es.upm.pproject.geditor.model.SVGElement;

public class SVGCanvas extends JPanel {
    private SVGDocument document;
    // private SVGEditorController controller;

    public void setDocument(SVGDocument document) {
        this.document = document;

        if (document != null) {
            setPreferredSize(new Dimension(document.getWidth(), document.getHeight()));
        }
    }

    public SVGDocument getDocument() {
        return this.document;
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
            g2d.setColor(Color.decode(element.getFillColor()));
            g2d.fill(element.getShape());
            g2d.setColor(Color.decode(element.getStrokeColor()));
            g2d.setStroke(new BasicStroke((float) element.getStrokeWidth()));
            g2d.draw(element.getShape());
        }
        g2d.dispose();
    }

}
