package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.geom.Path2D;
import org.junit.jupiter.api.Test;

class SVGPathTest {

    @Test
    void testAddPathElement() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        Path2D path = new Path2D.Double();
        path.moveTo(100, 100);
        path.lineTo(200, 200);
        path.lineTo(300, 100);
        path.closePath();

        SVGElement element = new SVGPath(
            0, 0,                          // x, y (starting position)
            Color.RED, 1.0,                // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0,         // strokeColor, strokeOpacity, strokeWidth
            path                           // Path2D object
        );

        document.addElement(element);

        assertEquals(1, document.getElements().size());
        assertEquals(element, document.getElements().get(0));
    }

    @Test
    void testAddPathElementOutOfBounds() {
        int width = 1200;
        int height = 800;
        SVGDocument document = new SVGDocument(width, height);

        Path2D path = new Path2D.Double();
        path.moveTo(1300, 900);
        path.lineTo(1400, 1000);
        path.lineTo(1500, 1100);
        path.closePath();

        SVGElement element = new SVGPath(
            0, 0,                          // x, y (starting position)
            Color.RED, 1.0,                // fillColor, fillOpacity
            Color.BLACK, 1.0, 2.0,         // strokeColor, strokeOpacity, strokeWidth
            path                           // Path2D object
        );

        document.addElement(element);

        assertEquals(0, document.getElements().size());
    }
}
