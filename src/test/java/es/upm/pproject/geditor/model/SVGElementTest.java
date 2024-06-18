package es.upm.pproject.geditor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.*;

class SVGElementTest {

    private SVGElement svgElement;

    @BeforeEach
    void setUp() {
        svgElement = new SVGElement(10, 20) {
            @Override
            public String toSVGString() {
                return null;
            }

            @Override
            public void move(double dx, double dy) {
            }

            @Override
            public boolean isWithinBounds(int width, int height) {
                return false;
            }
        };
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(10, svgElement.getX());
        assertEquals(20, svgElement.getY());
        assertEquals(Color.BLACK, svgElement.getFillColor());
        assertEquals(1.0, svgElement.getFillOpacity());
        assertEquals(Color.BLACK, svgElement.getStrokeColor());
        assertEquals(1.0, svgElement.getStrokeWidth());
        assertEquals(1.0, svgElement.getStrokeOpacity());

        Shape shape = new Rectangle2D.Double(0, 0, 100, 100);
        svgElement.setShape(shape);
        assertEquals(shape, svgElement.getShape());

        svgElement.setX(30);
        svgElement.setY(40);
        assertEquals(30, svgElement.getX());
        assertEquals(40, svgElement.getY());

        svgElement.setFillColor(Color.RED);
        svgElement.setFillOpacity(0.5);
        assertEquals(Color.RED, svgElement.getFillColor());
        assertEquals(0.5, svgElement.getFillOpacity());

        svgElement.setStrokeColor(Color.BLUE);
        svgElement.setStrokeWidth(2.0);
        svgElement.setStrokeOpacity(0.8);
        assertEquals(Color.BLUE, svgElement.getStrokeColor());
        assertEquals(2.0, svgElement.getStrokeWidth());
        assertEquals(0.8, svgElement.getStrokeOpacity());
    }

    @Test
    void testColorToHex() {
        assertEquals("#ff0000", svgElement.colorToHex(Color.RED));
        assertEquals("#00ff00", svgElement.colorToHex(Color.GREEN));
        assertEquals("#0000ff", svgElement.colorToHex(Color.BLUE));
    }
}