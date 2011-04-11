
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * @author Randall Hunt
 * 
 */
@SuppressWarnings("serial")
public class BisectionGraphPanel extends GraphPanel {

    /**
     * 
     */
    public BisectionGraphPanel(double a, double b, double tolerance, int iterations, F f) {
        super();
        this.method = new Bisection(a, b, tolerance, iterations, f);
    }

    protected void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        Graphics2D g = (Graphics2D) g2;
        System.out.println("in paintComponents method");
        Bisection bisection = (Bisection) this.method;
        bisection.step();
        System.out.println("step called");
        g.drawString("Iteration: " + bisection.getCurrentI(), 100, 100);
        int w = this.getWidth();
        int h = this.getHeight();
        int y0 = h / 2;
        int x0 = w / 2;
        double xScale = w / 100;
        double yScale = h / 500;
        g.setPaint(Color.red);
        int x = x0 + (int) (xScale * (bisection.getA()));
        int y = y0 - (int) (yScale * (method.f.eval(bisection.getA())));
        g.fillOval(x - 3, y - 3, 2 * 3, 2 * 3);
        x = x0 + (int) (xScale * bisection.getB());
        y = y0 - (int) (yScale * method.f.eval(bisection.getB()));
        g.fillOval(x - 3, y - 3, 6, 6);
    }
}
