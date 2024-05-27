package ru.ads.dynarray;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class DynArrayTest {

    private static final int[] ARRAY = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    void testMakeArray() {
        DynArray<Integer> array = new DynArray<>(Integer.class);
        assertNotNull(array.array);
        assertEquals(DynArray.MIN_CAPACITY, array.capacity);
        assertEquals(array.capacity, array.array.length);

        Arrays.stream(ARRAY).forEach(array::append);
        assertEquals(ARRAY.length, array.count);
        array.makeArray(DynArray.MIN_CAPACITY + 1);
        assertEquals(ARRAY.length, array.count);
        assertEquals(DynArray.MIN_CAPACITY + 1, array.capacity);
        assertEquals(array.capacity, array.array.length);
        assertTrue(IntStream.range(0, array.count).allMatch(i -> ARRAY[i] == array.array[i]));
    }

    @Test
    void testGetItem() {
        DynArray<Integer> array = new DynArray<>(Integer.class);
        assertThrows(IndexOutOfBoundsException.class, () -> array.getItem(0));

        array.append(1);
        assertEquals(1, array.count);
        assertEquals(1, array.getItem(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.getItem(1));

        assertThrows(IndexOutOfBoundsException.class, () -> array.getItem(-1));

        assertThrows(IndexOutOfBoundsException.class, () -> array.getItem(17));
    }

    @Test
    void testAppend() {
        DynArray<Integer> array = new DynArray<>(Integer.class);
        Arrays.stream(ARRAY).forEach(array::append);
        assertTrue(IntStream.range(0, ARRAY.length).allMatch(i -> ARRAY[i] == array.array[i]));

        Arrays.stream(ARRAY).forEach(array::append);
        assertEquals(DynArray.MIN_CAPACITY * DynArray.INCREASE_COEF, array.capacity);
        assertEquals(ARRAY.length * 2, array.count);
        assertTrue(IntStream.range(0, array.count)
                .allMatch(i -> ARRAY[i % ARRAY.length] == array.array[i]));
    }

    @Test
    void testInsert() {
        DynArray<Integer> array = new DynArray<>(Integer.class);
        Arrays.stream(ARRAY).forEach(array::append); // {1, 2, 3, 4, 5, 6, 7, 8, 9}

        array.insert(1, array.count); // {1, 2, 3, 4, 5, 6, 7, 8, 9, 1}
        assertEquals(ARRAY.length + 1, array.count);
        assertEquals(DynArray.MIN_CAPACITY, array.capacity);
        assertEquals(array.capacity, array.array.length);
        assertEquals(1, array.getItem(array.count - 1));

        int[] newArray = new int[]{1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        // {1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1}
        Arrays.stream(ARRAY).forEach((item) -> array.insert(item, 1));
        assertEquals(newArray.length, array.count);
        assertEquals(DynArray.MIN_CAPACITY * DynArray.INCREASE_COEF, array.capacity);
        assertTrue(IntStream.range(0, newArray.length)
                .allMatch(i -> newArray[i] == array.array[i]));

        assertThrows(IndexOutOfBoundsException.class,
                () -> array.insert(1, array.count + 1));
        assertThrows(IndexOutOfBoundsException.class,
                () -> array.insert(1, -1));
    }

    @Test
    void testRemove() {
        DynArray<Integer> array = new DynArray<>(Integer.class);
        Arrays.stream(ARRAY).forEach(array::append); // {1, 2, 3, 4, 5, 6, 7, 8, 9}

        array.remove(0); // {2, 3, 4, 5, 6, 7, 8, 9}
        assertEquals(ARRAY.length - 1, array.count);
        assertEquals(DynArray.MIN_CAPACITY, array.capacity);
        assertEquals(2, array.getItem(0));
        assertEquals(array.capacity, array.array.length);
        assertTrue(IntStream.range(0, array.count)
                .allMatch(i -> ARRAY[i + 1] == array.array[i]));

        array.remove(0); // {3, 4, 5, 6, 7, 8, 9}
        assertEquals(ARRAY.length - 2, array.count);
        assertEquals(DynArray.MIN_CAPACITY, array.capacity);
        assertEquals(array.capacity, array.array.length);
        assertEquals(3, array.getItem(0));

        // {3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        Arrays.stream(ARRAY).forEach(array::append);
        // {0, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9} 17
        array.insert(0, 0);
        assertEquals(ARRAY.length * 2 - 1, array.count);
        assertEquals(DynArray.MIN_CAPACITY * DynArray.INCREASE_COEF, array.capacity);

        // {3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9} 16
        array.remove(0);
        assertEquals(3, array.getItem(0));
        assertEquals(DynArray.MIN_CAPACITY * DynArray.INCREASE_COEF, array.capacity);

        // {4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9} 15
        array.remove(0);
        assertEquals(4, array.getItem(0));
        assertEquals(21, array.capacity);
        // {9, 1, 2, 3, 4, 5, 6, 7, 8, 9} 10
        for (int i = 0; i < 5; i++) {
            array.remove(0);
        }
        assertEquals(9, array.getItem(0));
        assertEquals(DynArray.MIN_CAPACITY, array.capacity);

        assertThrows(IndexOutOfBoundsException.class,
                () -> array.remove(array.count));
        assertThrows(IndexOutOfBoundsException.class,
                () -> array.remove(-1));
    }

}
