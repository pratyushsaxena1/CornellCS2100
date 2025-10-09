package cs2110;

/**
 * Contains utility methods for copying an array range to another array range (potentially within
 * the same array).
 */
public class ArrayUtilities {

    /**
     * Copies the elements of array 'src' from index 'srcStart' to 'srcStart' + 'length' - 1 and
     * writes them to array 'dst' from index 'dstStart' to 'dstStart' + 'length' - 1. Returns true
     * if the modification is successfully made. Otherwise, returns false if any inputs are invalid
     * and the modification isn't successfully made. Inputs are invalid if (1) there is a negative
     * value for 'length', 'srcStart', and/or 'dstStart', or (2) the 'length', 'srcStart', and/or
     * 'dstStart' results in an ArrayIndexOutOfBoundsException when performing the range copy. The
     * latter case may happen if (1) 'srcStart' plus 'length' are outside the bounds of 'src', (2)
     * if 'dstStart' plus 'length' are outside the bounds of 'dst', or (3) if 'length' is greater
     * than 0 and 'src' and/or 'dst' are/is empty. Ensure that no exceptions or AssertionErrors
     * propagate out of the method. Requires that 'src' and 'dst' are not null.
     */
    static boolean copyRange(int[] src, int srcStart, int[] dst, int dstStart, int length) {
        // Check for invalid inputs and return false if any of them are invalid
        if (length < 0 ||
                (srcStart < 0 && length > 0) ||
                (srcStart > src.length && length > 0) ||
                (dstStart < 0 && length > 0) ||
                (dstStart > dst.length && length > 0) ||
                (srcStart + length > src.length && length > 0) ||
                (dstStart + length > dst.length && length > 0)) {
            return false;
        }
        // If the 'src' and 'dst' arrays are the same array and the ranges being copied from and written
        // to overlap, then go backwards while copying to avoid overwriting the array incorrectly
        if (src == dst && srcStart < dstStart && srcStart + length > dstStart) {
            /*
            Loop invariant: dst[dstStart+i+1 .. dstStart+length-1] contains the elements from
            src[srcStart+i+1 .. srcStart+length-1]
             */
            for (int i = length - 1; i >= 0; i--) {
                dst[dstStart + i] = src[srcStart + i];
            }
        } else {
            /*
            Loop invariant: dst[dstStart .. dstStart+i-1] contains the elements from
            src[srcStart .. srcStart+i-1]
             */
            for (int i = 0; i < length; i++) {
                dst[dstStart + i] = src[srcStart + i];
            }
        }
        return true;
    }

    /**
     * Copies the elements of array 'src' from the rectangular region defined by the top-left corner
     * ('srcI', 'srcJ') with height 'height' and width 'width', and writes them to array 'dst'
     * starting at the top-left corner ('dstI', 'dstJ'). Returns true if the modification is
     * successfully made. Otherwise, returns false if any inputs are invalid and the modification is
     * not successfully made. Inputs are invalid if (1) there is a negative value for 'height',
     * 'width', 'srcI', 'srcJ', 'dstI', and/or 'dstJ', or (2) the values of 'height', 'width',
     * 'srcI', 'srcJ', 'dstI', and/or 'dstJ' result in an ArrayIndexOutOfBoundsException when
     * performing the range copy. The latter case may happen if (1) 'srcI + height' is outside the
     * bounds of 'src', (2) 'dstI + height' is outside the bounds of 'dst', (3) 'srcJ + width' is
     * outside the bounds of a row in 'src', (4) 'dstJ + width' is outside the bounds of a row in
     * 'dst', or (5) 'height' and 'width' are greater than 0 and 'src' and/or 'dst' are/is empty.
     * Ensure that no exceptions or AssertionErrors propagate out of the method. Requires that 'src'
     * and 'dst' are not null and that the arrays within them are not null. Requires that a 2D array
     * cannot have multiple nested arrays pointing to the same place in memory.
     */
    static boolean copy2DRange(int[][] src, int srcI, int srcJ, int[][] dst, int dstI, int dstJ,
            int height, int width) {
        // If the height or width are 0, then it's automatically true regardless of the content of
        // the arrays because nothing is being copied
        if (height == 0 || width == 0) {
            return true;
        }
        // Check for invalid inputs and return false if any of them are
        if (height < 0 || width < 0 ||
                srcI < 0 || srcJ < 0 || dstI < 0 || dstJ < 0 ||
                srcI + height > src.length || dstI + height > dst.length) {
            return false;
        }
        /*
        Loop invariant: loop through rows src[srcI .. srcI+i-1] and dst[dstI .. dstI+i-1] to ensure
        they are long enough to hold 'width' columns starting at srcJ and dstJ.
         */
        for (int i = 0; i < height; i++) {
            if (srcJ + width > src[srcI + i].length || dstJ + width > dst[dstI + i].length) {
                return false;
            }
        }
        // If the 'src' and 'dst' 2D arrays are the same and the ranges overlap,
        // copy it backwards to avoid overwriting the array incorrectly
        if (src == dst &&
                srcI < dstI + height && dstI < srcI + height &&
                srcJ < dstJ + width && dstJ < srcJ + width) {
        /*
         Loop invariant: loop through every row in the 2D array in reverse order.
         */
            for (int i = height - 1; i >= 0; i--) {
            /*
             Loop invariant: dst[dstI+i][dstJ+j+1 .. dstJ+width-1] contains the elements from
             src[srcI+i][srcJ+j+1 .. srcJ+width-1].
             */
                for (int j = width - 1; j >= 0; j--) {
                    dst[dstI + i][dstJ + j] = src[srcI + i][srcJ + j];
                }
            }
        } else {
        /*
         Loop invariant: loop through every row of the 2D arrays.
         */
            for (int i = 0; i < height; i++) {
            /*
             Loop invariant: dst[dstI+i][dstJ .. dstJ+j-1] contains the elements from
             src[srcI+i][srcJ .. srcJ+j-1].
             */
                for (int j = 0; j < width; j++) {
                    dst[dstI + i][dstJ + j] = src[srcI + i][srcJ + j];
                }
            }
        }
        return true;
    }
}