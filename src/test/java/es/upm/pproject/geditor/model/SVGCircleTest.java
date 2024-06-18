package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SVGCircleTest {

    private SVGDocument document;
    private SVGCircle element;

    @BeforeEach
    void setUp() {
        document = new SVGDocument(1200, 800);
        element = new SVGCircle(100, 100, 50);

        document.addElement(element);
    }

    @Test
    void testAddCircleElement() {
        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }

    @Test
    void testAddCircleElementOutOfBounds() {
    	SVGCircle element2 = new SVGCircle(1300, 900, 50);
        document.addElement(element2);

        assertEquals(1, document.getElements().size()); 
    }
    
    @Test
    void testRemoveCircleElement() {
        assertEquals(1, document.getElements().size());

        document.removeElement(element);
        assertEquals(0, document.getElements().size());
    }

    @Test
    void testToSVGStringCircle() {
        element.setStyles(Color.RED, 1.0, Color.BLACK, 2.0, 1.0);

        String expectedSVGString = "<svg width=\"1200\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">\n\n" +
                "  <rect width=\"100%\" height=\"100%\" fill=\"#ffffff\" />\n\n" +
                "  <circle cx=\"100.00\" cy=\"100.00\" r=\"50.00\" style=\"fill:#ff0000; fill-opacity:1.00; stroke:#000000; stroke-opacity:1.00; stroke-width:2.00\" />\n\n" +
                "</svg>";

        assertEquals(expectedSVGString, document.toSVGString());
    }
    
    @Test
    void testCircleMove() {
        element.move(10, 10);

        assertEquals(110, element.getCx());
        assertEquals(110, element.getCy());
    }

    @Test
    void testCircleIsWithinBounds() {
        assertTrue(element.isWithinBounds(500, 500));
    }
}
