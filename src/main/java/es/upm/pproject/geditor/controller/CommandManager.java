package es.upm.pproject.geditor.controller;

import javax.swing.undo.UndoManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.undo.CannotUndoException;

public class CommandManager {
    private UndoManager undoManager = new UndoManager();
    private static final Logger logger = LoggerFactory.getLogger(CommandManager.class);

    public void executeCommand(Command command) {
        command.execute();
        undoManager.addEdit(new CommandEdit(command));
    }

    public void undo() {
        try {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        } catch (CannotUndoException e) {
            logger.error(e.getMessage());
        }
    }
}