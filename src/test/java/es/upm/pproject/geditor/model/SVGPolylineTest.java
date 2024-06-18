package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SVGPolylineTest {

    private SVGDocument document;
    private SVGPolyline element;

    @BeforeEach
    void setUp() {
        document = new SVGDocument(1200, 800);

        List<Integer> xPoints = Arrays.asList(100, 200, 300);
        List<Integer> yPoints = Arrays.asList(100, 200, 100);
        element = new SVGPolyline(xPoints, yPoints);

        document.addElement(element);
    }

    @Test
    void testAddPolylineElement() {
        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }

    @Test
    void testAddPolylineElementOutOfBounds() {
        List<Integer> xPoints = Arrays.asList(1300, 1400, 1500);
        List<Integer> yPoints = Arrays.asList(900, 1000, 1100);

        SVGElement element2 = new SVGPolyline(xPoints, yPoints);

        document.addElement(element2);

        assertEquals(1, document.getElements().size()); 
    }
    
    @Test
    void testRemovePolylineElement() {
        assertEquals(1, document.getElements().size());

        document.removeElement(element);
        assertEquals(0, document.getElements().size());
    }

    @Test
    void testToSVGStringPolyline() {
        element.setStrokeWidth(2.0);

        String expectedSVGString = "<svg width=\"1200\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">\n\n" +
                "  <rect width=\"100%\" height=\"100%\" fill=\"#ffffff\" />\n\n" +
                "  <polyline points=\"100,100 200,200 300,100\" style=\"fill:none; stroke:#000000; stroke-opacity:1.00; stroke-width:2.00\" />\n\n" +
                "</svg>";

        assertEquals(expectedSVGString, document.toSVGString());
    }
    
    @Test
    void testPolylineMove() {
        element.move(10, 10);

        List<Integer> expectedXPoints = Arrays.asList(110, 210, 310);
        List<Integer> expectedYPoints = Arrays.asList(110, 210, 110);

        assertEquals(expectedXPoints, element.getXPoints());
        assertEquals(expectedYPoints, element.getYPoints());
    }

    @Test
    void testPolylineIsWithinBounds() {
        assertTrue(element.isWithinBounds(500, 500));
    }
}
