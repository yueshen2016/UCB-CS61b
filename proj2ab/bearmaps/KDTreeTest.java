package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KDTreeTest {
    @Test
    public void naiveTest() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(2.0,3.0));
        points.add(new Point(4.0,2.0));
        points.add(new Point(1.0,5.0));
        points.add(new Point(4.0,5.0));
        points.add(new Point(3.0,3.0));
        points.add(new Point(4.0,4.0));
        KDTree kdTree = new KDTree(points);
        NaivePointSet naivePointSet = new NaivePointSet(points);
        Point best = naivePointSet.nearest(0,7);
        System.out.println(best.toString());
        Point best2 = kdTree.nearest(0,7);
        System.out.println(best2.toString());
        assertEquals(best, best2);
    }

    @Test
    public void randomizedTest() {
        List<Point> points = new ArrayList<>();
        Random random = new Random();
        random.setSeed(1);
        for(int i = 0; i < 100000; i++) {
            double randX = random.nextDouble();
            double randY = random.nextDouble();
            points.add(new Point(randX, randY));
        }
        NaivePointSet naivePointSet = new NaivePointSet(points);
        KDTree kdTree = new KDTree(points);
        for(int j = 0; j < 10000; j++) {
            double testRandX = random.nextDouble();
            double testRandY = random.nextDouble();
            Point naiveBest = naivePointSet.nearest(testRandX, testRandY);
            Point kdTreeBest = kdTree.nearest(testRandX, testRandY);
            assertEquals(naiveBest, kdTreeBest);
        }
    }

    @Test
    public void timeSpendTest() {
        List<Point> points = new ArrayList<>();
        Random random = new Random();
        random.setSeed(1);
        for(int i = 0; i < 100000; i++) {
            double randX = random.nextDouble();
            double randY = random.nextDouble();
            points.add(new Point(randX, randY));
        }
        NaivePointSet naivePointSet = new NaivePointSet(points);
        KDTree kdTree = new KDTree(points);
        long start1 = System.currentTimeMillis();
        Random random1 = new Random();
        random1.setSeed(2);
        for(int j = 0; j < 10000; j++) {
            double testRandX = random1.nextDouble();
            double testRandY = random1.nextDouble();
            Point naiveBest = naivePointSet.nearest(testRandX, testRandY);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("Time Elapsed for naivePointSet: " + (end1 - start1));

        long start2 = System.currentTimeMillis();
        Random random2 = new Random();
        random2.setSeed(2);
        for(int j = 0; j < 10000; j++) {
            double testRandX = random2.nextDouble();
            double testRandY = random2.nextDouble();
            Point kdTreeBest = kdTree.nearest(testRandX, testRandY);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Time Elapsed for KDTree: " + (end2 - start2));
        assertTrue((end2 - start2) / (end1 - start1) < 0.1);
    }
}
