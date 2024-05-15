package es.upm.pproject.geditor;

import es.upm.pproject.geditor.controller.SVGEditorController;
import es.upm.pproject.geditor.model.SVGModel;
import es.upm.pproject.geditor.view.SVGEditorView;

public class Main {
    public static void main(String[] args) {
        SVGModel model = new SVGModel(1200, 800);
        SVGEditorView view = new SVGEditorView();
        SVGEditorController controller = new SVGEditorController(model, view);
    }
}
