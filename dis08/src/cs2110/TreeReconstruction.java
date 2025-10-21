package cs2110;

public class TreeReconstruction {

    /**
     * Reconstructs a binary tree from its pre-order and in-order traversals.
     * Requires that `preorder` and `inorder` are valid pre-order and in-order traversals
     * (respectively) of the same non-empty binary tree.
     */
    public static ImmutableBinaryTree<Integer> reconstructTree(int[] preorder, int[] inorder) {
        return build(preorder, inorder, 0, preorder.length, 0, inorder.length);
    }

    /**
     * Constructs the subtree described by pre-order traversal segment `preorder[preStart,preEnd)`
     * and in-order traversal segment `inorder[inStart,inEnd)` and returns a reference to its root.
     */
    private static ImmutableBinaryTree<Integer> build(int[] preorder, int[] inorder, int preStart,
        int preEnd, int inStart, int inEnd) {

        if (preStart == preEnd || inStart == inEnd) {
            return null;
        }

        int root = preorder[preStart];

        int inRoot = -1;

        for (int i = inStart; i < inEnd; i++) {
            if (inorder[i] == root) {
                inRoot = i;
                break;
            }
        }

        int sizeOfLeftSubtree = inRoot - inStart;

        ImmutableBinaryTree left = build(preorder, inorder, preStart + 1, preStart + 1 + sizeOfLeftSubtree, inStart, inRoot);
        ImmutableBinaryTree right =  build(preorder, inorder, preStart + 1 + sizeOfLeftSubtree, preEnd, inRoot + 1, inEnd);

        return new ImmutableBinaryTree<>(root, left, right);
    }
}
