package edu.wcu.cs.cs363.team4.project04.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import edu.wcu.cs.cs363.team4.project04.gui.shape.producer.
									AbstractShapeProducer;
import edu.wcu.cs.cs363.team4.project04.gui.shape.producer.
									CurveProducer;
import edu.wcu.cs.cs363.team4.project04.gui.shape.producer.
									FilledOvalProducer;
import edu.wcu.cs.cs363.team4.project04.gui.shape.producer.
									FilledRectangleProducer;
import edu.wcu.cs.cs363.team4.project04.gui.shape.producer.
									LineProducer;
import edu.wcu.cs.cs363.team4.project04.gui.shape.producer.
									NullShapeProducer;
import edu.wcu.cs.cs363.team4.project04.gui.shape.producer.
									OvalProducer;
import edu.wcu.cs.cs363.team4.project04.gui.shape.producer.
									RectangleProducer;
import edu.wcu.cs.cs363.team4.project04.gui.shape.producer.
									ShapeProducer;

/**
 * This class is a shape selection panel using the observer pattern.
 * 
 * @author Raphel Ester
 * @version 11/20/10
 */
public class ShapeButtonPanel extends JPanel {

    /** List of listeners */
    private List<ButtonSelectionListener> listeners = 
    							new ArrayList<ButtonSelectionListener>();

    /** currently selected shape producer */
    private AbstractShapeProducer selectedShapeProducer = 
    							NullShapeProducer.getNullShapeProducer();

    /** Path to images */
    private static final String IMAGE_PATH = "/edu/wcu/cs/cs363/" +
    										 "team4/project04/gui/images/";

    /** Preferred Width */
    private static final int PREFERRED_WIDTH = 85;

    /** Preferred Height */
    private static final int PREFERRED_HEIGHT = 550;

    /**
     * Set the layout of the panel and create and add sub-components
     * and creates and adds listeners.
     */
    public ShapeButtonPanel() {

        /* MAKE BUTTONS */
        URL filledOval = getClass().getResource(IMAGE_PATH + 
        										"FilledOval.png");
        ImageIcon filledOvalIcon = new ImageIcon(filledOval);
        JToggleButton filledOvalButton = new JToggleButton(filledOvalIcon);
        filledOvalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeButtonPanel.this.selectedShapeProducer = 
                							new FilledOvalProducer();
                notifyButtonChange();
            }
        });

        URL oval = getClass().getResource(IMAGE_PATH + "Oval.png");
        ImageIcon ovalIcon = new ImageIcon(oval);
        JToggleButton ovalButton = new JToggleButton(ovalIcon);
        ovalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeButtonPanel.this.selectedShapeProducer = 
                								new OvalProducer();
                notifyButtonChange();
            }
        });

        URL filledRectangle = getClass().getResource(IMAGE_PATH + 
        										"FilledRectangle.png");
        ImageIcon filledRectangleIcon = new ImageIcon(filledRectangle);
        JToggleButton filledRectangleButton = 
        						new JToggleButton(filledRectangleIcon);
        filledRectangleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeButtonPanel.this.selectedShapeProducer =  
                							new	FilledRectangleProducer();
                notifyButtonChange();
            }
        });

        URL rectangle = getClass().getResource(IMAGE_PATH + 
        										"Rectangle.png");
        ImageIcon rectangleIcon = new ImageIcon(rectangle);
        JToggleButton rectangleButton = new JToggleButton(rectangleIcon);
        rectangleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeButtonPanel.this.selectedShapeProducer =  
                									new	RectangleProducer();
                notifyButtonChange();
            }
        });

        URL line = getClass().getResource(IMAGE_PATH + "Line.png");
        ImageIcon lineIcon = new ImageIcon(line);
        JToggleButton lineButton = new JToggleButton(lineIcon);
        lineButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeButtonPanel.this.selectedShapeProducer =  
                									new	LineProducer();
                notifyButtonChange();
            }
        });

        URL curve = getClass().getResource(IMAGE_PATH + "Curve.png");
        ImageIcon curveIcon = new ImageIcon(curve);
        JToggleButton curveButton = new JToggleButton(curveIcon);
        curveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeButtonPanel.this.selectedShapeProducer =  
                									new	CurveProducer();
                notifyButtonChange();
            }
        });

        /* PANEL SETUP */
        ButtonGroup shapeButtonGroup = new ButtonGroup();
        shapeButtonGroup.add(filledOvalButton);
        shapeButtonGroup.add(ovalButton);
        shapeButtonGroup.add(filledRectangleButton);
        shapeButtonGroup.add(rectangleButton);
        shapeButtonGroup.add(lineButton);
        shapeButtonGroup.add(curveButton);
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH,
        									PREFERRED_HEIGHT));
        this.setLayout(new GridLayout(6, 1)); // 6 Buttons
        this.add(filledOvalButton);
        this.add(ovalButton);
        this.add(filledRectangleButton);
        this.add(rectangleButton);
        this.add(lineButton);
        this.add(curveButton);
    }

    /**
     * Get the shape producer for the currently selected button.
     * 
     * @return the current selected shape producer
     */
    public ShapeProducer getShapeProducer() {
        return this.selectedShapeProducer;
    }

    /**
     * Add the specified button selection listener to the list of
     * button selection listeners
     * 
     * @param bsl
     *            <code>ButtonSelectionListener</code> to add.
     */
    public void addButtonSelectionListener(ButtonSelectionListener bsl) {
        this.listeners.add(bsl);
    }

    /**
     * Removes the specified button selection listeners from the list
     * of button selection listeners
     * 
     * @param bsl
     *            <code>ButtonSelectionListener</code> to remove.
     */
    public void removeButtonSelectionListener(ButtonSelectionListener bsl) {
        this.listeners.remove(bsl);
    }

    /**
     * This method loops over the button selection listeners and
     * notifies them of the change in button selection.
     */
    protected void notifyButtonChange() {
        for (ButtonSelectionListener bsl : this.listeners) {
            bsl.selectionChanged();
        }
    }
}