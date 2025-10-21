package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("WHEN given its pre-order and in-order traversals, we can successfully re-construct a "
        + "tree...")
public class TreeReconstructionTest {

    /**
     * Returns an array of the elements in the given `tree` in pre-order traversal order
     */
    private int[] preorderArray(ImmutableBinaryTree<Integer> tree) {
        int[] array = new int[tree.size()];
        Iterator<Integer> it = tree.preorderIterator();
        int i = 0;
        while (it.hasNext()) {
            array[i] = it.next();
            i++;
        }
        return array;
    }

    /**
     * Returns an array of the elements in the given `tree` in in-order traversal order
     */
    private int[] inorderArray(ImmutableBinaryTree<Integer> tree) {
        int[] array = new int[tree.size()];
        Iterator<Integer> it = tree.inorderIterator();
        int i = 0;
        while (it.hasNext()) {
            array[i] = it.next();
            i++;
        }
        return array;
    }

    /**
     * Reconstructs tree and performs both traversals to make sure they are correct.
     */
    private void assertCorrectReconstruction(int[] preorder, int[] inorder) {
        ImmutableBinaryTree<Integer> tree = TreeReconstruction.reconstructTree(preorder, inorder);
        assertEquals(preorder.length, tree.size());
        assertArrayEquals(preorder, preorderArray(tree));
        assertArrayEquals(inorder, inorderArray(tree));
    }

    @DisplayName("That contains one element.")
    @Test
    void testOneElement() {
        int[] preorder = {1};
        int[] inorder = {1};
        assertCorrectReconstruction(preorder, inorder);
    }

    @DisplayName("That contains two elements with a non-empty left subtree.")
    @Test
    void testTwoElementsLeft() {
        int[] preorder = {1, 2};
        int[] inorder = {2, 1};
        assertCorrectReconstruction(preorder, inorder);
    }

    @DisplayName("That contains two elements with a non-empty right subtree.")
    @Test
    void testTwoElementsRight() {
        int[] preorder = {1, 2};
        int[] inorder = {1, 2};
        assertCorrectReconstruction(preorder, inorder);
    }

    @DisplayName("That contains three elements and has height 1.")
    @Test
    void testThreeElementsBothSubtrees() {
        int[] preorder = {1, 2, 3};
        int[] inorder = {2, 1, 3};
        assertCorrectReconstruction(preorder, inorder);
    }

    @DisplayName("That contains multiple nodes (Example 1)")
    @Test
    void testExampleTree1() {
        int[] preorder = {1, 2, 4, 5, 6, 7, 3};
        int[] inorder = {4, 2, 6, 5, 7, 1, 3};
        assertCorrectReconstruction(preorder, inorder);
    }

    @DisplayName("That contains multiple nodes (Example 2)")
    @Test
    void testExampleTree2() {
        int[] preorder = {6, 5, 4, 7, 1, 2, 3};
        int[] inorder = {4, 5, 7, 6, 2, 1, 3};
        assertCorrectReconstruction(preorder, inorder);
    }

    @DisplayName("That contains multiple nodes (Example 3)")
    @Test
    void testExampleTree3() {
        int[] preorder = {2, 1, 7, 4, 3, 5, 8, 9, 6};
        int[] inorder = {7, 1, 3, 4, 8, 5, 9, 2, 6};
        assertCorrectReconstruction(preorder, inorder);
    }

    @DisplayName("That contains multiple nodes (Example 4)")
    @Test
    void testExampleTree4() {
        int[] preorder = {1, 2, 4, 5, 3, 6};
        int[] inorder = {4, 2, 5, 1, 6, 3};
        assertCorrectReconstruction(preorder, inorder);
    }
}
