package edu.wcu.cs.cs363.team4.project04.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;

import edu.wcu.cs.cs363.team4.project04.gui.shape.NullShape;
import edu.wcu.cs.cs363.team4.project04.gui.shape.Shape;
import edu.wcu.cs.cs363.team4.project04.gui.shape.producer.NullShapeProducer;
import edu.wcu.cs.cs363.team4.project04.gui.shape.producer
.ShapeCreationListener;
import edu.wcu.cs.cs363.team4.project04.gui.shape.producer.ShapeProducer;

/**
 * This class is where ShapeProdcuers will draw.
 * 
 * @author Joseph Randall Hunt
 * @author Brian M. Lenau
 * @version Nov 28, 2010
 */
public class DrawPanel extends JPanel implements ShapeCreationListener {

    /** This list of shapes currently being drawn */
    private List<Shape> shapes = new ArrayList<Shape>();

    /** Stack of undo DrawingMementos */
    private Stack<DrawingMemento> undoStack = new Stack<DrawingMemento>();

    /** Stack of redo DrawingMementos */
    private Stack<DrawingMemento> redoStack = new Stack<DrawingMemento>();

    /** The current selected ShapeProducer */
    private ShapeProducer shapeProducer = 
        NullShapeProducer.getNullShapeProducer();

    /** The shape in progress from the selected ShapeProducer */
    private Shape shapeInProgress = NullShape.getNullShape();

    /** The current color for drawing */
    private Color drawColor;

    /**
     * Create the DrawPanel with the specified background
     * 
     * @param background
     *            The color of the background of draw panel.
     */
    public DrawPanel(Color background) {
        this.setBackground(background);
    }

    /**
     * Set the current shape producer, remove listeners from old and
     * add listeners to new.
     * 
     * @param newProducer
     *            the new shapeProducer.
     */
    public void setShapeProducer(ShapeProducer newProducer) {
        this.removeMouseListener(this.shapeProducer);
        this.removeMouseMotionListener(this.shapeProducer);
        this.shapeProducer.removeShapeCreationListener(this);
        this.shapeProducer = newProducer;
        this.shapeProducer.setDrawColor(this.drawColor);
        this.shapeProducer.addShapeCreationListener(this);
        this.addMouseListener(this.shapeProducer);
        this.addMouseMotionListener(this.shapeProducer);
    }

    /**
     * Set the current drawing color
     * 
     * @param drawColor
     *            the new color.
     */
    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
        this.shapeProducer.setDrawColor(drawColor);
    }


    /**
     * Save the current list of shapes to a file.
     * 
     * @param file
     *            the file to save to
     * @throws IOException
     *             If file can't be saved.
     */
    public void save(File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(this.shapes);
        oos.flush();
    }

    /**
     * Reads in a file containing the list of shapes.
     * 
     * @param file
     *            the file to be read in
     * @throws IOException
     *             unable to read in file
     * @throws ClassNotFoundException
     *             unable to cast to class
     */
    public void load(File file) throws IOException, ClassNotFoundException {
        InputStream in = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(in);
        this.shapes = (List<Shape>) ois.readObject();
        this.redoStack.clear();
        this.undoStack.clear();
        this.repaint();
    }

    /**
     * Clear the list of shapes and set the shapeInProgress to null.
     */
    public void clear() {
        this.shapes.clear();
        this.shapeInProgress = NullShape.getNullShape();
        this.repaint();
    }

    /**
     * Returns if the undoStack is empty
     * 
     * @return if there are items in the undoStack
     */
    public boolean canUndo() {
        return !this.undoStack.isEmpty();
    }

    /**
     * returns if the redoStack is empty
     * 
     * @return if there are items in the redoStack
     */
    public boolean canRedo() {
        return !this.redoStack.isEmpty();
    }

    /**
     * This method will undo the most recent change to draw panel.
     */
    public void undo() {
        this.redoStack.push(new DrawingMemento(this.shapes));
        this.shapes = this.undoStack.pop().getSavedShapes();
        this.shapeInProgress = NullShape.getNullShape();
        this.repaint();
    }

    /** This method will redo the most recent undo to draw panel */
    public void redo() {
        this.undoStack.push(new DrawingMemento(this.shapes));
        this.shapes = this.redoStack.pop().getSavedShapes();
        this.shapeInProgress = NullShape.getNullShape();
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : this.shapes) {
            shape.draw(g);
        }
        this.shapeInProgress.draw(g);
    }

    /*
     * (non-Javadoc)
     * @see edu.wcu.cs.cs363.team4.project04.gui.shape.producer.
     * ShapeCreationListener
     * #shapeCreated(edu.wcu.cs.cs363.team4.project04.gui.shape.Shape)
     */
    @Override
    public void shapeCreated(Shape shape) {
        this.undoStack.push(new DrawingMemento(this.shapes));
        this.shapes.add(shape);
        this.redoStack.clear();
        this.repaint();
    }

    /*
     * (non-Javadoc)
     * @see edu.wcu.cs.cs363.team4.project04.gui.shape.producer.
     * ShapeCreationListener
     * #shapeInProgress(edu.wcu.cs.cs363.team4.project04
     * .gui.shape.Shape)
     */
    @Override
    public void shapeInProgress(Shape shape) {
        this.shapeInProgress = shape;
        this.repaint();
    }
}
