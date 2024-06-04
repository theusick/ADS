package ru.ads.orderedlist;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class OrderedListTest {

    private static final Integer[] EMPTY_ARRAY = new Integer[]{};
    private static final Integer[] ONE_ELEM_ARRAY = new Integer[]{2};
    private static final Integer[] MANY_ELEMS_ARRAY = new Integer[]{-10, 0, 4, 5, 0, 0, 4, 3, 5, -10};
    private static final String[] MANY_STRINGS_ARRAY = new String[]{"a", "f", "Joe", "Mary"};

    @Test
    void testCompare() {
        OrderedList<Integer> list = new OrderedList<>(true);

        assertThrows(IllegalArgumentException.class, () -> list.compare(null, null));

        assertEquals(1, list.compare(0, -1));
        assertEquals(1, list.compare(1000, -1000));
        assertEquals(1, list.compare(151, 150));

        assertEquals(-1, list.compare(1, 2));
        assertEquals(-1, list.compare(10, 11));
        assertEquals(-1, list.compare(-10000, 99));

        assertEquals(0, list.compare(0, 0));
        assertEquals(0, list.compare(10, 10));
        assertEquals(0, list.compare(100, 100));

        OrderedList<String> listStrings = new OrderedList<>(true);

        assertThrows(IllegalArgumentException.class, () -> listStrings.compare(null, null));

        assertEquals(1, listStrings.compare("clang", "brand"));
        assertEquals(1, listStrings.compare("go", "asdj123oad"));
        assertEquals(1, listStrings.compare("bad", "apqwopr"));

        assertEquals(-1, listStrings.compare("AAng", "aang"));
        assertEquals(-1, listStrings.compare("Denver", "denver"));
        assertEquals(-1, listStrings.compare("moo", "zoo"));

        assertEquals(0, listStrings.compare("abcd", "abcd"));
        assertEquals(0, listStrings.compare("", ""));
        assertEquals(0, listStrings.compare("good", "good"));
    }

    @Test
    void testAdd() {
        OrderedList<Integer> list = generateOrderedList(EMPTY_ARRAY, true);
        list.add(1); // { 1 }
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.value);
        assertNull(list.head.prev);
        assertNull(list.head.next);
        assertNull(list.tail.prev);
        assertNull(list.tail.next);

        list.add(10);  // { 1, 10 }
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.prev.value);
        assertEquals(10, list.tail.value);
        assertEquals(10, list.head.next.value);

        list.add(-10);  // { -10, 1, 10 }
        assertEquals(-10, list.head.value);
        assertEquals(-10, list.head.next.prev.value);
        assertEquals(1, list.tail.prev.value);
        assertEquals(10, list.tail.value);
        assertEquals(10, list.tail.prev.next.value);
        assertEquals(1, list.head.next.value);

        list.add(2);  // { -10, 1, 2, 10 }
        assertEquals(-10, list.head.value);
        assertEquals(1, list.head.next.value);
        assertEquals(2, list.head.next.next.value);
        assertEquals(10, list.head.next.next.next.value);
        assertEquals(10, list.tail.value);
        assertEquals(2, list.tail.prev.value);
        assertEquals(1, list.tail.prev.prev.value);
        assertEquals(-10, list.tail.prev.prev.prev.value);
        assertNull(list.head.prev);
        assertNull(list.tail.next);

        list = generateOrderedList(ONE_ELEM_ARRAY, false);
        list.add(4); // { 4, 2 }
        assertEquals(4, list.head.value);
        assertEquals(4, list.tail.prev.value);
        assertEquals(2, list.tail.value);
        assertEquals(2, list.head.next.value);

        list.add(-100); // { 4, 2, -100 }
        assertEquals(4, list.head.value);
        assertEquals(4, list.head.next.prev.value);
        assertEquals(-100, list.tail.value);
        assertEquals(-100, list.tail.prev.next.value);
        assertEquals(2, list.tail.prev.value);

        list.add(3); // { 4, 3, 2, -100 }
        assertEquals(4, list.head.value);
        assertEquals(3, list.head.next.value);
        assertEquals(2, list.head.next.next.value);
        assertEquals(-100, list.head.next.next.next.value);
        assertEquals(-100, list.tail.value);
        assertEquals(2, list.tail.prev.value);
        assertEquals(3, list.tail.prev.prev.value);
        assertEquals(4, list.tail.prev.prev.prev.value);
        assertNull(list.head.prev);
        assertNull(list.tail.next);

        // { "Joe", "Mary", "a", "f" }
        OrderedList<String> listStrings = generateOrderedList(MANY_STRINGS_ARRAY, true);
        listStrings.add("z"); // { "Joe", "Mary", "a", "f", "z" }
        assertEquals("z", listStrings.tail.value);
        assertEquals("f", listStrings.tail.prev.value);

        listStrings.add("Abby"); // { "Abby", "Joe", "Mary", "a", "f", "z" }
        assertEquals("Abby", listStrings.head.value);
        assertEquals("Joe", listStrings.head.next.value);

        listStrings.add("Bill"); // { "Abby", "Bill", "Joe", "Mary", "a", "f", "z" }
        assertEquals("Abby", listStrings.head.value);
        assertEquals("Bill", listStrings.head.next.value);
        assertEquals("Joe", listStrings.head.next.next.value);
        assertEquals("Bill", listStrings.head.next.next.prev.value);
    }

    @Test
    void testFind() {
        OrderedList<Integer> list = generateOrderedList(EMPTY_ARRAY, true);
        assertNull(list.find(99));

        // { -10, -10, 0, 0, 0, 3, 4, 4, 5, 5 }
        list = generateOrderedList(MANY_ELEMS_ARRAY, true);
        assertNull(list.find(100));
        assertEquals(list.head, list.find(-10));
        assertEquals(list.tail.prev, list.find(5));
        assertEquals(list.head.next.next, list.find(0));
        assertEquals(3, list.find(3).value);

        // { "f", "a", "Mary", "Joe" }
        OrderedList<String> listStrings = generateOrderedList(MANY_STRINGS_ARRAY, false);
        assertNull(listStrings.find("Bill"));
        assertEquals(listStrings.head, listStrings.find("f"));
        assertEquals(listStrings.tail, listStrings.find("Joe"));
        assertEquals(listStrings.tail.prev, listStrings.find("Mary"));
    }

    @Test
    void testDelete() {
        OrderedList<Integer> list = generateOrderedList(EMPTY_ARRAY, true);
        list.delete(19);
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        // { -10, -10, 0, 0, 0, 3, 4, 4, 5, 5 }
        list = generateOrderedList(MANY_ELEMS_ARRAY, true);
        list.delete(5);
        assertEquals(9, list.count());
        assertEquals(4, list.tail.prev.value);
        assertEquals(5, list.tail.value);

        // { -10, -10, 0, 0, 0, 3, 4, 4, 5 }
        list.delete(5);
        assertEquals(8, list.count());
        assertEquals(4, list.tail.value);
        assertEquals(4, list.tail.prev.value);
        assertEquals(4, list.tail.prev.next.value);
        assertNull(list.tail.next);

        // { -10, 0, 0, 0, 3, 4, 4, 5 }
        list.delete(-10);
        assertEquals(7, list.count());
        assertEquals(-10, list.head.value);
        assertEquals(0, list.head.next.value);
        assertEquals(-10, list.head.next.prev.value);
        assertNull(list.head.prev);

        // { -10, 0, 0, 0, 4, 4, 5 }
        list.delete(3);
        assertEquals(6, list.count());
        assertEquals(4, list.head.next.next.next.next.value);
        assertEquals(0, list.tail.prev.prev.prev.value);
    }

    @Test
    void testClear() {
        OrderedList<Integer> list = generateOrderedList(EMPTY_ARRAY, true);
        list.clear(true);
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        list = generateOrderedList(ONE_ELEM_ARRAY, false);
        list.clear(false);
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        list = generateOrderedList(MANY_ELEMS_ARRAY, true);
        list.clear(true);
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    void testCount() {
        OrderedList<Integer> list = generateOrderedList(EMPTY_ARRAY, true);
        assertEquals(0, list.count());

        list = generateOrderedList(ONE_ELEM_ARRAY, false);
        assertEquals(1, list.count());
        list.delete(2);
        assertEquals(0, list.count());

        list = generateOrderedList(MANY_ELEMS_ARRAY, true);
        assertEquals(10, list.count());
        list.delete(-10);
        list.delete(-10);
        assertEquals(MANY_ELEMS_ARRAY.length - 2, list.count());
    }

    @Test
    void testGetAll() {
        OrderedList<Integer> list = generateOrderedList(EMPTY_ARRAY, true);
        assertTrue(list.getAll().isEmpty());

        list = generateOrderedList(ONE_ELEM_ARRAY, true);
        ArrayList<Node<Integer>> arrayList = list.getAll();
        ArrayList<Node<Integer>> finalArrayList = arrayList;
        assertEquals(ONE_ELEM_ARRAY.length, finalArrayList.size());
        assertTrue(IntStream.range(0, arrayList.size())
                .allMatch(i -> Objects.equals(ONE_ELEM_ARRAY[i], finalArrayList.get(i).value)));

        // { 5, 5, 4, 4, 3, 0, 0, 0, -10, -10 }
        list = generateOrderedList(MANY_ELEMS_ARRAY, false);
        int[] sortedArray = new int[]{5, 5, 4, 4, 3, 0, 0, 0, -10, -10};
        arrayList = list.getAll();
        ArrayList<Node<Integer>> finalArrayList1 = arrayList;
        assertEquals(sortedArray.length, finalArrayList1.size());
        assertTrue(IntStream.range(0, arrayList.size())
                .allMatch(i -> Objects.equals(sortedArray[i], finalArrayList1.get(i).value)));
    }

    private <T> OrderedList<T> generateOrderedList(T[] data, boolean asc) {
        OrderedList<T> list = new OrderedList<>(asc);
        for (T value : data) {
            list.add(value);
        }
        return list;
    }
}