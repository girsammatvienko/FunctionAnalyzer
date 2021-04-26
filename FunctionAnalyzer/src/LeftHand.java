import java.io.IOException;

public class LeftHand implements Evaluatable {

    private double a;

    public LeftHand(double a) {
        this.a = a;
    }

    public LeftHand() {
        this(0);
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    @Override
    public double evalf(double x) {
        return 1.0 / Math.pow(Math.cosh(x), 2) - a * x;
    }

    public static void main(String[] args) throws IOException {
        LeftHand fun = new LeftHand();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("a: ");
        double a = scanner.nextDouble();
        fun.setA(a);
        System.out.print("xBeg: ");
        double xBeg = scanner.nextDouble();
        System.out.print("xEnd: ");
        double xEnd = scanner.nextDouble();
        System.out.print("xStep: ");
        double xStep = scanner.nextDouble();

        System.out.println("Parametr a: " + fun.getA());
        java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("LeftHand_A" + a + ".dat"));

        for (double x = xBeg; x <= xEnd; x += xStep) {
            System.out.printf("x: %6.4f\tf: %6.4f\n", x, fun.evalf(x));
            writer.printf("x: %6.4f\tf: %6.4f\n", x, fun.evalf(x));
        }
        writer.close();
    }
}
