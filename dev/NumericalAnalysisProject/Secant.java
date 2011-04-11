/**
 * @author Randall Hunt
 */
public class Secant extends AbstractMethod {
    private double tolerance;
    private double x0;
    private double x1;
    private double x;
    private double q0;
    private double q1;
    private int    maxIterations;

    public F       f;

    public Secant(double x0, double x1, double tolerance, int maxIterations, F f) {
        this.x0 = x0;
        this.x1 = x1;
        this.tolerance = tolerance;
        this.maxIterations = maxIterations;
        this.currentI = 2;
        this.f = f;
        this.q0 = f.eval(x0);
        this.q1 = f.eval(x1);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 5) {
            printUsageAndExit();
        }
        Secant secant =
                new Secant(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double
                        .parseDouble(args[2]), Integer.parseInt(args[3]), F.valueOf(args[4]));
        Long startTime = System.currentTimeMillis();
        while (!secant.finished()) {
            System.out.println(secant.printStep());
            secant.step();
        }
        Long endTime = System.currentTimeMillis();

        if (secant.foundSolution())
            System.out.println("\n\n Done. \n 0 found at: " + secant.getX1());
        else
            System.out.println("\n\n Unable to find result in " + secant.getMaxIterations());

        System.out.println("Execution took: " + (endTime - startTime) + " ms and "
                + secant.getCurrentI() + " iterations");
    }

    public static void printUsageAndExit() {
        System.out.println("Usage: java Secant x0 x1 tolerance iterations <function>");
        System.out.println("Optional parameter functions can be:");
        for (F f : F.values())
            System.out.println(f.name() + " == " + f.getDescription());
        System.exit(0);
    }

    @Override
    public void back() {
        // TODO
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String printStep() {
        return String.format("\n%d %15f %15f", currentI, x1, f.eval(x1));
    }

    @Override
    public void step() {
        x = x1 - q1 * (x1 - x0) / (q1 - q0);
        if (currentI < maxIterations && finished != true) {
            if (Math.abs(x - x1) < tolerance) {
                foundSolution = true;
                finished = true;
                return;
            }
            currentI++;
            x0 = x1;
            q0 = q1;
            x1 = x;
            q1 = f.eval(x);
        } else
            finished = true;
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
     * @return the x0
     */
    public double getX0() {
        return x0;
    }

    /**
     * @param x0
     *            the x0 to set
     */
    public void setX0(double x0) {
        this.x0 = x0;
    }

    /**
     * @return the x1
     */
    public double getX1() {
        return x1;
    }

    /**
     * @param x1
     *            the x1 to set
     */
    public void setX1(double x1) {
        this.x1 = x1;
    }

    /**
     * @return the maxIterations
     */
    public int getMaxIterations() {
        return maxIterations;
    }

    /**
     * @param maxIterations
     *            the maxIterations to set
     */
    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }
}
