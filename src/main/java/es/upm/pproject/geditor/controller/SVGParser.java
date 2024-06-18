package es.upm.pproject.geditor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.Color;
import java.awt.geom.Path2D;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

import es.upm.pproject.geditor.model.SVGCircle;
import es.upm.pproject.geditor.model.SVGElement;
import es.upm.pproject.geditor.model.SVGEllipse;
import es.upm.pproject.geditor.model.SVGGroup;
import es.upm.pproject.geditor.model.SVGLine;
import es.upm.pproject.geditor.model.SVGModel;
import es.upm.pproject.geditor.model.SVGPath;
import es.upm.pproject.geditor.model.SVGPolygon;
import es.upm.pproject.geditor.model.SVGPolyline;
import es.upm.pproject.geditor.model.SVGRectangle;

public class SVGParser {

    private SVGParser() {
        // private constructor to hide the implicit public one
    }

    public static void parseDocument(SVGDocument document, SVGModel model) {
        // Process the SVGDocument and update the model
        NodeList svgElements = document.getDocumentElement().getElementsByTagName("*");

        // First is the background color
        Element bgElement = (Element) svgElements.item(0);
        String bgColor = bgElement.getAttribute("fill");
        model.getDocument().setBackgroundColor(parseColor(bgColor, Color.WHITE));

        // Set to keep track of processed elements to avoid duplication for elements
        // within groups
        Set<Element> processedElements = new HashSet<>();

        for (int i = 1; i < svgElements.getLength(); i++) {
            Element element = (Element) svgElements.item(i);
            if (!processedElements.contains(element)) {
                SVGElement svgElement = parseElement(element, processedElements);
                if (svgElement != null) {
                    model.getDocument().addElement(svgElement);
                }
            }
        }
    }

