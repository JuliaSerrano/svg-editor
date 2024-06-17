package es.upm.pproject.geditor.controller;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.geditor.model.SVGElement;

public class ChangeFillColorCommand implements Command {
    private List<SVGElement> elements;
    private Color newColor;
    private Color[] oldColors;
    private static final Logger logger = LoggerFactory.getLogger(ChangeFillColorCommand.class);

    public ChangeFillColorCommand(List<SVGElement> elements, Color newColor) {
        this.elements = new ArrayList<>(elements);
        this.newColor = newColor;
        this.oldColors = new Color[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            oldColors[i] = elements.get(i).getFillColor();
        }
    }

    @Override
    public void execute() {
        for (SVGElement element : elements) {
            element.setFillColor(newColor);
            logger.info("changed fill color to {} to element {}", newColor, element.getShape());
        }
    }

    @Override
    public void undo() {
        logger.info("undo elements: {}", elements);
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).setFillColor(oldColors[i]);
            logger.info("undo fill color from {} to {} to element {}", newColor, oldColors[i], elements.get(i).getShape());
        }
    }
}