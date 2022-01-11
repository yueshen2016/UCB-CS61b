package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Point retPoint = new Point(0, 0);
        retPoint = points.get(0);
        double ret = Point.distance(points.get(0), target);
        for (Point point : points) {
            double curr = Point.distance(point, target);
            if (curr < ret) {
                ret = curr;
                retPoint = point;
            }
        }
        return retPoint;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        ret.getX(); // evaluates to 3.3
        ret.getY(); // evaluates to 4.4
        System.out.println(ret.toString());
    }
}
