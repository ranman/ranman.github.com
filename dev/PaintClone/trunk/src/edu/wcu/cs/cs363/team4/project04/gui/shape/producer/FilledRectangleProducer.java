package edu.wcu.cs.cs363.team4.project04.gui.shape.producer;

import java.awt.Point;
import java.awt.event.MouseEvent;

import edu.wcu.cs.cs363.team4.project04.gui.shape.FilledRectangle;
import edu.wcu.cs.cs363.team4.project04.gui.shape.NullShape;
import edu.wcu.cs.cs363.team4.project04.gui.shape.Shape;

/**
 * This class handles creation of the FilledRectangle
 * while the mouse is moving.
 * 
 * @author Nathaniel von Sprecken
 * @version 11/18/10
 */
public class FilledRectangleProducer extends AbstractTwoPointShapeProducer {

    /**
     * End is the end of the filled rectangle. Set to start so that
     * is where the mouse is clicked.
     */
    private Point end = getStart();

    /** Current filledRectangle to be drawn. */
    private Shape filledRectangle;

    /**
     * Notifies the observers at mouse release that a filled rectangle
     * is being created.
     *  
     * @param e the mouse event when the mouse is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.filledRectangle = new FilledRectangle(getDrawColor(), getStart()
        										   ,this.end);
        notifyShapeCreated(this.filledRectangle);
        this.filledRectangle = NullShape.getNullShape();
    }

    /**
     * Notifies the observer that a filled rectangle is being drawn.
     * 
     * @param e the mouse event when the mouse is dragged 
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        this.end = e.getPoint();
        this.filledRectangle = new FilledRectangle(getDrawColor(), getStart()
        										   ,this.end);
        notifyShapeInProgress(this.filledRectangle);
    }
}
