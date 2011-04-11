package edu.wcu.cs.cs363.team4.project04.gui.shape.producer;

import java.awt.Point;
import java.awt.event.MouseEvent;

import edu.wcu.cs.cs363.team4.project04.gui.shape.FilledOval;
import edu.wcu.cs.cs363.team4.project04.gui.shape.NullShape;
import edu.wcu.cs.cs363.team4.project04.gui.shape.Shape;

/**
 * A producer that is responsible for creating FilledOvals. It is also
 * responsible for notifying the attached listeners that a shape is
 * being created or has been created.
 * 
 * @author Brian M. Lenau
 * @version 11/17/10
 */
public class FilledOvalProducer extends AbstractTwoPointShapeProducer {

    /** The ending point of the shape. */
    private Point end = getStart();

    /** The shape that will be drawn by the producer. */
    private Shape filledOval;

    /**
     * Determines what occurs when the mouse is released. This method
     * notifies the corresponding listeners that the shape has been
     * created and then sets the shape to a NullShape.
     * 
     * @param e
     *            the MouseEvent that will occur when the mouse is
     *            released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        notifyShapeCreated(this.filledOval);
        this.filledOval = NullShape.getNullShape();
    }

    /**
     * Determines what occurs when the mouse is dragged. This method
     * sets the end point to whatever point the mouse is dragged at
     * and then creates a new FilledOval from the points.
     * 
     * @param e
     *            the MouseEvent that will occur when the mouse is
     *            dragged
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        this.end = e.getPoint();
        this.filledOval = new FilledOval(getDrawColor(), getStart(), this.end);
        notifyShapeInProgress(this.filledOval);
    }
}
