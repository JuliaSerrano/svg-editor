package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SVGGroup extends SVGElement {
    private List<SVGElement> elements;

    public SVGGroup() {
        super(0, 0, Color.BLACK, 1.0, Color.BLACK, 1.0, 1.0); // Default styles
        this.elements = new ArrayList<>();
    }

    public void addElement(SVGElement element) {
        elements.add(element);
    }

    public void removeElement(SVGElement element) {
        elements.remove(element);
    }

    public List<SVGElement> getElements() {
        return elements;
    }

    @Override
    public String toSVGString() {
        StringBuilder sb = new StringBuilder("<g>");
        for (SVGElement element : elements) {
            sb.append(element.toSVGString());
        }
        sb.append("</g>");
        return sb.toString();
    }

    @Override
    public void move(double dx, double dy) {
        for (SVGElement element : elements) {
            element.move(dx, dy);
        }
    }

    public boolean isWithinBounds(int width, int height) {
        for (SVGElement element : elements) {
            if (!element.isWithinBounds(width, height)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setFillColor(Color fillColor) {
        for (SVGElement element : elements) {
            element.setFillColor(fillColor);
        }
        super.setFillColor(fillColor);
    }

    @Override
    public void setFillOpacity(double fillOpacity) {
        for (SVGElement element : elements) {
            element.setFillOpacity(fillOpacity);
        }
        super.setFillOpacity(fillOpacity);
    }

    @Override
    public void setStrokeColor(Color strokeColor) {
        for (SVGElement element : elements) {
            element.setStrokeColor(strokeColor);
        }
        super.setStrokeColor(strokeColor);
    }

    @Override
    public void setStrokeOpacity(double strokeOpacity) {
        for (SVGElement element : elements) {
            element.setStrokeOpacity(strokeOpacity);
        }
        super.setStrokeOpacity(strokeOpacity);
    }

    @Override
    public void setStrokeWidth(double strokeWidth) {
        for (SVGElement element : elements) {
            element.setStrokeWidth(strokeWidth);
        }
        super.setStrokeWidth(strokeWidth);
    }
}
