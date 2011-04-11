package edu.wcu.cs.cs363.team4.project04.gui.shape;

import java.awt.Color;
import java.awt.Point;

/**
 * This abstract class defines the common functionality of all shapes
 * that are expressible as two points (i.e, the starting and ending
 * points)
 * 
 * @author Brian M. Lenau
 * @version 11/15/10
 */
public abstract class AbstractTwoPointShape extends AbstractShape {

    /** The Serializable UID for the class. */
    static final long serialVersionUID = 7911914207593582739L;

    /** The starting point of the shape. */
    private Point start;

    /** The ending point of the shape. */
    private Point end;

    /**
     * Creates a new AbstractTwoPointShape with the specified color,
     * starting and ending point.
     * 
     * @param color
     *            the color of the shape
     * @param start
     *            the starting point of the shape
     * @param end
     *            then ending point of the shape
     */
    public AbstractTwoPointShape(Color color, Point start, Point end) {
        super(color);

        this.start = start;
        this.end = end;
    }

    /**
     * Returns the starting Point of the shape.
     * 
     * @return the starting corner of the shape
     */
    protected Point getStart() {
        return this.start;
    }

    /**
     * Returns the ending Point of the shape.
     * 
     * @return the ending corner of the shape
     */
    protected Point getEnd() {
        return this.end;
    }
}
