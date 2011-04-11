package edu.wcu.cs.cs363.team4.project04.gui;

/**
 * An interface that holds a method that will notify the object that
 * a button has changed it's state. The state of the button will
 * usually be clicked. This notification will be used by an Observer to
 * determine what functionality needs to be executed when the state of the
 * button is changed.
 * 
 * @author Brian M. Lenau
 * @version 11/15/10
 */
public interface ButtonSelectionListener {

    /**
     * Determines whether of not the state of the selected button
     * has changed from it's original state.
     */
    public void selectionChanged();
}
