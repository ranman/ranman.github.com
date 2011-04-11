/** All of the functions */
public enum F {
    /** x^3 + 4x^2 -10 */
    Poly {
        @Override
        public double eval(double x) {
            return Math.pow(x, 3.0) + 4 * Math.pow(x, 2.0) - 10;
        }

        @Override
        public double deriv(double x) {
            return 3 * Math.pow(x, 2) + 8 * x;
        }

        @Override
        public double integrate(double x, int c) {
            return (Math.pow(x, 4) / 4) + (4 * Math.pow(x, 3) / 3) - 10 * x + c;
        }

        @Override
        public String getDescription() {
            return "x^3 + 4x^2 -10";
        }
    },
    /** x^2 - 2 */
    x2 {
        @Override
        public double eval(double x) {
            return Math.pow(x, 2.0) - 2;
        }

        @Override
        public double deriv(double x) {
            return 2 * x;
        }

        @Override
        public double integrate(double x, int c) {
            return (Math.pow(x, 3.0) / 3) - 2 * x + c;
        }

        @Override
        public String getDescription() {
            return "x^2-2 \n Use 0 for Newtons method to get horizontal tangent.";
        }
    },
    /** x^3 - 3x + 2 */
    x3 {
        @Override
        public double eval(double x) {
            return Math.pow(x, 3.0) - 3 * x + 2;
        }

        @Override
        public double deriv(double x) {
            return 3 * Math.pow(x, 2.0) - 3;
        }

        @Override
        public double integrate(double x, int c) {
            // FIX
            return (Math.pow(x, 4.0) / 4) + c;
        }

        @Override
        public String getDescription() {
            return "x^3-3x+2 \n Double root at 1";
        }
    },
    /** e^x */
    eX {
        @Override
        public double eval(double x) {
            if (x < .5 && x > -.5) {
                return Math.expm1(x) + 1;
            }
            return Math.exp(x);
        }

        @Override
        public double deriv(double x) {
            return eval(x);
        }

        @Override
        public double integrate(double x, int c) {
            return eval(x);
        }

        @Override
        public String getDescription() {
            return "e^x";
        }
    },
    /** 4arctan */
    arctanx {
        @Override
        public double eval(double x) {
            return 4 * Math.atan(x);
        }

        @Override
        public double deriv(double x) {
            return 4 / (Math.pow(x, 2.0) + 1);
        }

        @Override
        public double integrate(double x, int c) {
            // NEEDS FIXING
            return (x * Math.atan(x) - (.5 * Math.log(Math.pow(x, 2.0) + 1))) + c;
        }

        @Override
        public String getDescription() {
            return "4arctan(x) \n use start = 1.5, and maxIterations = 4 for newtons.";
        }
    },

    /** sin(x) */
    sinx {
        @Override
        public double eval(double x) {
            return Math.sin(x);
        }

        @Override
        public double deriv(double x) {
            return Math.cos(x);
        }

        @Override
        public String getDescription() {
            return "sin(x) \n Newton's function can't decide which root.";
        }

        @Override
        public double integrate(double x, int c) {
            // FIX
            return 0;
        }
    },
    /** cos(x) - x^3 */
    cosx3 {

        @Override
        public double deriv(double x) {
            return 0;
        }

        @Override
        public double eval(double x) {
            return Math.cos(x) - Math.pow(x, 3.0);
        }

        @Override
        public String getDescription() {
            return "cos(x) - x^3";
        }

        @Override
        public double integrate(double x, int c) {
            // TODO Auto-generated method stub
            return 0;
        }

    };

    /**
     * /** returns f(x)
     */
    public abstract double eval(double x);

    /** returns the derivative at x */
    public abstract double deriv(double x);

    /** returns the integral at x */
    public abstract double integrate(double x, int c);

    /** A textual description of the function */
    public abstract String getDescription();

    /** Returns the integral with c=0 */
    public double integrate(double x) {
        return integrate(x, 0);
    }

    /**
     * Returns the numerical derivative of the function. More accurate than the
     * analytical version for some numbers near 0.
     */
    public double numDeriv(double x) {
        return (eval(x + BIT) - eval(x - BIT)) / (2 * BIT);
    }

    /** The constant for use with numerical derivation */
    public static final double BIT = 1e-8;
}
