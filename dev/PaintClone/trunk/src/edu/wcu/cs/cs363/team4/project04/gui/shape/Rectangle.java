package edu.wcu.cs.cs363.team4.project04.gui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * A class that creates a rectangle.
 * 
 * @author Nathaniel von Sprecken
 * @version 11/18/2010
 */

public class Rectangle extends AbstractTwoPointShape {

    /** A generated Serial Version ID for Rectangle */
    private static final long serialVersionUID = 2210186940294668529L;

    /**
     * Creates a Rectangle and sets the color, start point, and end
     * point.
     * 
     * @param color
     *            The color of the rectangle
     * @param start
     *            The starting point of the rectangle.
     * @param end
     *            The ending point of the rectangle.
     */
    public Rectangle(Color color, Point start, Point end) {
        super(color, start, end);
    }

    /**
     * This draws the created rectangle.
     * 
     * @param g
     *            The graphics that draw the rectangle.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        translate(g);
    }
    /**
     * This section handles the situations where the shape isn't drawn from 
     * top left down to bottom right.
     * 
     * @param g the graphics that draw the rectangle.
     */
    private void translate(Graphics g) {
        Point end = getEnd();
        Point start = getStart();
        int[] drawNumbers = { start.x, start.y, (end.x - start.x), 
        						(end.y - start.y) };
        if (end.x < start.x) {
            drawNumbers[0] = end.x;
            drawNumbers[2] = (start.x - end.x);
        }
        if (end.y < start.y) {
            drawNumbers[1] = end.y;
            drawNumbers[3] = (start.y - end.y);
        }
        g.drawRect(drawNumbers[0], drawNumbers[1], drawNumbers[2], 
        			drawNumbers[3]);

    }
}
