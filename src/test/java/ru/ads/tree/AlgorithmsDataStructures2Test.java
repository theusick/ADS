package ru.ads.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmsDataStructures2Test {

    @Test
    public void testGenerateBBSTArrayEmptyArray() {
        int[] input = {};
        int[] expected = {};
        assertArrayEquals(expected, AlgorithmsDataStructures2.GenerateBBSTArray(input));
    }

    @Test
    public void testGenerateBBSTArraySingleElement() {
        int[] input = {5};
        int[] expected = {5};
        assertArrayEquals(expected, AlgorithmsDataStructures2.GenerateBBSTArray(input));
    }

    @Test
    public void testGenerateBBSTArrayMultipleElements() {
        int[] input = {10, 20, 30, 40, 50, 60, 0};
        int[] expected = {30, 10, 50, 0, 20, 40, 60};

        int[] result = AlgorithmsDataStructures2.GenerateBBSTArray(input);

        assertArrayEquals(expected, result);
    }

    @Test
    public void testGenerateBBSTArrayMultipleElementsComplex() {
        int[] input = {15, 10, 20, 5, 12, 18, 25}; // 5 10 12 15 18 20 25
        int[] expected = {15, 10, 20, 5, 12, 18, 25};

        int[] result = AlgorithmsDataStructures2.GenerateBBSTArray(input);

        assertArrayEquals(expected, result);
    }

    @Test
    public void testGenerateBBSTArrayLargeArray() {
        int[] input = {5, 3, 8, 1, 13, 4, 7, 9, 12, 6, 2, 0, 11, 10, 14};
        int[] expected = {7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};

        int[] result = AlgorithmsDataStructures2.GenerateBBSTArray(input);

        assertArrayEquals(expected, result);
    }

}
