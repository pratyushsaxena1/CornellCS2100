package cs2110;

/**
 * Models CS 2110's ADT for a list containing elements of type `T`.
 */
public interface CS2110List<T> {

    /**
     * Adds the given `elem` to the end of this list. Requires that `elem` is not `null`.
     */
    void add(T elem);

    /**
     * Inserts the given `elem` at the given `index` in this list, shifting all later Strings one
     * index to the right to make space. Requires that `0 <= index <= size()` and `elem` is not
     * `null`.
     */
    void insert(int index, T elem);

    /**
     * Returns the current number of element in this list.
     */
    int size();

    /**
     * Returns the `T` element located at the given `index` in this list. Requires that `0 <= index
     * < size()`.
     */
    T get(int index);

    /**
     * Returns whether `elem` is stored in this list.
     */
    boolean contains(T elem);

    /**
     * Returns the smallest index `i` at which `elem` is stored in this list. Requires that
     * `contains(elem)` is true.
     */
    int indexOf(T elem);

    /**
     * Reassign the entry at the given `index` in this list to store `elem`. Requires that `0 <=
     * index < size()` and `elem` is not `null`.
     */
    void set(int index, T elem);

    /**
     * Removes and returns the element stored at the given `index` in this list, shifting all later
     * elements left to close the gap left by the removal. Requires that `0 <= index < size()`.
     */
    T remove(int index);

    /**
     * Removes the first instance of `elem` from this list, shifting all later elements left to
     * close the gap left by the removal. Requires that `contains(elem)` is true.
     */
    void delete(T elem);
}
