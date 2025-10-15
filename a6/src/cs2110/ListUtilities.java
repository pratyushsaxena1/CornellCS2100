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
        int listSize = list.size();
        for (int i = 0; i < listSize / 2; i++) {
            T front = list.get(i);
            T back = list.get(listSize - 1 - i);
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
        Iterator<T> fwdIterator = list.iterator();
        Iterator<T> revIterator = list.reverseIterator();
        while (fwdIterator.hasNext() && revIterator.hasNext()) {
            T firstIterator = fwdIterator.next();
            T lastIterator = revIterator.next();
            if (firstIterator == null || lastIterator == null) {
                return false;
            }
            if (!firstIterator.equals(lastIterator)) {
                return false;
            }
        }
        return !fwdIterator.hasNext() && !revIterator.hasNext();
    }
}