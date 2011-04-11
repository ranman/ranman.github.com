package edu.wcu.cs.cs363.team4.project04.gui.shape.producer;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import edu.wcu.cs.cs363.team4.project04.gui.shape.Shape;

/**
 * This class includes functionality common to all shapes.
 * 
 * @author Brian M. Lenau
 * @author Joseph R. Hunt
 */
public abstract class AbstractShapeProducer extends MouseAdapter 
    implements ShapeProducer {

    /**
     * This field is a list of all the
     * <code>ShapeCreationListener</code>s.
     */
    private List<ShapeCreationListener> listeners = 
        new ArrayList<ShapeCreationListener>();

    /** This field holds the color the object should be drawn in. */
    private Color drawColor;

    /**
     * Sets the entered draw color to the designated color.
     * 
     * @param drawColor
     *            the color that the producer will draw the shape
     *            in
     */
    @Override
    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
    }

    /**
     * Allows access to the current drawing color of the shape.
     * 
     * @return the color the producer will draw the shape in
     */
    public Color getDrawColor() {
        return this.drawColor;
    }

    /**
     * Adds the designated ShapeCreationListener to the list of
     * ShapeCreationListeners.
     * 
     * @param scl
     *            the ShapeCreationListener to be added to the list
     */
    @Override
    public void addShapeCreationListener(ShapeCreationListener scl) {
        this.listeners.add(scl);
    }

    /**
     * Removes the designated ShapeCreationListener from the list of
     * ShapeCreationListeners.
     * 
     * @param scl
     *            the <code>ShapeCreationListener</code> to add.
     * @return true if the list contained the ShapeCreationListener
     */
    @Override
    public boolean removeShapeCreationListener(ShapeCreationListener scl) {
        return this.listeners.remove(scl);
    }

    /**
     * Notifies all the ShapeCreationListeners in the list that a
     * shape
     * has been created.
     * 
     * @param shape
     *            the shape that was created
     */
    protected void notifyShapeCreated(Shape shape) {
        for (ShapeCreationListener scl : this.listeners) {
            scl.shapeCreated(shape);
        }
    }

    /**
     * Notifies all the ShapeCreationListeners in the list that a
     * shape
     * is in the process of being created.
     * 
     * @param shape
     *            The shape that is in progress
     */
    protected void notifyShapeInProgress(Shape shape) {
        for (ShapeCreationListener scl : this.listeners) {
            scl.shapeInProgress(shape);
        }
    }
}
