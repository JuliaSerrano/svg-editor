package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SVGPolygonTest {

    private SVGDocument document;
    private SVGPolygon element;

    @BeforeEach
    void setUp() {
        document = new SVGDocument(1200, 800);

        List<Integer> xPoints = Arrays.asList(100, 200, 300);
        List<Integer> yPoints = Arrays.asList(100, 200, 100);
        element = new SVGPolygon(xPoints, yPoints, xPoints.size());

        document.addElement(element);
    }

    @Test
    void testAddPolygonElement() {
        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }

    @Test
    void testAddPolygonElementOutOfBounds() {
        List<Integer> xPoints = Arrays.asList(1300, 1400, 1500);
        List<Integer> yPoints = Arrays.asList(900, 1000, 1100);

        SVGElement element2 = new SVGPolygon(xPoints, yPoints, xPoints.size());

        document.addElement(element2);

        assertEquals(1, document.getElements().size()); 
    }
    
    void testRemovePolygonElement() {
        document.addElement(element);
        assertEquals(1, document.getElements().size());

        document.removeElement(element);
        assertEquals(0, document.getElements().size());
    }

    @Test
    void testToSVGStringPolygon() {
        element.setFillColor(Color.RED);
        element.setStrokeWidth(2.0);

        String expectedSVGString = "<svg width=\"1200\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "<rect width=\"100%\" height=\"100%\" fill=\"#ffffff\" />\n" +
                "<polygon points=\"100,100 200,200 300,100\" style=\"fill:#ff0000;fill-opacity:1.0;stroke:#000000;stroke-opacity:1.0;stroke-width:2.0\" />\n" +
                "</svg>";

        assertEquals(expectedSVGString.replaceAll("\\s+", ""), document.toSVGString().replaceAll("\\s+", ""));
    }
    
    @Test
    void testPolygonMove() {
        element.move(10, 10);

        List<Integer> expectedXPoints = Arrays.asList(110, 210, 310);
        List<Integer> expectedYPoints = Arrays.asList(110, 210, 110);

        assertEquals(expectedXPoints, element.getXPoints());
        assertEquals(expectedYPoints, element.getYPoints());
    }

    @Test
    void testPolygonIsWithinBounds() {
        assertTrue(element.isWithinBounds(500, 500));
    }

    @Test
    void testPolygonGetXPoints() {
        List<Integer> expectedXPoints = Arrays.asList(100, 200, 300);
        assertEquals(expectedXPoints, element.getXPoints());
    }

    @Test
    void TestPolygonSetXPoints() {
        List<Integer> expectedXPoints = Arrays.asList(200, 300, 400);
        element.setXPoints(expectedXPoints);
        assertEquals(expectedXPoints, element.getXPoints());
    }

    @Test
    void testPolygonGetYPoints() {
        List<Integer> expectedYPoints = Arrays.asList(100, 200, 100);
        assertEquals(expectedYPoints, element.getYPoints());
    }

    @Test
    void testPolygonSetYPoints() {
        List<Integer> expectedYPoints = Arrays.asList(200, 300, 200);
        element.setYPoints(expectedYPoints);
        assertEquals(expectedYPoints, element.getYPoints());
    }

    @Test
    void testPolygonGetNumPoints() {
        assertEquals(3, element.getNumPoints());
    }

    @Test
    void testPolygonSetNumPoints() {
        element.setNumPoints(4);
        assertEquals(4, element.getNumPoints());
    }
}
