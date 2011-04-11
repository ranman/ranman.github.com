package edu.wcu.cs.cs363.team4.project04.gui.shape.producer;

import java.awt.Point;
import java.awt.event.MouseEvent;

import edu.wcu.cs.cs363.team4.project04.gui.shape.NullShape;
import edu.wcu.cs.cs363.team4.project04.gui.shape.Oval;
import edu.wcu.cs.cs363.team4.project04.gui.shape.Shape;

/**
 * The class that is responsible for creating new oval objects.
 * Whenever a
 * mouse-action is performed when attempting to draw the shape, the
 * List of
 * ShapeCreationListeners about the change in the shape.
 * 
 * @author Brian M. Lenau
 * @version 11/17/10
 */
public class OvalProducer extends AbstractTwoPointShapeProducer {

    /** The ending point of the shape. */
    private Point end = getStart();

    /** The shape being drawn */
    private Shape oval;

    /**
     * Notifies any observers when an oval has been created. The shape
     * is only
     * fully created when the mouse is released, otherwise, it is only
     * in-
     * progress.
     * 
     * @param e
     *            the MouseEvent that occurs when the mouse is
     *            released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.oval = new Oval(getDrawColor(), getStart(), this.end);
        notifyShapeCreated(this.oval);
        this.oval = NullShape.getNullShape();
    }

    /**
     * Notifies an observers when n oval is in the process of being
     * created.
     * While the mouse is being dragged in the action of drawing the
     * shape,
     * the shape is not fully created, but simply in progress.
     * 
     * @param e
     *            the MouseEvent that occurs when the mouse is being
     *            dragged
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        this.end = e.getPoint();
        this.oval = new Oval(getDrawColor(), getStart(), this.end);
        notifyShapeInProgress(this.oval);
    }
}
