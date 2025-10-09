package cs2110;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class Copy2DRangeTest {

    /**
     * Helper method to create a deep copy of a 2D array.
     */
    private int[][] deepClone(int[][] original) {
        if (original == null) {
            return null;
        }
        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    //================================================================================
    // Tests for Successful Copies (should return true)
    //================================================================================

    @DisplayName("[Success] WHEN parameters are valid, THEN sub-array is transferred successfully.")
    @Test
    void testBasicTransfer() {
        // Arrange
        int[][] src = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] dst = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        int[][] originalSrc = deepClone(src);
        int[][] expectedDst = {{0, 5, 6, 0}, {0, 8, 9, 0}, {0, 0, 0, 0}};

        // Act
        boolean result = ArrayUtilities.copy2DRange(src, 1, 1, dst, 0, 1, 2, 2);

        // Assert
        assertTrue(result, "A valid copy should return true.");
        assertArrayEquals(originalSrc, src, "Source array should not be modified.");
        assertArrayEquals(expectedDst, dst, "Destination array was not modified correctly.");
    }

    @DisplayName("[Success] WHEN copying an entire array, THEN copy is successful.")
    @Test
    void testEntireArrayTransfer() {
        // Arrange
        int[][] src = {{1, 2}, {3, 4}};
        int[][] dst = {{0, 0}, {0, 0}};
        int[][] originalSrc = deepClone(src);
        int[][] expectedDst = {{1, 2}, {3, 4}};

        // Act
        boolean result = ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, 2, 2);

        // Assert
        assertTrue(result, "Copying an entire array should return true.");
        assertArrayEquals(originalSrc, src, "Source array should not be modified.");
        assertArrayEquals(expectedDst, dst, "Destination should match the source exactly.");
    }

    @DisplayName("[Success] WHEN height is 0, THEN return true and dst is unchanged.")
    @Test
    void testZeroHeight() {
        // Arrange
        int[][] src = {{1, 2}, {3, 4}};
        int[][] dst = {{0, 0}, {0, 0}};
        int[][] originalDst = deepClone(dst);

        // Act
        boolean result = ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, 0, 2);

        // Assert
        assertTrue(result, "A zero-height copy should return true.");
        assertArrayEquals(originalDst, dst, "Destination array should be unchanged.");
    }

    @DisplayName("[Success] WHEN width is 0, THEN return true and dst is unchanged.")
    @Test
    void testZeroWidth() {
        // Arrange
        int[][] src = {{1, 2}, {3, 4}};
        int[][] dst = {{0, 0}, {0, 0}};
        int[][] originalDst = deepClone(dst);

        // Act
        boolean result = ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, 2, 0);

        // Assert
        assertTrue(result, "A zero-width copy should return true.");
        assertArrayEquals(originalDst, dst, "Destination array should be unchanged.");
    }

    @DisplayName("[Success] WHEN width is 0 but indices are invalid, THEN return true.")
    @Test
    void testZeroWidthInvalidIndices() {
        // Arrange
        int[][] src = {{1}};
        int[][] dst = {{0}};
        int[][] originalDst = deepClone(dst);

        // Act
        boolean result = ArrayUtilities.copy2DRange(src, -5, -5, dst, -5, -5, 2, 0);

        // Assert
        assertTrue(result, "Zero-width copy should succeed even with invalid indices.");
        assertArrayEquals(originalDst, dst, "Destination should not change.");
    }

    @DisplayName("[Success] WHEN copying from a valid region in a jagged array, THEN copy is successful.")
    @Test
    void testJaggedArraySuccess() {
        // Arrange
        int[][] jaggedSrc = {{1, 2}, {3, 4, 5, 6}, {7, 8, 9}};
        int[][] jaggedDst = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        int[][] originalSrc = deepClone(jaggedSrc); // Assuming you have a deepClone helper

        // FIX: The expected destination must match the structure of the actual destination.
        // The copy operation only changes values, not the length of the inner arrays.
        int[][] expectedDst = {
                {4, 5, 0},  // The first row is updated, but its length remains 3.
                {8, 9, 0},  // The second row is updated, its length also remains 3.
                {0, 0, 0}   // The third row is untouched.
        };

        // Act
        boolean result = ArrayUtilities.copy2DRange(jaggedSrc, 1, 1, jaggedDst, 0, 0, 2, 2);

        // Assert
        assertTrue(result, "A valid copy from a jagged array should succeed.");
        assertArrayEquals(originalSrc, jaggedSrc, "Source jagged array should not be modified.");
        assertArrayEquals(expectedDst, jaggedDst, "Destination jagged array was not modified correctly.");
    }

    // Helper method to create a deep copy of a 2D array, needed for robust testing.
