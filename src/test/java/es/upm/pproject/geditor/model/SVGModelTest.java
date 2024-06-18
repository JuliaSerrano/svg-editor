package es.upm.pproject.geditor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SVGModelTest {

    private SVGModel model;

    @BeforeEach
    void setUp() {
        model = new SVGModel(800, 600);
    }

    @Test
    void testGetDocument() {
        SVGDocument document = model.getDocument();
        assertNotNull(document);
        assertEquals(800, document.getWidth());
        assertEquals(600, document.getHeight());
    }

    @Test
    void testAddElement() {
        SVGElement element = new SVGRectangle(10, 20, 100, 50);
        model.addElement(element);

        SVGDocument document = model.getDocument();
        assertEquals(1, document.getElements().size());
        assertTrue(document.getElements().contains(element));
    }

    @Test
    void testRemoveElement() {
        SVGElement element1 = new SVGRectangle(10, 20, 100, 50);
        SVGElement element2 = new SVGCircle(50, 50, 30);
        model.addElement(element1);
        model.addElement(element2);

        model.removeElement(element1);

        SVGDocument document = model.getDocument();
        assertFalse(document.getElements().contains(element1));
        assertTrue(document.getElements().contains(element2));
    }
}