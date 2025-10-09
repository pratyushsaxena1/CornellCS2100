package cs2110;

public class IntLinkedList extends SinglyLinkedList<Integer> {

    /**
     * Rearranges the entries of this list into sorted order.
     */
    public void sort() {
        head = mergeSort(head);
    }

    /**
     * Sorts this list using the Merge Sort algorithm. Returns the head of the sorted list.
     */
    private static Node<Integer> mergeSort(Node<Integer> head) {
        // TODO 3c: Implement this method *recursively* according to its specifications.
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the head of the merged list containing all nodes from the lists `head1`
     * and `head2`, in sorted order. Requires that `head1` and `head2` are sorted.
     */
    static Node<Integer> merge(Node<Integer> head1, Node<Integer> head2) {
        // TODO 2e: Implement this method *iteratively* according to its specifications.
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the head of the merged list containing all nodes from the lists `head1`
     * and `head2`, in sorted order. Requires that `head1` and `head2` are sorted.
     */
    static Node<Integer> mergeRecursive(Node<Integer> head1, Node<Integer> head2) {
        // TODO 2f: (Bonus Challenge) Implement this method *recursively* according to its
        //  specifications.
        throw new UnsupportedOperationException();
    }

    /**
     * Splits the linked list starting at `head` into two halves. Returns the head of the second
     * half. If the list has an odd number of nodes, the extra node goes in the first half.
     */
    static Node<Integer> split(Node<Integer> head) {
        Node<Integer> slow = head;
        Node<Integer> fast = head.next;

        while (fast.data != null) {
            fast = fast.next;
            if (fast.data != null) {
                fast = fast.next;
                slow = slow.next;
            }
        }

        Node<Integer> mid = slow.next;
        slow.next = new Node<>(null, null);

        return mid;
    }
}
