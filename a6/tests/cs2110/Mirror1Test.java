package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ListUtilities.isMirrored1 (CS2110List)")
public class Mirror1Test {

    /** Helper: build a CS2110List from varargs using your DoublyLinkedList. */
    private static <T> CS2110List<T> listOf(T... elems) {
        CS2110List<T> L = new DoublyLinkedList<>();
        for (T e : elems) L.add(e);
        return L;
    }

    @Nested @DisplayName("true cases")
    class TrueCases {
        @Test @DisplayName("empty list is mirrored")
        void empty() {
            CS2110List<Integer> L = listOf();
            assertTrue(ListUtilities.isMirrored1(L));
        }

        @Test @DisplayName("single element is mirrored")
        void single() {
            CS2110List<String> L = listOf("x");
            assertTrue(ListUtilities.isMirrored1(L));
        }

        @Test @DisplayName("[1,2,3,2,1] is mirrored")
        void oddLength() {
            CS2110List<Integer> L = listOf(1,2,3,2,1);
            assertTrue(ListUtilities.isMirrored1(L));
        }

        @Test @DisplayName("[a,b,b,a] is mirrored")
        void evenLength() {
            CS2110List<String> L = listOf("a","b","b","a");
            assertTrue(ListUtilities.isMirrored1(L));
        }

        @Test @DisplayName("uses equals(), not reference identity")
        void usesEquals() {
            String s1 = new String("banana");
            String s2 = new String("banana");
            CS2110List<String> L = listOf(s1, s2);
            assertTrue(ListUtilities.isMirrored1(L)); // ["banana","banana"]
        }
    }

    @Nested @DisplayName("false cases")
    class FalseCases {
        @Test @DisplayName("[1,2] not mirrored")
        void twoDifferent() {
            CS2110List<Integer> L = listOf(1,2);
            assertFalse(ListUtilities.isMirrored1(L));
        }

        @Test @DisplayName("[1,2,3] not mirrored")
        void threeDifferent() {
            CS2110List<Integer> L = listOf(1,2,3);
            assertFalse(ListUtilities.isMirrored1(L));
        }

        @Test @DisplayName("[x,y,x,y] not mirrored")
        void alternating() {
            CS2110List<String> L = listOf("x","y","x","y");
            assertFalse(ListUtilities.isMirrored1(L));
        }
    }

    @Test
    @DisplayName("function does not mutate the list")
    void notMutating() {
        CS2110List<Integer> L = listOf(1,2,3,2,1);
        int n = L.size();
        assertTrue(ListUtilities.isMirrored1(L));
        assertEquals(n, L.size());
        // spot check contents preserved
        assertEquals(1, L.get(0));
        assertEquals(1, L.get(n-1));
    }
}