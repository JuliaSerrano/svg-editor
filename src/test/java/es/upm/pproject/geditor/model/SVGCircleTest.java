package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class SVGCircleTest {

    @Test
    void testAddCircleElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGCircle(
            100, 100, 50,                    // x, y, radius
            Color.RED, 1.0,                  // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0            // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);

        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }

    @Test
    void testAddCircleElementOutOfBounds() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGCircle(
            1300, 900, 50,                  // x, y, radius
            Color.RED, 1.0,                 // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0           // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);

        assertEquals(0, document.getElements().size()); 
    }
    
    @Test
    void testRemoveCircleElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGCircle(
            100, 100, 50,                   // x, y, radius
            Color.RED, 1.0,                 // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0           // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);
        assertEquals(1, document.getElements().size());

        document.removeElement(element);
        assertEquals(0, document.getElements().size());
    }

    @Test
    void testToSVGStringCircle() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGCircle(
            100, 100, 50,                   // x, y, radius
            Color.RED, 1.0,                 // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0           // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);

        String expectedSVGString = "<svg width=\"1200\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">\n\n" +
                "  <rect width=\"100%\" height=\"100%\" fill=\"#ffffff\" />\n\n" +
                "  <circle cx=\"100.00\" cy=\"100.00\" r=\"50.00\" style=\"fill:#ff0000; fill-opacity:1.00; stroke:#000000; stroke-opacity:1.00; stroke-width:2.00\" />\n\n" +
                "</svg>";

        assertEquals(expectedSVGString, document.toSVGString());
    }
}
