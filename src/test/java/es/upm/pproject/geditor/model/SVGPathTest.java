package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

class SVGPathTest {

    private SVGDocument document;
    private SVGPath element;

    @BeforeEach
    void setUp() {
        document = new SVGDocument(1200, 800);

        Path2D path = new Path2D.Double();
        path.moveTo(100, 100);
        path.lineTo(200, 200);
        path.lineTo(300, 100);
        path.closePath();

        element = new SVGPath(0, 0, path);

        document.addElement(element);
    }

    @Test
    void testAddPathElement() {
        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }

    @Test
    void testAddPathElementOutOfBounds() {
        Path2D path = new Path2D.Double();
        path.moveTo(1300, 900);
        path.lineTo(1400, 1000);
        path.lineTo(1500, 1100);
        path.closePath();

        SVGElement element2 = new SVGPath(0, 0, path);

        document.addElement(element2);

        assertEquals(1, document.getElements().size());
    }
    
    @Test
    void testRemovePathElement() {
        assertEquals(1, document.getElements().size());

        document.removeElement(element);
        assertEquals(0, document.getElements().size());
    }

    @Test
    void testToSVGStringPath() {
        element.setFillColor(Color.RED);
        element.setStrokeWidth(2.0);

        String expectedSVGString = "<svg width=\"1200\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "<rect width=\"100%\" height=\"100%\" fill=\"#ffffff\" />\n" +
                "<path d=\"M100.0 100.0 L200.0 200.0 L300.0 100.0 Z\" style=\"fill:#ff0000;fill-opacity:1.0;stroke:#000000;stroke-opacity:1.0;stroke-width:2.0\" />\n" +
                "</svg>";

        assertEquals(expectedSVGString.replaceAll("\\s+", ""), document.toSVGString().replaceAll("\\s+", ""));
    }
    
    @Test
    void testPathMove() {
        element.move(10, 10);

        Path2D expectedPath = new Path2D.Double();
        expectedPath.moveTo(110, 110);
        expectedPath.lineTo(210, 210);
        expectedPath.lineTo(310, 110);
        expectedPath.closePath();

        PathIterator expectedIterator = expectedPath.getPathIterator(null);
        PathIterator actualIterator = element.getPath().getPathIterator(null);
        float[] expectedCoords = new float[6];
        float[] actualCoords = new float[6];
        while (!expectedIterator.isDone() && !actualIterator.isDone()) {
            int expectedSegment = expectedIterator.currentSegment(expectedCoords);
            int actualSegment = actualIterator.currentSegment(actualCoords);
            assertEquals(expectedSegment, actualSegment);
            for (int i = 0; i < 6; i++) {
                assertEquals(expectedCoords[i], actualCoords[i], 0.01);
            }
            expectedIterator.next();
            actualIterator.next();
        }
    }

    @Test
    void testPathIsWithinBounds() {
        assertTrue(element.isWithinBounds(500, 500));
    }

    @Test
    void testPathGetPath() {
        Path2D expectedPath = new Path2D.Double();
        expectedPath.moveTo(100, 100);
        expectedPath.lineTo(200, 200);
        expectedPath.lineTo(300, 100);
        expectedPath.closePath();

        PathIterator expectedIterator = expectedPath.getPathIterator(null);
        PathIterator actualIterator = element.getPath().getPathIterator(null);
        float[] expectedCoords = new float[6];
        float[] actualCoords = new float[6];
        while (!expectedIterator.isDone() && !actualIterator.isDone()) {
            int expectedSegment = expectedIterator.currentSegment(expectedCoords);
            int actualSegment = actualIterator.currentSegment(actualCoords);
            assertEquals(expectedSegment, actualSegment);
            for (int i = 0; i < 6; i++) {
                assertEquals(expectedCoords[i], actualCoords[i], 0.01);
            }
            expectedIterator.next();
            actualIterator.next();
        }
    }

    @Test
    void testPathSetPath() {
        Path2D path = new Path2D.Double();
        path.moveTo(200, 200);
        path.lineTo(300, 300);
        path.lineTo(400, 200);
        path.closePath();

        element.setPath(path);

        PathIterator expectedIterator = path.getPathIterator(null);
        PathIterator actualIterator = element.getPath().getPathIterator(null);
        float[] expectedCoords = new float[6];
        float[] actualCoords = new float[6];
        while (!expectedIterator.isDone() && !actualIterator.isDone()) {
            int expectedSegment = expectedIterator.currentSegment(expectedCoords);
            int actualSegment = actualIterator.currentSegment(actualCoords);
            assertEquals(expectedSegment, actualSegment);
            for (int i = 0; i < 6; i++) {
                assertEquals(expectedCoords[i], actualCoords[i], 0.01);
            }
            expectedIterator.next();
            actualIterator.next();
        }
    }
}
