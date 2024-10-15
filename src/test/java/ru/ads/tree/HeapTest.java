package ru.ads.tree;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeapTest {

    @Test
    public void testMakeHeapEmptyArray() {
        Heap heap = new Heap();
        int[] input = {};

        heap.MakeHeap(input, 0);

        assertNotNull(heap.HeapArray);
        assertEquals(0, heap.HeapArray[0]);
    }

    @Test
    public void testMakeHeapSingleElement() {
        Heap heap = new Heap();
        int[] input = {5};

        heap.MakeHeap(input, 0);

        assertNotNull(heap.HeapArray);
        assertEquals(5, heap.HeapArray[0]);
    }

    @Test
    public void testMakeHeapOddArray() {
        Heap heap = new Heap();
        int[] input = {4, 2, 6, 1, 3, 5, 7};

        heap.MakeHeap(input, 2);

        assertNotNull(heap.HeapArray);
        assertArrayEquals(new int[] {7, 3, 6, 1, 2, 4, 5}, heap.HeapArray);
    }

    @Test
    public void testMakeHeapEvenArray() {
        Heap heap = new Heap();
        int[] input = {3, 2, 1, 6, 7, 4, 5, 8};

        heap.MakeHeap(input, 3);

        assertNotNull(heap.HeapArray);
        assertArrayEquals(new int[] {8, 7, 5, 6, 3, 1, 4, 2, 0, 0, 0, 0, 0, 0, 0}, heap.HeapArray);
    }

    @Test
    public void testGetMaxFromEmptyHeap() {
        Heap heap = new Heap();
        int[] input = {};

        heap.MakeHeap(input, 0);

        assertEquals(-1, heap.GetMax());
    }

    @Test
    public void testGetMaxFromOddHeap() {
        Heap heap = new Heap();
        int[] input = {4, 2, 6, 1, 3, 5, 7};

        heap.MakeHeap(input, 2);

        assertEquals(7, heap.GetMax());
        assertArrayEquals(new int[] {6, 3, 5, 1, 2, 4, 0}, heap.HeapArray);
    }

    @Test
    public void testGetMaxFromEvenHeap() {
        Heap heap = new Heap();
        int[] input = {3, 2, 1, 6, 7, 4, 5, 8};

        heap.MakeHeap(input, 3);

        assertEquals(8, heap.GetMax());
        assertArrayEquals(new int[] {7, 6, 5, 2, 3, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0}, heap.HeapArray);
    }

    @Test
    public void testAddToEmptyHeap() {
        Heap heap = new Heap();
        int[] input = {};

        heap.MakeHeap(input, 0);

        assertTrue(heap.Add(1));
        assertArrayEquals(new int[] {1}, heap.HeapArray);
    }

    @Test
    public void testAddToFullHeap() {
        Heap heap = new Heap();
        int[] input = {4, 2, 6, 1, 3, 5, 7};

        heap.MakeHeap(input, 2);

        assertFalse(heap.Add(9));
    }

    @Test
    public void testIsHeapEmptyArray() {
        Heap heap = new Heap();
        heap.MakeHeap(new int[] {}, 0);
        assertTrue(heap.IsHeap());
    }

    @Test
    public void testIsHeapSingleElement() {
        Heap heap = new Heap();
        heap.MakeHeap(new int[] {5}, 0);
        assertTrue(heap.IsHeap());
    }

    @Test
    public void testIsHeapValidHeap() {
        Heap heap = new Heap();
        heap.MakeHeap(new int[] {7, 3, 6, 1, 2, 4, 5}, 2);
        assertTrue(heap.IsHeap());
    }

    @Test
    public void testIsHeapInvalidHeap() {
        Heap heap = new Heap();
        heap.HeapArray = new int[] {1, 2, 3, 4, 5, 6, 7};
        assertFalse(heap.IsHeap());
    }

    @Test
    public void testIsHeapLargeValidHeap() {
        Heap heap = new Heap();
        int[] input = {8, 7, 5, 6, 3, 1, 4, 2};
        heap.MakeHeap(input, 3);
        assertTrue(heap.IsHeap());
    }

    @Test
    public void testIsHeapLargeInvalidHeap() {
        Heap heap = new Heap();
        heap.HeapArray = new int[] {1, 8, 9, 4, 7, 6, 5};
        assertFalse(heap.IsHeap());
    }

    @Test
    public void testGetMaxInRangeValidRange() {
        Heap heap = new Heap();
        heap.MakeHeap(new int[]{10, 5, 3, 2, 8, 1, 9}, 2);

        int result = heap.GetMaxInRange(1, 5);
        assertEquals(9, result);
    }

    @Test
    public void testGetMaxInRangeSingleElementRange() {
        Heap heap = new Heap();
        heap.MakeHeap(new int[]{15, 10, 12, 8, 9, 3}, 2);

        int result = heap.GetMaxInRange(3, 3);
        assertEquals(8, result);
    }

    @Test
    public void testGetMaxInRangeFullRange() {
        Heap heap = new Heap();
        heap.MakeHeap(new int[]{20, 10, 15, 7, 8, 12}, 2);

        int result = heap.GetMaxInRange(0, 5);
        assertEquals(20, result);
    }

    @Test
    public void testGetMaxInRangeInvalidRange() {
        Heap heap = new Heap();
        heap.MakeHeap(new int[]{10, 20, 30, 40, 50}, 2);

        assertNull(heap.GetMaxInRange(-1, 2));
        assertNull(heap.GetMaxInRange(3, 7));
        assertNull(heap.GetMaxInRange(5, 2));
    }

    @Test
    public void testFindElementsLessThan() {
        Heap heap = new Heap();
        heap.MakeHeap(new int[]{20, 15, 10, 8, 7, 9, 5}, 2);

        List<Integer> result = heap.FindElementsLessThan(10);

        assertTrue(result.contains(8));
        assertTrue(result.contains(7));
        assertTrue(result.contains(5));
        assertFalse(result.contains(10));
        assertFalse(result.contains(15));
    }

    @Test
    public void testFindElementsLessThanNoMatches() {
        Heap heap = new Heap();
        heap.MakeHeap(new int[]{30, 25, 20, 15, 10}, 2);

        List<Integer> result = heap.FindElementsLessThan(5);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindElementsLessThanAllMatches() {
        Heap heap = new Heap();
        heap.MakeHeap(new int[]{3, 2, 1}, 1);

        List<Integer> result = heap.FindElementsLessThan(5);
        assertEquals(3, result.size());
        assertTrue(result.contains(3));
        assertTrue(result.contains(2));
        assertTrue(result.contains(1));
    }

}
