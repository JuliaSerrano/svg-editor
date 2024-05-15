package es.upm.pproject.geditor.controller;

import java.awt.Color;

import javax.swing.JOptionPane;

import es.upm.pproject.geditor.model.SVGModel;
import es.upm.pproject.geditor.view.SVGCanvas;
import es.upm.pproject.geditor.view.SVGEditorView;

public class SVGEditorController {
    private SVGModel model;
    private SVGEditorView view;
    private SVGCanvas canvas;

    public SVGEditorController(SVGModel model, SVGEditorView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    public void createNewImage(int width, int height) {
        model = new SVGModel(width, height);
        view.updateCanvas(model.getDocument());
    }

    public void resizeImage(int width, int height) {
        model.getDocument().setWidth(width);
        model.getDocument().setHeight(height);
        view.updateCanvas(model.getDocument());
    }

    public void changeBackgroundColor(Color color) {
        model.getDocument().setBackgroundColor(color);
        view.updateCanvas(model.getDocument());
    }

    public void saveImage(String filename) {
        // Save SVGDocument to file
    }

    public void openSVGFile(String filename) {
        // Open SVG file and update model and view
    }

    public void undo() {
        // Undo the last operation
    }

    public void exit() {
        System.exit(0);
    }

}
