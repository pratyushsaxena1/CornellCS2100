package cs2110;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs2110.SinglyLinkedList.Node;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SLLMergeSortTest {

    static IntLinkedList createList(int... elements) {
        IntLinkedList list = new IntLinkedList();
        for (int elem : elements) {
            list.add(elem);
        }
        return list;
    }

    @DisplayName("sort()")
    @Nested
    class SortTest {
        @DisplayName("WHEN we sort the empty list, THEN we get the empty list.")
        @Test
        public void testEmptySort() {
            IntLinkedList list = createList();
            list.sort();
            assertEquals(createList(), list);
        }

        @DisplayName("WHEN we sort a one-element list, THEN it does not change.")
        @Test
        public void testOneElementSort() {
            IntLinkedList list = createList(5);
            list.sort();
            assertEquals(createList(5), list);
        }

        @DisplayName("WHEN we sort a two-element list with elements in ascending order, "
                + "THEN the list does not change.")
        @Test
        public void testTwoElementsAlreadySorted() {
            IntLinkedList list = createList(1,3);
            list.sort();
            assertEquals(createList(1,3), list);
        }

        @DisplayName("WHEN we sort a two-element list with elements in descending order, "
                + "THEN the positions of these elements swap.")
        @Test
        public void testTwoElementsSwap() {
            IntLinkedList list = createList(3,1);
            list.sort();
            assertEquals(createList(1,3), list);
        }

        @DisplayName("WHEN we sort an even-length list, THEN it is sorted correctly.")
        @Test
        public void testEvenLengthList() {
            IntLinkedList list = createList(3,1,0,5);
            list.sort();
            assertEquals(createList(0,1,3,5), list);
        }

        @DisplayName("WHEN we sort an odd-length list, THEN it is sorted correctly.")
        @Test
        public void testOddLengthList() {
            IntLinkedList list = createList(1,4,2,5,3);
            list.sort();
            assertEquals(createList(1,2,3,4,5), list);
        }

        @DisplayName("WHEN we sort a list that is sorted in decreasing order, "
                + "THEN it is sorted correctly.")
        @Test
        public void testDecreasingList() {
            IntLinkedList list = createList(25,17,15,3,1);
            list.sort();
            assertEquals(createList(1,3,15,17,25), list);
        }

        @DisplayName("WHEN we sort a list with the same element repeated, "
                + "THEN it is sorted correctly.")
        @Test
        public void testSameElementList() {
            IntLinkedList list = createList(3,3,3,3);
            list.sort();
            assertEquals(createList(3,3,3,3), list);
        }

        @DisplayName("WHEN we sort a large list, THEN it is sorted correctly.")
        @Test
        public void testLargeList() {
            IntLinkedList list = createList(1,10,7,4,6,28,3,15,17,25,3,1,4);
            list.sort();
            assertEquals(createList(1,1,3,3,4,4,6,7,10,15,17,25,28), list);
        }

    }

    abstract class MergeTest {
        // Use the template method pattern so we can test both merge implementations
        public abstract Node<Integer> performMerge(Node<Integer> head1, Node<Integer> head2);

        public void assertListEquals(IntLinkedList expected, Node<Integer> actualHead) {
            IntLinkedList actual = new IntLinkedList();
            if (actualHead == null || actualHead.data == null) {
                assertEquals(expected, actual);
                return;
            }
            Node<Integer> current = actualHead;
            while (current.data != null) {
                actual.add(current.data);
                current = current.next;
            }
            assertEquals(expected, actual);
        }

        @DisplayName("WHEN we merge two empty lists, THEN we get the empty list.")
        @Test
        public void testTwoEmptyLists() {
            IntLinkedList list1 = createList();
            IntLinkedList list2 = createList();

            Node<Integer> mergedHead = performMerge(list1.head, list2.head);
            assertListEquals(createList(), mergedHead);
        }

        @DisplayName("WHEN the first list is empty AND the second list is non-empty, "
                + "THEN the merged list is the same as the second list.")
        @Test
        public void testEmptyList1() {
            IntLinkedList list1 = createList();
            IntLinkedList list2 = createList(1,2,3);

            Node<Integer> mergedHead = performMerge(list1.head, list2.head);
            assertListEquals(list2, mergedHead);
        }

        @DisplayName("WHEN the first list is non-empty AND the second list is empty, "
                + "THEN the merged list is the same as the first list.")
        @Test
        public void testEmptyList2() {
            IntLinkedList list1 = createList(1,2,3);
            IntLinkedList list2 = createList();

            Node<Integer> mergedHead = performMerge(list1.head, list2.head);
            assertListEquals(list1, mergedHead);
        }

        @DisplayName("WHEN  both lists are non-empty and their elements interweave, " 
                + "THEN the merged list contains both of their elements in succession.")
        @Test
        public void testInterweavingLists() {
            IntLinkedList list1 = createList(1,3,5);
            IntLinkedList list2 = createList(2,4);

            Node<Integer> mergedHead = performMerge(list1.head, list2.head);
            assertListEquals(createList(1,2,3,4,5), mergedHead);
        }

        @DisplayName("WHEN all elements of the first list are less than or equal to " 
                + "the elements in the second list, THEN the merged list contains the "
                + "elements of the first list followed by the elements of the second list.")
        @Test
        public void testList1AllSmaller() {
            IntLinkedList list1 = createList(1,2,6);
            IntLinkedList list2 = createList(6,7);

            Node<Integer> mergedHead = performMerge(list1.head, list2.head);
            assertListEquals(createList(1,2,6,6,7), mergedHead);
        }

        @DisplayName("WHEN all elements of the second list are less than or equal to " 
                + "the elements in the first list, THEN the merged list contains the "
                + "elements of the second list followed by the elements of the first list.")
        @Test
        public void testList2AllSmaller() {
            IntLinkedList list1 = createList(6,7);
            IntLinkedList list2 = createList(1,2,6);

            Node<Integer> mergedHead = performMerge(list1.head, list2.head);
            assertListEquals(createList(1,2,6,6,7), mergedHead);
        }
    }

    @DisplayName("merge()")
    @Nested
    class MergeIterativeTest extends MergeTest {
        @Override
        public Node<Integer> performMerge(Node<Integer> head1, Node<Integer> head2) {
            return IntLinkedList.merge(head1, head2);
        }
    }

    // TODO: Uncomment this inner test class if you complete the bonus challenge.
//    @DisplayName("mergeRecursive()")
//    @Nested
//    class MergeRecursiveTest extends MergeTest {
//        @Override
//        public Node<Integer> performMerge(Node<Integer> head1, Node<Integer> head2) {
//            return IntLinkedList.mergeRecursive(head1, head2);
//        }
//    }
}
