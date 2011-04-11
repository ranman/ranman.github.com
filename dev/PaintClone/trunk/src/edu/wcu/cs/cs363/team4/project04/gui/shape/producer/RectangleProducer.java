package edu.wcu.cs.cs363.team4.project04.gui.shape.producer;

import java.awt.Point;
import java.awt.event.MouseEvent;

import edu.wcu.cs.cs363.team4.project04.gui.shape.NullShape;
import edu.wcu.cs.cs363.team4.project04.gui.shape.Rectangle;
import edu.wcu.cs.cs363.team4.project04.gui.shape.Shape;

/**
 * This class creates a rectangle while the mouse is moving.
 * 
 * @author Nathaniel von Sprecken
 * @version 11/18/10
 */
public class RectangleProducer extends AbstractTwoPointShapeProducer {

    /**
     * Creates the end drag point and its set to be where the mouse is
     * first clicked.
     */
    private Point end = getStart();

    /** Current shape being drawn. */
    private Shape rectangle;

    /**
     * Notifies the observers at mouse release that a rectangle is 
     * being created.
     * 
     * @param e the mouse event when the mouse is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.rectangle = new Rectangle(getDrawColor(), getStart(), this.end);
        notifyShapeCreated(this.rectangle);
        this.rectangle = NullShape.getNullShape();

    }

    /**
     * Notifies the observer that a rectangle is being drawn.
     * 
     * @param e the mouse event when the mouse is dragged 
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        this.end = e.getPoint();
        this.rectangle = new Rectangle(getDrawColor(), getStart(), this.end);
        notifyShapeInProgress(this.rectangle);
    }
}
