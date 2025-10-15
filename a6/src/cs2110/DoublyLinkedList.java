package cs2110;

import java.util.Iterator;

/**
 * A list of elements of type `T` implemented using a doubly-linked chain. `null` may not be stored
 * as an element in this list.
 */
public class DoublyLinkedList<T> implements CS2110List<T> {

    /**
     * A node of a doubly-linked list whose elements have type `T`.
     */
    private class DNode {

        /**
         * The element in this node.
         */
        T data;

        /**
         * The node in the chain (or null if this is the last node).
         */
        DNode next;

        /**
         * The previous node in the chain (or null if this is the first node).
         */
        DNode prev;

        /**
         * Create a Node containing element `elem`, pointing backward to node 'prev' (may be null),
         * and pointing forward to node `next` (may be null).
         */
        DNode(T elem, DNode prev, DNode next) {
            data = elem;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * The number of elements in the list.  Equal to the number of linked nodes reachable from
     * `head` (including `head` itself) using `next` arrows.
     */
    private int size;

    /**
     * The first node of the doubly-linked list (null for empty list). `head.prev` must be null
     */
    private DNode head;

    /**
     * The last node of the doubly-linked list (null for empty list). `tail.next` must be null.
     */
    private DNode tail;

    /**
     * Assert that this object satisfies all of its class invariants.
     */
    private void assertInv() {
        assert size >= 0;
        if (size == 0) {
            assert head == null;
            assert tail == null;
        } else {
            assert head != null;
            assert head.prev == null;
            assert tail != null;
            int count = 0;
            DNode curr = head;
            DNode prev = null;
            while (curr != null) {
                assert curr.data != null;
                assert curr.prev == prev;
                if (curr.next != null) {
                    assert curr.next.prev == curr;
                } else {
                    assert curr == tail;
                    assert tail.next == null;
                }
                count++;
                prev = curr;
                curr = curr.next;
            }
            assert count == size;
            assert prev == tail;
        }
    }

    /**
     * Constructs an empty list.
     */
    public DoublyLinkedList() {
        size = 0;
        head = null;
        tail = null;
        assertInv();
    }

    /**
     * Assert that this object isn't null.
     */
    private static void requireNonNull(Object element) {
        assert element != null;
    }

    /**
     * Assert that index >= 0 && index < size.
     */
    private void checkElementIndex(int index) {
        assert index >= 0 && index < size;
    }

    /**
     * Assert that index >= 0 && index <= size.
     */
    private void checkPositionIndex(int index) {
        assert index >= 0 && index <= size;
    }

    /**
     * Returns the node at the given 'index'.
     */
    private DNode nodeAt(int index) {
        if (index < (size / 2)) {
            DNode curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            return curr;
        } else {
            DNode curr = tail;
            for (int i = size - 1; i > index; i--) {
                curr = curr.prev;
            }
            return curr;
        }
    }

    /**
     * Link the element as the last node.
     */
    private void linkLast(T element) {
        DNode last = tail;
        DNode curr = new DNode(element, last, null);
        tail = curr;
        if (last == null) {
            head = curr;
        } else {
            last.next = curr;
        }
        size++;
    }

    /**
     * Link the element before the given 'successor' node.
     */
    private void linkBefore(T element, DNode successor) {
        DNode previous = successor.prev;
        DNode curr = new DNode(element, previous, successor);
        successor.prev = curr;
        if (previous == null) {
            head = curr;
        } else {
            previous.next = curr;
        }
        size++;
    }

    /**
     * Unlink the node 'curr' and return its element.
     */
    private T unlink(DNode curr) {
        DNode previous = curr.prev;
        DNode successor = curr.next;
        if (previous == null) {
            head = successor;
        } else {
            previous.next = successor;
            curr.prev = null;
        }
        if (successor == null) {
            tail = previous;
        } else {
            successor.prev = previous;
            curr.next = null;
        }
        T elem = curr.data;
        curr.data = null;
        size--;
        return elem;
    }

    @Override
    public void add(T elem) {
        requireNonNull(elem);
        linkLast(elem);
        assertInv();
    }

    @Override
    public void insert(int index, T elem) {
        requireNonNull(elem);
        checkPositionIndex(index);
        if (index == size) {
            linkLast(elem);
        } else {
            linkBefore(elem, nodeAt(index));
        }
        assertInv();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return nodeAt(index).data;
    }

    @Override
    public boolean contains(T elem) {
        if (elem == null) {
            return false;
        }
        for (DNode curr = head; curr != null; curr = curr.next) {
            if (elem.equals(curr.data)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(T elem) {
        requireNonNull(elem);
        int i = 0;
        for (DNode curr = head; curr != null; curr = curr.next, i++) {
            if (elem.equals(curr.data)) {
                return i;
            }
        }
        return i;
    }

    @Override
    public void set(int index, T elem) {
        requireNonNull(elem);
        checkElementIndex(index);
        nodeAt(index).data = elem;
        assertInv();
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        T result = unlink(nodeAt(index));
        assertInv();
        return result;
    }

    @Override
    public void delete(T elem) {
        assert elem != null;
        for (DNode curr = head; curr != null; curr = curr.next) {
            if (elem.equals(curr.data)) {
                unlink(curr);
                assertInv();
                return;
            }
        }
    }

    /**
     * Return an iterator over the elements of this list (in forward order). To ensure the
     * functionality of this iterator, this list is not mutated while the iterator is in use.
     */
    @Override
    public Iterator<T> iterator() {
        return new ForwardIterator();
    }

    /**
     * A forward iterator over a doubly-linked list. Guarantees correct behavior (visits each
     * element exactly once in the correct order) only if the list is not mutated during the
     * lifetime of this iterator.
     */
    private class ForwardIterator implements Iterator<T> {

        /**
         * The node whose value will next be returned by the iterator, or null once the iterator
         * reaches the end of the list.
         */
        private DNode nextToVisit;

        /**
         * Constructs a new ForwardIterator over this list
         */
        public ForwardIterator() {
            nextToVisit = head;
        }

        @Override
        public boolean hasNext() {
            return nextToVisit != null;
        }

        @Override
        public T next() {
            T elem = nextToVisit.data;
            nextToVisit = nextToVisit.next;
            return elem;
        }
    }

    public Iterator<T> reverseIterator() {
        return new ReverseIterator();
    }

    private class ReverseIterator implements Iterator<T> {

        private DNode nextNode = tail;

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public T next() {
            T elem = nextNode.data;
            nextNode = nextNode.prev;
            return elem;
        }
    }
}
