package edu.wcu.cs.cs363.team4.project04.gui.shape.producer;

import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * ShapeProducer defines the interface to objects that encapsulate the
 * logic necessary to produce shapes in response to mouse events.
 * 
 * @author Brian M. Lenau
 * @version 11/15/10
 */
public interface ShapeProducer extends MouseListener, MouseMotionListener {

    /**
     * Adds the designated ShapezCreationListener to the producer
     * 
     * @param l
     *            the ShapeCreationListener to be added to the
     *            producer
     */
    public void addShapeCreationListener(ShapeCreationListener l);

    /**
     * Removes the designated ShapeCreationListener from the producer.
     * 
     * @param l
     *            the listener to be removed.
     * @return whether removal was successful, should return false if
     *         listener did not exist.
     */
    public boolean removeShapeCreationListener(ShapeCreationListener l);

    /**
     * This method should set the color of the item being drawn.
     * 
     * @param drawColor
     *            the color that should be used to draw the shape
     */
    public void setDrawColor(Color drawColor);
}
