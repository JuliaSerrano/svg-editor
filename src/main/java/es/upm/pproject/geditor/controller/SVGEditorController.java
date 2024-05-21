package es.upm.pproject.geditor.controller;

import java.awt.Color;

import java.awt.*;
import javax.swing.JOptionPane;

import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.model.SVGModel;
import es.upm.pproject.geditor.model.SVGRectangle;
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
        view.resizeEditor(width, height);
    }

    public void resizeImage(int width, int height) {
        model.getDocument().setWidth(width);
        model.getDocument().setHeight(height);
        view.updateCanvas(model.getDocument());
        view.resizeEditor(width, height);
    }

    public void changeBackgroundColor(Color color) {
        model.getDocument().setBackgroundColor(color);
        view.updateCanvas(model.getDocument());
    }

    public void addElement(SVGElement element) {
        // Add the element to the SVGDocument in the model
        model.getDocument().addElement(element);

        // Update the canvas view to reflect the changes
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
