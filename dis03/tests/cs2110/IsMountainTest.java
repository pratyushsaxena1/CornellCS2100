package cs2110;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class IsMountainTest {

    @DisplayName("WHEN an array is a single element, THEN it's considered a mountain")
    @Test
    void testSingleton() {
        int[] nums = {1};
        assertTrue(LoopInvariants.isMountain(nums));
    }

    @DisplayName("WHEN an array is strictly increasing, THEN it's considered a mountain")
    @Test
    void testIncreasing() {
        int[] nums = {10, 20, 30, 40, 50};
        assertTrue(LoopInvariants.isMountain(nums));
    }

    @DisplayName("WHEN an array is strictly decreasing, THEN it's considered a mountain")
    @Test
    void testDecreasing() {
        int[] nums = {50, 40, 30, 20, 10};
        assertTrue(LoopInvariants.isMountain(nums));
    }

    @DisplayName("WHEN an array is not strictly increasing and then decreasing, THEN it's not considered a mountain")
    @Test
    void testNotMountainBasic() {
        int[] nums = {10, 20, 10, 20, 10};
        assertFalse(LoopInvariants.isMountain(nums));
    }

    @DisplayName("WHEN all elements are equal, THEN it's considered a mountain")
    @Test
    void testFlat() {
        int[] nums = {10, 10, 10, 10, 10};
        assertTrue(LoopInvariants.isMountain(nums));
    }

    @DisplayName("WHEN the array is never decreasing, THEN it's considered a mountain")
    @Test
    void testIncreasingFlat() {
        int[] nums = {10, 20, 30, 40, 40};
        assertTrue(LoopInvariants.isMountain(nums));
        int[] nums2 = {10, 40, 40, 40, 40};
        assertTrue(LoopInvariants.isMountain(nums2));
        int[] nums3 = {10, 20, 20, 40, 40};
        assertTrue(LoopInvariants.isMountain(nums3));
    }

    @DisplayName("WHEN the array is never increasing, THEN it's considered a mountain")
    @Test
    void testDecreasingFlat() {
        int[] nums = {50, 40, 30, 20, 20};
        assertTrue(LoopInvariants.isMountain(nums));
        int[] nums2 = {50, 50, 30, 20, 20};
        assertTrue(LoopInvariants.isMountain(nums2));
        int[] nums3 = {50, 50, 50, 50, 40};
        assertTrue(LoopInvariants.isMountain(nums3));
    }

    @DisplayName("WHEN the array has multiple flat sections but is non-decreasing and " +
            "then non-increasing, THEN it's considered a mountain")
    @Test
    void testMultipleFlat() {
        int[] nums = {10, 10, 20, 20, 30, 30, 30, 20, 10};
        assertTrue(LoopInvariants.isMountain(nums));
        int[] nums2 = {40, 50, 60, 20, 10, 10};
        assertTrue(LoopInvariants.isMountain(nums2));
        int[] nums3 = {10, 20, 20, 20, 10, 10, 10, 10};
        assertTrue(LoopInvariants.isMountain(nums3));
    }

    @DisplayName("WHEN the array is large and has multiple flat sections but is non-decreasing and " +
            "then non-increasing, THEN it's considered a mountain")
    @Test
    void testLarge() {
        int[] nums = new int[200];
        for (int i = 0; i < 200; i++) {
            if (i < 100) nums[i] = (i / 3) * 10;
            if (i > 100) nums[i] = ((100 - i) / 4) * 10;
        }
        assertTrue(LoopInvariants.isMountain(nums));
    }

    @DisplayName("WHEN the array is large and has one element that disqualifies it from being a mountain, " +
            "THEN it's not considered a mountain")
    @Test
    void testLargeNotMountain() {
        int[] nums = new int[200];
        for (int i = 0; i < 200; i++) {
            if (i < 100) nums[i] = (i / 3) * 10;
            if (i > 100) nums[i] = ((100 - i) / 4) * 10;
        }
        nums[95] = 0;
        assertFalse(LoopInvariants.isMountain(nums));
    }

}
