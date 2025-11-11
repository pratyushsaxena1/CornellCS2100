package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * Test suite for `MinPQueue`.
 */
public class MinPQueueTest {

    @DisplayName("WHEN a new MinPQueue is constructed, THEN it is empty and has size 0.")
    @Test
    void testEmptyAtConstruction() {
        MinPQueue<String> pq = new MinPQueue<>();
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
    }

    @DisplayName("WHEN we addOrUpdate() one element, THEN the queue is not empty and size is 1.")
    @Test
    void testAddOne() {
        MinPQueue<String> pq = new MinPQueue<>();
        pq.addOrUpdate("a", 5.0);
        assertFalse(pq.isEmpty());
        assertEquals(1, pq.size());
        assertEquals("a", pq.peek());
        assertEquals(5.0, pq.minPriority());
    }

    @DisplayName("WHEN we addOrUpdate() multiple elements with increasing priorities, THEN peek() returns the smallest.")
    @Test
    void testPeekMin() {
        MinPQueue<String> pq = new MinPQueue<>();
        pq.addOrUpdate("a", 5.0);
        pq.addOrUpdate("b", 2.0);
        pq.addOrUpdate("c", 9.0);
        assertEquals("b", pq.peek());
        assertEquals(2.0, pq.minPriority());
    }

    @DisplayName("WHEN we remove() after adding elements, THEN they come out in nondecreasing priority order.")
    @Test
    void testRemoveOrder() {
        MinPQueue<String> pq = new MinPQueue<>();
        pq.addOrUpdate("a", 5.0);
        pq.addOrUpdate("b", 1.0);
        pq.addOrUpdate("c", 3.0);
        assertEquals("b", pq.remove());
        assertEquals("c", pq.remove());
        assertEquals("a", pq.remove());
        assertTrue(pq.isEmpty());
    }

    @DisplayName("WHEN we remove() from an empty queue, THEN it throws NoSuchElementException.")
    @Test
    void testRemoveEmptyThrows() {
        MinPQueue<String> pq = new MinPQueue<>();
        assertThrows(NoSuchElementException.class, pq::remove);
    }

    @DisplayName("WHEN we peek() an empty queue, THEN it throws NoSuchElementException.")
    @Test
    void testPeekEmptyThrows() {
        MinPQueue<String> pq = new MinPQueue<>();
        assertThrows(NoSuchElementException.class, pq::peek);
    }

    @DisplayName("WHEN we update() an element to a lower priority, THEN it becomes the min.")
    @Test
    void testUpdateLowerPriority() {
        MinPQueue<String> pq = new MinPQueue<>();
        pq.addOrUpdate("a", 5.0);
        pq.addOrUpdate("b", 3.0);
        pq.addOrUpdate("a", 1.0);
        assertEquals("a", pq.peek());
        assertEquals(1.0, pq.minPriority());
    }

    @DisplayName("WHEN we update() an element to a higher priority, THEN the previous min stays the min.")
    @Test
    void testUpdateHigherPriority() {
        MinPQueue<String> pq = new MinPQueue<>();
        pq.addOrUpdate("a", 1.0);
        pq.addOrUpdate("b", 2.0);
        pq.addOrUpdate("a", 10.0);
        assertEquals("b", pq.peek());
        assertEquals(2.0, pq.minPriority());
    }

    @DisplayName("WHEN we addOrUpdate() on an existing key, THEN the size does not grow.")
    @Test
    void testAddOrUpdateDoesNotDuplicate() {
        MinPQueue<String> pq = new MinPQueue<>();
        pq.addOrUpdate("x", 4.0);
        pq.addOrUpdate("x", 2.0);
        assertEquals(1, pq.size());
        assertEquals("x", pq.peek());
        assertEquals(2.0, pq.minPriority());
    }

    @DisplayName("WHEN multiple elements have the same priority, THEN all can be removed and size reaches 0.")
    @Test
    void testEqualPriorities() {
        MinPQueue<String> pq = new MinPQueue<>();
        pq.addOrUpdate("a", 5.0);
        pq.addOrUpdate("b", 5.0);
        pq.addOrUpdate("c", 5.0);
        assertEquals(3, pq.size());
        assertTrue(pq.minPriority() == 5.0);
        pq.remove();
        pq.remove();
        pq.remove();
        assertTrue(pq.isEmpty());
    }

    @DisplayName("WHEN we add many elements, THEN size reflects the number added.")
    @Test
    void testManyAddsSize() {
        MinPQueue<Integer> pq = new MinPQueue<>();
        for (int i = 0; i < 50; i++) {
            pq.addOrUpdate(i, 100 - i);
        }
        assertEquals(50, pq.size());
    }

    @DisplayName("WHEN we combine adds, updates, and removes, THEN the queue maintains the heap property.")
    @Test
    void testMixedOperations() {
        MinPQueue<String> pq = new MinPQueue<>();
        pq.addOrUpdate("a", 5.0);
        pq.addOrUpdate("b", 2.0);
        pq.addOrUpdate("c", 8.0);
        assertEquals("b", pq.remove());
        pq.addOrUpdate("d", 1.0);
        pq.addOrUpdate("c", 0.5);
        assertEquals("c", pq.remove());
        assertEquals("d", pq.remove());
        assertEquals("a", pq.remove());
        assertTrue(pq.isEmpty());
    }

}