import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DerivativeApplication {
    public static void main(String[] args) throws IOException {
        Evaluatable functs[] = new Evaluatable[3];
        functs[0] = new FFunction(0.5);
        functs[1] = new SolveEqFunction();
        functs[2] = new FileListInterpolation();

        ((SolveEqFunction)functs[1]).setRootApprox(0.7);

        try {
            ((FileListInterpolation)functs[2]).readFromFile("TblFunc.dat");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        String fileName = "";
        for (Evaluatable f : functs) {
            System.out.println("Function: " + f.getClass().getSimpleName());
            fileName = f.getClass().getSimpleName() + ".dat";
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            for (double x = 0.5; x <= 6.5; x += 0.5) {
                System.out.println("x: " + x + "\tf: " + f.evalf(x) + "\tf: " + NumMethods.der(x, 1.0e-4, f));
                writer.printf("%16.6e%16.6e%16.6e\n", x, f.evalf(x), NumMethods.der(x, 1.0e-4, f));
            }
            System.out.println("\n");
            writer.close();
        }
    }
}
