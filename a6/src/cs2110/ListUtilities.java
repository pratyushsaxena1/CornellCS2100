package cs2110;

import java.util.Iterator;

/**
 * Contains static methods for checking whether a list is mirrored.
 */
public class ListUtilities {

    /**
     * Returns whether this list is "mirrored" meaning it produces the same sequence of elements
     * when iterated in the forward or reverse directions.
     */
    public static <T> boolean isMirrored1(CS2110List<T> list) {
        int n = list.size();
        for (int i = 0; i < n / 2; i++) {
            T front = list.get(i);
            T back = list.get(n - 1 - i);
            if (front == null || back == null) {
                return false;
            }
            if (!front.equals(back)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether this list is "mirrored" meaning it produces the same sequence of elements
     * when iterated in the forward or reverse directions.
     */
    public static <T> boolean isMirrored2(DoublyLinkedList<T> list) {
        Iterator<T> fwd = list.iterator();
        Iterator<T> rev = list.reverseIterator();
        while (fwd.hasNext() && rev.hasNext()) {
            T a = fwd.next();
            T b = rev.next();
            if (a == null || b == null) return false;
            if (!a.equals(b)) return false;
        }
        return !fwd.hasNext() && !rev.hasNext();
    }
}