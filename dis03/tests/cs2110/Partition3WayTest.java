package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Partition3WayTest {

    @DisplayName("WHEN an array is a single element, THEN it's unchanged regardless of pivot")
    @Test
    void testSingleton() {
        int[] nums = {1};
        int[] original = nums.clone();

        LoopInvariants.partition3Way(nums, 0);      // nums[0] < pivot
        assertArrayEquals(nums, original);

        LoopInvariants.partition3Way(nums, 1);      // nums[0] == pivot
        assertArrayEquals(original, nums);

        LoopInvariants.partition3Way(nums, 2);      // nums[0] > pivot
        assertArrayEquals(original, nums);
    }

    boolean isPermutation(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }

    @DisplayName("WHEN partitioned, THEN the result is a permutation of the original and no elements get added")
    @Test
    void testPermutation() {
        int[] nums = {3, 2, 4, 1, 6, 8, 3};
        int[] original = nums.clone();

        LoopInvariants.partition3Way(nums, 1);      // nums[0] < pivot
        assertTrue(isPermutation(nums, original));

        LoopInvariants.partition3Way(nums, 6);      // nums[0] == pivot
        assertTrue(isPermutation(nums, original));

        LoopInvariants.partition3Way(nums, 8);      // nums[0] > pivot
        assertTrue(isPermutation(nums, original));
    }

    @DisplayName("WHEN partitioning an array of elements where one element is out of place, " +
            "THEN the result changes the position of the element")
    @Test
    void testSingleChange() {
        int[] nums = {1, 1, 3, 1};
        int[] correct = {1, 1, 1, 3};

        for (int i = 1; i <= 3; i++) {
            int[] cloned = nums.clone();
            LoopInvariants.partition3Way(cloned, i);      // tests with pivots 1, 2, 3
            assertArrayEquals(correct, cloned);
        }

        nums = new int[]{3, 3, 3, 1};
        correct = new int[]{1, 3, 3, 3};
        for (int i = 1; i <= 3; i++) {
            int[] cloned = nums.clone();
            LoopInvariants.partition3Way(cloned, i);      // tests with pivots 1, 2, 3
            assertArrayEquals(correct, cloned);
        }
    }

    boolean isPartitioned(int[] a, int pivot) {
        /*
            a slightly different loop invariant is in use here:
            the partition invariant holds for nums[0..k) for some index k

            this is useful bc we can only return true if k = a.length
        */

        int i = 0; // stops at the last index of the range < pivot
        int j = 0; // stops at the last index of the range == pivot
        int k = 0; // scans over entire array
        while (k < a.length) {
            if (a[k] < pivot && i == k) {
                i++;
                j++;
            } else if (a[k] == pivot && j == k) {
                j++;
            } else if (a[k] <= pivot) {
                return false;          // partition invariant breaks
            }
            k++;
        }
        return true;
    }

    @DisplayName("WHEN partitioning an array of elements, THEN the result is partitioned by the pivot")
    @Test
    void testPartitionBasic() {
        int[] nums = {3, 2, 4, 1, 6, 8, 3};

        for (int i = 0; i < 10; i++) {
            int[] cloned = nums.clone();
            LoopInvariants.partition3Way(cloned, i);
            assertTrue(isPartitioned(cloned, i));
        }
    }

    @DisplayName("WHEN partitioning a large array of elements, THEN the result is partitioned by the pivot")
    @Test
    void testPartitionLarge() {
        int[] nums = new int[500];
        for (int i = 0; i < 500; i++) {
            nums[i] = i;
        }

        // Fisher-Yates shuffle
        Random rand = new Random(2110);
        for (int i = nums.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }

        for (int i = 0; i < 500; i++) {
            int[] cloned = nums.clone();
            LoopInvariants.partition3Way(cloned, i);
            assertTrue(isPartitioned(cloned, i));
        }
    }

}
