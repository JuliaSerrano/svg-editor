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

        assertEquals(0, document.getElements().size()); // El elemento no debe ser añadido
    }
}
