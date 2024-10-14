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

    @Test
    public void testIsBBSTEmptyTree() {
        Integer[] emptyTree = {};
        assertTrue(AlgorithmsDataStructures2.IsBBST(emptyTree));
    }

    @Test
    public void testIsBBSTSingleNode() {
        Integer[] singleNodeTree = {10};
        assertTrue(AlgorithmsDataStructures2.IsBBST(singleNodeTree));
    }

    @Test
    public void testIsBBSTBalancedTree() {
        Integer[] balancedTree = {10, 5, 15, 3, 7, 13, 17};
        assertTrue(AlgorithmsDataStructures2.IsBBST(balancedTree));
    }

    @Test
    public void testIsBBSTUnbalancedLeftTree() {
        Integer[] leftHeavyTree = {10, 5, null, 3, null, null, null};
        assertFalse(AlgorithmsDataStructures2.IsBBST(leftHeavyTree));
    }

    @Test
    public void testIsBBSTUnbalancedRightTree() {
        Integer[] rightHeavyTree = {10, null, 15, null, null, null, 17};
        assertFalse(AlgorithmsDataStructures2.IsBBST(rightHeavyTree));
    }

    @Test
    public void testIsBBSTNullNodeInTree() {
        Integer[] treeWithNull = {10, 5, null, 3, null, 6, null};
        assertFalse(AlgorithmsDataStructures2.IsBBST(treeWithNull));
    }

    @Test
    public void testDeleteNodeBBSTEmptyArray() {
        Integer[] input = {};

        assertFalse(AlgorithmsDataStructures2.DeleteNodeBBST(input, 5));
        assertTrue(AlgorithmsDataStructures2.IsBBST(input));
    }

    @Test
    public void testDeleteNodeBBSTSingleElement() {
        Integer[] input = {5};
        Integer[] expected = {null};

        assertTrue(AlgorithmsDataStructures2.DeleteNodeBBST(input, 5));
        assertArrayEquals(expected, input);
        assertTrue(AlgorithmsDataStructures2.IsBBST(input));
    }

    @Test
    public void testDeleteNodeBBSTDepth2() {
        Integer[] input = {3, 1, 4};
        Integer[] expected = {3, null, 4};

        assertTrue(AlgorithmsDataStructures2.DeleteNodeBBST(input, 1));
        assertArrayEquals(expected, input);
        assertTrue(AlgorithmsDataStructures2.IsBBST(input));
    }

    @Test
    public void testDeleteNodeBBSTDepth4() {
        Integer[] input = {7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};
        Integer[] expected = {7, 3, 10, 1, 5, 9, 13, 0, 2, 4, 6, 8, null, 12, 14};

        assertTrue(AlgorithmsDataStructures2.DeleteNodeBBST(input, 11));
        assertArrayEquals(expected, input);
        assertTrue(AlgorithmsDataStructures2.IsBBST(input));

        expected = new Integer[]{7, 3, 10, 1, 5, 9, 12, 0, 2, 4, 6, 8, null, null, 14};

        assertTrue(AlgorithmsDataStructures2.DeleteNodeBBST(input, 13));
        assertArrayEquals(expected, input);
        assertTrue(AlgorithmsDataStructures2.IsBBST(input));

        expected = new Integer[]{7, 3, 10, 1, 5, 9, 12, 0, 2, 4, 6, 8, null, null, null};

        assertTrue(AlgorithmsDataStructures2.DeleteNodeBBST(input, 14));
        assertArrayEquals(expected, input);
        assertTrue(AlgorithmsDataStructures2.IsBBST(input));
    }

    @Test
    public void testDeleteNodeLeftRotation() {
        Integer[] input = {30, 20, 40, 10, 25, 35, 50};
        Integer[] expected = {30, 10, 40, null, 25, 35, 50};

        assertTrue(AlgorithmsDataStructures2.DeleteNodeBBST(input, 20));
        assertArrayEquals(expected, input);
        assertTrue(AlgorithmsDataStructures2.IsBBST(input));
    }

    @Test
    public void testDeleteNodeRightRotation() {
        Integer[] input = {30, 20, 40, 10, null, 35, 50};
        Integer[] expected = {30, 10, 40, null, null, 35, 50};

        assertTrue(AlgorithmsDataStructures2.DeleteNodeBBST(input, 20));
        assertArrayEquals(expected, input);
        assertTrue(AlgorithmsDataStructures2.IsBBST(input));
    }

    @Test
    public void testDeleteNodeLeftRightRotation() {
        Integer[] input = {30, 10, 40, null, 20, 35, 50};
        Integer[] expected = {30, 20, 40, null, null, 35, 50};

        assertTrue(AlgorithmsDataStructures2.DeleteNodeBBST(input, 10));
        assertArrayEquals(expected, input);
        assertTrue(AlgorithmsDataStructures2.IsBBST(input));
    }

    @Test
    public void testDeleteNodeRightLeftRotation() {
        Integer[] input = {30, 10, 40, 5, 20, 35, 50};
        Integer[] expected = {30, 10, 35, 5, 20, null, 50};

        assertTrue(AlgorithmsDataStructures2.DeleteNodeBBST(input, 40));
        assertArrayEquals(expected, input);
        assertTrue(AlgorithmsDataStructures2.IsBBST(input));
    }

    @Test
    public void testDeleteNodeBBSTNoGapsBeforeDeletion() {
        Integer[] input = {7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};
        Integer[] expected = {7, 3, 11, 1, 5, 9, 12, 0, 2, 4, 6, 8, 10, null, 14};

        assertTrue(AlgorithmsDataStructures2.DeleteNodeBBST(input, 13));
        assertArrayEquals(expected, input);
        assertTrue(AlgorithmsDataStructures2.IsBBST(input));
    }

}
