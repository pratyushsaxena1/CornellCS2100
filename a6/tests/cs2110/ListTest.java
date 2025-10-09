package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public abstract class ListTest {

    /**
     * Used by testing subclasses to specify which subtype of CS2110List to construct.
     */
    public abstract <T> CS2110List<T> constructList();

    @DisplayName("WHEN a new list is constructed, THEN it should be empty, so have size 0.")
    @Test
    public void testEmptyAtConstruction() {
        CS2110List<String> list = constructList();
        assertEquals(0, list.size());
    }

    /*
     * Note: Using the `@Nested` annotation allows us to define *inner classes* that help organize
     * our tests in the JUnit output. This will make it easier to tell which methods are working
     * correctly and which need more work, aiding in our test-driven development.
     */

    /**
     * These tests check the correctness of `add()` using only `get()` and `size()`.
     */
    @DisplayName("add()")
    @Nested
    class ListAddTest {

        @DisplayName("WHEN we add an element to the list, THEN its size should increase by 1.")
        @Test
        public void testAddIncreasesSize() {
            CS2110List<Integer> list = constructList();
            int oldSize;
            for (int i = 0; i < 100; i++) {
                oldSize = list.size();
                list.add(i);
                assertEquals(oldSize + 1, list.size());
            }
        }

        @DisplayName(
                "WHEN we add an element to an empty list, THEN that element should be present in "
                        + "the list at index 0.")
        @Test
        public void testAddOnlyElement() {
            CS2110List<Integer> list = constructList();
            list.add(2);
            assertEquals(2, list.get(0));
        }

        @DisplayName(
                "WHEN we add an element to a non-empty list, THEN that element should be added to"
                        + "the end of the list.")
        @Test
        public void testAddsToEnd() {
            CS2110List<Integer> list = constructList();
            list.add(0);
            for (int i = 1; i < 100; i++) {
                list.add(i);
                assertEquals(i, list.get(i));
            }
        }
    }

    /**
     * These tests check the correctness of `insert()` using only `get()` and `size()`.
     */
    @DisplayName("insert()")
    @Nested
    class ListInsertTest {

        @DisplayName("WHEN we insert an element into the list, THEN its size should increase by 1.")
        @Test
        public void testInsertIncreasesSize() {
            CS2110List<Integer> list = constructList();
            int oldSize;
            for (int i = 0; i < 10; i++) {
                oldSize = list.size();
                list.insert(0, i);
                assertEquals(oldSize + 1, list.size());
            }
        }

        @DisplayName(
                "WHEN we insert an element at index 0 of an empty list, THEN that element should "
                        + "be present in the list at index 0.")
        @Test
        public void testInsertOnlyElement() {
            CS2110List<Integer> list = constructList();
            list.insert(0, 2);
            assertEquals(2, list.get(0));
        }

        @DisplayName(
                "WHEN we insert an element at index 0 of a list with one element, THEN the new "
                        + "element should be present in the list at index 0 and the old element should be "
                        + "present in the list at index 1.")
        @Test
        public void testInsertBeforeOnlyElement() {
            CS2110List<Integer> list = constructList();
            list.add(1);
            list.insert(0, 2);
            assertEquals(2, list.get(0));
            assertEquals(1, list.get(1));
        }

        @DisplayName(
                "WHEN we insert an element at index 1 of a list with one element, THEN the new "
                        + "element should be present in the list at index 1 and the old element should be "
                        + "present in the list at index 0.")
        @Test
        public void testInsertAfterOnlyElement() {
            CS2110List<Integer> list = constructList();
            list.add(1);
            list.insert(1, 2);
            assertEquals(2, list.get(1));
            assertEquals(1, list.get(0));
        }

        @DisplayName(
                "WHEN we insert an element `b` at index 1 of a list with two element [`a`,`c`], "
                        + "THEN `b` should be at index 1 in the list, `a` should be at index 0, and `c` should "
                        + "be at index `1`.")
        @Test
        public void testInsertBetweenElements() {
            CS2110List<Character> list = constructList();
            list.add('a');
            list.add('c');
            list.insert(1, 'b');
            assertEquals('a', list.get(0));
            assertEquals('b', list.get(1));
            assertEquals('c', list.get(2));
        }

        @DisplayName("WHEN we insert an element into the middle of a long list, THEN it should be "
                + "located at the correct index and all elements following it should shift one index "
                + "to the right.")
        @Test
        public void testInsertBetweenLongList() {
            CS2110List<Integer> list = constructList();
            for (int i = 0; i < 100; i++) {
                list.add(i);
            }
            for (int i = 101; i < 200; i++) {
                list.add(i);
            }
            list.insert(100, 100);
            for (int i = 0; i < 200; i++) {
                assertEquals(i, list.get(i));
            }
        }
    }

    /**
     * These tests check the functionality of `set()`, using `add()` to build the list and `get()`
     * and `size()` to check correctness.
     */
    @DisplayName("set()")
    @Nested
    class ListSetTest {

        @DisplayName(
                "WHEN we use `set()` to update the value of the only element in a list, THEN this "
                        + "updated value is returned by `get()`.")
        @Test
        public void testSetOnlyElement() {
            CS2110List<String> list = constructList();
            list.add("Hello");
            list.set(0, "Goodbye");
            assertEquals("Goodbye", list.get(0));
        }

        @DisplayName(
                "WHEN we use `set()` to update the value of the first element in a longer list, "
                        + "THEN this updated value is returned by `get()`.")
        @Test
        public void testSetFirstElement() {
            CS2110List<String> list = constructList();
            list.add("apple");
            list.add("banana");
            list.add("cherry");
            list.set(0, "apricot");
            assertEquals("apricot", list.get(0));
        }

        @DisplayName(
                "WHEN we use `set()` to update the value of the last element in a longer list, "
                        + "THEN this updated value is returned by `get()`.")
        @Test
        public void testSetLastElement() {
            CS2110List<String> list = constructList();
            list.add("apple");
            list.add("banana");
            list.add("cherry");
            list.set(2, "currant");
            assertEquals("currant", list.get(2));
        }

        @DisplayName("WHEN we use `set()` to update a value in the middle of a longer list, "
                + "THEN this updated value is returned by `get()`.")
        @Test
        public void testSetMiddleElement() {
            CS2110List<String> list = constructList();
            list.add("apple");
            list.add("banana");
            list.add("cherry");
            list.set(1, "blueberry");
            assertEquals("blueberry", list.get(1));
        }

        @DisplayName(
                "WHEN we make multiple calls to `set()` to update a value of an element, THEN the "
                        + "argument of the last `set()` call is returned by `get()`.")
        @Test
        public void testSetMultipleTimes() {
            CS2110List<String> list = constructList();
            list.add("apple");
            list.add("banana");
            list.add("cherry");
            list.set(1, "blueberry");
            list.set(1, "boysenberry");
            assertEquals("boysenberry", list.get(1));
        }

        @DisplayName("WHEN we call `set()` to update a value in a list, THEN the size of the list "
                + "does not change.")
        @Test
        public void testSetPreservesSize() {
            CS2110List<Integer> list = constructList();
            for (int i = 0; i < 100; i++) {
                list.add(i);
            }
            for (int i = 0; i < 100; i++) {
                list.set(i, i + 1);
                assertEquals(100, list.size());
            }
        }
    }

    /**
     * These tests check the functionality of the `remove()` method, using `add()` to build the list
     * and `get()` and `size()` to check correctness.
     */
    @DisplayName("remove()")
    @Nested
    class ListRemoveTest {

        @DisplayName("WHEN we `remove()` the only element of the list, THEN this element is "
                + "returned and the size of the list becomes 0.")
        @Test
        public void testRemoveOnly() {
            CS2110List<Integer> list = constructList();
            list.add(1);
            int i = list.remove(0); // auto-unboxing
            assertEquals(1, i);
            assertEquals(0, list.size());
        }

        @DisplayName("WHEN we `remove()` elements from a list, THEN its size decreases by 1.")
        @Test
        public void testRemoveDecreasesSize() {
            CS2110List<Integer> list = constructList();
            for (int i = 0; i < 100; i++) {
                list.add(i);
            }
            int oldSize;
            for (int i = 0; i < 100; i++) {
                oldSize = list.size();
                list.remove(0);
                assertEquals(oldSize - 1, list.size());
            }
        }

        @DisplayName("WHEN we `remove()` the second element of a 2-element list, THEN the second "
                + "element is returned and the first element remains unchanged.")
        @Test
        public void testRemoveSecondOf2() {
            CS2110List<String> list = constructList();
            list.add("First");
            list.add("Second");
            assertEquals("Second", list.remove(1));
            assertEquals("First", list.get(0));
        }

        @DisplayName("WHEN we `remove()` the first element of a 2-element list, THEN the first "
                + "element is returned and the second element shifts to index 0.")
        @Test
        public void testRemoveFirstOf2() {
            CS2110List<String> list = constructList();
            list.add("First");
            list.add("Second");
            assertEquals("First", list.remove(0));
            assertEquals("Second", list.get(0));
        }

        @DisplayName("WHEN we `remove()` an element in the middle of a long list, THEN the correct "
                + "element is returned, the elements before it remain unchanged and the elements "
                + "after it shift one index left.")
        @Test
        public void testRemoveFromMiddle() {
            CS2110List<Integer> list = constructList();
            for (int i = 0; i < 100; i++) {
                list.add(i);
            }
            assertEquals(50, list.remove(50));
            for (int i = 0; i < 50; i++) {
                assertEquals(i, list.get(i));
            }
            for (int i = 50; i < 99; i++) {
                assertEquals(i + 1, list.get(i));
            }
        }
    }

    /**
     * These tests check the functionality of the `contains()` method, using `add()` and `remove()`
     * to modify the contents of the list.
     */
    @DisplayName("contains()")
    @Nested
    class ListContainsTest {

        /*
         * DisplayNames for accessor methods often don't fit into the "WHEN ... THEN ..." since
         * they simply observe the state of an object, they don't perform any actions on it.
         */
        @DisplayName("An empty list does not `contain()` any elements.")
        @Test
        public void testContainsEmpty() {
            CS2110List<Integer> list = constructList();
            assertFalse(list.contains(0));
            assertFalse(list.contains(1));
            assertFalse(list.contains(null));
        }

        @DisplayName("A 1-element list `contains()` that element but no other element.")
        @Test
        public void testContainsSize1() {
            CS2110List<Integer> list = constructList();
            list.add(0);
            assertTrue(list.contains(0));
            assertFalse(list.contains(1));
            assertFalse(list.contains(2));
            assertFalse(list.contains(null));
        }

        @DisplayName("A list with multiple elements `contains()` those elements but no others.")
        @Test
        public void testContainsLongerList() {
            CS2110List<Integer> list = constructList();
            list.add(0);
            list.add(2);
            list.add(4);
            assertTrue(list.contains(0));
            assertTrue(list.contains(2));
            assertTrue(list.contains(4));
            assertFalse(list.contains(1));
            assertFalse(list.contains(3));
            assertFalse(list.contains(5));
            assertFalse(list.contains(null));
        }

        @DisplayName("WHEN a list includes an element multiple times, THEN calling `contains()` on "
                + "that element should return `true`.")
        @Test
        public void testContainsMultiple() {
            CS2110List<Integer> list = constructList();
            list.add(0);
            list.add(1);
            list.add(1);
            list.add(2);
            list.add(1);
            assertTrue(list.contains(1));
        }

        @DisplayName("Containment checking should use `equals()` rather than reference equality.")
        @Test
        public void testContainsUsesEquals() {
            CS2110List<String> list = constructList();
            list.add("apple");
            list.add("banana");
            list.add("cherry");
            assertTrue(list.contains(new String("banana")));
            /* Note: We use the explicit String constructor in the previous line to ensure that Java
             * actually creates a second object rather than "interning" both String literals with
             * the same object.
             */
        }

        @DisplayName("WHEN we remove an element that appeared once in a list, THEN the list no "
                + "longer `contains()` that element but should contain all other elements.")
        @Test
        public void testContainsFalseAfterRemove() {
            CS2110List<Integer> list = constructList();
            list.add(0);
            list.add(1);
            list.add(2);
            assertTrue(list.contains(0));
            assertTrue(list.contains(1));
            assertTrue(list.contains(2));
            list.remove(2);
            assertTrue(list.contains(0));
            assertTrue(list.contains(1));
            assertFalse(list.contains(2));
        }

        @DisplayName("WHEN a list includes multiple copies of an element, it should still "
                + "`contain()` that element when not all copies have been removed.")
        @Test
        public void testContainsRemoveMultiple() {
            CS2110List<Integer> list = constructList();
            list.add(0);
            list.add(0);
            list.add(0);
            assertTrue(list.contains(0));
            list.remove(2);
            assertTrue(list.contains(0));
            list.remove(1);
            assertTrue(list.contains(0));
            list.remove(0);
            assertFalse(list.contains(0));
        }
    }

    /**
     * These tests check the functionality of the `indexOf()` method, using `add()`, `insert()` and
     * `remove()` to modify the contents of the list.
     */
    @DisplayName("indexOf()")
    @Nested
    class ListIndexOfTest {

        @DisplayName("WHEN we query the  `indexOf()` the contents of a 1-element list, THEN "
                + "it should return 0.")
        @Test
        public void testIndexOfSize1() {
            CS2110List<Integer> list = constructList();
            list.add(4);
            assertEquals(0, list.indexOf(4));
        }

        @DisplayName("WHEN we query the `indexOf()` the first element of a 2-element list, THEN "
                + "it should return 0.")
        @Test
        public void testIndexOfFirstOf2() {
            CS2110List<String> list = constructList();
            list.add("First");
            list.add("Second");
            assertEquals(0, list.indexOf("First"));
        }

        @DisplayName("WHEN we query the `indexOf()` the second element of a 2-element list, THEN "
                + "it should return 1.")
        @Test
        public void testIndexOfSecondOf2() {
            CS2110List<String> list = constructList();
            list.add("First");
            list.add("Second");
            assertEquals(1, list.indexOf("Second"));
        }

        @DisplayName("WHEN we query the `indexOf()` an element in a long list of distinct "
                + "elements, THEN the correct index should be returned.")
        @Test
        public void testIndexOfLongDistinct() {
            CS2110List<Integer> list = constructList();
            for (int i = 0; i < 100; i++) {
                list.add(i);
            }
            for (int i = 0; i < 100; i++) {
                assertEquals(i, list.indexOf(i));
            }
        }

        @DisplayName("WHEN we query the `indexOf()` an element that appears multiple times in a "
                + "list, THEN index of its first occurrence should be returned.")
        @Test
        public void testIndexOfMultiple() {
            CS2110List<Integer> list = constructList();
            list.add(0);
            list.add(2);
            list.add(2);
            list.add(1);
            list.add(2);
            assertEquals(1, list.indexOf(2));
        }

        @DisplayName("WHEN we insert another copy of a value earlier in a list, THEN `indexOf()` "
                + "should return this new index.")
        @Test
        public void testIndexOfInsert() {
            CS2110List<Integer> list = constructList();
            list.add(0);
            list.add(1);
            list.add(2);
            list.add(3);
            assertEquals(3, list.indexOf(3));
            list.insert(1, 3);
            assertEquals(1, list.indexOf(3));
        }

        @DisplayName("WHEN we remove the first copy of a value from a list, THEN `indexOf()` "
                + "should return the new index of the second copy.")
        @Test
        public void testIndexOfRemove() {
            CS2110List<Integer> list = constructList();
            list.add(0);
            list.add(3);
            list.add(1);
            list.add(2);
            list.add(3);
            assertEquals(1, list.indexOf(3));
            list.remove(1);
            assertEquals(3, list.indexOf(3));
        }

        @DisplayName("`instanceOf()` should use `equals()` rather than reference equality.")
        @Test
        public void testContainsUsesEquals() {
            CS2110List<String> list = constructList();
            String s1 = new String("apple");
            String s2 = new String("apple");
            list.add(s1);
            list.add(s2);
            assertEquals(0, list.indexOf(s2));
        }
    }

    /**
     * These tests check the functionality of the `delete()` method, using `add()` to modify the
     * contents of the list and `get()` and `size()` to check correctness.
     */
    @DisplayName("delete()")
    @Nested
    class ListDeleteTest {

        @DisplayName("WHEN we `delete()` the only element of the list, THEN the size of the list "
                + "becomes 0.")
        @Test
        public void testDeleteOnly() {
            CS2110List<Integer> list = constructList();
            list.add(1);
            list.delete(1);
            assertEquals(0, list.size());
        }

        @DisplayName("WHEN we `delete()` elements from a list, THEN its size decreases by 1.")
        @Test
        public void testDeleteDecreasesSize() {
            CS2110List<Integer> list = constructList();
            for (int i = 0; i < 100; i++) {
                list.add(i);
            }
            int oldSize;
            for (int i = 0; i < 100; i++) {
                oldSize = list.size();
                list.delete(i);
                assertEquals(oldSize - 1, list.size());
            }
        }

        @DisplayName("WHEN we `delete()` the second element of a 2-element list, THEN the first "
                + "element remains unchanged.")
        @Test
        public void testDeleteSecondOf2() {
            CS2110List<String> list = constructList();
            list.add("First");
            list.add("Second");
            list.delete("Second");
            assertEquals("First", list.get(0));
        }

        @DisplayName("WHEN we `delete()` the first element of a 2-element list, THEN the second "
                + "element shifts to index 0.")
        @Test
        public void testDeleteFirstOf2() {
            CS2110List<String> list = constructList();
            list.add("First");
            list.add("Second");
            list.delete("First");
            assertEquals("Second", list.get(0));
        }

        @DisplayName(
                "WHEN we `delete()` an element in the middle of a long list, THEN the elements "
                        + "before it remain unchanged and the elements after it shift one index left.")
        @Test
        public void testDeleteFromMiddle() {
            CS2110List<Integer> list = constructList();
            for (int i = 0; i < 100; i++) {
                list.add(i);
            }
            list.delete(50);
            for (int i = 0; i < 50; i++) {
                assertEquals(i, list.get(i));
            }
            for (int i = 50; i < 99; i++) {
                assertEquals(i + 1, list.get(i));
            }
        }

        @DisplayName("WHEN we `delete()` an element that appears multiple times in a list, THEN "
                + "only the first occurrence is deleted.")
        @Test
        public void testDeleteDuplicate() {
            CS2110List<Integer> list = constructList();
            list.add(0);
            list.add(1);
            list.add(2);
            list.add(1);
            list.add(3);
            list.delete(1);
            assertEquals(0, list.get(0));
            assertEquals(2, list.get(1));
            assertEquals(1, list.get(2));
            assertEquals(3, list.get(3));
        }
    }

    @DisplayName("iterator()")
    @Nested
    class ListIteratorTest {

        @DisplayName("WHEN we construct an iterator over an empty list and call `hasNext()`, "
                + "THEN the iterator should return `false`.")
        @Test
        public void testIteratorEmpty() {
            CS2110List<Integer> list = constructList();
            Iterator<Integer> it = list.iterator();
            assertFalse(it.hasNext());
        }

        @DisplayName("WHEN we construct an iterator over a list with one element, THEN `hasNext()`"
                + "should initially be `true`, calling `next()` should return this element, and "
                + "after this `hasNext()` should return `false`.")
        @Test
        public void testIterator1Element() {
            CS2110List<Integer> list = constructList();
            list.add(3);
            Iterator<Integer> it = list.iterator();
            assertTrue(it.hasNext());
            assertEquals(3, it.next());
            assertFalse(it.hasNext());
        }

        @DisplayName("WHEN we construct an iterator over a list with multiple element, THEN the "
                + "elements are produced in the correct (increasing index) order, with each being "
                + "returned exactly once.")
        @Test
        public void testIteratorMultipleElements() {
            CS2110List<Integer> list = constructList();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            Iterator<Integer> it = list.iterator();
            assertTrue(it.hasNext());
            assertEquals(1, it.next());
            assertTrue(it.hasNext());
            assertEquals(2, it.next());
            assertTrue(it.hasNext());
            assertEquals(3, it.next());
            assertTrue(it.hasNext());
            assertEquals(4, it.next());
            assertFalse(it.hasNext());
        }

        @DisplayName("WHEN we call `hasNext()` on an iterator multiple times in succession, THEN"
                + "the return values are consistent.")
        @Test
        public void testRepeatedHasNext() {
            CS2110List<Integer> list = constructList();
            list.add(1);
            list.add(2);
            Iterator<Integer> it = list.iterator();
            assertTrue(it.hasNext());
            assertTrue(it.hasNext());
            assertEquals(1, it.next());
            assertTrue(it.hasNext());
            assertTrue(it.hasNext());
            assertEquals(2, it.next());
            assertFalse(it.hasNext());
            assertFalse(it.hasNext());
        }

        @DisplayName("WHEN we create multiple iterators over the same list, THEN they both separately "
                + "keep track of which elements they have returned.")
        @Test
        public void testMultipleIterators() {
            CS2110List<Integer> list = constructList();
            list.add(1);
            list.add(2);
            list.add(3);
            Iterator<Integer> it1 = list.iterator();
            assertTrue(it1.hasNext());
            assertEquals(1, it1.next());

            Iterator<Integer> it2 = list.iterator();
            assertTrue(it1.hasNext());
            assertTrue(it2.hasNext());
            assertEquals(1, it2.next());

            assertTrue(it1.hasNext());
            assertTrue(it2.hasNext());
            assertEquals(2, it2.next());

            assertTrue(it1.hasNext());
            assertTrue(it2.hasNext());
            assertEquals(3, it2.next());

            assertTrue(it1.hasNext());
            assertFalse(it2.hasNext());
            assertEquals(2, it1.next());

            assertTrue(it1.hasNext());
            assertFalse(it2.hasNext());
            assertEquals(3, it1.next());

            assertFalse(it1.hasNext());
            assertFalse(it2.hasNext());
        }
    }
}
