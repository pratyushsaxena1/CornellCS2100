package cs2110;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CopyRangeTest {

    @DisplayName("WHEN the length is negative, THEN the method returns false.")
    @Test
    void testNegativeLength() {
        boolean result = ArrayUtilities.copyRange(new int[]{0, 1}, 1, new int[]{0, 1, 2, 3}, 1, -1);
        assertFalse(result);
    }

    @DisplayName("WHEN the srcStart is negative AND the length is more than 0, THEN the method returns false.")
    @Test
    void testNegativeSrcStart() {
        boolean result = ArrayUtilities.copyRange(new int[]{0, 1}, -1, new int[]{0, 1, 2, 3}, 1, 1);
        assertFalse(result);
    }

    @DisplayName("WHEN the dstStart is negative AND the length is more than 0, THEN the method returns false.")
    @Test
    void testNegativeDstStart() {
        boolean result = ArrayUtilities.copyRange(new int[]{0, 1}, 1, new int[]{0, 1, 2, 3}, -1, 1);
        assertFalse(result);
    }

    @DisplayName("WHEN the length plus the srcStart index is greater than src.length AND the length is more than 0, THEN the method returns false.")
    @Test
    void testLengthPlusSrcStart() {
        boolean result = ArrayUtilities.copyRange(new int[]{0, 1}, 1, new int[]{0, 1}, 1, 2);
        assertFalse(result);
    }

    @DisplayName("WHEN the length plus the dstStart index is greater than dst.length AND the length is more than 0, THEN the method returns false.")
    @Test
    void testLengthPlusDstStart() {
        boolean result = ArrayUtilities.copyRange(new int[]{0, 1}, 0, new int[]{0, 1}, 1, 2);
        assertFalse(result);
    }

    @DisplayName("WHEN the srcStart index is out of range AND the length is more than 0, THEN the method returns false.")
    @Test
    void testOutOfRangeSrcStart() {
        boolean result = ArrayUtilities.copyRange(new int[]{0, 1}, 2, new int[]{0, 1, 2, 3}, 1, 1);
        assertFalse(result);
        boolean result2 = ArrayUtilities.copyRange(new int[]{0, 1}, -1, new int[]{0, 1, 2, 3}, 1, 1);
        assertFalse(result2);
    }

    @DisplayName("WHEN the dstStart index is out of range AND the length is more than 0, THEN the method returns false.")
    @Test
    void testOutOfRangeDstStart() {
        boolean result = ArrayUtilities.copyRange(new int[]{0, 1}, 0, new int[]{0, 1}, 2, 1);
        assertFalse(result);
        boolean result2 = ArrayUtilities.copyRange(new int[]{0, 1}, 0, new int[]{0, 1}, -1, 1);
        assertFalse(result2);
    }

    @DisplayName("WHEN src or dst is an empty array AND length is not 0, THEN the method returns false.")
    @Test
    void testEmptyArrays() {
        boolean result = ArrayUtilities.copyRange(new int[]{}, 0, new int[]{1, 2}, 0, 1);
        assertFalse(result);
        boolean result2 = ArrayUtilities.copyRange(new int[]{1, 2}, 0, new int[]{}, 0, 1);
        assertFalse(result2);
    }

    @DisplayName("WHEN copying a valid range between two arrays, THEN dst is updated and the method returns true.")
    @Test
    void testValidCopy() {
        int[] dst = {9, 9, 9, 9};
        boolean result = ArrayUtilities.copyRange(new int[]{1, 2, 3, 4}, 1, dst, 0, 2);
        assertTrue(result);
        assertArrayEquals(new int[]{2, 3, 9, 9}, dst);
    }

    @DisplayName("WHEN copying a full array into another, THEN dst becomes equal to src and the method returns true.")
    @Test
    void testCopyWholeArray() {
        int[] dst = {0, 0, 0};
        boolean result = ArrayUtilities.copyRange(new int[]{5, 6, 7}, 0, dst, 0, 3);
        assertTrue(result);
        assertArrayEquals(new int[]{5, 6, 7}, dst);
    }

    @DisplayName("WHEN copying within the same array with overlapping ranges, THEN the copy works correctly and the method returns true.")
    @Test
    void testCopyWithinSameArrayOverlap() {
        int[] arr = {1, 2, 3, 4, 5};
        boolean result = ArrayUtilities.copyRange(arr, 0, arr, 2, 3);
        assertTrue(result);
        assertArrayEquals(new int[]{1, 2, 1, 2, 3}, arr);
    }

    @DisplayName("WHEN length is zero, THEN nothing changes and the method returns true.")
    @Test
    void testZeroLength() {
        int[] dst = {9, 9, 9};
        boolean result = ArrayUtilities.copyRange(new int[]{1, 2, 3}, 0, dst, 0, 0);
        assertTrue(result);
        assertArrayEquals(new int[]{9, 9, 9}, dst);
    }
}