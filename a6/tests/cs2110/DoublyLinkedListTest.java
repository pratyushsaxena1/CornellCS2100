package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DoublyLinkedListTest extends ListTest {
    @Override
    public <T> CS2110List<T> constructList() {
        return new DoublyLinkedList<>();
    }
    @DisplayName("reverseIterator()")
    @Nested
    class ListReverseIteratorTest {

        @DisplayName("WHEN we construct a reverse iterator over an empty list and call `hasNext()`, "
                + "THEN the iterator should return `false`.")
        @Test
        public void testReverseIteratorEmpty() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            Iterator<Integer> it = list.reverseIterator();
            assertFalse(it.hasNext());
        }

        @DisplayName("WHEN we construct a reverse iterator over a list with one element, THEN `hasNext()`"
                + "should initially be `true`, calling `next()` should return this element, and "
                + "after this `hasNext()` should return `false`.")
        @Test
        public void testReverseIterator1Element() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            list.add(3);
            Iterator<Integer> it = list.reverseIterator();
            assertTrue(it.hasNext());
            assertEquals(3, it.next());
            assertFalse(it.hasNext());
        }

        @DisplayName("WHEN we construct a reverse iterator over a list with multiple elements, THEN the "
                + "elements are produced in the correct (decreasing index) order, with each being "
                + "returned exactly once.")
        @Test
        public void testReverseIteratorMultipleElements() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            Iterator<Integer> it = list.reverseIterator();
            assertTrue(it.hasNext());
            assertEquals(4, it.next());
            assertTrue(it.hasNext());
            assertEquals(3, it.next());
            assertTrue(it.hasNext());
            assertEquals(2, it.next());
            assertTrue(it.hasNext());
            assertEquals(1, it.next());
            assertFalse(it.hasNext());
        }

        @DisplayName("WHEN we call `hasNext()` on a reverse iterator multiple times in succession, THEN "
                + "the return values are consistent.")
        @Test
        public void testReverseIteratorRepeatedHasNext() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            list.add(1);
            list.add(2);
            Iterator<Integer> it = list.reverseIterator();
            assertTrue(it.hasNext());
            assertTrue(it.hasNext());
            assertEquals(2, it.next());
            assertTrue(it.hasNext());
            assertTrue(it.hasNext());
            assertEquals(1, it.next());
            assertFalse(it.hasNext());
            assertFalse(it.hasNext());
        }

        @DisplayName("WHEN we create multiple reverse iterators over the same list, THEN they both "
                + "separately keep track of which elements they have returned.")
        @Test
        public void testMultipleReverseIterators() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            list.add(1);
            list.add(2);
            list.add(3);

            Iterator<Integer> it1 = list.reverseIterator();
            assertTrue(it1.hasNext());
            assertEquals(3, it1.next());

            Iterator<Integer> it2 = list.reverseIterator();
            assertTrue(it1.hasNext());
            assertTrue(it2.hasNext());
            assertEquals(3, it2.next());

            assertTrue(it1.hasNext());
            assertTrue(it2.hasNext());
            assertEquals(2, it2.next());

            assertTrue(it1.hasNext());
            assertTrue(it2.hasNext());
            assertEquals(1, it2.next());

            assertTrue(it1.hasNext());
            assertFalse(it2.hasNext());
            assertEquals(2, it1.next());

            assertTrue(it1.hasNext());
            assertFalse(it2.hasNext());
            assertEquals(1, it1.next());

            assertFalse(it1.hasNext());
            assertFalse(it2.hasNext());
        }
    }
}