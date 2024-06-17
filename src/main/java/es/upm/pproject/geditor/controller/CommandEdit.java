package es.upm.pproject.geditor.controller;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;

public class CommandEdit extends AbstractUndoableEdit {
    private transient Command command;

    public CommandEdit(Command command) {
        this.command = command;
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        command.undo();
    }
}