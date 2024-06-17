package es.upm.pproject.geditor.controller;

import javax.swing.undo.UndoManager;
import javax.swing.undo.CannotUndoException;

public class CommandManager {
    private UndoManager undoManager = new UndoManager();

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
            e.printStackTrace();
        }
    }
}