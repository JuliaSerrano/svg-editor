package es.upm.pproject.geditor.model;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

//ODO: REVISAR
public class SVGPath extends SVGElement {
    private Path2D path;

    public SVGPath(double x, double y, Path2D path) {
        super(x, y);
        this.path = path;
        this.shape = path;
        this.setFillColor(null);
    }

    @Override
    public String toSVGString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<path d=\"");

        // Append the SVG path commands
        PathIterator iterator = path.getPathIterator(null);
        float[] coords = new float[6];
        int x = 0;
        int y = 1;
        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    sb.append("M").append(coords[x]).append(" ").append(coords[y]).append(" ");
                    break;
                case PathIterator.SEG_LINETO:
                    sb.append("L").append(coords[x]).append(" ").append(coords[y]).append(" ");
                    break;
                case PathIterator.SEG_CLOSE:
                    sb.append("Z");
                    break;
                default:
                    break;
            }
            iterator.next();
        }

        // Append style attributes
        sb.append("\" style=\"");
        if (fillColor != null) {
            sb.append("fill:").append(colorToHex(fillColor)).append("; fill-opacity:").append(fillOpacity).append("; ");
        } else {
            sb.append("fill:none; ");
        }
        if (strokeColor != null) {
            sb.append("stroke:").append(colorToHex(strokeColor)).append("; stroke-opacity:").append(strokeOpacity).append("; ");
        }
        sb.append("stroke-width:").append(strokeWidth).append("\" />");

        return sb.toString();
    }
    @Override
    public void move(double dx, double dy) {
        PathIterator iterator = path.getPathIterator(null);
        Path2D newPath = new Path2D.Double();
        float[] coords = new float[6];

        int pointX = 0;
        int pointY = 1;
        int point2X = 2;
        int point2Y = 3;
        int point3X = 4;
        int point3Y = 5;

        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    newPath.moveTo(coords[pointX] + dx, coords[pointY] + dy);
                    break;
                case PathIterator.SEG_LINETO:
                    newPath.lineTo(coords[pointX] + dx, coords[pointY] + dy);
                    break;
                case PathIterator.SEG_QUADTO:
                    newPath.quadTo(coords[pointX] + dx, coords[pointY] + dy, coords[point2X] + dx, coords[point2Y] + dy);
                    break;
                case PathIterator.SEG_CUBICTO:
                    newPath.curveTo(coords[pointX] + dx, coords[pointY] + dy, coords[point2X] + dx, coords[point2Y] + dy, coords[point3X] + dx,
                            coords[point3Y] + dy);
                    break;
                case PathIterator.SEG_CLOSE:
                    newPath.closePath();
                    break;
                default:
                    break;
            }
            iterator.next();
        }

        this.path = newPath;
        this.shape = newPath; // Update the shape reference to the new path
    }

    public boolean isWithinBounds(int width, int height) {
        PathIterator iterator = path.getPathIterator(null);
        float[] coords = new float[6];

        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            if (!areCoordinatesWithinBounds(coords, type, width, height)) {
                return false;
            }
            iterator.next();
        }

        return true;
    }

    private boolean areCoordinatesWithinBounds(float[] coords, int type, int width, int height) {
        int pointX = 0;
        int pointY = 1;
        int point2X = 2;
        int point2Y = 3;
        int point3X = 4;
        int point3Y = 5;

        switch (type) {
            case PathIterator.SEG_MOVETO:
            case PathIterator.SEG_LINETO:
                return coords[pointX] >= 0 && coords[pointX] <= width && coords[pointY] >= 0 && coords[pointY] <= height;
            case PathIterator.SEG_QUADTO:
                return coords[pointX] >= 0 && coords[pointX] <= width && coords[pointY] >= 0 && coords[pointY] <= height &&
                        coords[point2X] >= 0 && coords[point2X] <= width && coords[point2Y] >= 0 && coords[point2Y] <= height;
            case PathIterator.SEG_CUBICTO:
                return coords[pointX] >= 0 && coords[pointX] <= width && coords[pointY] >= 0 && coords[pointY] <= height &&
                        coords[point2X] >= 0 && coords[point2X] <= width && coords[point2Y] >= 0 && coords[point2Y] <= height &&
                        coords[point3X] >= 0 && coords[point3X] <= width && coords[point3Y] >= 0 && coords[point3Y] <= height;
            default:
                return true;
        }
    }

    // Getters and setters
    public Path2D getPath() {
        return path;
    }

    public void setPath(Path2D path) {
        this.path = path;
    }
}
