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
     * Assert that this object satisfies its class invariants.
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

    /** Throws NPE if elem is null. This list does not allow null elements. */
    private static void requireNonNull(Object elem) {
        assert elem != null;
    }

    /** Bounds check for 0 <= index < size. */
    private void checkElementIndex(int index) {
        assert index >= 0 && index < size;
    }

    /** Bounds check for insertion: 0 <= index <= size. */
    private void checkPositionIndex(int index) {
        assert index >= 0 && index <= size;
    }

    /** Returns node at index in O(min(index, size-1-index)). */
    private DNode nodeAt(int index) {
        if (index < (size >> 1)) {
            DNode x = head;
            for (int i = 0; i < index; i++) x = x.next;
            return x;
        } else {
            DNode x = tail;
            for (int i = size - 1; i > index; i--) x = x.prev;
            return x;
        }
    }

    /** Link elem as last node (append) in O(1). */
    private void linkLast(T elem) {
        DNode last = tail;
        DNode x = new DNode(elem, last, null);
        tail = x;
        if (last == null) head = x;
        else last.next = x;
        size++;
    }

    /** Link elem before given successor node `succ` in O(1). */
    private void linkBefore(T elem, DNode succ) {
        DNode pred = succ.prev;
        DNode x = new DNode(elem, pred, succ);
        succ.prev = x;
        if (pred == null) head = x;
        else pred.next = x;
        size++;
    }

    /** Unlink given node x and return its element. O(1). */
    private T unlink(DNode x) {
        DNode pred = x.prev;
        DNode succ = x.next;

        if (pred == null) head = succ;
        else { pred.next = succ; x.prev = null; }

        if (succ == null) tail = pred;
        else { succ.prev = pred; x.next = null; }

        T elem = x.data;
        x.data = null;
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
        if (elem == null) return false;
        for (DNode x = head; x != null; x = x.next) {
            if (elem.equals(x.data)) return true;
        }
        return false;
    }

    @Override
    public int indexOf(T elem) {
        requireNonNull(elem);
        int i = 0;
        for (DNode x = head; x != null; x = x.next, i++) {
            if (elem.equals(x.data)) return i;
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
        for (DNode x = head; x != null; x = x.next) {
            if (elem.equals(x.data)) {
                unlink(x);
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
        private DNode nextToVisit = tail;   // start at the end

        @Override
        public boolean hasNext() {
            return nextToVisit != null;
        }

        @Override
        public T next() {
            T elem = nextToVisit.data;
            nextToVisit = nextToVisit.prev; // walk backward
            return elem;
        }
    }
}
