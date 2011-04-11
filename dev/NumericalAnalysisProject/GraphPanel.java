
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author Randall Hunt
 * 
 */
@SuppressWarnings("serial")
public abstract class GraphPanel extends JPanel {
    // avoid null pointer
    public AbstractMethod method    = new Bisection(-5.0, 5.0, 1E-3, 13, F.x2);
    boolean               drawLine  = true;
    boolean               drawDots  = false;
    int                   dotRadius = 3;
    int                   nPoints   = 100;
    double                maxValue  = 500.0;

    /**
     * 
     */
    public GraphPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(501, 501);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        int w = this.getWidth();
        int h = this.getHeight();
        int y0 = h / 2;
        int x0 = w / 2;
        double xScale = w / nPoints;
        double yScale = h / maxValue;
        g2.drawLine(0, y0, w, y0);
        g2.drawLine(x0, 0, x0, h);

        if (drawLine) {
            for (int i = -nPoints; i < nPoints; i++) {
                int x1 = x0 + (int) (xScale * (i));
                int y1 = y0 - (int) (yScale * method.f.eval(i));
                int x2 = x0 + (int) (xScale * (i + 1));
                int y2 = y0 - (int) (yScale * method.f.eval(i + 1));
                g2.drawLine(x1, y1, x2, y2);
            }
        }
    }

}
