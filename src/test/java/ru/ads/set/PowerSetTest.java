package ru.ads.set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PowerSetTest {

    private static final int LARGE_ELEM_NUM = 20000;

    @Test
    void testSize() {
        PowerSet set = new PowerSet();
        assertEquals(0, set.size());

        set.put("1");
        assertEquals(1, set.size());

        set.put("2");
        set.put("2");
        assertEquals(2, set.size());

        set.remove("1");
        set.remove("1");
        assertEquals(1, set.size());

        set.remove("2");
        assertEquals(0, set.size());
    }

    @Test
    void testPut() {
        PowerSet set = new PowerSet();

        set.put("1");
        assertEquals(1, set.size());
        assertEquals("1", set.values.getFirst());

        set.put("2");
        set.put("2");
        assertEquals(2, set.size());
        assertEquals("2", set.values.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> set.values.get(2));

        set.put("apple");
        set.put("apple");
        set.put("apple");
        assertEquals(3, set.size());
        assertEquals("apple", set.values.get(2));
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testPutLargeSet() {
        PowerSet largeSet = generateLargeSet();
        assertEquals(LARGE_ELEM_NUM, largeSet.size());
    }

    @Test
    void testGet() {
        PowerSet set = new PowerSet();

        set.put("1");
        assertTrue(set.get("1"));
        assertFalse(set.get("apple"));

        set.put("2");
        set.put("2");
        assertTrue(set.get("2"));

        set.put("apple");
        assertTrue(set.get("apple"));
    }

    @Test
    void testRemove() {
        PowerSet set = new PowerSet();
        set.put("1");
        set.put("2");
        set.put("2");
        set.put("3");
        set.put("3");
        set.put("3");

        assertEquals(3, set.size());

        assertTrue(set.remove("3"));
        assertFalse(set.remove("3"));
        assertEquals(2, set.size());

        assertTrue(set.remove("2"));
        assertFalse(set.remove("2"));
        assertEquals(1, set.size());

        assertTrue(set.remove("1"));
        assertEquals(0, set.size());
    }

    @Test
    void testRemoveLargeSet() {
        PowerSet largeSet = generateLargeSet();
        long startTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < LARGE_ELEM_NUM; i++) {
            assertTrue(largeSet.remove("Elem: " + i));
        }
        long endTimeMillis = System.currentTimeMillis();
        assertTrue((endTimeMillis - startTimeMillis) < 2000L);
    }

    @Test
    void testIntersection() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        set1.put("c");

        PowerSet set2 = new PowerSet();
        set2.put("b");
        set2.put("c");
        set2.put("d");

        PowerSet resultSet = set1.intersection(set2);
        assertEquals(2, resultSet.size());
        assertTrue(resultSet.get("b"));
        assertTrue(resultSet.get("c"));

        PowerSet emptySet = new PowerSet();
        resultSet = set1.intersection(emptySet);
        assertEquals(0, resultSet.size());

        PowerSet set3 = new PowerSet();
        set2.put("e");
        set2.put("f");
        set2.put("g");
        resultSet = set1.intersection(set3);
        assertEquals(0, resultSet.size());

        resultSet = set2.intersection(set3);
        assertEquals(0, resultSet.size());

        resultSet = set1.intersection(set1);
        assertEquals(3, resultSet.size());
    }

    @Test
    void testIntersectionLargeSet() {
        PowerSet set1 = generateLargeSet();
        PowerSet set2 = new PowerSet();
        for (int i = LARGE_ELEM_NUM / 2; i < LARGE_ELEM_NUM + LARGE_ELEM_NUM / 2; i++) {
            set2.put("Elem: " + i);
        }

        long startTimeMillis = System.currentTimeMillis();
        PowerSet resultSet = set1.intersection(set2);
        long endTimeMillis = System.currentTimeMillis();
        assertTrue((endTimeMillis - startTimeMillis) < 2000L);

        assertEquals(LARGE_ELEM_NUM / 2, resultSet.size());
    }

    @Test
    void testUnion() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");

        PowerSet set2 = new PowerSet();
        set2.put("b");
        set2.put("c");

        PowerSet resultSet = set1.union(set2);
        assertEquals(3, resultSet.size());
        assertTrue(resultSet.get("a"));
        assertTrue(resultSet.get("b"));
        assertTrue(resultSet.get("c"));

        PowerSet emptySet = new PowerSet();
        resultSet = set1.union(emptySet);
        assertEquals(2, resultSet.size());
        assertTrue(resultSet.get("a"));
        assertTrue(resultSet.get("b"));

        resultSet = set1.union(set1);
        assertEquals(2, resultSet.size());
    }

    @Test
    void testUnionLargeSet() {
        PowerSet set1 = generateLargeSet();
        PowerSet set2 = new PowerSet();
        for (int i = LARGE_ELEM_NUM / 2; i < LARGE_ELEM_NUM + LARGE_ELEM_NUM / 2; i++) {
            set2.put("Elem: " + i);
        }

        long startTimeMillis = System.currentTimeMillis();
        PowerSet resultSet = set1.union(set2);
        long endTimeMillis = System.currentTimeMillis();
        assertTrue((endTimeMillis - startTimeMillis) < 2000L);

        assertEquals(LARGE_ELEM_NUM + LARGE_ELEM_NUM / 2, resultSet.size());
    }

    @Test
    void testDifference() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        set1.put("c");

        PowerSet set2 = new PowerSet();
        set2.put("b");
        set2.put("c");
        set2.put("d");

        PowerSet resultSet = set1.difference(set2);
        assertEquals(1, resultSet.size());
        assertTrue(resultSet.get("a"));

        PowerSet emptySet = new PowerSet();
        resultSet = set1.difference(emptySet);
        assertEquals(3, resultSet.size());
        assertTrue(resultSet.get("a"));
        assertTrue(resultSet.get("b"));
        assertTrue(resultSet.get("c"));

        resultSet = set1.difference(set1);
        assertEquals(0, resultSet.size());
    }

    @Test
    void testDifferenceLargeSet() {
        PowerSet set1 = generateLargeSet();
        PowerSet set2 = new PowerSet();
        for (int i = LARGE_ELEM_NUM / 2; i < LARGE_ELEM_NUM + LARGE_ELEM_NUM / 2; i++) {
            set2.put("Elem: " + i);
        }

        long startTimeMillis = System.currentTimeMillis();
        PowerSet resultSet = set1.difference(set2);
        long endTimeMillis = System.currentTimeMillis();
        assertTrue((endTimeMillis - startTimeMillis) < 2000L);

        assertEquals(LARGE_ELEM_NUM / 2, resultSet.size());
    }

    @Test
    void testIsSubset() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        set1.put("c");

        PowerSet set2 = new PowerSet();
        set2.put("a");
        set2.put("b");

        PowerSet set3 = new PowerSet();
        set3.put("a");
        set3.put("b");
        set3.put("c");
        set3.put("d");

        assertTrue(set1.isSubset(set2));
        assertFalse(set2.isSubset(set1));
        assertTrue(set1.isSubset(set1));
        assertFalse(set1.isSubset(set3));
    }

    @Test
    void testIsSubsetLargeSet() {
        PowerSet set1 = generateLargeSet();
        PowerSet set2 = new PowerSet();
        for (int i = 0; i < LARGE_ELEM_NUM / 2; i++) {
            set2.put("Elem: " + i);
        }

        long startTimeMillis = System.currentTimeMillis();
        boolean isSubset = set1.isSubset(set2);
        long endTimeMillis = System.currentTimeMillis();
        assertTrue((endTimeMillis - startTimeMillis) < 2000L);

        assertTrue(isSubset);
    }

    private PowerSet generateLargeSet() {
        PowerSet largeSet = new PowerSet();
        for (int i = 0; i < LARGE_ELEM_NUM; i++) {
            largeSet.put("Elem: " + i);
        }
        return largeSet;
    }

}
