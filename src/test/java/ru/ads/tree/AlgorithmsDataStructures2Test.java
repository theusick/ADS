package ru.ads.tree;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
        int[] input = {10, 20, 30, 40, 50, 60};
        int[] expected = {30, 10, 20, 50, 40, 60};

        int[] result = AlgorithmsDataStructures2.GenerateBBSTArray(input);

        assertArrayEquals(expected, result);
    }

    @Test
    public void testGenerateBBSTArrayMultipleElementsComplex() {
        int[] input = {15, 10, 20, 5, 12, 18, 25}; // 5 10 12 15 18 20 25
        int[] expected = {15, 10, 5, 12, 20, 18, 25};

        int[] result = AlgorithmsDataStructures2.GenerateBBSTArray(input);

        assertArrayEquals(expected, result);
    }

    @Test
    public void testGenerateBBSTArrayLargeArray() {
        int[] input = {5, 3, 8, 1, 4, 7, 9, 6, 2, 0, 11, 10}; // 0 1 2 3 4 5 6 7 8 9 10 11
        int[] expected = {5, 2, 0, 1, 3, 4, 8, 6, 7, 10, 9, 11};

        int[] result = AlgorithmsDataStructures2.GenerateBBSTArray(input);

        assertArrayEquals(expected, result);
    }

}
