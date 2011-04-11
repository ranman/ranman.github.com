package edu.wcu.cs.cs363.team4.project04.gui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * A class that encapsulates the logic for drawing a line between
 * two points.
 * 
 * @author Brian M. Lenau
 * @version 11/17/10
 */
public class Line extends AbstractTwoPointShape {

    /** The Serializable UID for the class. */
    static final long serialVersionUID = 8966760661260062267L;

    /**
     * The constructor that sets the color, starting point, and
     * ending point of the Line.
     * 
     * @param color
     *            the to paint in.
     * @param start
     *            the beginning point.
     * @param end
     *            the end point.
     */
    public Line(Color color, Point start, Point end) {
        super(color, start, end);
    }

    /**
     * Draws the object with the specified color, starting point
     * and ending point.
     * 
     * @param g
     *            the Graphics that will draw the Line
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.drawLine(getStart().x, getStart().y, getEnd().x, getEnd().y);
    }
}