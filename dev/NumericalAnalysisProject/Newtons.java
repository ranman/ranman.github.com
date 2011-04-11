public class Newtons extends AbstractMethod {
    private double start         = 0.5;
    private double tolerance     = 1e-14;
    private int    maxIterations = DEFAULT_MAX_ITERATIONS;
    public F       f             = F.Poly;

    public Newtons(double start, double tolerance, int maxIterations, F f) {
        this.start = start;
        this.tolerance = tolerance;
        this.maxIterations = maxIterations;
        this.currentI = 1;
        this.f = f;
    }

    @Override
    public void back() {
        // TO-DO
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String printStep() {
        return String.format("\n%d %12f", currentI, start);
    }

    @Override
    public void step() {
        if (currentI < maxIterations && finished != true) {
            if (Math.abs(f.eval(start)) < tolerance) {
                foundSolution = true;
                finished = true;
                return;
            }
            start = start - f.eval(start) / f.deriv(start);
            currentI++;
        } else
            finished = true;
    }

    public static void main(String args[]) {
        if (args.length != 4) {
            printUsageAndExit();
        }
        Newtons newton =
                new Newtons(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Integer
                        .parseInt(args[2]), F.valueOf(args[3]));
        Long startTime = System.currentTimeMillis();
        while (!newton.finished()) {
            System.out.print(newton.printStep());
            newton.step();
        }
        Long endTime = System.currentTimeMillis();

        if (newton.foundSolution())
            System.out.println("\n\n Done. \n 0 found at: " + newton.getStart());
        else
            System.out.println("\n\n Unable to find result in " + newton.getMaxIterations());

        System.out.println("Execution took: " + (endTime - startTime) + " ms and "
                + newton.getCurrentI() + " iterations");

    }

    public static void printUsageAndExit() {
        System.out.println("Usage: java Newton start tolerance iterations <function>");
        System.out.println("Optional parameter functions can be:");
        for (F f : F.values())
            System.out.println(f.name() + " == " + f.getDescription());
        System.exit(0);
    }

    /**
     * @return the start
     */
    public double getStart() {
        return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(double start) {
        this.start = start;
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
