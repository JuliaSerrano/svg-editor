package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class SVGPolylineTest {

    @Test
    void testAddPolylineElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        List<Integer> xPoints = Arrays.asList(100, 200, 300);
        List<Integer> yPoints = Arrays.asList(100, 200, 100);

        SVGElement element = new SVGPolyline(xPoints, yPoints);

        document.addElement(element);

        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }

    @Test
    void testAddPolylineElementOutOfBounds() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        List<Integer> xPoints = Arrays.asList(1300, 1400, 1500);
        List<Integer> yPoints = Arrays.asList(900, 1000, 1100);

        SVGElement element = new SVGPolyline(xPoints, yPoints);

        document.addElement(element);

        assertEquals(0, document.getElements().size()); 
    }
    
    @Test
    void testRemovePolylineElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        List<Integer> xPoints = Arrays.asList(100, 200, 300, 400);
        List<Integer> yPoints = Arrays.asList(100, 150, 250, 300);

        SVGElement element = new SVGPolyline(xPoints, yPoints);

        document.addElement(element);
        assertEquals(1, document.getElements().size());

        document.removeElement(element);
        assertEquals(0, document.getElements().size());
    }

    @Test
    void testToSVGStringPolyline() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        List<Integer> xPoints = Arrays.asList(100, 200, 300, 400);
        List<Integer> yPoints = Arrays.asList(100, 150, 250, 300);

        SVGElement element = new SVGPolyline(xPoints, yPoints);
        element.setStrokeWidth(2.0);

        document.addElement(element);

        String expectedSVGString = "<svg width=\"1200\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">\n\n" +
                "  <rect width=\"100%\" height=\"100%\" fill=\"#ffffff\" />\n\n" +
                "  <polyline points=\"100,100 200,150 300,250 400,300\" style=\"fill:none; stroke:#000000; stroke-opacity:1.00; stroke-width:2.00\" />\n\n" +
                "</svg>";

        assertEquals(expectedSVGString, document.toSVGString());
    }
    
    @Test
    void testPolylineMove() {
        List<Integer> xPoints = Arrays.asList(100, 200, 300, 400);
        List<Integer> yPoints = Arrays.asList(100, 150, 250, 300);

        SVGPolyline element = new SVGPolyline(xPoints, yPoints);

        element.move(10, 10);

        List<Integer> expectedXPoints = Arrays.asList(110, 210, 310, 410);
        List<Integer> expectedYPoints = Arrays.asList(110, 160, 260, 310);

        assertEquals(expectedXPoints, element.getXPoints());
        assertEquals(expectedYPoints, element.getYPoints());
    }

    @Test
    void testPolylineIsWithinBounds() {
        List<Integer> xPoints = Arrays.asList(100, 200, 300, 400);
        List<Integer> yPoints = Arrays.asList(100, 150, 250, 300);

        SVGPolyline element = new SVGPolyline(xPoints, yPoints );

        assertTrue(element.isWithinBounds(500, 500));
    }

    @Test
    void testPolylineColorToHex() {
        List<Integer> xPoints = Arrays.asList(100, 200, 300, 400);
        List<Integer> yPoints = Arrays.asList(100, 150, 250, 300);

        SVGPolyline element = new SVGPolyline(xPoints, yPoints);
        element.setStrokeWidth(2.0);

        assertEquals("#ff0000", element.colorToHex(Color.RED));
        assertEquals("#000000", element.colorToHex(Color.BLACK));
        assertEquals("#ffffff", element.colorToHex(Color.WHITE));
    }
}
