import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetInterpolator extends Interpolator {
    TreeSet<Point2D> points = null;

    public TreeSetInterpolator() {
        points = new TreeSet<>();
    }

    @Override
    public void clear() {
        points.clear();
    }

    @Override
    public int numPoints() {
        return points.size();
    }

    @Override
    public void addPoint(Point2D pt) {
        points.add(pt);
    }

    @Override
    public Point2D getPoint(int i) {
        Iterator iterator = points.iterator();
        for(int j = 0; j <= i; j++){
            if(j==i && iterator.hasNext()){
                return (Point2D) iterator.next();
            }
            if(iterator.hasNext()){
                iterator.next();
            }
        }
        return null;
    }

    @Override
    public void setPoint(int i, Point2D pt) {
        Iterator iterator = points.iterator();
        Object temp = null;
        for(int j = 0; j <= i; j++){
            if(j==i && iterator.hasNext()){
                temp = iterator.next();
            }
            if(iterator.hasNext()){
                iterator.next();
            }
        }
        points.remove(temp);
        points.add(pt);
    }

    @Override
    public void removeLastPoint() {
        Iterator iterator = points.iterator();
        Object temp = null;
        while(iterator.hasNext()){
            temp = iterator.next();
        }
        points.remove(temp);
    }

    @Override
    public void sort() {

    }
}
