/**
 * @author Randall Hunt
 * 
 */
public abstract class AbstractMethod {
    public static final int DEFAULT_MAX_ITERATIONS = 100;
    /** finished? */
    boolean                 finished;
    /** solution? */
    boolean                 foundSolution;
    /** A description of the method */
    public String           DESCRIPTION;
    /** The function f to be used in evaluating */
    public F                f;

    /**
     * @return foundSolution
     */
    public boolean foundSolution() {
        return foundSolution;
    }

    /**
     * @return the finished
     */
    public boolean finished() {
        return finished;
    }

    /**
     * @return the currentI
     */
    public int getCurrentI() {
        return currentI;
    }

    int currentI;

    /** go forward one iteration */
    public abstract void step();

    /** go backwards one iteration */
    public abstract void back();

    /**
     * print step results
     * 
     * @return the stuff to print for this step
     */
    public abstract String printStep();

    /**
     * @return The description of the method in use.
     */
    public abstract String getDescription();
}
