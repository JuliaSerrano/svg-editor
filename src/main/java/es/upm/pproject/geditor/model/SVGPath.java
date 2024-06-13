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
        sb.append("\" style=\"fill:").append(fillColor).append("; fill-opacity:")
                .append(fillOpacity).append("; stroke:").append(strokeColor)
                .append("; stroke-opacity:").append(strokeOpacity).append("; stroke-width:").append(strokeWidth)
                .append("\" />");

        return sb.toString();
    }

    @Override
    public void move(double dx, double dy) {
        // TODO: first fix path creation
    }

    // Getters and setters
    public Path2D getPath() {
        return path;
    }

    public void setPath(Path2D path) {
        this.path = path;
    }
}
