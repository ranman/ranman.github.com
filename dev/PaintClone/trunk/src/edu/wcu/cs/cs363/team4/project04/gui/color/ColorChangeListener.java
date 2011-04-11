package edu.wcu.cs.cs363.team4.project04.gui.color;

import java.awt.Color;

/**
 * An interface that defines a method that will notify an object that
 * the currently selected color within the color palette has changed.
 * This method will be used by an Observer to determine if the
 * selected color has changed, and if it has, some action will occur.
 * 
 * @author Brian M. Lenau
 * @author Joseph R. Hunt
 * @version 11/15/10
 */
public interface ColorChangeListener {

    /**
     * Notifies an object that the currently selected color has
     * changed.
     * 
     * @param color
     *            the newly selected color
     */
    public void colorChanged(Color color);
}
