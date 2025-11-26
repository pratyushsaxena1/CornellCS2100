package graph;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A min priority queue of distinct elements of type `KeyType` associated with (extrinsic) double
 * priorities. Supports updating the priorities of elements currently in the queue, and guarantees
 * O(log N) performance for all modifying operations, where N is the queue size.
 */
public class MinPQueue<KeyType> {

    /**
     * Pairs an element `key` with its associated priority `priority`.
     */
    private record Entry<KeyType>(KeyType key, double priority) {

    }

    /**
     * ArrayList representing a binary min-heap of element-priority pairs.  Satisfies
     * `heap.get(i).priority() >= heap.get((i-1)/2).priority()` for all `i` in `[1..heap.size())`.
     */
    private final ArrayList<Entry<KeyType>> heap;

    /**
     * Associates each element in the queue with its index in `heap`.  Satisfies
     * `heap.get(index.get(e)).key().equals(e)` if `e` is an element in the queue. Only maps
     * elements that are in the queue (`index.size() == heap.size()`).
     */
    private final PacMap<KeyType, Integer> index;


    /**
     * Create an empty queue.
     */
    public MinPQueue() {
        index = new ProbingPacMap<>();
        heap = new ArrayList<>();
    }

    /**
     * Return whether this queue contains no elements.
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Return the number of elements contained in this queue.
     */
    public int size() {
        return heap.size();
    }

    /**
     * Return an element associated with the smallest priority in this queue.  This is the same
     * element that would be removed by a call to `remove()` (assuming no mutations in between).
     * Throws a `NoSuchElementException` if this queue is empty.
     */
    public KeyType peek() {
        // Propagate exception from `List::getFirst()` if empty.
        return heap.getFirst().key();
    }

    /**
     * Return the minimum priority associated with an element in this queue.  Throws a
     * `NoSuchElementException` if this queue is empty.
     */
    public double minPriority() {
        return heap.getFirst().priority();
    }

    /**
     * Swap the `Entry`s at indices `i` and `j` in `heap`, updating `index` accordingly.  Requires
     * `0 <= i,j < heap.size()`.
     */
    private void swap(int i, int j) {
        Entry<KeyType> entryAtI = heap.get(i);
        Entry<KeyType> entryAtJ = heap.get(j);
        heap.set(i, entryAtJ);
        heap.set(j, entryAtI);
        index.put(entryAtI.key(), j);
        index.put(entryAtJ.key(), i);
    }

    /**
     * Restores the heap invariant by moving the element at index `i` up as long as its priority is
     * less than that of its parent. Requires that 0 <= i < heap.size().
     */

    private void bubbleUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap.get(i).priority() < heap.get(parent).priority()) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    /**
     * Restores the heap invariant by moving the element at index `i` down by swapping it with the
     * smaller child as long as its priority is more than that of the child. Requires that 0 <= i <
     * heap.size().
     */

    private void bubbleDown(int i) {
        int heapSize = heap.size();
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left >= heapSize) {
                break;
            }
            int smallest = left;
            if (right < heapSize && heap.get(right).priority() < heap.get(left).priority()) {
                smallest = right;
            }
            if (heap.get(i).priority() <= heap.get(smallest).priority()) {
                break;
            }
            swap(i, smallest);
            i = smallest;
        }
    }

    /**
     * Add element `key` to this queue, associated with priority `priority`.  Requires `key` is not
     * contained in this queue.
     */
    private void add(KeyType key, double priority) {
        assert !index.containsKey(key);
        int i = heap.size();
        heap.add(new Entry<>(key, priority));
        index.put(key, i);
        bubbleUp(i);
    }

    /**
     * Change the priority associated with element `key` to `priority`.  Requires that `key` is
     * contained in this queue.
     */
    private void update(KeyType key, double priority) {
        assert index.containsKey(key);
        int i = index.get(key);
        heap.set(i, new Entry<>(key, priority));
        if (i > 0 && heap.get(i).priority() < heap.get((i - 1) / 2).priority()) {
            bubbleUp(i);
        } else {
            bubbleDown(i);
        }
    }

    /**
     * If `key` is already contained in this queue, change its associated priority to `priority`.
     * Otherwise, add it to this queue with that priority.
     */
    public void addOrUpdate(KeyType key, double priority) {
        if (!index.containsKey(key)) {
            add(key, priority);
        } else {
            update(key, priority);
        }
    }

    /**
     * Remove and return the element associated with the smallest priority in this queue.  If
     * multiple elements are tied for the smallest priority, an arbitrary one will be removed.
     * Throws NoSuchElementException if this queue is empty.
     */
    public KeyType remove() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException();
        }
        Entry<KeyType> minimum = heap.get(0);
        KeyType minimumKey = minimum.key();
        int lastIndex = heap.size() - 1;
        Entry<KeyType> last = heap.remove(lastIndex);
        index.remove(minimumKey);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            index.put(last.key(), 0);
            bubbleDown(0);
        }
        return minimumKey;
    }

}