    private static SVGElement parseElement(Element element, Set<Element> processedElements) {
        String tagName = element.getTagName();

        // Style attributes
        Map<String, String> styleMap = parseStyle(element.getAttribute("style"));

        Color fillColor = parseColor(styleMap.get("fill"), Color.BLACK);
        Double fillOpacity = parseDouble(styleMap.get("fill-opacity"), 1.0);
        Color strokeColor = parseColor(styleMap.get("stroke"), Color.BLACK);
        Double strokeOpacity = parseDouble(styleMap.get("stroke-opacity"), 1.0);
        Double strokeWidth = parseDouble(styleMap.get("stroke-width"), 1.0);

        // direct style attributes
        fillColor = parseColor(element.getAttribute("fill"), fillColor);
        fillOpacity = parseDouble(element.getAttribute("fill-opacity"), fillOpacity);
        strokeColor = parseColor(element.getAttribute("stroke"), strokeColor);
        strokeOpacity = parseDouble(element.getAttribute("stroke-opacity"), strokeOpacity);
        strokeWidth = parseDouble(element.getAttribute("stroke-width"), strokeWidth);

        switch (tagName) {
            case "rect":
                return parseRect(element, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
            case "circle":
                return parseCircle(element, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
            case "ellipse":
                return parseEllipse(element, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
            case "line":
                return parseLine(element, strokeColor, strokeOpacity, strokeWidth);
            case "polyline":
                return parsePolyline(element, strokeColor, strokeOpacity, strokeWidth);
            case "polygon":
                return parsePolygon(element, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
            case "path":
                return parsePath(element, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
            case "g":
                return parseGroup(element, processedElements);
            default:
                throw new IllegalArgumentException(tagName);
        }
    }

    private static SVGRectangle parseRect(Element element, Color fillColor, Double fillOpacity, Color strokeColor,
            Double strokeOpacity, Double strokeWidth) {
        double x = Double.parseDouble(element.getAttribute("x"));
        double y = Double.parseDouble(element.getAttribute("y"));
        double width = Double.parseDouble(element.getAttribute("width"));
        double height = Double.parseDouble(element.getAttribute("height"));
        return new SVGRectangle(x, y, width, height, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
    }

    private static SVGCircle parseCircle(Element element, Color fillColor, Double fillOpacity, Color strokeColor,
            Double strokeOpacity, Double strokeWidth) {
        double cx = Double.parseDouble(element.getAttribute("cx"));
        double cy = Double.parseDouble(element.getAttribute("cy"));
        double r = Double.parseDouble(element.getAttribute("r"));
        return new SVGCircle(cx, cy, r, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
    }

    private static SVGEllipse parseEllipse(Element element, Color fillColor, Double fillOpacity, Color strokeColor,
            Double strokeOpacity, Double strokeWidth) {
        double cx = Double.parseDouble(element.getAttribute("cx"));
        double cy = Double.parseDouble(element.getAttribute("cy"));
        double rx = Double.parseDouble(element.getAttribute("rx"));
        double ry = Double.parseDouble(element.getAttribute("ry"));
        return new SVGEllipse(cx, cy, rx, ry, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
    }

    private static SVGLine parseLine(Element element, Color strokeColor, Double strokeOpacity, Double strokeWidth) {
        double x1 = Double.parseDouble(element.getAttribute("x1"));
        double y1 = Double.parseDouble(element.getAttribute("y1"));
        double x2 = Double.parseDouble(element.getAttribute("x2"));
        double y2 = Double.parseDouble(element.getAttribute("y2"));
        return new SVGLine(x1, y1, x2, y2, strokeColor, strokeOpacity, strokeWidth);
    }

    private static SVGPolyline parsePolyline(Element element, Color strokeColor, Double strokeOpacity,
            Double strokeWidth) {
        String pointsStr = element.getAttribute("points");
        String[] points = pointsStr.split("\\s+");
        List<Integer> xPoints = new ArrayList<>();
        List<Integer> yPoints = new ArrayList<>();

        for (String point : points) {
            String[] coords = point.split(",");
            xPoints.add(Integer.parseInt(coords[0]));
            yPoints.add(Integer.parseInt(coords[1]));
        }

        return new SVGPolyline(xPoints, yPoints, strokeColor, strokeOpacity, strokeWidth);
    }

    private static SVGPolygon parsePolygon(Element element, Color fillColor, Double fillOpacity, Color strokeColor,
            Double strokeOpacity, Double strokeWidth) {
        String pointsStr = element.getAttribute("points");
        String[] points = pointsStr.split("\\s+");
        List<Integer> xPoints = new ArrayList<>();
        List<Integer> yPoints = new ArrayList<>();

        for (String point : points) {
            String[] coords = point.split(",");
            xPoints.add(Integer.parseInt(coords[0]));
            yPoints.add(Integer.parseInt(coords[1]));
        }

        return new SVGPolygon(xPoints, yPoints, xPoints.size(), fillColor, fillOpacity, strokeColor, strokeOpacity,
                strokeWidth);
    }

    private static SVGPath parsePath(Element element, Color fillColor, Double fillOpacity, Color strokeColor,
            Double strokeOpacity, Double strokeWidth) {
        String pathData = element.getAttribute("d");
        Path2D path = new Path2D.Double();
        String[] commands = pathData.split("(?=[MmLlZz])");
        double currentX = 0;
        double currentY = 0;
        for (String command : commands) {
            char type = command.charAt(0);
            String[] params = command.substring(1).trim().split("[,\\s]+");
            switch (type) {
                case 'M':
                    currentX = Double.parseDouble(params[0]);
                    currentY = Double.parseDouble(params[1]);
                    path.moveTo(currentX, currentY);
                    break;
                case 'L':
                    currentX = Double.parseDouble(params[0]);
                    currentY = Double.parseDouble(params[1]);
                    path.lineTo(currentX, currentY);
                    break;
                case 'Z':
                    path.closePath();
                    break;
                default:
                    break;
            }
        }
        return new SVGPath(0, 0, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth, path);
    }

    private static SVGGroup parseGroup(Element element, Set<Element> processedElements) {
        SVGGroup group = new SVGGroup();
        NodeList groupElements = element.getChildNodes();
        for (int j = 0; j < groupElements.getLength(); j++) {
            if (groupElements.item(j) instanceof Element) {
                Element childElement = (Element) groupElements.item(j);
                SVGElement childSvgElement = parseElement(childElement, processedElements);
                if (childSvgElement != null) {
                    group.addElement(childSvgElement);
                    processedElements.add(childElement);
                }
            }
        }
        return group;
    }

    private static double parseDouble(String value, double defaultValue) {
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        return Double.parseDouble(value);
    }

    private static Color parseColor(String colorStr, Color defaultColor) {
        if (colorStr == null || colorStr.isEmpty() || "none".equals(colorStr)) {
            return defaultColor;
        }

        // support for named colors
        switch (colorStr) {
            case "black":
                return Color.BLACK;
            case "blue":
                return Color.BLUE;
            case "cyan":
                return Color.CYAN;
            case "darkGray":
                return Color.DARK_GRAY;
            case "gray":
                return Color.GRAY;
            case "green":
                return Color.GREEN;
            case "lightGray":
                return Color.LIGHT_GRAY;
            case "magenta":
                return Color.MAGENTA;
            case "orange":
                return Color.ORANGE;
            case "pink":
                return Color.PINK;
            case "red":
                return Color.RED;
            case "white":
                return Color.WHITE;
            case "yellow":
                return Color.YELLOW;
            default:
                return Color.decode(colorStr);
        }
    }

    private static Map<String, String> parseStyle(String style) {
        Map<String, String> styleMap = new HashMap<>();
        if (style != null && !style.isEmpty()) {
            String[] styleProperties = style.split(";");
            for (String property : styleProperties) {
                String[] keyValue = property.split(":");
                if (keyValue.length == 2) {
                    styleMap.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
        }
        return styleMap;
    }
}
