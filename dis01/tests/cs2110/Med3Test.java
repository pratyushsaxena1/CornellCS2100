package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Med3Test {

    @DisplayName("WHEN three different inputs are passed to the `med3()` in any order, THEN the "
            + "correct median is returned.")
    @Test
    public void testMed3Distinct() {
        assertEquals(2, Practice.med3(1,2,3));
        assertEquals(2, Practice.med3(1,3,2));
        assertEquals(2, Practice.med3(2,1,3));
        assertEquals(2, Practice.med3(2,3,1));
        assertEquals(2, Practice.med3(3,1,2));
        assertEquals(2, Practice.med3(3,2,1));
    }

    @DisplayName("WHEN the inputs to `med3()` include repeated elements, THEN the correct median "
            + "is returned.")
    @Test
    public void testMed3Repeats() {
        assertEquals(2, Practice.med3(1,2,2));
        assertEquals(2, Practice.med3(2,1,2));
        assertEquals(2, Practice.med3(2,2,1));
        assertEquals(2, Practice.med3(3,2,2));
        assertEquals(2, Practice.med3(2,3,2));
        assertEquals(2, Practice.med3(2,2,3));
        assertEquals(2, Practice.med3(2,2,2));
    }
}
