package ru.ads.balancedtree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalancedBSTTest {

    private BalancedBST bst;

    @BeforeEach
    void setUp() {
        bst = new BalancedBST();
    }

    @Test
    public void testGenerateTreeEmptyArray() {
        int[] input = {};
        bst.GenerateTree(input);
        assertNull(bst.Root);
    }

    @Test
    public void testGenerateTreeSingleElement() {
        int[] input = {5};
        bst.GenerateTree(input);

        assertNotNull(bst.Root);
        assertEquals(5, bst.Root.NodeKey);
        assertNull(bst.Root.LeftChild);
        assertNull(bst.Root.RightChild);
    }

    @Test
    public void testGenerateTreeMultipleElements() {
        int[] input = {10, 20, 30, 40, 50, 60, 0};
        bst.GenerateTree(input);

        assertNotNull(bst.Root);
        assertEquals(30, bst.Root.NodeKey);
        assertEquals(10, bst.Root.LeftChild.NodeKey);
        assertEquals(50, bst.Root.RightChild.NodeKey);
    }

    @Test
    public void testGenerateTreeMultipleElementsComplex() {
        int[] input = {15, 10, 20, 5, 12, 18, 25}; // 5, 10, 12, 15, 18, 20, 25
        bst.GenerateTree(input);

        assertNotNull(bst.Root);
        assertEquals(15, bst.Root.NodeKey);
        assertEquals(10, bst.Root.LeftChild.NodeKey);
        assertEquals(20, bst.Root.RightChild.NodeKey);
        assertEquals(5, bst.Root.LeftChild.LeftChild.NodeKey);
        assertEquals(12, bst.Root.LeftChild.RightChild.NodeKey);
        assertEquals(18, bst.Root.RightChild.LeftChild.NodeKey);
        assertEquals(25, bst.Root.RightChild.RightChild.NodeKey);
    }

    @Test
    public void testGenerateTreeLargeArray() {
        int[] input = {5, 3, 8, 1, 13, 4, 7, 9, 12, 6, 2, 0, 11, 10, 14};
        bst.GenerateTree(input);

        assertNotNull(bst.Root);
        assertEquals(7, bst.Root.NodeKey);
        assertEquals(3, bst.Root.LeftChild.NodeKey);
        assertEquals(11, bst.Root.RightChild.NodeKey);
        assertEquals(1, bst.Root.LeftChild.LeftChild.NodeKey);
        assertEquals(5, bst.Root.LeftChild.RightChild.NodeKey);
        assertEquals(9, bst.Root.RightChild.LeftChild.NodeKey);
        assertEquals(13, bst.Root.RightChild.RightChild.NodeKey);
    }

    @Test
    public void testIsBalancedEmptyTree() {
        assertTrue(bst.IsBalanced(bst.Root));
    }

    @Test
    public void testIsBalancedSingleNode() {
        bst.GenerateTree(new int[]{5});

        assertTrue(bst.IsBalanced(bst.Root));
    }

    @Test
    public void testIsBalancedBalancedTree() {
        bst.GenerateTree(new int[]{10, 20, 30, 5, 15});

        assertTrue(bst.IsBalanced(bst.Root));
    }

    @Test
    public void testIsBalancedUnbalancedLeftHeavyTree() {
        bst.Root = new BSTNode(10, null);
        bst.Root.RightChild = new BSTNode(15, bst.Root);
        bst.Root.LeftChild = new BSTNode(5, bst.Root);

        bst.Root.LeftChild.LeftChild = new BSTNode(4, bst.Root.LeftChild);
        bst.Root.LeftChild.LeftChild.LeftChild = new BSTNode(2, bst.Root.LeftChild.LeftChild);


        assertFalse(bst.IsBalanced(bst.Root));
    }

    @Test
    public void testIsBalancedUnbalancedRightHeavyTree() {
        bst.Root = new BSTNode(10, null);
        bst.Root.RightChild = new BSTNode(15, bst.Root);
        bst.Root.LeftChild = new BSTNode(5, bst.Root);

        bst.Root.RightChild.RightChild = new BSTNode(20, bst.Root.RightChild);
        bst.Root.RightChild.RightChild.RightChild = new BSTNode(25, bst.Root.RightChild.RightChild);

        assertFalse(bst.IsBalanced(bst.Root));
    }

    @Test
    public void testIsBalancedIncorrectStructure() {
        bst.Root = new BSTNode(10, null);
        bst.Root.LeftChild = new BSTNode(15, bst.Root);
        bst.Root.RightChild = new BSTNode(5, bst.Root);

        assertFalse(bst.IsBalanced(bst.Root));
    }

    @Test
    public void testIsBalancedLargeTree() {
        bst.GenerateTree(new int[]{5, 3, 8, 1, 13, 4, 7, 9, 12, 6, 2, 0, 11, 10, 14});

        assertTrue(bst.IsBalanced(bst.Root));
    }

}
