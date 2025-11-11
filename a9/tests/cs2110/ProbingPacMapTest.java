package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * Test suite for `ProbingPacMap`.
 */
class ProbingPacMapTest {

    @DisplayName("WHEN a new `ProbingPacMap` is constructed, THEN it has size 0.")
    @Test
    void testEmptyAtConstruction() {
        ProbingPacMap<String, Integer> map = new ProbingPacMap<>();
        assertEquals(0, map.size());
    }

    @DisplayName("WHEN we `put()` a (key,value) pair into a `ProbingPacMap`, THEN the map should "
            + "report that it contains that key.")
    @Test
    void testContainsKeyAfterPut() {
        ProbingPacMap<String, Integer> map = new ProbingPacMap<>();
        map.put("hello", 4);
        assertTrue(map.containsKey("hello"));
    }

    @DisplayName("WHEN we `put()` a (key,value) pair into a `ProbingPacMap`, THEN we should be "
            + "able to `get()` its value by passing in its key.")
    @Test
    void testGetAfterPut() {
        ProbingPacMap<String, Integer> map = new ProbingPacMap<>();
        map.put("hello", 4);
        assertEquals(4, map.get("hello"));
    }

    @DisplayName("WHEN we `remove()` an entry from a `ProbingPacMap` by its key, THEN the map "
            + "should report that it no longer contains that key.")
    @Test
    void testContainsKeyAfterRemove() {
        ProbingPacMap<String, Integer> map = new ProbingPacMap<>();
        map.put("hello", 4);
        map.remove("hello");
        assertFalse(map.containsKey("hello"));
    }

    /**
     * A class with a poorly-chosen hashCode that can be used to help test that our `ProbingPacMap`
     * correctly handles hash collisions.
     */
    record StringBadHash(String str) {

        @Override
        public int hashCode() {
            return str.length(); // hash this object to the length of its `str` field
        }
    }

    @DisplayName("WHEN we `put()` two entries with colliding keys into a `ProbingPacMap`, THEN the "
            + "map should report that it contains both of those keys.")
    @Test
    void testContainsKeyCollision() {
        ProbingPacMap<StringBadHash, Integer> map = new ProbingPacMap<>();
        StringBadHash aaa = new StringBadHash("AAA");
        StringBadHash bbb = new StringBadHash("BBB");

        map.put(aaa, 1);
        map.put(bbb, 2);
        assertTrue(map.containsKey(aaa));
        assertTrue(map.containsKey(bbb));
    }

    @DisplayName("WHEN we `get()` a key that is not in the map, THEN it throws NoSuchElementException.")
    @Test
    void testGetMissingThrows() {
        ProbingPacMap<String, Integer> map = new ProbingPacMap<>();
        map.put("x", 1);
        assertThrows(NoSuchElementException.class, () -> map.get("y"));
    }

    @DisplayName("WHEN we `remove()` a key that exists, THEN the old value is returned.")
    @Test
    void testRemoveReturnsOldValue() {
        ProbingPacMap<String, Integer> map = new ProbingPacMap<>();
        map.put("x", 10);
        assertEquals(10, map.remove("x"));
    }

    @DisplayName("WHEN we `remove()` a key that does not exist, THEN it throws NoSuchElementException.")
    @Test
    void testRemoveMissingThrows() {
        ProbingPacMap<String, Integer> map = new ProbingPacMap<>();
        map.put("x", 10);
        assertThrows(NoSuchElementException.class, () -> map.remove("y"));
    }

    @DisplayName("WHEN we `put()` twice with the same key, THEN the value is overwritten and size stays 1.")
    @Test
    void testOverwriteKeepsSize() {
        ProbingPacMap<String, Integer> map = new ProbingPacMap<>();
        map.put("k", 1);
        map.put("k", 2);
        assertEquals(1, map.size());
        assertEquals(2, map.get("k"));
    }

    @DisplayName("WHEN we call `containsKey()` on a key that was never inserted, THEN it returns false.")
    @Test
    void testContainsKeyMissing() {
        ProbingPacMap<String, Integer> map = new ProbingPacMap<>();
        map.put("a", 1);
        assertFalse(map.containsKey("b"));
    }

    @DisplayName("WHEN we insert then remove then `containsKey()`, THEN it returns false.")
    @Test
    void testContainsKeyAfterRemoveAgain() {
        ProbingPacMap<String, Integer> map = new ProbingPacMap<>();
        map.put("a", 1);
        map.remove("a");
        assertFalse(map.containsKey("a"));
    }

    @DisplayName("WHEN we `put()` a key that hashes to a tombstone slot, THEN it reuses the slot.")
    @Test
    void testTombstoneReuse() {
        ProbingPacMapTest.StringBadHash k1 = new ProbingPacMapTest.StringBadHash("AAA");
        ProbingPacMapTest.StringBadHash k2 = new ProbingPacMapTest.StringBadHash("BBB");
        ProbingPacMap<ProbingPacMapTest.StringBadHash, Integer> map = new ProbingPacMap<>();
        map.put(k1, 1);
        map.remove(k1);
        map.put(k2, 2);
        assertTrue(map.containsKey(k2));
        assertEquals(2, map.get(k2));
        assertEquals(1, map.size());
    }

    @DisplayName("WHEN we insert multiple colliding keys, THEN they are all retrievable.")
    @Test
    void testMultipleCollisions() {
        ProbingPacMap<ProbingPacMapTest.StringBadHash, Integer> map = new ProbingPacMap<>();
        ProbingPacMapTest.StringBadHash k1 = new ProbingPacMapTest.StringBadHash("AAA");
        ProbingPacMapTest.StringBadHash k2 = new ProbingPacMapTest.StringBadHash("AAB");
        ProbingPacMapTest.StringBadHash k3 = new ProbingPacMapTest.StringBadHash("AAC");
        map.put(k1, 1);
        map.put(k2, 2);
        map.put(k3, 3);
        assertEquals(1, map.get(k1));
        assertEquals(2, map.get(k2));
        assertEquals(3, map.get(k3));
    }

    @DisplayName("WHEN we insert enough entries to exceed the max load factor, THEN they remain after resize.")
    @Test
    void testResizeKeepsEntries() {
        ProbingPacMap<Integer, Integer> map = new ProbingPacMap<>();
        for (int i = 0; i < 40; i++) {
            map.put(i, i + 100);
        }
        for (int i = 0; i < 40; i++) {
            assertTrue(map.containsKey(i));
            assertEquals(i + 100, map.get(i));
        }
    }

    @DisplayName("WHEN we remove several existing keys, THEN size reflects only the remaining keys.")
    @Test
    void testSizeAfterMultipleRemoves() {
        ProbingPacMap<String, Integer> map = new ProbingPacMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.remove("b");
        map.remove("a");
        assertEquals(1, map.size());
        assertTrue(map.containsKey("c"));
    }
}