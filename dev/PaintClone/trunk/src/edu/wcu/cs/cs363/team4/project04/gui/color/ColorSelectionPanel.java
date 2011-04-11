package edu.wcu.cs.cs363.team4.project04.gui.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * A Swing panel that enables a user to select a color. The panel
 * includes a <code>JColorChooser</code> and a preview panel. <br />
 * Instances of this class play the role of <i>subjects</i> in
 * instances of the observer pattern. Observers are registered and
 * deregistered using the <code>addColorChangeListener()</code> and
 * <code>removeColorChangeListener()</code> methods. Registered
 * listeners will be notified when the user selects a different color.
 * 
 * @author Dr. Andrew R. Dalton
 * @version March 15, 2010
 */
@SuppressWarnings("serial")
public class ColorSelectionPanel extends JPanel {

    /** The size of the preview panel, in pixels. */
    private static final int PREVIEW_SIZE = 100;

    /** The preferred width of the panel. */
    private static final int PREFERED_WIDTH = 400;

    /** The preferred height of the panel. */
    private static final int PREFERED_HEIGHT = 150;

    /**
     * The underlying color selection model for the
     * <code>JColorChooser</code> that this component presents.
     */
    private ColorSelectionModel model;

    /** The preview panel, used to show the currently selected color. */
    private JPanel preview;

    /** A list of registered <code>ColorChangeListener</code>s. */
    private List<ColorChangeListener> colorChangeListeners;


    /**
     * Constructs a new <code>ColorSelectionPanel</code> whose draw
     * color is
     * initially black.
     */
    public ColorSelectionPanel() {
        super(new FlowLayout());

        JColorChooser chooser = new JColorChooser(Color.BLACK);

        this.colorChangeListeners = new ArrayList<ColorChangeListener>();
        this.model = chooser.getChooserPanels()[0].getColorSelectionModel();
        this.preview = new JPanel();

        this.setBorder(new TitledBorder("Color"));

        this.preview.setBackground(model.getSelectedColor());
        this.preview.setPreferredSize(new Dimension(PREVIEW_SIZE, 
                                                    PREVIEW_SIZE));

        this.setPreferredSize(new Dimension(PREFERED_WIDTH, PREFERED_HEIGHT));

        this.model.addChangeListener(new ChangeListener() {

            @SuppressWarnings({ "synthetic-access", "unused" })
            @Override
            public void stateChanged(ChangeEvent e) {
                preview.setBackground(model.getSelectedColor());
                notifyColorChange(model.getSelectedColor());
            }
        });

        this.add(preview);
        this.add(chooser.getChooserPanels()[0]);
    }


    /**
     * Registers a new <code>ColorChangeListener</code> with this
     * panel.
     * All registered listeners will be notified when the user selects
     * a
     * color.
     * 
     * @param listener
     *            The new listener to add to the list of registered
     *            listeners.
     */
    public void addColorChangeListener(ColorChangeListener listener) {
        colorChangeListeners.add(listener);

        // Notify the newly added listener of the current color
        listener.colorChanged(model.getSelectedColor());
    }


    /**
     * De-registers a new <code>ColorChangeListener</code> with this
     * panel.
     * 
     * @param listener
     *            The new listener to remove from the list of
     *            registered listeners.
     * @return whether the removal was successful
     */
    public boolean removeColorChangeListener(ColorChangeListener listener) {
        return colorChangeListeners.remove(listener);
    }


    /**
     * Enables clients to get the currently selected color.
     * 
     * @return The currently selected color.
     */
    public Color getSelectedColor() {
        return model.getSelectedColor();
    }


    /**
     * Notifies all registered <code>ColorChangeListener</code>s that
     * the
     * selected color has changed.
     * 
     * @param color
     *            The newly selected color.
     */
    protected void notifyColorChange(Color color) {
        for (ColorChangeListener i : colorChangeListeners) {
            i.colorChanged(color);
        }
    }
}
