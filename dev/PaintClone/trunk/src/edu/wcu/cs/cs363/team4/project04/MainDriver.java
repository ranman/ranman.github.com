package edu.wcu.cs.cs363.team4.project04;

import javax.swing.SwingUtilities;

import edu.wcu.cs.cs363.team4.project04.gui.PaintFrame;

/**
 * This class is the entry-point for the Paint application.
 * 
 * @author Joseph Randall Hunt
 * @author Brian M. Lenau
 * @author Nathan von Sprecken
 * @author Raphel Ester
 * @version Nov 28, 2010
 */
public class MainDriver {

    /**
     * This method creates a <code>PaintFrame</code>, packs it, and
     * makes it visible.
     */
    private static void createAndShowGUI() {
        PaintFrame paintFrame = new PaintFrame();
        paintFrame.pack();
        paintFrame.setLocationRelativeTo(null); // center frame
        paintFrame.setVisible(true);
    }

    /**
     * This method will start the paint application in the event-queue
     * thread.
     * 
     * @param args
     *            not used in this application
     */
    public static void main(String...args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
