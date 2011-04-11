package edu.wcu.cs.cs363.team4.project04.gui.shape.producer;

import edu.wcu.cs.cs363.team4.project04.gui.shape.Shape;

/**
 * This interfaces defines the interface to objects that can create
 * shapes.
 * 
 * @author Brian M. Lenau
 * @version 11/15/10
 */
public interface ShapeCreationListener {
    /**
     * This method should be called on shape creation.
     * 
     * @param shape
     *            the shape created
     */
    public void shapeCreated(Shape shape);

    /**
     * This method should be called when a shape is started.
     * 
     * @param shape
     *            the shape in progress
     */
    public void shapeInProgress(Shape shape);
}
