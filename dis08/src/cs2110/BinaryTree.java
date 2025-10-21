package cs2110;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A binary tree that stores non-null elements of type T with a provided String representation and
 * three iterators (pre-order, in-order, and post-order). The in-order iterator is the default
 * returned by `iterator()`.
 */
public abstract class BinaryTree<T> implements Iterable<T> {

    /**
     * The element at the root of this tree.
     */
    protected T root;

    /*
     * Note: We do not introduce fields for the left and right subtree (such as "BinaryTree<T> left"
     * and "BinaryTree<T> right") because subclasses may want the types of these fields to be more
     * specific than BinaryTree (so they can recursively leverage their own bespoke methods). We'll
     * see this in action when we define binary search trees. Since the fields will be declared in
     * the subclasses, we include abstract methods to retrieve the left and right subtrees so they
     * can be used in this class.
     */

    /**
     * Returns the left subtree of this tree or null if this tree does not have a left child.
     */
    protected abstract BinaryTree<T> left();

    /**
     * Returns the right subtree of this tree or null if this tree does not have a right child.
     */
    protected abstract BinaryTree<T> right();

    /**
     * Returns the height (i.e., maximum length of a path from root to leaf) of this binary tree.
     */
    public int height() {
        return 1 + Math.max((left() == null ? -1 : left().height()),
                (right() == null ? -1 : right().height()));
    }

    /**
     * Returns the number of elements stored in this tree.
     */
    public int size() {
        return 1 + (left() == null ? 0 : left().size()) + (right() == null ? 0 : right().size());
    }

    /**
     * Produces a multi-line String visualizing the structure of this binary tree.
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (String line : toStringRecursive(this, height())) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * A recursive helper method that returns an array of Strings representing each line in the
     * String representation of `tree`, possibly padded with extra lines of spaces so the array has
     * length `2*height - 1` (the height of the representation of a tree with the given `height`).
     * This method works by laying out its subtree's representations with adequate spacing to
     * prevent overlapping and using unicode characters to draw the connections from these subtrees
     * to the root.
     */
    private static <T> String[] toStringRecursive(BinaryTree<T> tree, int height) {
        String root = (tree.root == null) ? " " : tree.root.toString();

        if (height == 0) {
            return new String[]{root};
        }

        int rows = 2 * height + 1;
        StringBuilder[] sb = new StringBuilder[rows]; // we'll use StringBuilder to make repeated concatenations more performant
        for (int i = 0; i < rows; i++) {
            sb[i] = new StringBuilder(); // all rows start as empty strings
        }

        if (tree.left() == null) { // no left subtree
            sb[0].append(root); // root is leftmost node, no need for left padding
            sb[1].append(" ".repeat(root.length() / 2));
            if (tree.right() != null) {
                sb[1].append("└"); // branch up to root, right only
            }
            for (int i = 2; i < rows; i++) {
                sb[i].append(" ".repeat(root.length() / 2 + 1)); // left padding for right subtree
            }
        } else { // left subtree
            String[] left = toStringRecursive(tree.left(), height - 1);
            // length of bar between root and left subtree root
            int bar = Math.max(root.length() / 2, left[0].length() - midpoint(left[0]) - 1);
            sb[1].append(" ".repeat(midpoint(left[0]))); // padding for left half of left subtree
            sb[1].append("┌"); // branch down to left subtree
            sb[1].append("─".repeat(bar)); // horizontal bar
            if (tree.right() == null) {
                sb[1].append("┘"); // branch up to root, left only
            } else {
                sb[1].append("┴"); // branch up to root, both subtrees
            }
            sb[0].append(" ".repeat(sb[1].length() - (root.length() + 1) / 2)); // left padding
            sb[0].append(root);
            for (int i = 2; i < rows; i++) {
                sb[i].append(left[i - 2]); // left subtree's rows
            }
        }

        if (tree.right() == null) { // no right subtree
            for (int i = 1; i < rows; i++) {
                sb[i].append(" ".repeat(sb[0].length() - sb[i].length())); // right padding
            }
        } else { // right subtree
            String[] right = toStringRecursive(tree.right(), height - 1);
            // length of bar between root and left subtree root
            int bar = Math.max(root.length() / 2, midpoint(right[0]));
            sb[1].append("─".repeat(bar)); // horizontal bar
            sb[1].append("┐"); // branch down to right subtree
            sb[1].append(" ".repeat(right[0].length() - midpoint(right[0]) - 1)); // right padding
            sb[0].append(" ".repeat(sb[1].length() - sb[0].length())); // right padding
            for (int i = 2; i < rows; i++) {
                int padding = sb[1].length() - sb[i].length() - right[i - 2].length();
                sb[i].append(" ".repeat(padding)); // middle padding between subtrees
                sb[i].append(right[i - 2]); // right subtree's rows
            }
        }

        // convert StringBuilder[] array to String[] array
        String[] lines = new String[rows];
        for (int i = 0; i < rows; i++) {
            lines[i] = sb[i].toString();
        }
        return lines;
    }

