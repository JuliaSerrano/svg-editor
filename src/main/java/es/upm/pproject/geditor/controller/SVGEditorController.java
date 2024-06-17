package es.upm.pproject.geditor.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.model.SVGGroup;
import es.upm.pproject.geditor.model.SVGModel;
import es.upm.pproject.geditor.view.SVGEditorView;

public class SVGEditorController {
    private static final Logger logger = LoggerFactory.getLogger(SVGEditorController.class);

    private SVGModel model;
    private SVGEditorView view;
    private CommandManager commandManager = new CommandManager();

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
        Command changeBgColorCommand = new ChangeBgColorCommand(model.getDocument(), color);
        commandManager.executeCommand(changeBgColorCommand);
        view.updateCanvas(model.getDocument());
    }

    public void addElement(SVGElement element) {
        // Add the element to the SVGDocument in the model
        Command addElementCommand = new AddElementCommand(model.getDocument(), element);
        commandManager.executeCommand(addElementCommand);

        // Update the canvas view to reflect the changes
        view.updateCanvas(model.getDocument());
    }

    public void moveSelectedElements(List<SVGElement> selectedElements, double dx, double dy) {
        for (SVGElement element : selectedElements) {
            element.move(dx, dy);
        }
        view.updateCanvas(model.getDocument());
    }

    public void changeSelectedElementFillColor(List<SVGElement> selectedElements, Color newColor) {
        Command changeFillColorCommand = new ChangeFillColorCommand(selectedElements, newColor);
        commandManager.executeCommand(changeFillColorCommand);
        view.updateCanvas(model.getDocument());
    }

    public void changeSelectedElementFillOpacity(List<SVGElement> selectedElements, double fillOpacity) {
        Command changeFillOpacityCommand = new ChangeFillOpacityCommand(selectedElements, fillOpacity);
        commandManager.executeCommand(changeFillOpacityCommand);
        view.updateCanvas(model.getDocument());
    }

    public void changeSelectedElementStrokeColor(List<SVGElement> selectedElements, Color color) {
        Command changeStrokeColorCommand = new ChangeStrokeColorCommand(selectedElements, color);
        commandManager.executeCommand(changeStrokeColorCommand);
        view.updateCanvas(model.getDocument());
    }

    public void changeSelectedElementStrokeWidth(List<SVGElement> selectedElements, double strokeWidth) {
        Command changeStrokeWidthCommand = new ChangeStrokeWidthCommand(selectedElements, strokeWidth);
        commandManager.executeCommand(changeStrokeWidthCommand);
        view.updateCanvas(model.getDocument());
    }

    public void changeSelectedElementStrokeOpacity(List<SVGElement> selectedElements, double strokeOpacity) {
        Command changeStrokeOpacityCommand = new ChangeStrokeOpacityCommand(selectedElements, strokeOpacity);
        commandManager.executeCommand(changeStrokeOpacityCommand);
        view.updateCanvas(model.getDocument());
    }

    public void removeSelectedElement(List<SVGElement> selectedElements) {
        Command removeElementsCommand = new RemoveElementsCommand(model.getDocument(), selectedElements);
        commandManager.executeCommand(removeElementsCommand);
        selectedElements.clear();
        view.updateCanvas(model.getDocument());
    }

    public void groupSelectedElements(List<SVGElement> selectedElements) {
        if (selectedElements.isEmpty()) {
            return;
        }

        SVGGroup group = new SVGGroup();
        for (SVGElement element : selectedElements) {
            model.getDocument().removeElement(element);
            group.addElement(element);
        }

        model.getDocument().addElement(group);
        selectedElements.clear();
        selectedElements.add(group);
        view.updateCanvas(model.getDocument());
    }

    public void ungroupSelectedElements(List<SVGElement> selectedElements) {
        ArrayList<SVGElement> elementsToRemove = new ArrayList<>();
        ArrayList<SVGElement> elementsToAdd = new ArrayList<>();

        for (SVGElement element : selectedElements) {
            if (element instanceof SVGGroup) {
                SVGGroup group = (SVGGroup) element;
                elementsToRemove.add(group);
                elementsToAdd.addAll(group.getElements());
            }
        }

        selectedElements.removeAll(elementsToRemove);
        selectedElements.addAll(elementsToAdd);

        for (SVGElement element : elementsToRemove) {
            model.getDocument().removeElement(element);
        }
        for (SVGElement element : elementsToAdd) {
            model.getDocument().addElement(element);
        }

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
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        try {
            SVGDocument document = (SVGDocument) factory.createDocument(new File(filename).toURI().toString());
            Element svgRoot = document.getDocumentElement();

            int svgWidth = Integer.parseInt(svgRoot.getAttribute("width"));
            int svgHeight = Integer.parseInt(svgRoot.getAttribute("height"));
            createNewImage(svgWidth, svgHeight);

            SVGParser.parseDocument(document, model);
            view.updateCanvas(model.getDocument());
            logger.info("Successfully loaded SVG file from {}", filename);
        } catch (IOException e) {
            view.showErrorDialog("Error loading SVG file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            view.showErrorDialog("Unsupported SVG element: " + e.getMessage());
        }

    }

    public void undo() {
        commandManager.undo();
        // Update the canvas view to reflect the changes
        view.updateCanvas(model.getDocument());
    }

    public void exit() {
        logger.info("Closing the SVG editor ...");
        System.exit(0);
    }

}
