package es.upm.pproject.geditor.view.ui;

import java.awt.Shape;
import java.awt.event.MouseEvent;

public interface ShapeCreator {
    void startShape(MouseEvent e);

    void updateShape(MouseEvent e);

    void finishShape(MouseEvent e);

    Shape getCurrentShape();

}
