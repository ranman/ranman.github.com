package edu.wcu.cs.cs363.team4.project04.gui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * A class that encapsulates the logic to create and draw
 * Oval objects.
 * 
 * @author Brian M. Lenau
 * @author Joseph Randall Hunt
 * @version 11/17/10
 */
public class Oval extends AbstractTwoPointShape {


    /** The Serializable UID of the class. */
    static final long serialVersionUID = -4726352741450101460L;

    /**
     * Creates an Oval object and sets the color, starting
     * point, and ending point of the Oval.
     * 
     * @param color
     *            the color that the Oval will be drawn in
     * @param start
     *            the starting point of the Oval
     * @param end
     *            the ending point of the Oval
     */
    public Oval(Color color, Point start, Point end) {
        super(color, start, end);
    }

    /**
     * Draws the Oval with the specified color and at the
     * specified starting and ending point.
     * 
     * @param g
     *            The Graphics that will draw the Oval
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        translate(g);
    }

    /**
     * Determines which way the Oval should be drawn. If the oval
     * is in the first quadrant, then draw normally. If it is in any
     * of the other, then special logic has to be implemented.
     * 
     * @param g
     *            the Graphics that will draw the FilledOval
     */
    private void translate(Graphics g) {
        Point end = getEnd();
        Point start = getStart();
        if ((end.x < start.x) && (end.y < start.y)) {
            g.drawOval(
                end.x,
                end.y,
                (start.x - end.x),
                (start.y - end.y));
        } else if (end.y < start.y) {
            g.drawOval(
                start.x,
                end.y,
                (end.x - start.x),
                (start.y - end.y));
        } else if (end.x < start.x) {
            g.drawOval(
                end.x,
                start.y,
                (start.x - end.x),
                (end.y - start.y));
        } else {
            g.drawOval(
                start.x,
                start.y,
                (end.x - start.x),
                (end.y - start.y));
        }
    }
}