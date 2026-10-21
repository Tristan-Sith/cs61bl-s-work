package deque;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A double-ended queue backed by a circular array.
 *
 * @param <T> the type of elements stored in this deque
 */
public class ArrayDeque61B<T> implements Deque61B<T> {
    private static final int INITIAL_CAPACITY = 8;

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int modCount;

    @SuppressWarnings("unchecked")
    public ArrayDeque61B() {
        items = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        nextFirst = 0;
        nextLast = 0;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        nextFirst = decrement(nextFirst);
        items[nextFirst] = x;
        size++;
        modCount++;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = x;
        nextLast = increment(nextLast);
        size++;
        modCount++;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(items[indexToArrayPosition(i)]);
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T removed = items[nextFirst];
        items[nextFirst] = null;
        nextFirst = increment(nextFirst);
        size--;
        modCount++;
        resizeIfNeeded();
        return removed;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        nextLast = decrement(nextLast);
        T removed = items[nextLast];
        items[nextLast] = null;
        size--;
        modCount++;
        resizeIfNeeded();
        return removed;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[indexToArrayPosition(index)];
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursive(index, nextFirst);
    }

    private T getRecursive(int remaining, int position) {
        if (remaining == 0) {
            return items[position];
        }
        return getRecursive(remaining - 1, increment(position));
    }

    private int indexToArrayPosition(int index) {
        return (nextFirst + index) % items.length;
    }

    private int increment(int index) {
        return index + 1 == items.length ? 0 : index + 1;
    }

    private int decrement(int index) {
        return index == 0 ? items.length - 1 : index - 1;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        T[] resized = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            resized[i] = items[indexToArrayPosition(i)];
        }
        items = resized;
        nextFirst = 0;
        nextLast = size;
    }

    private void resizeIfNeeded() {
        if (items.length > INITIAL_CAPACITY && size < items.length / 4) {
            resize(items.length / 2);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Deque61B<?> other) || size != other.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(get(i), other.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < size; i++) {
            result = 31 * result + Objects.hashCode(get(i));
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(get(i));
        }
        return result.append(']').toString();
    }

    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<T> {
        private int position = nextFirst;
        private int remaining = size;
        private final int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            checkForComodification();
            return remaining > 0;
        }

        @Override
        public T next() {
            checkForComodification();
            if (remaining == 0) {
                throw new NoSuchElementException();
            }
            T item = items[position];
            position = increment(position);
            remaining--;
            return item;
        }

        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
