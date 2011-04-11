package edu.wcu.cs.cs363.team4.project04.gui;

import java.util.ArrayList;
import java.util.List;

import edu.wcu.cs.cs363.team4.project04.gui.shape.Shape;

/**
 * Used to Save the list of Shapes;
 * 
 * @author Joseph Randall Hunt
 * @version Nov 29, 2010
 */
public class DrawingMemento {

    /** A Field that holds the list of shapes */
    private List<Shape> shapes;

    /**
     * Takes a list of shapes as and parameter and creates a new
     * object with the list of shapes.
     * so that the list of shapes will be saved and can be restored.
     * 
     * @param shapesToSave
     *            the shapes that will be saved.
     */
    public DrawingMemento(List<Shape> shapesToSave) {
        this.shapes = new ArrayList<Shape>(shapesToSave);
    }

    /**
     * Allows other class to get the list of shapes.
     * 
     * @return a list of shapes.
     */
    public List<Shape> getSavedShapes() {
        return this.shapes;
    }
}
