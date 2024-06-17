package es.upm.pproject.geditor.controller;

import java.awt.Color;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.geditor.model.SVGDocument;

public class ChangeBgColorCommand implements Command {
    private SVGDocument document;
    private Color newColor;
    private Color oldColor;
    private static final Logger logger = LoggerFactory.getLogger(ChangeBgColorCommand.class);

    public ChangeBgColorCommand(SVGDocument document, Color newColor) {
        this.document = document;
        this.newColor = newColor;
        this.oldColor = document.getBackgroundColor();
    }

    @Override
    public void execute() {
        document.setBackgroundColor(newColor);
        logger.info("Changed background color to {}", newColor);
    }

    @Override
    public void undo() {
        document.setBackgroundColor(oldColor);
        logger.info("Undoing background color from {} to {}", newColor, oldColor);
    }
}