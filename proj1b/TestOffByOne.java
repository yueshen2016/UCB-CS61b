import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualCharts() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('A', 'B'));
        assertTrue(offByOne.equalChars('%', '&'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertFalse(offByOne.equalChars('a', 'c'));
        assertFalse(offByOne.equalChars('@', '#'));
        assertFalse(offByOne.equalChars('a', 'B'));
    }
}