//    private int[][] deepClone(int[][] original) {
//        if (original == null) {
//            return null;
//        }
//        final int[][] result = new int[original.length][];
//        for (int i = 0; i < original.length; i++) {
//            if (original[i] != null) {
//                result[i] = original[i].clone();
//            }
//        }
//        return result;
//    }

    //================================================================================
    // Tests for Overlapping Copies (should return true)
    //================================================================================

    @DisplayName("[Overlap] WHEN regions overlap vertically (downward shift), THEN copy is successful.")
    @Test
    void testSameArrayOverlapVertical() {
        // Arrange
        int[][] sameArray = {{1, 2}, {3, 4}, {0, 0}, {0, 0}};
        int[][] expected = {{1, 2}, {1, 2}, {3, 4}, {0, 0}};

        // Act - Copy the top 2x2 block to a position one row down.
        boolean result = ArrayUtilities.copy2DRange(sameArray, 0, 0, sameArray, 1, 0, 2, 2);

        // Assert
        assertTrue(result, "Downward overlapping copy should succeed.");
        assertArrayEquals(expected, sameArray, "Array was not modified correctly.");
    }

    @DisplayName("[Overlap] WHEN regions overlap horizontally (rightward shift), THEN copy is successful.")
    @Test
    void testSameArrayOverlapHorizontal() {
        // Arrange
        int[][] sameArray = {{1, 2, 0, 0}, {3, 4, 0, 0}};
        int[][] expected = {{1, 1, 2, 0}, {3, 3, 4, 0}};

        // Act - Copy the left 2x2 block to a position one column right.
        boolean result = ArrayUtilities.copy2DRange(sameArray, 0, 0, sameArray, 0, 1, 2, 2);

        // Assert
        assertTrue(result, "Rightward overlapping copy should succeed.");
        assertArrayEquals(expected, sameArray, "Array was not modified correctly.");
    }

    //================================================================================
    // Tests for Failed Copies (should return false)
    //================================================================================

    @DisplayName("[Failure] WHEN height is negative, THEN return false and dst is unchanged.")
    @Test
    void testNegativeHeight() {
        // Arrange
        int[][] src = {{1}};
        int[][] dst = {{0}};
        int[][] originalDst = deepClone(dst);

        // Act
        boolean result = ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, -1, 1);

        // Assert
        assertFalse(result, "Negative height should return false.");
        assertArrayEquals(originalDst, dst, "Destination should not change on failure.");
    }

    @DisplayName("[Failure] WHEN width is negative, THEN return false and dst is unchanged.")
    @Test
    void testNegativeWidth() {
        // Arrange
        int[][] src = {{1}};
        int[][] dst = {{0}};
        int[][] originalDst = deepClone(dst);

        // Act
        boolean result = ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, 1, -1);

        // Assert
        assertFalse(result, "Negative width should return false.");
        assertArrayEquals(originalDst, dst, "Destination should not change on failure.");
    }

    @DisplayName("[Failure] WHEN srcI is negative, THEN return false.")
    @Test
    void testNegativeSrcI() {
        // Arrange
        int[][] src = {{1}};
        int[][] dst = {{0}};
        int[][] originalDst = deepClone(dst);

        // Act
        boolean result = ArrayUtilities.copy2DRange(src, -1, 0, dst, 0, 0, 1, 1);

        // Assert
        assertFalse(result, "Negative srcI should return false.");
        assertArrayEquals(originalDst, dst, "Destination should not change on failure.");
    }

    @DisplayName("[Failure] WHEN copy region exceeds src's row bounds, THEN return false.")
    @Test
    void testOutOfBoundsSrcRows() {
        // Arrange
        int[][] src = {{1, 2}, {3, 4}};
        int[][] dst = new int[3][2];
        int[][] originalDst = deepClone(dst);

        // Act - Tries to copy 2 rows from index 1 (needs indices 1, 2), but src only has 0, 1.
        boolean result = ArrayUtilities.copy2DRange(src, 1, 0, dst, 0, 0, 2, 2);

        // Assert
        assertFalse(result, "Exceeding source row bounds should return false.");
        assertArrayEquals(originalDst, dst, "Destination should not change on failure.");
    }

    @DisplayName("[Failure] WHEN copy region exceeds dst's row bounds, THEN return false.")
    @Test
    void testOutOfBoundsDstRows() {
        // Arrange
        int[][] src = new int[3][2];
        int[][] dst = {{1, 2}, {3, 4}};
        int[][] originalDst = deepClone(dst);

        // Act - Tries to copy to 2 rows from index 1 (needs indices 1, 2), but dst only has 0, 1.
        boolean result = ArrayUtilities.copy2DRange(src, 0, 0, dst, 1, 0, 2, 2);

        // Assert
        assertFalse(result, "Exceeding destination row bounds should return false.");
        assertArrayEquals(originalDst, dst, "Destination should not change on failure.");
    }

    @DisplayName("[Failure] WHEN copy region exceeds a src row's column bounds, THEN return false.")
    @Test
    void testOutOfBoundsSrcColsJagged() {
        // Arrange
        int[][] jaggedSrc = {{1, 2, 3}, {4, 5}}; // Second row is too short
        int[][] dst = new int[2][2];
        int[][] originalDst = deepClone(dst);

        // Act - Tries to copy 2 columns from index 1. This is fine for row 0, but fails on row 1.
        boolean result = ArrayUtilities.copy2DRange(jaggedSrc, 0, 1, dst, 0, 0, 2, 2);

        // Assert
        assertFalse(result, "Exceeding source column bounds on a jagged array should return false.");
        assertArrayEquals(originalDst, dst, "Destination should not change on failure.");
    }

    @DisplayName("[Failure] WHEN copy region exceeds a dst row's column bounds, THEN return false.")
    @Test
    void testOutOfBoundsDstColsJagged() {
        // Arrange
        int[][] src = new int[2][2];
        int[][] jaggedDst = {{0, 0, 0}, {0, 0}}; // Second row is too short
        int[][] originalDst = deepClone(jaggedDst);

        // Act - Tries to copy to 2 columns starting at index 1. Fails on second row.
        boolean result = ArrayUtilities.copy2DRange(src, 0, 0, jaggedDst, 0, 1, 2, 2);

        // Assert
        assertFalse(result, "Exceeding destination column bounds on a jagged array should return false.");
        assertArrayEquals(originalDst, jaggedDst, "Destination should not change on failure.");
    }
}

