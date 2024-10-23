package ru.ads.tree;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HeapTest {

    private final Heap heap = new Heap();

    @Test
    public void testMakeHeapEmptyArray() {
        int[] input = {};

        heap.MakeHeap(input, 0);

        assertNotNull(heap.HeapArray);
        assertEquals(0, heap.HeapArray[0]);
    }

    @Test
    public void testMakeHeapSingleElement() {
        int[] input = {5};

        heap.MakeHeap(input, 0);

        assertNotNull(heap.HeapArray);
        assertEquals(5, heap.HeapArray[0]);
    }

    @Test
    public void testMakeHeapOddArray() {
        int[] input = {4, 2, 6, 1, 3, 5, 7};

        heap.MakeHeap(input, 2);

        assertNotNull(heap.HeapArray);
        assertArrayEquals(new int[]{7, 3, 6, 1, 2, 4, 5}, heap.HeapArray);
    }

    @Test
    public void testMakeHeapEvenArray() {
        int[] input = {3, 2, 1, 6, 7, 4, 5, 8};

        heap.MakeHeap(input, 3);

        assertNotNull(heap.HeapArray);
        assertArrayEquals(new int[]{8, 7, 5, 6, 3, 1, 4, 2, 0, 0, 0, 0, 0, 0, 0}, heap.HeapArray);
    }

    @Test
    public void testGetMaxFromEmptyHeap() {
        int[] input = {};

        heap.MakeHeap(input, 0);

        assertEquals(-1, heap.GetMax());
    }

    @Test
    public void testGetMaxFromOddHeap() {
        int[] input = {4, 2, 6, 1, 3, 5, 7};

        heap.MakeHeap(input, 2);

        assertEquals(7, heap.GetMax());
        assertArrayEquals(new int[]{6, 3, 5, 1, 2, 4, 0}, heap.HeapArray);
    }

    @Test
    public void testGetMaxFromEvenHeap() {
        int[] input = {3, 2, 1, 6, 7, 4, 5, 8};

        heap.MakeHeap(input, 3);

        assertEquals(8, heap.GetMax());
        assertArrayEquals(new int[]{7, 6, 5, 2, 3, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0}, heap.HeapArray);
    }

    @Test
    public void testAddToEmptyHeap() {
        int[] input = {};

        heap.MakeHeap(input, 0);

        assertTrue(heap.Add(1));
        assertArrayEquals(new int[]{1}, heap.HeapArray);
    }

    @Test
    public void testAddToFullHeap() {
        int[] input = {4, 2, 6, 1, 3, 5, 7};

        heap.MakeHeap(input, 2);

        assertFalse(heap.Add(9));
    }

    @Test
    public void testIsHeapEmptyArray() {
        heap.MakeHeap(new int[]{}, 0);
        assertTrue(heap.IsHeap());
    }

    @Test
    public void testIsHeapSingleElement() {
        heap.MakeHeap(new int[]{5}, 0);
        assertTrue(heap.IsHeap());
    }

    @Test
    public void testIsHeapValidHeap() {
        heap.MakeHeap(new int[]{7, 3, 6, 1, 2, 4, 5}, 2);
        assertTrue(heap.IsHeap());
    }

    @Test
    public void testIsHeapInvalidHeap() {
        heap.HeapArray = new int[]{1, 2, 3, 4, 5, 6, 7};
        assertFalse(heap.IsHeap());
    }

    @Test
    public void testIsHeapLargeValidHeap() {
        int[] input = {8, 7, 5, 6, 3, 1, 4, 2};
        heap.MakeHeap(input, 3);
        assertTrue(heap.IsHeap());
    }

    @Test
    public void testIsHeapLargeInvalidHeap() {
        heap.HeapArray = new int[]{1, 8, 9, 4, 7, 6, 5};
        assertFalse(heap.IsHeap());
    }

    @Test
    public void testGetMaxInRangeValidRange() {
        heap.MakeHeap(new int[]{10, 5, 3, 2, 8, 1, 9}, 2);

        int result = heap.GetMaxInRange(1, 5);
        assertEquals(9, result);
    }

    @Test
    public void testGetMaxInRangeSingleElementRange() {
        heap.MakeHeap(new int[]{15, 10, 12, 8, 9, 3}, 2);

        int result = heap.GetMaxInRange(3, 3);
        assertEquals(8, result);
    }

    @Test
    public void testGetMaxInRangeFullRange() {
        heap.MakeHeap(new int[]{20, 10, 15, 7, 8, 12}, 2);

        int result = heap.GetMaxInRange(0, 5);
        assertEquals(20, result);
    }

    @Test
    public void testGetMaxInRangeInvalidRange() {
        heap.MakeHeap(new int[]{10, 20, 30, 40, 50}, 2);

        assertNull(heap.GetMaxInRange(-1, 2));
        assertNull(heap.GetMaxInRange(3, 7));
        assertNull(heap.GetMaxInRange(5, 2));
    }

    @Test
    public void testFindElementsGreaterThanSingleElement() {
        int[] input = {10};
        heap.MakeHeap(input, 0);

        List<Integer> result = heap.FindElementsGreaterThan(5);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10, result.getFirst());
    }

    @Test
    public void testFindElementsGreaterThanMultipleElements() {
        int[] input = {20, 15, 10, 5};
        heap.MakeHeap(input, 2);

        List<Integer> result = heap.FindElementsGreaterThan(10);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(20));
        assertTrue(result.contains(15));
    }

    @Test
    public void testFindElementsGreaterThanNoMatch() {
        int[] input = {3, 2, 1};
        heap.MakeHeap(input, 1);

        List<Integer> result = heap.FindElementsGreaterThan(5);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindElementsGreaterThanEmptyHeap() {
        int[] input = {};
        heap.MakeHeap(input, 0);

        List<Integer> result = heap.FindElementsGreaterThan(5);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindElementLessThanSingleMatch() {
        int[] input = {10, 20, 30};
        heap.MakeHeap(input, 1);

        Integer result = heap.FindElementLessThan(15);

        assertNotNull(result);
        assertEquals(10, result);
    }

    @Test
    public void testFindElementLessThanNoMatch() {
        int[] input = {30, 25, 20};
        heap.MakeHeap(input, 1);

        Integer result = heap.FindElementLessThan(10);

        assertNull(result);
    }

    @Test
    public void testFindElementLessThanEmptyHeap() {
        int[] input = {};
        heap.MakeHeap(input, 0);

        Integer result = heap.FindElementLessThan(5);

        assertNull(result);
    }

    @Test
    public void testFindElementLessThanMultipleMatches() {
        int[] input = {25, 15, 30, 10, 5};
        heap.MakeHeap(input, 2);

        Integer result = heap.FindElementLessThan(12);

        assertNotNull(result);
        assertTrue(result == 10 || result == 5);
    }

    @Test
    public void testMergeHeapBothHeapsSameSize() {
        Heap heap1 = new Heap();
        Heap heap2 = new Heap();

        heap1.MakeHeap(new int[]{4, 2, 6, 1}, 2);
        heap2.MakeHeap(new int[]{3, 5, 7}, 1);

        heap1.MergeHeap(heap2);

        assertTrue(heap1.IsHeap());
        assertArrayEquals(new int[]{7, 6, 5, 1, 2, 4, 3}, heap1.HeapArray);
    }

    @Test
    public void testMergeHeapOneEmptyHeap() {
        Heap heap1 = new Heap();
        Heap heap2 = new Heap();

        heap1.MakeHeap(new int[]{4, 2, 6, 1}, 2);
        heap2.MakeHeap(new int[]{}, 0);

        heap1.MergeHeap(heap2);

        assertTrue(heap1.IsHeap());
        assertArrayEquals(new int[]{6, 2, 4, 1, 0, 0, 0}, heap1.HeapArray);
    }

    @Test
    public void testMergeHeapBothEmptyHeaps() {
        Heap heap1 = new Heap();
        Heap heap2 = new Heap();

        heap1.MakeHeap(new int[]{}, 0);
        heap2.MakeHeap(new int[]{}, 0);

        heap1.MergeHeap(heap2);

        assertTrue(heap1.IsHeap());
        assertArrayEquals(new int[]{0}, heap1.HeapArray);
    }

    @Test
    public void testMergeHeapNoSpaceInFirstHeap() {
        Heap heap1 = new Heap();
        Heap heap2 = new Heap();

        heap1.MakeHeap(new int[]{5, 2, 4}, 1);
        heap2.MakeHeap(new int[]{7, 6}, 1);

        heap1.MergeHeap(heap2);

        assertTrue(heap1.IsHeap());
        assertArrayEquals(new int[]{5, 2, 4}, heap1.HeapArray);
    }

    @Test
    public void testMergeHeapNoSpaceInBothHeaps() {
        Heap heap1 = new Heap();
        Heap heap2 = new Heap();

        heap1.MakeHeap(new int[]{9, 8, 7}, 1);
        heap2.MakeHeap(new int[]{6, 5, 4}, 1);

        heap1.MergeHeap(heap2);

        assertTrue(heap1.IsHeap());
        assertArrayEquals(new int[]{9, 8, 7}, heap1.HeapArray);
    }

    @Test
    public void testMergeHeapLargerSecondHeap() {
        Heap heap1 = new Heap();
        Heap heap2 = new Heap();

        heap1.MakeHeap(new int[]{2, 1}, 1);
        heap2.MakeHeap(new int[]{9, 8, 7, 6}, 2);

        heap1.MergeHeap(heap2);

        assertTrue(heap1.IsHeap());
        assertArrayEquals(new int[]{2, 1, 0}, heap1.HeapArray);
    }

    @Test
    public void testMergeHeapHeapIsFull() {
        Heap heap1 = new Heap();
        Heap heap2 = new Heap();

        heap1.MakeHeap(new int[]{5, 3, 4}, 1);
        heap2.MakeHeap(new int[]{6}, 1);

        heap1.MergeHeap(heap2);

        assertTrue(heap1.IsHeap());
        assertArrayEquals(new int[]{5, 3, 4}, heap1.HeapArray);
    }

}
