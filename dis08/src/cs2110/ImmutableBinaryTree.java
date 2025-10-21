package cs2110;

/**
 * An immutable implementation of a binary tree.
 */
public class ImmutableBinaryTree<T> extends BinaryTree<T> {

    /**
     * The left subtree of this tree.
     */
    private final ImmutableBinaryTree<T> left;

    /**
     * The right subtree of this tree.
     */
    private final ImmutableBinaryTree<T> right;

    /**
     * Constructs a binary tree with the given `root` value and `left` and `right` subtrees.
     */
    public ImmutableBinaryTree(T root, ImmutableBinaryTree<T> left, ImmutableBinaryTree<T> right) {
        this.root = root;
        this.left = left;
        this.right = right;
    }

    @Override
    protected BinaryTree<T> left() {
        return left;
    }

    @Override
    protected BinaryTree<T> right() {
        return right;
    }
}
