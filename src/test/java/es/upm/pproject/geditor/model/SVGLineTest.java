package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SVGLineTest {

    private SVGDocument document;
    private SVGLine element;

    @BeforeEach
    void setUp() {
        document = new SVGDocument(1200, 800);
        element = new SVGLine(100, 100, 300, 300);

        document.addElement(element);
    }


    @Test
    void testAddLineElement() {
        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }

    @Test
    void testAddLineElementOutOfBounds() {
        SVGLine element2 = new SVGLine(1300, 900, 1500, 1100);

        document.addElement(element2);
        assertEquals(1, document.getElements().size());
    }
    
    @Test
    void testRemoveLineElement() {
        assertEquals(1, document.getElements().size());

        document.removeElement(element);
        assertEquals(0, document.getElements().size());
    }

    @Test
    void testToSVGStringLine() {
        element.setStrokeWidth(2.0);

        String expectedSVGString = "<svg width=\"1200\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">\n\n" +
                "  <rect width=\"100%\" height=\"100%\" fill=\"#ffffff\" />\n\n" +
                "  <line x1=\"100.00\" y1=\"100.00\" x2=\"300.00\" y2=\"300.00\" style=\"stroke:#000000; stroke-opacity:1.00; stroke-width:2.00\" />\n\n" +
                "</svg>";

        assertEquals(expectedSVGString, document.toSVGString());
    }
   
    @Test
    void testLineMove() {
        element.move(10, 10);

        assertEquals(110, element.getX());
        assertEquals(110, element.getY());
        assertEquals(310, element.getX2());
        assertEquals(310, element.getY2());
    }

    @Test
    void testLineIsWithinBounds() {
        assertTrue(element.isWithinBounds(500, 500));
    }
}
