package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class SVGPolygonTest {

    @Test
    void testAddPolygonElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        List<Integer> xPoints = Arrays.asList(100, 200, 300);
        List<Integer> yPoints = Arrays.asList(100, 200, 100);

        SVGElement element = new SVGPolygon(
            xPoints, yPoints, xPoints.size(),  // xPoints, yPoints, numPoints
            Color.RED, 1.0,                    // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0              // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);

        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }

    @Test
    void testAddPolygonElementOutOfBounds() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        List<Integer> xPoints = Arrays.asList(1300, 1400, 1500);
        List<Integer> yPoints = Arrays.asList(900, 1000, 1100);

        SVGElement element = new SVGPolygon(
            xPoints, yPoints, xPoints.size(),  // xPoints, yPoints, numPoints
            Color.RED, 1.0,                    // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0              // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);

        assertEquals(0, document.getElements().size()); 
    }
    
    void testRemovePolygonElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        List<Integer> xPoints = Arrays.asList(100, 200, 300, 400);
        List<Integer> yPoints = Arrays.asList(100, 150, 250, 300);
        int numPoints = xPoints.size();

        SVGElement element = new SVGPolygon(
            xPoints, yPoints, numPoints,       // xPoints, yPoints, numPoints
            Color.RED, 1.0,                    // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0              // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);
        assertEquals(1, document.getElements().size());

        document.removeElement(element);
        assertEquals(0, document.getElements().size());
    }

    @Test
    void testToSVGStringPolygon() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        List<Integer> xPoints = Arrays.asList(100, 200, 300, 400);
        List<Integer> yPoints = Arrays.asList(100, 150, 250, 300);
        int numPoints = xPoints.size();

        SVGElement element = new SVGPolygon(
            xPoints, yPoints, numPoints,       // xPoints, yPoints, numPoints
            Color.RED, 1.0,                    // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0              // strokeColor, strokeOpacity, strokeWidth
        );

        document.addElement(element);

        String expectedSVGString = "<svg width=\"1200\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "<rect width=\"100%\" height=\"100%\" fill=\"#ffffff\" />\n" +
                "<polygon points=\"100,100 200,150 300,250 400,300\" style=\"fill:#ff0000;fill-opacity:1.0;stroke:#000000;stroke-opacity:1.0;stroke-width:2.0\" />\n" +
                "</svg>";

        assertEquals(expectedSVGString.replaceAll("\\s+", ""), document.toSVGString().replaceAll("\\s+", ""));
    }
}
