package es.upm.pproject.geditor.controller;
import java.util.ArrayList;
import java.util.List;

import es.upm.pproject.geditor.model.SVGElement;

public class MoveElementsCommand implements Command {
    private List<SVGElement> elements;
    private double dx;
    private double dy;

    public MoveElementsCommand(List<SVGElement> elements, double dx, double dy) {
        this.elements = new ArrayList<>(elements);
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        for (SVGElement element : elements) {
            element.move(dx, dy);
        }
    }

    @Override
    public void undo() {
        for (SVGElement element : elements) {
            element.move(-dx, -dy);
        }
    }
}