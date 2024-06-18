package es.upm.pproject.geditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SVGGroupTest {

    private SVGGroup group;
    private SVGRectangle rect;

    @BeforeEach
    void setUp() {
        group = new SVGGroup();
        rect = new SVGRectangle(10, 10, 50, 50);

        group.addElement(rect);
    }

    @Test
    void testAddElement() {
        List<SVGElement> elements = group.getElements();
        assertEquals(1, elements.size());
        assertEquals(rect, elements.get(0));
    }

    @Test
    void testRemoveElement() {
        group.removeElement(rect);

        List<SVGElement> elements = group.getElements();
        assertTrue(elements.isEmpty());
    }

    @Test
    void testToSVGString() {
        rect.setStyles(Color.RED, 1.0, Color.BLACK, 1.0, 1.0);

        SVGRectangle rect2 = new SVGRectangle(70, 10, 50, 50);
        rect2.setStyles(Color.BLUE, 0.5, Color.GREEN, 2, 0.5);

        group.addElement(rect2);

        String expectedSVGString = "<g>" +
                "<rect x=\"10.00\" y=\"10.00\" width=\"50.00\" height=\"50.00\" style=\"fill:#ff0000; fill-opacity:1.00; stroke:#000000; stroke-opacity:1.00; stroke-width:1.00\" />" +
                "<rect x=\"70.00\" y=\"10.00\" width=\"50.00\" height=\"50.00\" style=\"fill:#0000ff; fill-opacity:0.50; stroke:#00ff00; stroke-opacity:0.50; stroke-width:2.00\" />" +
                "</g>";

        assertEquals(expectedSVGString.replaceAll("\\s+", ""), group.toSVGString().replaceAll("\\s+", ""));
    }

    @Test
    void testMove() {
        SVGRectangle rect2 = new SVGRectangle(70, 10, 50, 50);

        group.addElement(rect2);

        group.move(10, 10);

        List<SVGElement> elements = group.getElements();
        assertEquals(20, ((SVGRectangle) elements.get(0)).getX());
        assertEquals(20, ((SVGRectangle) elements.get(0)).getY());
        assertEquals(80, ((SVGRectangle) elements.get(1)).getX());
        assertEquals(20, ((SVGRectangle) elements.get(1)).getY());
    }

    @Test
    void testIsWithinBounds() {
        SVGRectangle rect2 = new SVGRectangle(70, 10, 50, 50);

        group.addElement(rect);
        group.addElement(rect2);

        assertTrue(group.isWithinBounds(500, 500));
        assertFalse(group.isWithinBounds(100, 100));
    }

    @Test
    void testSetFillColor() {
        SVGRectangle rect2 = new SVGRectangle(70, 10, 50, 50);

        group.addElement(rect2);

        group.setFillColor(Color.YELLOW);

        List<SVGElement> elements = group.getElements();
        assertEquals(Color.YELLOW, elements.get(0).getFillColor());
        assertEquals(Color.YELLOW, elements.get(1).getFillColor());
    }

    @Test
    void testSetFillOpacity() {
        SVGRectangle rect2 = new SVGRectangle(70, 10, 50, 50);

        group.addElement(rect2);

        group.setFillOpacity(0.8);

        List<SVGElement> elements = group.getElements();
        assertEquals(0.8, elements.get(0).getFillOpacity());
        assertEquals(0.8, elements.get(1).getFillOpacity());
    }

    @Test
    void testSetStrokeColor() {
        SVGRectangle rect2 = new SVGRectangle(70, 10, 50, 50);

        group.addElement(rect2);

        group.setStrokeColor(Color.PINK);

        List<SVGElement> elements = group.getElements();
        assertEquals(Color.PINK, elements.get(0).getStrokeColor());
        assertEquals(Color.PINK, elements.get(1).getStrokeColor());
    }

    @Test
    void testSetStrokeOpacity() {
        SVGRectangle rect2 = new SVGRectangle(70, 10, 50, 50);

        group.addElement(rect2);

        group.setStrokeOpacity(0.6);

        List<SVGElement> elements = group.getElements();
        assertEquals(0.6, elements.get(0).getStrokeOpacity());
        assertEquals(0.6, elements.get(1).getStrokeOpacity());
    }

    @Test
    void testSetStrokeWidth() {
        SVGRectangle rect2 = new SVGRectangle(70, 10, 50, 50);

        group.addElement(rect2);

        group.setStrokeWidth(3.0);

        List<SVGElement> elements = group.getElements();
        assertEquals(3.0, elements.get(0).getStrokeWidth());
        assertEquals(3.0, elements.get(1).getStrokeWidth());
    }
}
