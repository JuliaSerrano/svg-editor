package es.upm.pproject.geditor.model;

public class SVGModel {
    private SVGDocument document;

    public SVGModel(int width, int height) {
        this.document = new SVGDocument(width, height);
    }

    public SVGDocument getDocument() {
        return document;
    }

    public void addElement(SVGElement element) {
        document.addElement(element);
    }

    public void removeElement(SVGElement element) {
        document.removeElement(element);
    }
}
