package es.upm.pproject.geditor.controller;

public interface Command {
    void execute();
    void undo();
}