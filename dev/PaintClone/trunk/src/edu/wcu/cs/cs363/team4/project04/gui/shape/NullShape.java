package edu.wcu.cs.cs363.team4.project04.gui.shape;

import java.awt.Graphics;

/**
 * This class employs the Null Object design patter and the Singleton
 * pattern to ensure that draw panel can have a null strategy for
 * drawing.
 * 
 * @author Joseph R. Hunt
 * @version Nov 19, 2010
 */
public final class NullShape implements Shape {
    /** Version ID for the null object */
    private static final long         serialVersionUID =
                                                       6973671988871371609L;
    /** Single instance of NullShape */
    private static volatile NullShape nullShape;

    /** */
    private NullShape() {
        // Nothing needs to be instantiated
    }

    /**
     * Creates and instance of the NullShape if one does not already
     * exist. Returns a single instance of NullShape.
     * 
     * @return the singular instance of a NullShape
     */
    public static NullShape getNullShape() {
        if (nullShape == null) {
            nullShape = new NullShape();
        }
        return nullShape;
    }

    /*
     * (non-Javadoc)
     * @see
     * edu.wcu.cs.cs363.team4.project04.gui.shape.Shape#draw(java.
     * awt.Graphics)
     */
    @Override
    public void draw(Graphics g) {
        // This method should not do anything
    }

}
