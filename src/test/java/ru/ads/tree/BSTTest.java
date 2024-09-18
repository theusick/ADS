package ru.ads.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BSTTest {

    private BST<Integer> tree;
    private BSTNode<Integer> root;

    @BeforeEach
    void setUp() {
        root = new BSTNode<>(10, 10, null);
        tree = new BST<>(root);
    }

    @Test
    void testFindNodeByKeyEmptyTree() {
        BST<Integer> emptyBST = new BST<>(null);

        BSTFind<Integer> result = emptyBST.FindNodeByKey(10);
        assertNull(result.Node);
        assertFalse(result.NodeHasKey);
    }

    @Test
    void testFindNodeByKeyMissingKeyToLeft() {
        tree.AddKeyValue(5, 5);

        BSTFind<Integer> result = tree.FindNodeByKey(3);

        assertFalse(result.NodeHasKey);
        assertEquals(5, result.Node.NodeKey);
        assertTrue(result.ToLeft);
    }

    @Test
    void testFindNodeByKeyMissingKeyToRight() {
        tree.AddKeyValue(15, 15);

        BSTFind<Integer> result = tree.FindNodeByKey(17);

        assertFalse(result.NodeHasKey);
        assertEquals(15, result.Node.NodeKey);
        assertFalse(result.ToLeft);
    }

    @Test
    void testFindNodeByKeyExistingKeySmallTree() {
        tree.AddKeyValue(5, 5);

        BSTFind<Integer> result = tree.FindNodeByKey(5);

        assertTrue(result.NodeHasKey);
        assertEquals(5, result.Node.NodeKey);
    }

    @Test
    void testFindNodeByKeyMissingKeyInLargeTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(20, 20);

        BSTFind<Integer> result = tree.FindNodeByKey(8);

        assertFalse(result.NodeHasKey);
        assertEquals(7, result.Node.NodeKey);
        assertFalse(result.ToLeft);
    }

    @Test
    void testFindNodeByKeyExistingKeyLargeTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(20, 20);

        BSTFind<Integer> result = tree.FindNodeByKey(15);

        assertTrue(result.NodeHasKey);
        assertEquals(15, result.Node.NodeKey);
    }

    @Test
    void testDeleteNodeByKeyNodeExists() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);

        boolean deleted = tree.DeleteNodeByKey(5);

        assertTrue(deleted);

        BSTFind<Integer> result = tree.FindNodeByKey(5);
        assertFalse(result.NodeHasKey);
    }

    @Test
    void testDeleteNodeByKeyNodeNotExist() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        boolean deleted = tree.DeleteNodeByKey(100);

        assertFalse(deleted);
    }

    @Test
    void testAddKeyValueEmptyTree() {
        BST<Integer> emptyBST = new BST<>(null);

        assertTrue(emptyBST.AddKeyValue(1, 1));
        assertEquals(1, emptyBST.Root.NodeKey);
        assertEquals(1, emptyBST.Root.NodeValue);
    }

    @Test
    void testAddKeyValueAddLeft() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        assertFalse(tree.AddKeyValue(5, 5));
        assertTrue(tree.AddKeyValue(3, 3));
        assertEquals(3, tree.Root.LeftChild.LeftChild.NodeKey);
    }

    @Test
    void testAddKeyValueAddRight() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        assertTrue(tree.AddKeyValue(17, 17));
        assertEquals(17, tree.Root.RightChild.RightChild.NodeKey);
    }

    @Test
    void testAddKeyValueDuplicateKey() {
        assertFalse(tree.AddKeyValue(10, 10));
    }

    @Test
    void testAddKeyValueAddLeftLargeTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(20, 20);

        assertTrue(tree.AddKeyValue(6, 6));
        assertEquals(6, tree.Root.LeftChild.RightChild.LeftChild.NodeKey);
    }

    @Test
    void testAddKeyValueAddRightLargeTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(20, 20);

        assertTrue(tree.AddKeyValue(33, 33));
        assertEquals(33, tree.Root.RightChild.RightChild.RightChild.NodeKey);
    }

    @Test
    void testFinMinMaxEmptyTree() {
        BST<Integer> emptyBST = new BST<>(null);

        assertNull(emptyBST.FinMinMax(emptyBST.Root, true));
        assertNull(emptyBST.FinMinMax(emptyBST.Root, false));
    }

    @Test
    void testFinMinMaxSingleNode() {
        BST<Integer> singleNodeTree = new BST<>(new BSTNode<>(50, 50, null));

        assertEquals(50, singleNodeTree.FinMinMax(singleNodeTree.Root, true).NodeKey);
        assertEquals(50, singleNodeTree.FinMinMax(singleNodeTree.Root, false).NodeKey);
    }

    @Test
    void testFinMinMaxFromRootSmallTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        assertEquals(15, tree.FinMinMax(tree.Root, true).NodeKey);
        assertEquals(5, tree.FinMinMax(tree.Root, false).NodeKey);
    }

    @Test
    void testFinMinMaxFromSubtreeSmallTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        assertEquals(15, tree.FinMinMax(tree.Root.RightChild, true).NodeKey);
        assertEquals(15, tree.FinMinMax(tree.Root.RightChild, false).NodeKey);
    }

    @Test
    void testFinMinMaxLargeTree() {
        tree.AddKeyValue(20, 20);
        tree.AddKeyValue(30, 30);
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(25, 25);
        tree.AddKeyValue(35, 35);

        assertEquals(35, tree.FinMinMax(tree.Root, true).NodeKey);
        assertEquals(5, tree.FinMinMax(tree.Root, false).NodeKey);
    }

    @Test
    void testCountEmptyTree() {
        BST<Integer> emptyBST = new BST<>(null);

        assertEquals(0, emptyBST.Count());
    }

    @Test
    void testCountSingleNode() {
        BST<Integer> singleNodeTree = new BST<>(new BSTNode<>(50, 50, null));

        assertEquals(1, singleNodeTree.Count());
    }

    @Test
    void testCountSmallTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        assertEquals(3, tree.Count());
    }

    @Test
    void testCountLargeTree() {
        tree.AddKeyValue(20, 20);
        tree.AddKeyValue(30, 30);
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(25, 25);
        tree.AddKeyValue(35, 35);

        assertEquals(7, tree.Count());
    }

    @Test
    void testDeleteNodeByKeyEmptyTree() {
        BST<Integer> emptyTree = new BST<>(null);

        assertFalse(emptyTree.DeleteNodeByKey(5));
    }

    @Test
    void testDeleteNodeByKeySingleNode() {
        assertTrue(tree.DeleteNodeByKey(10));
        assertNull(tree.Root);
    }

    @Test
    void testDeleteNodeByKeyLeafNode() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        assertTrue(tree.DeleteNodeByKey(5));
        assertNull(tree.Root.LeftChild);

        assertTrue(tree.DeleteNodeByKey(15));
        assertNull(tree.Root.RightChild);
    }

    @Test
    void testDeleteNodeByKeySingleLeftChild() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(3, 3);

        assertTrue(tree.DeleteNodeByKey(5));
        assertEquals(3, tree.Root.LeftChild.NodeKey);
        assertEquals(tree.Root, tree.Root.LeftChild.Parent);
        assertNull(tree.Root.LeftChild.LeftChild);
    }

    @Test
    void testDeleteNodeByKeyCloseLeftMax() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(4, 4);

        assertTrue(tree.DeleteNodeByKey(5));
        assertEquals(4, tree.Root.LeftChild.NodeKey);
        assertEquals(tree.Root, tree.Root.LeftChild.Parent);
        assertEquals(3, tree.Root.LeftChild.LeftChild.NodeKey);
        assertEquals(4, tree.Root.LeftChild.LeftChild.Parent.NodeKey);
    }

    @Test
    void testDeleteNodeByKeyFarLeftMax() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(2, 2);
        tree.AddKeyValue(4, 4);

        assertTrue(tree.DeleteNodeByKey(5));
        assertEquals(4, tree.Root.LeftChild.NodeKey);
        assertEquals(tree.Root, tree.Root.LeftChild.Parent);
        assertEquals(3, tree.Root.LeftChild.LeftChild.NodeKey);
        assertEquals(4, tree.Root.LeftChild.LeftChild.Parent.NodeKey);
        assertNull(tree.Root.LeftChild.LeftChild.RightChild);
        assertEquals(2, tree.Root.LeftChild.LeftChild.LeftChild.NodeKey);
    }

    @Test
    void testDeleteNodeByKeySingleRightChild() {
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(20, 20);

        assertTrue(tree.DeleteNodeByKey(15));
        assertEquals(20, tree.Root.RightChild.NodeKey);
        assertEquals(tree.Root, tree.Root.RightChild.Parent);
        assertNull(tree.Root.RightChild.RightChild);
    }

    @Test
    void testDeleteNodeByKeyCloseRightMin() {
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(13, 13);
        tree.AddKeyValue(12, 12);

        assertTrue(tree.DeleteNodeByKey(15));
        assertEquals(13, tree.Root.RightChild.NodeKey);
        assertEquals(tree.Root, tree.Root.RightChild.Parent);
        assertEquals(12, tree.Root.RightChild.LeftChild.NodeKey);
        assertNull(tree.Root.RightChild.RightChild);
    }

    @Test
    void testDeleteNodeByKeyFarRightMin() {
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(17, 17);
        tree.AddKeyValue(16, 16);

        assertTrue(tree.DeleteNodeByKey(15));
        assertEquals(16, tree.Root.RightChild.NodeKey);
        assertEquals(tree.Root, tree.Root.RightChild.Parent);
        assertEquals(17, tree.Root.RightChild.RightChild.NodeKey);
        assertEquals(tree.Root.RightChild, tree.Root.RightChild.RightChild.Parent);
        assertNull(tree.Root.RightChild.RightChild.LeftChild);
    }

    @Test
    void testDeleteNodeByKeyBothChildren() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(12, 12);
        tree.AddKeyValue(17, 17);

        assertTrue(tree.DeleteNodeByKey(15));
        assertEquals(17, tree.Root.RightChild.NodeKey);
        assertEquals(tree.Root, tree.Root.RightChild.Parent);
        assertEquals(12, tree.Root.RightChild.LeftChild.NodeKey);
        assertEquals(tree.Root.RightChild, tree.Root.RightChild.LeftChild.Parent);
        assertNull(tree.Root.RightChild.RightChild);
    }

    @Test
    void testDeleteNodeByKeyRootFullTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(12, 12);
        tree.AddKeyValue(18, 18);

        assertTrue(tree.DeleteNodeByKey(10));

        assertEquals(12, tree.Root.NodeKey);
        assertNull(tree.Root.Parent);

        assertEquals(tree.Root, tree.Root.LeftChild.Parent);
        assertEquals(5, tree.Root.LeftChild.NodeKey);
        assertEquals(3, tree.Root.LeftChild.LeftChild.NodeKey);
        assertEquals(7, tree.Root.LeftChild.RightChild.NodeKey);

        assertEquals(tree.Root, tree.Root.RightChild.Parent);
        assertEquals(15, tree.Root.RightChild.NodeKey);
        assertEquals(18, tree.Root.RightChild.RightChild.NodeKey);
        assertNull(tree.Root.RightChild.LeftChild);
    }

}
