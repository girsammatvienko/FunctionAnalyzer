import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TreeMapInterpolator extends Interpolator{
    Map<Integer, Point2D> treeMap = null;

    public TreeMapInterpolator(){
        treeMap = new TreeMap();
    }

    @Override
    public void clear() {
        treeMap.clear();
    }

    @Override
    public int numPoints() {
        return treeMap.size();
    }

    @Override
    public void addPoint(Point2D pt) {
        treeMap.put(treeMap.size(), pt);
    }

    @Override
    public Point2D getPoint(int i) {
        return (Point2D) treeMap.get(i);
    }

    @Override
    public void setPoint(int i, Point2D pt) {
        treeMap.put(i,pt);
    }

    @Override
    public void removeLastPoint() {
        treeMap.remove(treeMap.size()-1);
    }

    @Override
    public void sort() {

    }
}
