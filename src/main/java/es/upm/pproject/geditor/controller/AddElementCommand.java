package es.upm.pproject.geditor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.geditor.model.SVGDocument;
import es.upm.pproject.geditor.model.SVGElement;

public class AddElementCommand implements Command {
    private SVGDocument document;
    private SVGElement element;
    private static final Logger logger = LoggerFactory.getLogger(AddElementCommand.class);

    public AddElementCommand(SVGDocument document, SVGElement element) {
        this.document = document;
        this.element = element;
    }

    @Override
    public void execute() {
        document.addElement(element);
        logger.info("Created a {} element", element.getShape());
    }

    @Override
    public void undo() {
        document.removeElement(element);
        logger.info("Undoing the creation of a {} element", element.getShape());
    }
}