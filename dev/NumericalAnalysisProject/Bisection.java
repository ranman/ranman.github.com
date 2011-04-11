/**
 * This method will find a solution to f(x) = 0 given the continuous function f
 * on the interval[a, b] where f(a) and f(b) have opposite signs.
 * 
 * @author Randall Hunt
 * 
 */
public class Bisection extends AbstractMethod {
    /** Endpoint */
    private double a         = 0;
    /** Endpoint */
    private double b         = 2;
    /** Tolerance for error */
    private double tolerance = 1E-3;
    /** Current Guess */
    private double p         = a + (b - a) / 2.0;
    /** Maximum number of iterations */
    private int    max       = 30;

    /**
     * @param a
     * @param b
     * @param tolerance
     * @param max
     * @param function
     */
    public Bisection(double a, double b, double tolerance, int max, F function) {
        this.a = a;
        this.b = b;
        this.p = a + (b - a) / 2.0;
        this.tolerance = tolerance;
        this.max = max;
        this.f = function;
        this.currentI = 0;
        this.finished = false;
        this.DESCRIPTION =
                "This method will find a solution to f(x) = 0 "
                        + "given the continuous function f on the"
                        + " interval [a, b] where f(a) and f(b) have opposite signs.";
    }

    @Override
    public void step() {
        if (currentI < max && finished != true) {
            if (Math.abs(b - a) < (2 * tolerance)) {
                finished = true;
                foundSolution = true;
                return;
            }
            if (f.eval(p) <= 0)
                a = p;
            else
                b = p;

            p = a + (b - a) / 2;

            ++currentI;
        } else {
            finished = true;
        }
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void back() {
        // TODO Auto-generated method stub
    }

    @SuppressWarnings("boxing")
    @Override
    public String printStep() {
        return String.format("\n%d %12f %12f %12f %12f", currentI, a, b, p, f.eval(p));
    }

    /**
     * @param args
     */
    @SuppressWarnings("boxing")
    public static void main(String args[]) {
        double a = 0, b = 0, tol = 0;
        int max = 0;
        F f = F.Poly;
        if (args.length == 5) {
            a = Double.parseDouble(args[0]);
            b = Double.parseDouble(args[1]);
            tol = Double.parseDouble(args[2]);
            max = Integer.parseInt(args[3]);
            f = F.valueOf(args[4]);
        } else if (args.length == 4) {
            a = Double.parseDouble(args[0]);
            b = Double.parseDouble(args[1]);
            tol = Double.parseDouble(args[2]);
            max = Integer.parseInt(args[3]);
        } else {
            printUsageAndExit();
        }
        Bisection bisection = new Bisection(a, b, tol, max, f);
        System.out.println(bisection.getDescription());
        System.out.printf("%s: %10s: %10s: %10s: %10s:\n", "i", "a", "b", "p", "f(p)");
        System.out.printf("------------------------------------------------------");

        Long startTime = System.currentTimeMillis();
        while (!bisection.finished()) {
            System.out.print(bisection.printStep());
            bisection.step();
        }
        Long endTime = System.currentTimeMillis();
        if (bisection.foundSolution())
            System.out.println("\n\nDone\n 0 found at: " + bisection.getP());
        else
            System.out.println("\n\nUnable to find result in " + bisection.getMax()
                    + " iterations.");

        System.out.println("Execution took: " + (endTime - startTime) + " ms and "
                + bisection.currentI + " iterations");
    }

    public static void printUsageAndExit() {
        System.out.println("Usage: java Bisection left right tolerance iterations <function>");
        System.out.println("Optional parameter functions can be:");
        for (F f : F.values())
            System.out.println(f.name() + " == " + f.getDescription());
        System.exit(0);
    }

    /**
     * @return the a
     */
    public double getA() {
        return a;
    }

    /**
     * @param a
     *            the a to set
     */
    public void setA(double a) {
        this.a = a;
    }

    /**
     * @return the b
     */
    public double getB() {
        return b;
    }

    /**
     * @param b
     *            the b to set
     */
    public void setB(double b) {
        this.b = b;
    }

    /**
     * @return the tolerance
     */
    public double getTolerance() {
        return tolerance;
    }

    /**
     * @param tolerance
     *            the tolerance to set
     */
    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    /**
     * @return the p
     */
    public double getP() {
        return p;
    }

    /**
     * @param p
     *            the p to set
     */
    public void setP(double p) {
        this.p = p;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max
     *            the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
}
