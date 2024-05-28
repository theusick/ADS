package ru.ads.dynarray;

import java.lang.reflect.Array;

public class DynArray<T> {
    public final static int MIN_CAPACITY = 16;
    public final static int INCREASE_COEF = 2;
    public final static float DECREASE_COEF = 1.5f;
    public final static int DECREASE_CHECK_VALUE = 2;

    public T[] array;
    public int count;
    public int capacity;
    Class clazz;

    public DynArray(Class clz) {
        clazz = clz;
        count = 0;
        makeArray(MIN_CAPACITY);
    }

    public void makeArray(int new_capacity) {
        T[] newArray = (T[]) Array.newInstance(clazz, new_capacity);
        if (array != null) {
            System.arraycopy(array, 0, newArray, 0, count);
        }
        array = newArray;
        capacity = new_capacity;
    }

    public T getItem(int index) {
        checkIndexRangeExclusive(index);
        return array[index];
    }

    public void append(T itm) {
        expandArray();
        array[count++] = itm;
    }

    /**
     * checkIndexRangeInclusive = O(1)
     * expandArray = O(n)
     * System.arraycopy = O(n)
     * insert = O(n) upper bound. O(1) average
     */
    public void insert(T itm, int index) {
        checkIndexRangeInclusive(index);
        expandArray();
        System.arraycopy(array, index, array, index + 1, count - index);
        array[index] = itm;
        count++;
    }

    /**
     * checkIndexRangeExclusive = O(1)
     * System.arraycopy = O(n)
     * decreaseArray = O(n)
     * remove = O(n) upper bound. O(1) average
     */
    public void remove(int index) {
        checkIndexRangeExclusive(index);
        System.arraycopy(array, index + 1, array, index, count - index - 1);
        count--;
        decreaseArray();
    }

    private void checkIndexRangeExclusive(int index) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException("Index '" + index + "' is out of range!");
        }
    }

    private void checkIndexRangeInclusive(int index) {
        if ((index < 0) || (index > count)) {
            throw new IndexOutOfBoundsException("Index '" + index + "' is out of range!");
        }
    }

    private void expandArray() {
        if (count >= capacity) {
            makeArray(capacity * INCREASE_COEF);
        }
    }

    private void decreaseArray() {
        if (DECREASE_CHECK_VALUE * count >= capacity) {
            return;
        }

        int newCapacity = (int) (capacity / DECREASE_COEF);
        if (newCapacity < MIN_CAPACITY) {
            newCapacity = MIN_CAPACITY;
        }
        if (capacity != newCapacity) {
            makeArray(newCapacity);
        }
    }

}


