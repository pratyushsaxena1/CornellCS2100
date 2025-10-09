package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ListUtilities.isMirrored2 (DoublyLinkedList)")
public class Mirror2Test {

    /** Helper: build a DoublyLinkedList from varargs. */
    private static <T> DoublyLinkedList<T> listOf(T... elems) {
        DoublyLinkedList<T> L = new DoublyLinkedList<>();
        for (T e : elems) L.add(e);
        return L;
    }

    @Nested
    @DisplayName("true cases")
    class TrueCases {
        @Test
        @DisplayName("empty list is mirrored")
        void empty() {
            DoublyLinkedList<Integer> L = listOf();
            assertTrue(ListUtilities.isMirrored2(L));
        }

        @Test
        @DisplayName("single element is mirrored")
        void single() {
            DoublyLinkedList<String> L = listOf("x");
            assertTrue(ListUtilities.isMirrored2(L));
        }

        @Test
        @DisplayName("[1,2,3,2,1] is mirrored")
        void oddLength() {
            DoublyLinkedList<Integer> L = listOf(1, 2, 3, 2, 1);
            assertTrue(ListUtilities.isMirrored2(L));
        }

        @Test
        @DisplayName("[a,b,b,a] is mirrored")
        void evenLength() {
            DoublyLinkedList<String> L = listOf("a", "b", "b", "a");
            assertTrue(ListUtilities.isMirrored2(L));
        }

        @Test
        @DisplayName("uses equals(), not reference identity")
        void usesEquals() {
            String s1 = new String("aa");
            String s2 = new String("aa");
            DoublyLinkedList<String> L = listOf(s1, s2);
            assertTrue(ListUtilities.isMirrored2(L));
        }
    }

    @Nested
    @DisplayName("false cases")
    class FalseCases {
        @Test
        @DisplayName("[1,2] not mirrored")
        void twoDifferent() {
            DoublyLinkedList<Integer> L = listOf(1, 2);
            assertFalse(ListUtilities.isMirrored2(L));
        }

        @Test
        @DisplayName("[1,2,3] not mirrored")
        void threeDifferent() {
            DoublyLinkedList<Integer> L = listOf(1, 2, 3);
            assertFalse(ListUtilities.isMirrored2(L));
        }

        @Test
        @DisplayName("[x,y,x,y] not mirrored")
        void alternating() {
            DoublyLinkedList<String> L = listOf("x", "y", "x", "y");
            assertFalse(ListUtilities.isMirrored2(L));
        }
    }

    @Test
    @DisplayName("function does not mutate the list")
    void notMutating() {
        DoublyLinkedList<Integer> L = listOf(1, 2, 3, 2, 1);
        int n = L.size();
        assertTrue(ListUtilities.isMirrored2(L));
        assertEquals(n, L.size());
        // spot check contents preserved
        assertEquals(1, L.get(0));
        assertEquals(1, L.get(n - 1));
    }
}