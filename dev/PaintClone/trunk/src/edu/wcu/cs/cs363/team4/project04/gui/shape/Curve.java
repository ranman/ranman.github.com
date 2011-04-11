package edu.wcu.cs.cs363.team4.project04.gui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/**
 * A class that models the behavior of a curve. It also encapsulates
 * the logic to draw the curve.
 * 
 * @author Joseph Randall Hunt
 * @author Brian M. Lenau
 * @version Nov 29, 2010
 */
public class Curve extends AbstractShape {

    /** Generated serialVersionUID */
    private static final long serialVersionUID = 2152793626877046967L;

    /** The list of points that make up the curve. */
    private List<Point> points;

    /**
     * Creates a new color object with the current drawing color.
     * 
     * @param color
     *            the color to draw the curve in
     */
    public Curve(Color color) {
        super(color);
    }

    /**
     * Creates a new curve from a list of points and with the current
     * draw color.
     * 
     * @param color
     *            the color to draw the curve in.
     * @param points
     *            the list of points that contains the curve
     */
    public Curve(Color color, List<Point> points) {
        super(color);
        this.points = points;
    }

    /*
     * (non-Javadoc)
     * @see
     * edu.wcu.cs.cs363.team4.project04.gui.shape.AbstractShape#draw
     * (java.awt.Graphics)
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        if (this.points.size() < 1) {
            return;
        }
        for (int i = 0; i < this.points.size() - 1; i++) {
            Point start = this.points.get(i);
            Point end = this.points.get(i + 1);

            g.drawLine(start.x, start.y, end.x, end.y);
        }
    }

}
