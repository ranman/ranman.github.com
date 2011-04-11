package edu.wcu.cs.cs363.team4.project04.gui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


/**
 * Creates a filled rectangle.
 * 
 * @author Nathaniel von Sprecken
 * @version 11/18/10
 */
public class FilledRectangle extends AbstractTwoPointShape {

    /** A generated serial version id for the FilledRectangle class. */
    private static final long serialVersionUID = 2138233529694394236L;

    /**
     * This creates a filled rectangle with the given color, starting
     * point,
     * and ending point.
     * 
     * @param color
     *            The color that the rectangle will be.
     * @param start
     *            The starting point of the filled rectangle.
     * @param end
     *            The ending point of the filled rectangle.
     */
    public FilledRectangle(Color color, Point start, Point end) {
        super(color, start, end);
    }

    /**
     * Draws the filled rectangle.
     * 
     * @param g
     *            The Graphics that is used to draw the filled
     *            rectangle.
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
     * @param g the graphics that draw the filled rectangle.
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
        g.fillRect(drawNumbers[0], drawNumbers[1], drawNumbers[2], 
        			drawNumbers[3]);
    }
}
