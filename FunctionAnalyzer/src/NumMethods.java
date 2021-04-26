public class NumMethods {

    private NumMethods() {}

    private static double meth(double x, double h, Evaluatable f) {
        return 0.5 * (f.evalf(x + h) - f.evalf(x - h))/h;
    }

    public static double der(double x, double tol, Evaluatable f) {
        final int MAX = 100;
        double h = 0.1;
        double one = meth(x, h, f);
        h = 0.1 * h;
        double two = meth(x, h, f);
        int i = 0;
        double tmp;
        boolean ok;

        do {
            h = 0.1 * h;
            tmp = meth(x, h, f);
            ok = (Math.abs(tmp - two) >= Math.abs(two - one)) || (Math.abs(two - one) < tol);

            if (i > MAX) {
                System.out.print("Too many steps of counting");
                System.exit(-1);
            }
            i += 1;
            one = two;
            two = tmp;
        } while (!ok);
        return two;
    }

    public static double findRoot(double appr, double eps, Evaluatable f) {
        final int MAX_ITER = 100;
        int k = 0;
        double delta = 1.0e-1*appr;
        double old1 = appr, old2 = appr + delta;
        double res, error;

        do {
            k += 1;
            res = old2 - f.evalf(old2) * (old1 - old2) / (f.evalf(old1) - f.evalf(old2));
            error = Math.abs(res - old2);
            old1 = old2;
            old2 = res;
            if (k > MAX_ITER) {
                System.out.print("Too many steps of counting");
                System.exit(-1);
            }
        } while (error >= eps);
        return res;
    }

    public static void main(String[] args) {
        double resEq1, resEq2;

        resEq1 = NumMethods.findRoot(1.0, 1.0e-7, new Evaluatable() {
        public double evalf(double x) {return x * x - 4;}
        });

        resEq2 = NumMethods.findRoot(-1.0, 1.0e-7, new Evaluatable() {
            public double evalf(double x) {return x * x - 4;}
        });

        System.out.println("First Root: " + resEq1 + "Second Root: " + resEq2);

        ListInterpolation fun = new ListInterpolation();

        int num;
        double x = -0.5 * Math.PI;
        double step = 0.1;
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        do {
            System.out.print("Number of points: ");
            num = scanner.nextInt();
        } while (num <= 0);

        for (int i = 0; i < num; i++) {
            x += step;
            fun.addPoint(new Point2D(x, Math.sin(x)));
        }

        x = 0.5 * (fun.getPoint(0).getX() + fun.getPoint(fun.numPoints() - 1).getX());

        double res = NumMethods.der(x, 0.1e-5, fun);

        System.out.println("Number value sin'(" + x + ")' = " + res);
        System.out.println("Symbol value sin'(" + x + ")' = " + Math.cos(x));

        System.out.println("Absolute mistake = " + Math.abs(res - Math.cos(x)));
    }

}
