package edu.wcu.cs.cs363.team4.project04.gui.shape;

import java.awt.Graphics;
import java.io.Serializable;

/**
 * An interface that defines that basic operations that any Shape
 * will have.
 * 
 * @author Brian M. Lenau
 * @version 11/15/10
 */
public interface Shape extends Serializable {

    /**
     * A method that, when called, will allow an implementation of the
     * interface to draw itself to a canvas.
     * 
     * @param g
     *            the Graphics object that will draw the Shape
     */
    public void draw(Graphics g);
}
