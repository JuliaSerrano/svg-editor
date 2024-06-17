package es.upm.pproject.geditor.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.geditor.model.SVGElement;

public class ChangeStrokeOpacityCommand implements Command {
    private List<SVGElement> elements;
    private double newOpacity;
    private double[] oldOpacities;
    private static final Logger logger = LoggerFactory.getLogger(ChangeStrokeOpacityCommand.class);

    public ChangeStrokeOpacityCommand(List<SVGElement> elements, double newOpacity) {
        this.elements = new ArrayList<>(elements);
        this.newOpacity = newOpacity;
        this.oldOpacities = new double[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            oldOpacities[i] = elements.get(i).getStrokeOpacity();
        }
    }

    @Override
    public void execute() {
        for (SVGElement element : elements) {
            element.setStrokeOpacity(newOpacity);
            logger.info("Changed stroke opacity to {} to element {}", newOpacity, element.getShape());
        }
    }

    @Override
    public void undo() {
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).setStrokeOpacity(oldOpacities[i]);
            logger.info("Undo stroke opacity from {} to {} to element {}", newOpacity, oldOpacities[i],
                    elements.get(i).getShape());
        }
    }
}