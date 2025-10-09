package cs2110;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public abstract class GcdTest {
    abstract int gcd(int m, int n);

    @DisplayName("WHEN m=n, THEN their GCD is m.")
    @Test
    void testGcdSame() {
        assertEquals(2, gcd(2,2));
        assertEquals(12, gcd(12,12));
        assertEquals(123456, gcd(123456,123456));
    }

    @DisplayName("WHEN m is a proper divisor of n, THEN their GCD is m.")
    @Test
    void testGcdDivisor() {
        assertEquals(2, gcd(2,6));
        assertEquals(5, gcd(5,1200));
        assertEquals(36, gcd(36,4572));
    }

    @DisplayName("WHEN m and n are relatively prime, THEN their GCD is 1.")
    @Test
    void testGcdRelativelyPrime() {
        assertEquals(1, gcd(4,9));
        assertEquals(1, gcd(51,1400));
        assertEquals(1, gcd(1791681,12702877));
    }

    @DisplayName("WHEN m is not a common divisor of n, but m and n share a proper divisor, THEN "
            + "their GCD is computed correctly.")
    @Test
    void testGcdNontrivial() {
        assertEquals(2, gcd(4,6));
        assertEquals(6, gcd(24,30));
        assertEquals(115, gcd(9660,330395));
        assertEquals(98192, gcd(381672304,1791120272));
    }
}

class GcdLoopTest extends GcdTest {
    @Override
    int gcd(int m, int n) {
        return Practice.gcdLoop(m, n);
    }
}

class GcdEuclideanIterativeTest extends GcdTest {
    @Override
    int gcd(int m, int n) {
        return Practice.gcdEuclideanIterative(m, n);
    }
}

class GcdEuclideanRecursiveTest extends GcdTest {
    @Override
    int gcd(int m, int n) {
        return Practice.gcdEuclideanRecursive(m, n);
    }
}