    /**
     * Returns the index of the midpoint of the String excluding its leading and trailing spaces.
     * Used to determine where to position the parent connection for the subtree root.
     */
    private static int midpoint(String s) {
        if (s.trim().isEmpty()) {
            return 0;
        }
        int i = 0;
        /* Loop invariant: s[..i) = ' ' */
        while (i < s.length() && s.charAt(i) == ' ') {
            i++;
        } // when we exit, `i` is the index of the first non-space character
        return i + (s.trim().length() - 1) / 2;
    }

    /**
     * Return a pre-order iterator over the elements in this binary tree.
     */
    public Iterator<T> preorderIterator() {
        return new PreorderIterator<>(this);
    }

    /**
     * Return an in-order iterator over the elements in this binary tree.
     */
    public Iterator<T> inorderIterator() {
        return new InorderIterator<>(this);
    }

    /**
     * Return a post-order iterator over the elements in this binary tree.
     */
    public Iterator<T> postorderIterator() {
        return new PostorderIterator<>(this);
    }

    /**
     * Returns an iterator over the elements in this binary tree. The default implementation is an
     * in-order iterator.
     */
    public Iterator<T> iterator() {
        return inorderIterator();
    }

    /**
     * A pre-order iterator over the elements in this binary tree.
     */
    private static class PreorderIterator<T> implements Iterator<T> {

        /**
         * Keeps track of state of the tree traversal. While `stack` is non-empty, the root of its
         * top element the `next()` element to be returned.
         */
        private final Deque<BinaryTree<T>> stack;

        /**
         * Constructs a new pre-order iterator over the given `tree`.
         */
        public PreorderIterator(BinaryTree<T> tree) {
            stack = new LinkedList<>();
            if (tree != null && tree.root != null) {
                stack.push(tree);
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            assert hasNext();
            BinaryTree<T> visiting = stack.pop();
            if (visiting.right() != null) {
                stack.push(visiting.right()); // push right first so stack visits it second
            }
            if (visiting.left() != null) {
                stack.push(visiting.left());
            }
            return visiting.root;
        }
    }

    /**
     * An in-order iterator over the elements in this binary tree.
     */
    private static class InorderIterator<T> implements Iterator<T> {

        /**
         * Keeps track of state of the tree traversal. While `stack` is non-empty, the root of its
         * top element the `next()` element to be returned.
         */
        private final Deque<BinaryTree<T>> stack;

        /**
         * Constructs a new in-order iterator over the given `tree`.
         */
        public InorderIterator(BinaryTree<T> tree) {
            stack = new LinkedList<>();
            cascadeLeft(tree);
        }

        /**
         * Walks down the left children of `tree` until a leaf node is reached, pushing all
         * subtrees onto the stack in the process.
         */
        private void cascadeLeft(BinaryTree<T> tree) {
            if (tree != null && tree.root != null) {
                stack.push(tree);
                cascadeLeft(tree.left());
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            assert hasNext();
            BinaryTree<T> visiting = stack.pop();
            if (visiting.right() != null) {
                cascadeLeft(visiting.right());
            }
            return visiting.root;
        }
    }

    /**
     * A post-order iterator over the elements in this binary tree.
     */
    private static class PostorderIterator<T> implements Iterator<T> {

        /**
         * Keeps track of state of the tree traversal. While `stack` is non-empty, the root of its
         * top element the `next()` element to be returned.
         */
        private final Deque<BinaryTree<T>> stack;

        /**
         * Constructs a new post-order iterator over the given `tree`.
         */
        public PostorderIterator(BinaryTree<T> tree) {
            stack = new LinkedList<>();
            if (tree != null && tree.root != null) {
                cascade(tree);
            }
        }

        /**
         * Walks down the left children of `tree` until a leaf node is reached, pushing all
         * subtrees onto the stack in the process. If any node has a right child but no left child,
         * the cascade proceeds to the right.
         */
        private void cascade(BinaryTree<T> tree) {
            if (tree != null && tree.root != null) {
                stack.push(tree);
                cascade(tree.left() == null ? tree.right() : tree.left());
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            assert hasNext();
            BinaryTree<T> visiting = stack.pop();
            if (!stack.isEmpty() && stack.peek().left() == visiting) {
                // we were exploring left subtree of `visiting`'s parent `stack.peek()`
                // must explore the parent's right subtree returning it
                if (stack.peek().right() != null) {
                    cascade(stack.peek().right());
                }
            }
            return visiting.root;
        }
    }
}
