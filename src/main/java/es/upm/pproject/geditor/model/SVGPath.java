package es.upm.pproject.geditor.model;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

//ODO: REVISAR
public class SVGPath extends SVGElement {
    private Path2D path;

    public SVGPath(double x, double y, Color fillColor, double fillOpacity, Color strokeColor,
            double strokeOpacity, double strokeWidth, Path2D path) {
        super(x, y, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWidth);
        this.path = path;
        this.shape = path;
    }

    @Override
    public String toSVGString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<path d=\"");

        // Append the SVG path commands
        PathIterator iterator = path.getPathIterator(null);
        float[] coords = new float[6];
        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    sb.append("M").append(coords[0]).append(" ").append(coords[1]).append(" ");
                    break;
                case PathIterator.SEG_LINETO:
                    sb.append("L").append(coords[0]).append(" ").append(coords[1]).append(" ");
                    break;
                case PathIterator.SEG_CLOSE:
                    sb.append("Z");
                    break;
                default:
                    // Other path types like curves can be handled similarly
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

        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    newPath.moveTo(coords[0] + dx, coords[1] + dy);
                    break;
                case PathIterator.SEG_LINETO:
                    newPath.lineTo(coords[0] + dx, coords[1] + dy);
                    break;
                case PathIterator.SEG_QUADTO:
                    newPath.quadTo(coords[0] + dx, coords[1] + dy, coords[2] + dx, coords[3] + dy);
                    break;
                case PathIterator.SEG_CUBICTO:
                    newPath.curveTo(coords[0] + dx, coords[1] + dy, coords[2] + dx, coords[3] + dy, coords[4] + dx,
                            coords[5] + dy);
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
        switch (type) {
            case PathIterator.SEG_MOVETO:
            case PathIterator.SEG_LINETO:
                return coords[0] >= 0 && coords[0] <= width && coords[1] >= 0 && coords[1] <= height;
            case PathIterator.SEG_QUADTO:
                return coords[0] >= 0 && coords[0] <= width && coords[1] >= 0 && coords[1] <= height &&
                        coords[2] >= 0 && coords[2] <= width && coords[3] >= 0 && coords[3] <= height;
            case PathIterator.SEG_CUBICTO:
                return coords[0] >= 0 && coords[0] <= width && coords[1] >= 0 && coords[1] <= height &&
                        coords[2] >= 0 && coords[2] <= width && coords[3] >= 0 && coords[3] <= height &&
                        coords[4] >= 0 && coords[4] <= width && coords[5] >= 0 && coords[5] <= height;
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
