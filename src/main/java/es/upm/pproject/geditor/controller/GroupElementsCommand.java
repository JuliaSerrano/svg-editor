package es.upm.pproject.geditor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.geditor.model.SVGDocument;
import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.model.SVGGroup;

public class GroupElementsCommand implements Command {
    private SVGDocument document;
    private List<SVGElement> elements;
    private SVGGroup group;
    private static final Logger logger = LoggerFactory.getLogger(GroupElementsCommand.class);

    public GroupElementsCommand(SVGDocument document, List<SVGElement> elements) {
        this.document = document;
        this.elements = new ArrayList<>(elements);
        this.group = new SVGGroup();
    }

    @Override
    public void execute() {
        boolean hasGroup = elements.stream().anyMatch(SVGGroup.class::isInstance);

        if (hasGroup) {
            JOptionPane.showMessageDialog(null,
                    "Cannot group elements because groups cannot be nested.",
                    "Grouping Error", JOptionPane.WARNING_MESSAGE);
            logger.warn("Cannot group elements because there is an SVGGroup in the list.");
            return;
        }
        for (SVGElement element : elements) {
            document.removeElement(element);
            group.addElement(element);
        }
        document.addElement(group);

        logger.info("Grouped {} elements", elements.size());
    }

    @Override
    public void undo() {
        document.removeElement(group);
        for (SVGElement element : elements) {
            document.addElement(element);
        }

        logger.info("Undo grouping of {} elements", elements.size());
    }

    public SVGGroup getGroup() {
        return group;
    }
}