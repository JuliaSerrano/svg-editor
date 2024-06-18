package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class SVGRectangleTest {
	
    @Test
    void testAddRectangleElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);
        
       
        SVGElement element = new SVGRectangle(
            100, 100, 200, 200,                // x, y, width, height
            Color.RED, 1.0,                    // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0              // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);

        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }
    
    @Test
    void testAddRectangleElementOutOfBounds() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);
        
        
        SVGElement element = new SVGRectangle(
            1300, 900, 200, 200,               // x, y, width, height
            Color.RED, 1.0,                    // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0              // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);

        assertEquals(0, document.getElements().size()); 
    }

}
