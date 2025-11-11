package cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A map with keys of type 'K' and values of type `V`, implemented using a hash table with linear
 * probing.
 */
public class ProbingPacMap<K, V> implements PacMap<K, V> {

    /**
     * Represents an association of a key `key` (of type `K`) with a value `value` (of type `V`).
     */
    private record Entry<K, V>(K key, V value) {

    }

    /**
     * Represents a tombstone. If an entry at index `i` is removed, element `i` will be replaced by
     * a reference to this object. Tombstones count toward the load factor, and are cleared when the
     * hash table is resized.
     */
    @SuppressWarnings("rawtypes")
    private static final Entry TOMBSTONE = new Entry<>(null, null);

    /**
     * The initial capacity of the hash table for new instances of `ProbingPacMap`.
     */
    private static final int INITIAL_CAPACITY = 16;

    /**
     * The maximum load factor (inclusive) that is allowed in the `entries` hash table. If the load
     * factor ever exceeds this maximum, then the hash table length must be immediately doubled to
     * reduce the load factor. Must have `0 < maxLoadFactor < 1`.
     */
    public static final double MAX_LOAD_FACTOR = 0.5;

    /**
     * The probing hash table backing this map. Indices (i.e., buckets) that don't currently store
     * an entry (possibly a TOMBSTONE) are `null`. If this map contains an entry with a key whose
     * hash code maps to index `i`, then the (unique) entry containing that key is reachable via
     * linear search starting at index `i` (wrapping around the array if necessary) without
     * encountering `null`.
     */
    private Entry<K, V>[] entries;
    /**
     * Number of live key–value pairs that currently in the map. Requires that 0 <= size <=
     * entries.length.
     */
    private int size;
    /**
     * Number of array slots that are not null (live entries plus tombstones). Requires that size <=
     * usedSlots <= entries.length.
     */
    private int usedSlots;

    /**
     * Create a new empty `ProbingPacMap`.
     */
    @SuppressWarnings("unchecked")
    public ProbingPacMap() {
        entries = new Entry[INITIAL_CAPACITY];
        size = 0;
        usedSlots = 0;
    }

    /**
     * Returns the number of keys currently associated with values in this map. Runs in O(1) time.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the current load factor of the hash table backing this map. Runs in O(1) time.
     */
    private double loadFactor() {
        return (double) usedSlots / entries.length;
    }

    /**
     * Returns what the starting index of the key should be.
     */
    private int startIndex(K key) {
        return Math.floorMod(key.hashCode(), entries.length);
    }

    /**
     * If `key` is a key in this map, return the index in `entries` for this key. Otherwise, returns
     * the first index of a `null` or tombstone entry in the table at or after the index
     * corresponding to the key's hash code (wrapping around).
     */
    private int findEntry(K key) {
        int entries_size = entries.length;
        int startIndex = startIndex(key);
        int firstTombstone = -1;
        for (int i = 0; i < entries_size; i++) {
            int index = (startIndex + i) % entries_size;
            Entry<K, V> entry = entries[index];
            if (entry == null) {
                if (firstTombstone != -1) {
                    return firstTombstone;
                }
                return index;
            } else if (entry == TOMBSTONE) {
                if (firstTombstone == -1) {
                    firstTombstone = index;
                }
            } else {
                if (entry.key.equals(key)) {
                    return index;
                }
            }
        }
        if (firstTombstone != -1) {
            return firstTombstone;
        }
        return startIndex;
    }

    /**
     * Returns whether this map contains the given `key`.
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        int index = findEntry(key);
        Entry<K, V> entry = entries[index];
        return entry != null && entry != TOMBSTONE && entry.key.equals(key);
    }

    /**
     * Returns the value associated with the given key or `null` if the key is not in the map.
     * Throws `NoSuchElementException` if the 'key' is not present.
     */
    @Override
    public V get(K key) {
        int index = findEntry(key);
        Entry<K, V> entry = entries[index];
        if (entry == null || entry == TOMBSTONE) {
            throw new NoSuchElementException();
        }
        return entry.value;
    }

    /**
     * Puts a key and value pair in the map. If `key` is already present, then replaces its value.
     * Resizes the table if the load factor is greater than the `MAX_LOAD_FACTOR`. Requires `key`
     * and `value` are not null.
     */
    @Override
    public void put(K key, V value) {
        int index = findEntry(key);
        Entry<K, V> entry = entries[index];
        if (entry == null) {
            entries[index] = new Entry<>(key, value);
            size += 1;
            usedSlots += 1;
        } else if (entry == TOMBSTONE) {
            entries[index] = new Entry<>(key, value);
            size += 1;
        } else {
            entries[index] = new Entry<>(key, value);
        }
        if (loadFactor() > MAX_LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Removes and returns the value that is associated with `key` in the map or `null` if the `key`
     * is not present. Throws `NoSuchElementException` if `key` is not present.
     */
    @Override
    @SuppressWarnings("unchecked")
    public V remove(K key) {
        int index = findEntry(key);
        Entry<K, V> entry = entries[index];
        if (entry == null || entry == TOMBSTONE) {
            throw new NoSuchElementException();
        }
        V oldValue = entry.value;
        entries[index] = (Entry<K, V>) TOMBSTONE;
        size -= 1;
        return oldValue;
    }

    /**
     * Doubles the table size and puts back all the live entries that were there before. Only
     * putting back the live entries clears the tombstones.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldEntries = entries;
        entries = new Entry[oldEntries.length * 2];
        size = 0;
        usedSlots = 0;
        for (Entry<K, V> entry : oldEntries) {
            if (entry != null && entry != TOMBSTONE) {
                int index = findEntry(entry.key);
                entries[index] = new Entry<>(entry.key, entry.value);
                size += 1;
                usedSlots += 1;
            }
        }
    }

    /**
     * Returns an iterator that iterates over the keys in the map.
     */
    @Override
    public Iterator<K> iterator() {
        return new ProbingPacMapIterator();
    }

    /**
     * An iterator over the keys in this hash table. This map must not be structurally modified
     * while any such iterators are alive.
     */
    private class ProbingPacMapIterator implements Iterator<K> {

        /**
         * The index of the entry in `entries` containing the next value to yield, or
         * `entries.length` if all values have been yielded.
         */
        private int iNext;

        /**
         * Create a new iterator over this dictionary's keys.
         */
        ProbingPacMapIterator() {
            iNext = 0;
            findNext();
        }

        /**
         * Set `iNext` to the first index `i` not less than the current value of `iNext` such that
         * `entries[i] != null` and 'entries[i] != TOMBSTONE', or set it to `entries.length` if
         * there are no remaining non-null and non-tombstone entries.  Note that if `iNext` is
         * already the index of a non-null and non-tombstone entry, then it will not be changed.
         */
        private void findNext() {
            while (iNext < entries.length && (entries[iNext] == null
                    || entries[iNext] == TOMBSTONE)) {
                iNext += 1;
            }
        }

        @Override
        public boolean hasNext() {
            return iNext < entries.length;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            K ans = entries[iNext].key;
            iNext += 1;
            findNext();
            return ans;
        }
    }
}