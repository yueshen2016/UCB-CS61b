package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;

public class ArrayHeapMinPQTest {

    @Test
    public void sanityAddTest()  {
        ArrayHeapMinPQ<String> myPQ = new ArrayHeapMinPQ<>();
        for(int i = 0; i < 10; i++) {
            myPQ.add("t" + i, i);
        }
        assertEquals("t0", myPQ.getSmallest());
        assertEquals("t0", myPQ.removeSmallest());
        assertEquals("t1", myPQ.getSmallest());
        assertNotEquals("t2", myPQ.getSmallest());
    }

    @Test
    public void sanityContainsTest() {
        ArrayHeapMinPQ<String> myPQ = new ArrayHeapMinPQ<>();
        for(int i = 0; i < 5; i++) {
            myPQ.add("t" + i, i);
        }
        assertTrue(myPQ.contains("t2"));
        assertFalse(myPQ.contains("t12"));
    }

    @Test
    public void sanityChangePriorityTest() {
        ArrayHeapMinPQ<String> myPQ = new ArrayHeapMinPQ<>();
        for(int i = 0; i < 10; i++) {
            myPQ.add("t" + i, i + 1);
        }
        myPQ.changePriority("t7", 0);
        assertTrue(myPQ.contains("t7"));
        assertEquals("t7", myPQ.getSmallest());
        myPQ.changePriority("t1", -1);
        assertEquals("t1", myPQ.removeSmallest());
    }

    @Test
    public void timeSpendTest() {
        NaiveMinPQ<Integer> naiveMinPQ = new NaiveMinPQ<>();

        for(int i = 0; i < 1000000; i++) {
            naiveMinPQ.add(i, i + 1);
        }
        long start1 = System.currentTimeMillis();
        for(int j = 0; j < 1000; j++) {
            int randomNum = StdRandom.uniform(1000000);
            naiveMinPQ.contains(randomNum);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + (end1 - start1));

        ArrayHeapMinPQ<Integer> myMinPQ = new ArrayHeapMinPQ<>();

        for(int i = 0; i < 1000000; i++) {
            myMinPQ.add(i, i + 1);
        }
        long start2 = System.currentTimeMillis();
        for(int j = 0; j < 1000; j++) {
            int randomNum = StdRandom.uniform(1000000);
            myMinPQ.contains(randomNum);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + (end2 - start2));
    }
}