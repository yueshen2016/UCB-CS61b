package bearmaps.proj2ab;

import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet{
    private Node root;

    public KDTree(List<Point> points) {
        root = new Node(points.get(0));
        if (points.size() > 1) {
            for (int i = 1; i < points.size(); i++) {
                add(root, points.get(i), 0);
            }
        }
    }

    private Node add(Node node, Point point, int depth) {
        if (node == null) return new Node(point);

        if(depth % 2 == 0) {
            if(Double.compare(point.getX(), node.point.getX()) >= 0) {
                node.rightChild = add(node.rightChild, point, depth + 1);
            } else {
                node.leftChild = add(node.leftChild, point, depth + 1);
            }
        } else {
            if(Double.compare(point.getY(), node.point.getY()) >= 0) {
                node.rightChild = add(node.rightChild, point, depth + 1);
            } else {
                node.leftChild = add(node.leftChild, point, depth + 1);
            }
        }
        return node;
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node ret = findNearest(root, goal, root, 0);
        return ret.point;
    }

    private Node findNearest(Node n, Point goal, Node best, int depth) {
        if (n == null) return best;
        if (Point.distance(n.point, goal) < Point.distance(best.point, goal)) {
            best = n;
        }
        Node goodSide = new Node(new Point(0,0));
        Node badSide = new Node(new Point(0,0));
        if (depth % 2 == 0) {
            if (Double.compare(goal.getX(), n.point.getX()) < 0) {
                goodSide = n.leftChild;
                badSide = n.rightChild;
            }
            else {
                goodSide = n.rightChild;
                badSide = n.leftChild;
            }
        } else {
            if (Double.compare(goal.getY(), n.point.getY()) < 0) {
                goodSide = n.leftChild;
                badSide = n.rightChild;
            }
            else {
                goodSide = n.rightChild;
                badSide = n.leftChild;
            }
        }
        best = findNearest(goodSide, goal, best, depth + 1);
        boolean checkBadSide = false;
        if (depth % 2 == 0) {
            if (Double.compare(Point.distance(best.point, goal), Point.distance(new Point(n.point.getX(), goal.getY()), goal)) > 0) {
                checkBadSide = true;
            }
        } else {
            if (Double.compare(Point.distance(best.point, goal), Point.distance(new Point(goal.getX(), n.point.getY()), goal)) > 0) {
                checkBadSide = true;
            }
        }
        if (checkBadSide) {
            best = findNearest(badSide, goal, best, depth + 1);
        }
        return best;
    }

    private class Node {
        private Point point;
        private Node leftChild;
        private Node rightChild;

        public Node(Point point) {
            this.point = point;
            leftChild = null;
            rightChild = null;
        }
    }
}
