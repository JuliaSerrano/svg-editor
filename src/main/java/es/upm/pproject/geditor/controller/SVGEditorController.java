package es.upm.pproject.geditor.controller;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.model.SVGModel;
import es.upm.pproject.geditor.view.SVGEditorView;

public class SVGEditorController {
    private static final Logger logger = LoggerFactory.getLogger(SVGEditorController.class);

    private SVGModel model;
    private SVGEditorView view;

    public SVGEditorController(SVGModel model, SVGEditorView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    public void createNewImage(int width, int height) {
        logger.info("Creating new image with width {} and height {}", width, height);

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
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(model.getDocument().toSVGString());
            logger.info("Image saved as {}", filename);
        } catch (IOException e) {
            logger.error("Failed to save image", e);
        }
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
