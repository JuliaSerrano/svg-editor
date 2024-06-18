package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class SVGLineTest {

    @Test
    void testAddLineElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGLine(
            100, 100, 300, 300,              // x1, y1, x2, y2
            Color.BLACK, 1.0, 2.0            // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);

        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }

    @Test
    void testAddLineElementOutOfBounds() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGLine(
            1300, 900, 1500, 1100,           // x1, y1, x2, y2
            Color.BLACK, 1.0, 2.0            // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);

        assertEquals(0, document.getElements().size()); // El elemento no debe ser añadido
    }
}
