package ru.ads.tree;

import org.junit.jupiter.api.Test;

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

}
