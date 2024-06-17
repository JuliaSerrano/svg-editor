package es.upm.pproject.geditor.controller;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.geditor.model.SVGElement;

public class MoveElementsCommand implements Command {
    private List<SVGElement> elements;
    private double dx;
    private double dy;
    private static final Logger logger = LoggerFactory.getLogger(MoveElementsCommand.class);

    public MoveElementsCommand(List<SVGElement> elements, double dx, double dy) {
        this.elements = new ArrayList<>(elements);
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        for (SVGElement element : elements) {
            element.move(dx, dy);
            logger.info("Moved element {} by x={} and y={}", element.getShape(), dx, dy);
        }
    }

    @Override
    public void undo() {
        for (SVGElement element : elements) {
            element.move(-dx, -dy);
            logger.info("Undoing move of element {} by x={} and y={}", element.getShape(), dx, dy);
        }
    }
}