package cs2110;

import java.util.Arrays;

public class LoopInvariants {

    /**
     * Returns the second-largest element of the given `nums` array. Requires that `nums.length >=
     * 2` and `nums` contains distinct elements.
     */
    static int secondLargest(int[] nums) {
        assert nums.length >= 2; // defensive programming
        int largest = Math.max(nums[0], nums[1]);
        int secondLargest = Math.min(nums[0], nums[1]);
        int i = 2;
        while (i < nums.length) {
            if (nums[i] > largest) {
                secondLargest = largest;
                largest = nums[i];
            }
            else if (nums[i] > secondLargest) {
                secondLargest = nums[i];
            }
            i++;
        }
        return secondLargest;
    }

    /**
     * Returns whether the entries of `nums` form a *mountain*, meaning there is some index `i` such
     * that `nums[..i]` is non-decreasing and `nums[i..]` is non-increasing. Requires that
     * nums.length > 0`.
     */
    static boolean isMountain(int[] nums) {
        assert nums.length > 0; // defensive programming
        if (nums.length == 1) {
            return true;
        }
        int currPeak = nums[0];
        int currValue = nums[0];
        int i = 1;
        while (currValue >= currPeak) {
            currPeak = currValue;
            currValue = nums[i];
            i++;
        }
        for (int x = i; x < nums.length; x++)  {
            currValue = nums[x];
            if (currValue > currPeak) {
                return false;
            }
        }
        return true;
    }

    /**
     * Swaps the entries `nums[x]` and `nums[y]`. Requires that `0 <= x < nums.length` and `0 <= y <
     * nums.length`.
     */
    static void swap(int[] nums, int x, int y) {
        int temp = nums[x];
        nums[x] = nums[y];
        nums[y] = temp;
    }

    /**
     * Rearranges the elements of `nums` about the given `pivot` so that `nums[..i) < pivot`,
     * `nums[i..j) == pivot` and `nums[j..] > pivot` for some indices `i,j`. Requires that
     * `nums.length > 0`.
     */
    static void partition3Way(int[] nums, int pivot) {
        assert nums.length > 0; // defensive programming

        // TODO 3: Use the loop invariant that you developed on the Discussion 3 handout to
        //  implement this method in a single array pass.
        throw new UnsupportedOperationException();
    }
}
