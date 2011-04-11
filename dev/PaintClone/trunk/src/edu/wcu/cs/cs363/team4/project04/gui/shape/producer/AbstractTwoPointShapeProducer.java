package edu.wcu.cs.cs363.team4.project04.gui.shape.producer;

import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * This class includes functionality common to all
 * <code>ShapeProducer</code>s with two points.
 * 
 * @author Brian M. Lenau
 * @version 11/15/10
 */
public abstract class AbstractTwoPointShapeProducer 
    extends AbstractShapeProducer {

    /** The start point */
    private Point start;

    /** Mouse was pressed, start point = e.getPoint. */
    @Override
    public void mousePressed(MouseEvent e) {
        this.start = e.getPoint();
    }

    /**
     * returns the starting point.
     * 
     * @return the starting point
     */
    protected Point getStart() {
        return this.start;
    }
}
