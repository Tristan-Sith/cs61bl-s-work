import java.util.Iterator;
import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V>  {
    @Override
    public void clear() {
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            bucket.clear();
        }
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return findEntry(key) != null;
    }

    @Override
    public V get(K key) {
        Entry<K, V> entry = findEntry(key);
        return entry == null ? null : entry.value;
    }

    @Override
    public void put(K key, V value) {
        Entry<K, V> entry = findEntry(key);
        if (entry != null) {
            entry.value = value;
            return;
        }
        buckets[indexOf(key)].add(new Entry<K, V>(key, value));
        size += 1;
        if (size > buckets.length * loadFactor) {
            resize(buckets.length * 2);
        }
    }

    @Override
    public V remove(K key) {
        Iterator<Entry<K, V>> entries = buckets[indexOf(key)].iterator();
        while (entries.hasNext()) {
            Entry<K, V> entry = entries.next();
            if (entry.key.equals(key)) {
                entries.remove();
                size -= 1;
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        Iterator<Entry<K, V>> entries = buckets[indexOf(key)].iterator();
        while (entries.hasNext()) {
            Entry<K, V> entry = entries.next();
            if (entry.key.equals(key) && entry.value.equals(value)) {
                entries.remove();
                size -= 1;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int bucketIndex = 0;
            private Iterator<Entry<K, V>> entries =
                    buckets.length == 0 ? null : buckets[0].iterator();

            @Override
            public boolean hasNext() {
                if (entries == null) {
                    return false;
                }
                while (!entries.hasNext() && bucketIndex < buckets.length - 1) {
                    bucketIndex += 1;
                    entries = buckets[bucketIndex].iterator();
                }
                return entries.hasNext();
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                return entries.next().key;
            }
        };
    }

    /* TODO: Instance variables here */
    private LinkedList<Entry<K, V>>[] buckets;
    private int size;
    private double loadFactor;

    /* TODO: Constructors here */
    public HashMap() {
        this(16);
    }

    public HashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    public HashMap(int initialCapacity, double loadFactor) {
        buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = new LinkedList<Entry<K, V>>();
        }
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    /* TODO: Interface methods here */
    /* Returns the number of buckets in this map. */
    public int capacity() {
        return buckets.length;
    }

    /* Returns the bucket that KEY belongs in, creating no new buckets. */
    private int indexOf(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    /* Returns the entry in this map whose key equals KEY, or null if there is
       no such entry. */
    private Entry<K, V> findEntry(K key) {
        for (Entry<K, V> entry : buckets[indexOf(key)]) {
            if (entry.key.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    /* Grows this map's bucket array to NEWCAPACITY and moves every existing
       entry into its new bucket. */
    private void resize(int newCapacity) {
        LinkedList<Entry<K, V>>[] oldBuckets = buckets;
        buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            buckets[i] = new LinkedList<Entry<K, V>>();
        }
        for (LinkedList<Entry<K, V>> bucket : oldBuckets) {
            for (Entry<K, V> entry : bucket) {
                buckets[indexOf(entry.key)].add(entry);
            }
        }
    }

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
