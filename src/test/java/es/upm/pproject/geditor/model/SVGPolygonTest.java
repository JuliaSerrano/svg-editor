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
}
