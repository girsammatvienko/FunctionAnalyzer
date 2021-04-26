
public class Point2D extends Point implements Comparable<Point2D> {

    public Point2D(double x, double y) {
        super(2);
        setCoords(1, x);
        setCoords(2, y);
    }

    public Point2D() {
        this(0,0);
    }

    public double getX() {
        return getCoords(1);
    }

    public double getY() {
        return  getCoords(2);
    }

    public void setX(double x) {
        setCoords(1, x);
    }

    public void setY(double y) {
        setCoords(2, y);
    }

    @Override
    public int compareTo(Point2D pt) {
        return Double.compare(getX(), pt.getX());
    }

    public static void main(String[] args) {
        java.util.List<Point2D> data = new java.util.ArrayList<>();
        int num;
        double x;

        java.util.Scanner scanner = new java.util.Scanner(System.in);

        do {
            System.out.print("Point number: ");
            num = scanner.nextInt();
        } while (num <= 0);

        for (int i = 0; i < num; i++) {
            x = 1.0 + (5.0 - 1.0)*Math.random();
            data.add(new Point2D(x, Math.sin(x)));
        }

        System.out.println("Unsorted data: ");
        for (Point2D pt : data) {
            System.out.println(pt);
        }

        System.out.println("Sorted data: ");
        java.util.Collections.sort(data);
        for (Point2D pt : data) {
            System.out.println("x = " + pt.getX() + "\t y = " + pt.getY());
        }
    }
}
