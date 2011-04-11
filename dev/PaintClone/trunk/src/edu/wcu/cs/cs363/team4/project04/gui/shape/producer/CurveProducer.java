package edu.wcu.cs.cs363.team4.project04.gui.shape.producer;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import edu.wcu.cs.cs363.team4.project04.gui.shape.Curve;
import edu.wcu.cs.cs363.team4.project04.gui.shape.NullShape;
import edu.wcu.cs.cs363.team4.project04.gui.shape.Shape;

/**
 * A producer that is responsible for the creation of Curve shapes. It
 * creates the curves from a list of points that each have a line
 * between itself and the point after it, if it exists.
 * 
 * @author Joseph Randall Hunt
 * @author Brian M. Lenau
 * @version Nov 29, 2010
 */
public class CurveProducer extends AbstractShapeProducer {

    /** The list of points that contains the Curve. */
    private List<Point> points = new ArrayList<Point>();

    /** The shape that will be drawn. */
    private Shape curve = new Curve(getDrawColor());

    /**
     * Adds the point at which the mouse is pressed into the list of
     * points for the curve.
     * 
     * @param e
     *            the MouseEvent that will occur when the mouse is
     *            pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.points.add(e.getPoint());
    }

    /**
     * Creates a new curve from the list of points when the mouse is
     * released. It also notifies any attached listeners and then
     * sets the shape to a NullShape and the list of points to a new
     * list.
     * 
     * @param e
     *            the MouseEvent that will occur when the mouse is
     *            released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.curve = new Curve(getDrawColor(), this.points);
        notifyShapeCreated(this.curve);
        this.points = new ArrayList<Point>();
        this.curve = NullShape.getNullShape();
    }

    /**
     * Determines what occurs when the mouse is dragged. This method
     * adds the point at which the mouse is dragged to the list of
     * points and then creates a new curve with the points. It then
     * notifies the corresponding listeners that a shape is in
     * progress.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        this.points.add(e.getPoint());
        this.curve = new Curve(getDrawColor(), this.points);
        notifyShapeInProgress(this.curve);
    }
}
