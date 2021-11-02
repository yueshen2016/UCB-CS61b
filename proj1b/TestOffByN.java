import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    @Test
    public void TestEqualCharts() {
        OffByN obn = new OffByN(3);
        assertTrue(obn.equalChars('a', 'd'));
        assertTrue(obn.equalChars('b', 'e'));
        assertFalse(obn.equalChars('a', 'c'));
        obn = new OffByN(4);
        assertTrue(obn.equalChars('a', 'e'));
        assertFalse(obn.equalChars('a', 'd'));
    }
}
