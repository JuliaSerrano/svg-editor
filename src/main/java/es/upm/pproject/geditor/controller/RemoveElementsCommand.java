package es.upm.pproject.geditor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.model.SVGDocument;

public class RemoveElementsCommand implements Command {
    private SVGDocument document;
    private List<SVGElement> elements;
    private static final Logger logger = LoggerFactory.getLogger(RemoveElementsCommand.class);

    public RemoveElementsCommand(SVGDocument document, List<SVGElement> elements) {
        this.document = document;
        this.elements = new ArrayList<>(elements);
    }

    @Override
    public void execute() {
        for (SVGElement element : elements) {
            document.removeElement(element);
            logger.info("Removed element {}", element.getShape());
        }
    }

    @Override
    public void undo() {
        for (SVGElement element : elements) {
            document.addElement(element);
            logger.info("Undo removing element {}", element.getShape());
        }
    }
}