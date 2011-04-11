package edu.wcu.cs.cs363.team4.project04.gui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * A class that models a filled oval.  It also contains the logic required to
 * draw a filled oval in any direction.
 * 
 * @author  Brian M. Lenau
 * @version 11/17/10
 */
public class FilledOval extends AbstractTwoPointShape {
    /** The Serializable UID for the class. */
    static final long serialVersionUID = -4002657310260453879L;

    /**
     * The constructor that sets the color, starting point, and
     * ending point for the class.
     * 
     * @param color
     *            the color that the shape will be drawn in
     * @param start
     *            the starting point of the FilledOval
     * @param end
     *            the ending point of the FilledOval
     */
    public FilledOval(Color color, Point start, Point end) {
        super(color, start, end);
    }

    /**
     * Draws the FilledOval with the specified color and points.
     * 
     * @param g
     *            the Graphics that will draw the FilledOval
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        translate(g);
    }

    /**
     * Determines how the filled oval should be drawn dependent on the
     * start and end points.
     * 
     * @param g
     */
    private void translate(Graphics g) {
        Point end = getEnd();
        Point start = getStart();
        if ((end.x < start.x) && (end.y < start.y)) {
            g
            .fillOval(end.x, end.y, start.x - end.x, start.y - end.y);
        } else if (end.y < start.y) {
            g.fillOval(start.x, end.y, end.x - start.x, start.y
            - end.y);
        } else if (end.x < start.x) {
            g.fillOval(end.x, start.y, start.x - end.x, end.y
            - start.y);
        } else {
            g.fillOval(start.x, start.y, end.x - start.x, end.y
            - start.y);
        }
    }
}