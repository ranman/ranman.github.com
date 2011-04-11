package edu.wcu.cs.cs363.team4.project04.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import edu.wcu.cs.cs363.team4.project04.gui.color.ColorChangeListener;
import edu.wcu.cs.cs363.team4.project04.gui.color.ColorSelectionPanel;

/**
 * This serves as the main application window for the Paint
 * application.
 * 
 * @author Joseph Randall Hunt
 * @author Brian M. Lenau
 * @version Nov 28, 2010
 */
public class PaintFrame extends JFrame {

    /** Used for selecting ShapeProducers. */
    private ShapeButtonPanel shapeButtonPanel = new ShapeButtonPanel();

    /** Used for changing the colors on ShapeProducers. */
    private ColorSelectionPanel colorSelectionPanel = 
                            new ColorSelectionPanel();

    /** Used for ShapeProducers to draw on. */
    private DrawPanel drawPanel = new DrawPanel(Color.WHITE);

    /** Edit-Undo menu item */
    private JMenuItem undoMenuItem = new JMenuItem("Undo");

    /** Edit-Redo menu item */
    private JMenuItem redoMenuItem = new JMenuItem("Redo");

    /** File-Exit menu item */
    private JMenuItem exitMenuItem = new JMenuItem("Exit");

    /** File-Save menu item */
    private JMenuItem saveMenuItem = new JMenuItem("Save");

    /** File-Open menu item */
    private JMenuItem openMenuItem = new JMenuItem("Open");

    /** Default window width */
    public static final int FRAME_WIDTH = 600;

    /** Default window width */
    public static final int FRAME_HEIGHT = 650;

    /**
     * Initialize paint frame, set the default width, height, center
     * it, set the layout, attach listeners, add the draw panel to a
     * scroll pane, and set the default close operation.
     */
    public PaintFrame() {
        // BorderLayout with 5 units spacing between elements
        this.setLayout(new BorderLayout(5, 5));
        this.setJMenuBar(createMenuBar());
        this.add(this.shapeButtonPanel, BorderLayout.WEST);
        this.add(this.colorSelectionPanel, BorderLayout.SOUTH);
        this.add(new JScrollPane(this.drawPanel), BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        attatchListeners();
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    }

    /** This method adds listeners to all of the buttons and menus. */
    private void attatchListeners() {
        ColorChangeListener colorChangeListener = new ColorChangeListener() {

            @Override
            public void colorChanged(Color color) {
                PaintFrame.this.drawPanel.setDrawColor(color);
            }
        };
        this.colorSelectionPanel.addColorChangeListener(colorChangeListener);
        ButtonSelectionListener buttonSelectionListener = 
            new ButtonSelectionListener() {

            @Override
            public void selectionChanged() {
                PaintFrame.this.drawPanel.setShapeProducer(
                        PaintFrame.this.shapeButtonPanel.getShapeProducer());
            }
        };
        this.shapeButtonPanel.addButtonSelectionListener(
                buttonSelectionListener);
    }

    /** Create the PaintFrame menubar. */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createHelpMenu());
        return menuBar;
    }

    /** Create the File menu and attach listeners. */
    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(this.openMenuItem);
        fileMenu.add(this.saveMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(this.exitMenuItem);

        this.openMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(PaintFrame.this);
                try {
                    PaintFrame.this.drawPanel.load(
                        fileChooser.getSelectedFile());
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.saveMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showSaveDialog(PaintFrame.this);
                try {
                    PaintFrame.this.drawPanel.save(
                        fileChooser.getSelectedFile());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.exitMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PaintFrame.this.dispose();
            }
        });
        return fileMenu;
    }

    /** Create the Edit menu and attatch listeners */
    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        JMenuItem clearMenuItem = new JMenuItem("Clear");

        this.undoMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PaintFrame.this.drawPanel.undo();
            }
        });

        this.redoMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PaintFrame.this.drawPanel.redo();
            }
        });

        clearMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PaintFrame.this.drawPanel.clear();
            }
        });
        this.undoMenuItem.setEnabled(false);
        this.redoMenuItem.setEnabled(false);
        editMenu.addMenuListener(new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) {
                PaintFrame.this.undoMenuItem.setEnabled(
                    PaintFrame.this.drawPanel.canUndo());
                PaintFrame.this.redoMenuItem.setEnabled(
                    PaintFrame.this.drawPanel.canRedo());
            }

            @Override
            public void menuDeselected(MenuEvent e) {
                // NOT NEEDED
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                PaintFrame.this.undoMenuItem.setEnabled(false);
                PaintFrame.this.redoMenuItem.setEnabled(false);
            }
        });
        editMenu.add(this.undoMenuItem);
        editMenu.add(this.redoMenuItem);
        editMenu.add(clearMenuItem);
        return editMenu;
    }

    /** Create the help menu and the help system information dialog */
    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(PaintFrame.this, 
                        "By Brian, Randall, Raphel, and Nathan", 
                        "Awesome Paint", 
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (HeadlessException ex) {
                    // DO NOTHING
                }
            }
        });
        helpMenu.add(aboutMenuItem);
        return helpMenu;
    }
}
