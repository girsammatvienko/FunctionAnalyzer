import java.io.*;
import java.util.StringTokenizer;

public class FileListInterpolation extends ListInterpolation {
    public FileListInterpolation() {
        super();
    }

    public void readFromFile (String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String s = reader.readLine();

        clear();

        while ((s = reader.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            addPoint(new Point2D(x, y));
        }
        reader.close();
    }

    public void writeToFile(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(fileName));
        writer.printf("%9s%25s\n", "x", "y");
        for (int i = 0; i < numPoints(); i++) {
            writer.println(getPoint(i).getX() + "\t" + getPoint(i).getY());
        }
        writer.close();
    }

    public static void main(String[] args) {
        FileListInterpolation fun = new FileListInterpolation();

        int num;
        double x;
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        do {
            System.out.print("Points number: ");
            num = scanner.nextInt();
        } while (num <= 0);

        for (int i = 0; i < num; i++) {
            x = 1.0 + (5.0 - 1.0)*Math.random();
            fun.addPoint(new Point2D(x, Math.sin(x)));
        }

        System.out.println("Interpolation for:" + fun.numPoints() + " points");
        System.out.println(" Unsorted array: ");

        for (int i = 0; i < fun.numPoints(); i++) {
            System.out.println("Point " + (i + 1) + ": " + fun.getPoint(i));
        }

        fun.sort();

        System.out.println("Sorted array: ");
        for (int i = 0 ; i < fun.numPoints(); i++) {
            System.out.println("Point " + (i + 1) + ": " + fun.getPoint(i));
        }

        System.out.println("MIN value for x: " + fun.getPoint(0).getX());
        System.out.println("MAX value for x: " + fun.getPoint(fun.numPoints() - 1).getX());

        System.out.println("Save in file");

        try {
            fun.writeToFile("data.dat");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Read from file");

        fun.clear();

        try {
            fun.readFromFile("data.dat");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Data from file: ");
        fun.sort();
        for (int i = 0; i < fun.numPoints(); i++) {
            System.out.println("Point " + (i + 1) + ": " + fun.getPoint(i));
        }
        System.out.println("MIN value for x: " + fun.getPoint(0).getX());
        System.out.println("MAX value for x: " + fun.getPoint(fun.numPoints() - 1).getX());

        x = 0.5 * (fun.getPoint(0).getX() + fun.getPoint(fun.numPoints() - 1).getX());

        System.out.println("Interpolation value fun(" + x + ") = " + fun.evalf(x));
        System.out.println("Value for sin(" + x + ") = " + Math.sin(x));
        System.out.println("Absolute misstate = " + Math.abs(fun.evalf(x) - Math.sin(x)));

        System.out.println("Reading data to count");
        fun.clear();

        for (x = 1.0; x < 7.0; x += 0.1) {
            fun.addPoint(new Point2D(x, Math.sin(x)));
        }

        try {
            fun.writeToFile("TblFunc.dat");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

}
