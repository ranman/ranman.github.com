package edu.wcu.cs.cs363.team4.project04.gui.shape;

import java.awt.Color;

/**
 * A class that models a Shape that contains basic properties that all
 * shapes have, such as a color and the ability to draw to a canvas.
 * 
 * @author Brian M. Lenau
 * @version 11/15/10
 */
public abstract class AbstractShape implements Shape {

    /** The color of the shape. */
    private Color color;

    /** The Serializable UID of the class. */
    static final long serialVersionUID = 8971014943684995932L;

    /**
     * A constructor that sets the color for the shape to be drawn
     * to the entered number.
     * 
     * @param color
     *            the color to draw the shape in
     */
    public AbstractShape(Color color) {
        this.color = color;
    }

    /**
     * A method that changes the color that the Shape will be drawn in
     * 
     * @return the color of the Shape
     */
    public Color getColor() {
        return this.color;
    }
}
