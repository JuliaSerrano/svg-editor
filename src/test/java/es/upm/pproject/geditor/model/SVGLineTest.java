package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class SVGLineTest {

    @Test
    void testAddLineElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGLine(100, 100, 300, 300);

        document.addElement(element);

        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }

    @Test
    void testAddLineElementOutOfBounds() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGLine(1300, 900, 1500, 1100);

        document.addElement(element);

        assertEquals(0, document.getElements().size());
    }
    
    @Test
    void testRemoveLineElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGLine(100, 100, 300, 300);

        document.addElement(element);
        assertEquals(1, document.getElements().size());

        document.removeElement(element);
        assertEquals(0, document.getElements().size());
    }

    @Test
    void testToSVGStringLine() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGLine(100, 100, 300, 300);

        element.setStrokeWidth(2.0);

        document.addElement(element);

        String expectedSVGString = "<svg width=\"1200\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">\n\n" +
                "  <rect width=\"100%\" height=\"100%\" fill=\"#ffffff\" />\n\n" +
                "  <line x1=\"100.00\" y1=\"100.00\" x2=\"300.00\" y2=\"300.00\" style=\"stroke:#000000; stroke-opacity:1.00; stroke-width:2.00\" />\n\n" +
                "</svg>";

        assertEquals(expectedSVGString, document.toSVGString());
    }
   
    @Test
    void testLineMove() {
        SVGLine element = new SVGLine(100, 100, 200, 200);

        element.move(10, 10);

        assertEquals(110, element.getX());
        assertEquals(110, element.getY());
        assertEquals(210, element.getX2());
        assertEquals(210, element.getY2());
    }

    @Test
    void testLineIsWithinBounds() {
        SVGLine element = new SVGLine(100, 100, 200, 200);

        assertTrue(element.isWithinBounds(500, 500));
    }

    @Test
    void testLineColorToHex() {
        SVGLine element = new SVGLine(100, 100, 200, 200);

        element.setStrokeWidth(2.0);

        assertEquals("#ff0000", element.colorToHex(Color.RED));
        assertEquals("#000000", element.colorToHex(Color.BLACK));
        assertEquals("#ffffff", element.colorToHex(Color.WHITE));
    }
    
}
