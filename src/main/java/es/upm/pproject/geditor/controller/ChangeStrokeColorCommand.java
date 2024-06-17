package es.upm.pproject.geditor.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.geditor.model.SVGElement;

public class ChangeStrokeColorCommand implements Command {
    private List<SVGElement> elements;
    private Color newColor;
    private Color[] oldColors;
    private static final Logger logger = LoggerFactory.getLogger(ChangeStrokeColorCommand.class);

    public ChangeStrokeColorCommand(List<SVGElement> elements, Color newColor) {
        this.elements = new ArrayList<>(elements);
        this.newColor = newColor;
        this.oldColors = new Color[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            oldColors[i] = elements.get(i).getStrokeColor();
        }
    }

    @Override
    public void execute() {
        for (SVGElement element : elements) {
            element.setStrokeColor(newColor);
            logger.info("Changed stroke color to {} to element {}", newColor, element.getShape());
        }
    }

    @Override
    public void undo() {
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).setStrokeColor(oldColors[i]);
            logger.info("Undo stroke color from {} to {} to element {}", newColor, oldColors[i],
                    elements.get(i).getShape());
        }
    }
}