package cs2110;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SecondLargestTest {

    @DisplayName("WHEN an array has two elements, THEN the second largest number is equal to " +
            "the array's smallest number")
    @Test
    void testSmallestOfTwo() {
        int[] nums = {2, 1};
        assertEquals(1, LoopInvariants.secondLargest(nums));

        // reversed
        int[] nums2 = {1, 2};
        assertEquals(1, LoopInvariants.secondLargest(nums2));
    }

    @DisplayName("WHEN an array has three elements, THEN the second largest number is equal to " +
            "the median of the array")
    @Test
    void testMiddleOfThree() {
        int[] nums = {2, 1, 3};
        assertEquals(2, LoopInvariants.secondLargest(nums));

        int[] nums2 = {1, 2, 3};
        assertEquals(2, LoopInvariants.secondLargest(nums2));

        int[] nums3 = {1, 3, 2};
        assertEquals(2, LoopInvariants.secondLargest(nums3));
    }

    @DisplayName("WHEN an array has negative elements, THEN the second largest number is correct")
    @Test
    void testNegativeElements() {
        int[] nums = {-2, -1, -3};
        assertEquals(-2, LoopInvariants.secondLargest(nums));

        int[] nums2 = {-1, -2, -3};
        assertEquals(-2, LoopInvariants.secondLargest(nums2));

        int[] nums3 = {-1, -3, -2};
        assertEquals(-2, LoopInvariants.secondLargest(nums3));
    }

    @DisplayName("WHEN an array has many elements, THEN the second largest number is correct")
    @Test
    void testManyElements() {
        int[] nums = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        assertEquals(90, LoopInvariants.secondLargest(nums));

        int[] nums2 = {10, 90, 30, 40, 50, 60, 70, 80, 20, 100};
        assertEquals(90, LoopInvariants.secondLargest(nums2));

        int[] nums3 = {10, 100, 30, 40, 90, 60, 70, 80, 50, 20};
        assertEquals(90, LoopInvariants.secondLargest(nums3));

        int[] nums4 = new int[10000];
        for (int i = 0; i < 10000; i++) {
            nums4[i] = i;
        }
        assertEquals(9998, LoopInvariants.secondLargest(nums4));
    }
}
