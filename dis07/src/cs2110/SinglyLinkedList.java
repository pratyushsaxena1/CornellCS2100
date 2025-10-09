package cs2110;

/**
 * An implementation of the CS2110List ADT using a singly-linked list.
 */
public class SinglyLinkedList<T> implements CS2110List<T> {

    /**
     * The first node in this list.
     */
    protected Node<T> head;

    /**
     * The final (empty) node in this list.
     */
    private Node<T> tail;

    /**
     * The current size of this list, equal to the number of `Node`s in the chain starting at (and
     * including) `head` and ending at (and excluding) `tail`.
     */
    private int size;

    /**
     * Asserts the SinglyLinkedList class invariant.
     */
    private void assertInv() {
        Node<T> current = head;
        int currentSize = 0;
        while (current != tail && currentSize <= size) {
            assert current.data != null;
            assert current.next != null;
            currentSize++;
            current = current.next;
        }
        assert currentSize == size; // size is correct, no circular linking
        // when the above assertion succeeds, we know that current == tail
        assert current.data == null;
        assert current.next == null;
    }

    /**
     * Constructs a new, initially empty, SinglyLinkedList.
     */
    public SinglyLinkedList() {
        size = 0;
        head = new Node<>(null, null);
        tail = head;
        assertInv();
    }

    /**
     * Adds the given `elem` to the given `node` in this list, moving the `node`'s current contents
     * to a new subsequent node to make space. Requires that `elem != null`.
     */
    private void spliceIn(Node<T> node, T elem) {
        assert elem != null; // defensive programming

        node.next = new Node<>(node.data, node.next);
        node.data = elem;
        if (node == tail) {
            tail = node.next;
        }
        size++;
        assertInv();
    }

    @Override
    public void add(T elem) {
        spliceIn(tail, elem);
    }

    /**
     * Returns a reference to the node at the given `index` (counting from 0) in this linked list.
     * Requires that `0 <= index <= size`.
     */
    private Node<T> nodeAtIndex(int index) {
        assert 0 <= index;
        assert index <= size;

        int i = 0;
        Node<T> current = head;
        /* Loop invariant: `current` is the Node at index `i` in this list. */
        while (i < index) {
            current = current.next;
            i++;
        }
        return current;
    }

    @Override
    public void insert(int index, T elem) {
        spliceIn(nodeAtIndex(index), elem);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        assert index < size;
        return nodeAtIndex(index).data;
    }

    @Override
    public boolean contains(T elem) {
        Node<T> current = head;
        /* Loop invariant: None of the Nodes before `current` contain `elem`. */
        while (current != tail) {
            if (current.data.equals(elem)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int indexOf(T elem) {
        int i = 0;
        Node<T> current = head;
        /* Loop invariant: `current` is the Node at index `i` in this list. None of the Nodes before `current` contain `elem`. */
        while (!current.data.equals(elem)) {
            current = current.next;
            i++;
        }
        return i;
    }

    @Override
    public void set(int index, T elem) {
        assert elem != null; // defensive programming
        assert index < size;

        Node<T> node = nodeAtIndex(index);
        node.data = elem;
        assertInv();
    }

    /**
     * Removes the given `node` from this linked list and returns its `data` field.
     */
    private T spliceOut(Node<T> node) {
        T removed = node.data;
        node.data = node.next.data;
        node.next = node.next.next;
        size--;
        if (node.next == null) {
            tail = node;
        }
        assertInv();
        return removed;
    }

    @Override
    public T remove(int index) {
        assert index < size;
        return spliceOut(nodeAtIndex(index));
    }

    @Override
    public void delete(T elem) {
        Node<T> current = head;
        /* Loop invariant: None of the Nodes before `current` contain `elem`. */
        while (!current.data.equals(elem)) {
            current = current.next;
        }
        spliceOut(current);
    }

    public String toString() {
        return head.toString();
    }

    /**
     * A node in this singly-linked list.
     */
    protected static class Node<T> {

        /**
         * The element stored in this node. `null` is used to indicate the node at the end of the
         * list.
         */
        T data;

        /**
         * A reference to the next node in the list. May only be null if `data == null`.
         */
        Node<T> next;

        /**
         * Constructs a new Node object with the given `data` and `next` reference.
         */
        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            Node<T> current = this;
            while(current.data != null) {
                sb.append(current.data);
                current = current.next;
                if (current.data != null) {
                    sb.append(",");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        SinglyLinkedList<?> list = (SinglyLinkedList<?>) obj;
        Node<T> c1 = head; // current position in this list
        Node<?> c2 = list.head; // current position in parameter list
        while (c1.data != null) {
            if (!c1.data.equals(c2.data)) {
                return false;
            }
            c1 = c1.next;
            c2 = c2.next;
        }
        return true;
    }
}
