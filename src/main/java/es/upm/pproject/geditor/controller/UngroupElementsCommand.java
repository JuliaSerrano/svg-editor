package es.upm.pproject.geditor.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.geditor.model.SVGDocument;
import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.model.SVGGroup;

public class UngroupElementsCommand implements Command {
    private SVGDocument document;
    private List<SVGElement> elements;
    private List<SVGElement> elementsToRemove;
    private List<SVGElement> elementsToAdd;
    private static final Logger logger = LoggerFactory.getLogger(UngroupElementsCommand.class);

    public UngroupElementsCommand(SVGDocument document, List<SVGElement> elements) {
        this.document = document;
        this.elements = elements;
        this.elementsToRemove = new ArrayList<>();
        this.elementsToAdd = new ArrayList<>();
    }

    @Override
    public void execute() {
        for (SVGElement element : elements) {
            if (element instanceof SVGGroup) {
                SVGGroup group = (SVGGroup) element;
                elementsToRemove.add(group);
                elementsToAdd.addAll(group.getElements());
            }
        }

        for (SVGElement element : elementsToRemove) {
            document.removeElement(element);
        }
        for (SVGElement element : elementsToAdd) {
            document.addElement(element);
        }

        logger.info("Ungrouped {} elements", elements.size());
    }

    @Override
    public void undo() {
        for (SVGElement element : elementsToAdd) {
            document.removeElement(element);
        }
        for (SVGElement element : elementsToRemove) {
            document.addElement(element);
        }

        logger.info("Reverted ungrouping of {} elements", elements.size());
    }

    public List<SVGElement> getElementsToRemove() {
        return elementsToRemove;
    }

    public List<SVGElement> getElementsToAdd() {
        return elementsToAdd;
    }
}