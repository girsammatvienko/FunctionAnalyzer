import java.util.Scanner;

public class FFunction implements Evaluatable {

    private double a;

    public FFunction(double a) {
        this.a = a;
    }

    public FFunction() {
        this(1.0);
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    @Override
    public double evalf(double x) {
        return Math.exp(-x * x) * Math.sin(x);
    }

    public static void main(String[] args) {

        FFunction fun = new FFunction();
        Scanner scanner = new Scanner(System.in);

        System.out.print("xBeg: ");
        double xBeg = scanner.nextDouble();

        System.out.print("xEnd: ");
        double xEnd = scanner.nextDouble();

        System.out.print("xStep: ");
        double xStep = scanner.nextDouble();

        System.out.println("Parametr a" + fun.getA());
        for (double x = xBeg; x <= xEnd; x += xStep) {
            System.out.printf("x: %6.4f\tf:%6.4f\n", x, fun.evalf(x));
        }

        System.out.print("x: ");
        double x = scanner.nextDouble();

        System.out.print("aBeg: ");
        double aBeg = scanner.nextDouble();

        System.out.print("aEnd: ");
        double aEnd= scanner.nextDouble();

        System.out.print("aStep: ");
        double aStep = scanner.nextDouble();

        System.out.println("Peremennaya x: " + x);
        for (double a = aBeg; a <= aEnd; a += aStep) {
            fun.setA(a);
            System.out.printf("a: %6.4f\tf: %6.4f\n", fun.getA(), fun.evalf(x));
        }
    }
}
