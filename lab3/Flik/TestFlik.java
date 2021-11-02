import static org.junit.Assert.*;

import org.junit.Test;

public class TestFlik {
    @Test
    public void test() {
        int a = 0;
        int b = 0;
        assertEquals(true, Flik.isSameNumber(a, b));
        a = 128;
        b = 128;
        assertTrue(Flik.isSameNumber(a, b));
        b = 129;
        assertNotEquals(true, Flik.isSameNumber(a, b));

    }
}