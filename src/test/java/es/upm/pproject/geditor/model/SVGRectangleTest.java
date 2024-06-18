package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class SVGRectangleTest {
	
    @Test
    void testAddRectangleElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);
        
       
        SVGElement element = new SVGRectangle(100, 100, 200, 200);

        document.addElement(element);

        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }
    
    @Test
    void testAddRectangleElementOutOfBounds() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);
        
        
        SVGElement element = new SVGRectangle(1300, 900, 200, 200);

        document.addElement(element);

        assertEquals(0, document.getElements().size()); 
    }
    
    @Test
    void testRemoveRectangleElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGRectangle(100, 100, 200, 200);

        document.addElement(element);
        assertEquals(1, document.getElements().size());

        document.removeElement(element);
        assertEquals(0, document.getElements().size());
    }

    @Test
    void testToSVGStringRectangle() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        SVGElement element = new SVGRectangle(100, 100, 200, 200);
        element.setFillColor(Color.RED);
        element.setStrokeWidth(2.0);

        document.addElement(element);

        String expectedSVGString = "<svg width=\"1200\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">\n\n" +
                "  <rect width=\"100%\" height=\"100%\" fill=\"#ffffff\" />\n\n" +
                "  <rect x=\"100.00\" y=\"100.00\" width=\"200.00\" height=\"200.00\" style=\"fill:#ff0000; fill-opacity:1.00; stroke:#000000; stroke-opacity:1.00; stroke-width:2.00\" />\n\n" +
                "</svg>";

        assertEquals(expectedSVGString, document.toSVGString());
    }
    
    @Test
    void testRectangleMove() {
        SVGRectangle element = new SVGRectangle(100, 100, 200, 200);

        element.move(10, 10);

        assertEquals(110, element.getX());
        assertEquals(110, element.getY());
    }

    @Test
    void testRectangleIsWithinBounds() {
        SVGRectangle element = new SVGRectangle(100, 100, 200, 200);

        assertTrue(element.isWithinBounds(500, 500));
    }

    @Test
    void testRectangleColorToHex() {
        SVGRectangle element = new SVGRectangle(100, 100, 200, 200);
        element.setFillColor(Color.RED);
        element.setStrokeWidth(2.0);

        assertEquals("#ff0000", element.colorToHex(Color.RED));
        assertEquals("#000000", element.colorToHex(Color.BLACK));
        assertEquals("#ffffff", element.colorToHex(Color.WHITE));
    }

}
