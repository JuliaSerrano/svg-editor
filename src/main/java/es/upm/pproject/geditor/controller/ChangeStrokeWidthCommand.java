package es.upm.pproject.geditor.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.geditor.model.SVGElement;

public class ChangeStrokeWidthCommand implements Command {
    private List<SVGElement> elements;
    private double newWidth;
    private double[] oldWidths;
    private static final Logger logger = LoggerFactory.getLogger(ChangeStrokeWidthCommand.class);

    public ChangeStrokeWidthCommand(List<SVGElement> elements, double newWidth) {
        this.elements = new ArrayList<>(elements);
        this.newWidth = newWidth;
        this.oldWidths = new double[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            oldWidths[i] = elements.get(i).getStrokeWidth();
        }
    }

    @Override
    public void execute() {
        for (SVGElement element : elements) {
            element.setStrokeWidth(newWidth);
            logger.info("Changed stroke width to {} to element {}", newWidth, element.getShape());
        }
    }

    @Override
    public void undo() {
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).setStrokeWidth(oldWidths[i]);
            logger.info("Undo stroke width from {} to {} to element {}", newWidth, oldWidths[i],
                    elements.get(i).getShape());
        }
    }
}