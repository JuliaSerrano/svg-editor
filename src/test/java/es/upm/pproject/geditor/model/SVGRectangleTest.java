package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SVGRectangleTest {
	
    private SVGDocument document;
    private SVGRectangle element;

    @BeforeEach
    void setUp() {
        document = new SVGDocument(1200, 800);
        element = new SVGRectangle(100, 100, 200, 200);

        document.addElement(element);
    }

    @Test
    void testAddRectangleElement() {
        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }
    
    @Test
    void testAddRectangleElementOutOfBounds() {
        SVGElement element2 = new SVGRectangle(1300, 900, 200, 200);

        document.addElement(element2);

        assertEquals(1, document.getElements().size()); 
    }
    
    @Test
    void testRemoveRectangleElement() {
        assertEquals(1, document.getElements().size());

        document.removeElement(element);
        assertEquals(0, document.getElements().size());
    }

    @Test
    void testToSVGStringRectangle() {
        element.setFillColor(Color.RED);
        element.setStrokeWidth(2.0);

        String expectedSVGString = "<svg width=\"1200\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">\n\n" +
                "  <rect width=\"100%\" height=\"100%\" fill=\"#ffffff\" />\n\n" +
                "  <rect x=\"100.00\" y=\"100.00\" width=\"200.00\" height=\"200.00\" style=\"fill:#ff0000; fill-opacity:1.00; stroke:#000000; stroke-opacity:1.00; stroke-width:2.00\" />\n\n" +
                "</svg>";

        assertEquals(expectedSVGString, document.toSVGString());
    }
    
    @Test
    void testRectangleMove() {
        element.move(10, 10);

        assertEquals(110, element.getX());
        assertEquals(110, element.getY());
    }

    @Test
    void testRectangleIsWithinBounds() {
        assertTrue(element.isWithinBounds(500, 500));
    }

    @Test
    void testRectangleGetWidth() {
        assertEquals(200, element.getWidth());
    }

    @Test
    void testRectangleSetWidth() {
        element.setWidth(300);
        assertEquals(300, element.getWidth());
    }

    @Test
    void testRectangleGetHeight() {
        assertEquals(200, element.getHeight());
    }

    @Test
    void testRectangleSetHeight() {
        element.setHeight(300);
        assertEquals(300, element.getHeight());
    }
}
