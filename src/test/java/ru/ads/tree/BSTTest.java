package ru.ads.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

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

    @Test
    void testWideAllNodesEmptyTree() {
        BST<Integer> emptyTree = new BST<>(null);

        assertTrue(emptyTree.WideAllNodes().isEmpty());
    }

    @Test
    void testWideAllNodesSingleNode() {
        ArrayList<BSTNode> bfsResult = tree.WideAllNodes();

        assertEquals(1, bfsResult.size());
        assertEquals(root, bfsResult.getFirst());
    }

    @Test
    void testWideAllNodesSmallTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        ArrayList<BSTNode> bfsResult = tree.WideAllNodes();

        assertEquals(3, bfsResult.size());
        assertEquals(root, bfsResult.getFirst());
        assertEquals(5, bfsResult.get(1).NodeKey);
        assertEquals(15, bfsResult.getLast().NodeKey);
    }

    @Test
    void testWideAllNodesLargeTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(12, 12);
        tree.AddKeyValue(18, 18);

        ArrayList<BSTNode> bfsResult = tree.WideAllNodes();

        assertEquals(7, bfsResult.size());
        assertEquals(root, bfsResult.getFirst());
        assertEquals(5, bfsResult.get(1).NodeKey);
        assertEquals(15, bfsResult.get(2).NodeKey);
        assertEquals(3, bfsResult.get(3).NodeKey);
        assertEquals(7, bfsResult.get(4).NodeKey);
        assertEquals(12, bfsResult.get(5).NodeKey);
        assertEquals(18, bfsResult.getLast().NodeKey);
    }

    @Test
    void testDeepAllNodesEmptyTree() {
        BST<Integer> emptyTree = new BST<>(null);

        assertTrue(emptyTree.DeepAllNodes(0).isEmpty());
        assertTrue(emptyTree.DeepAllNodes(1).isEmpty());
        assertTrue(emptyTree.DeepAllNodes(2).isEmpty());

        assertTrue(emptyTree.DeepAllNodes(-1).isEmpty());
        assertTrue(emptyTree.DeepAllNodes(3).isEmpty());
    }

    @Test
    void testDeepAllNodesSingleNode() {
        ArrayList<BSTNode> dfsInOrderResult = tree.DeepAllNodes(0);

        assertEquals(1, dfsInOrderResult.size());
        assertEquals(root, dfsInOrderResult.getFirst());

        ArrayList<BSTNode> dfsPostOrderResult = tree.DeepAllNodes(1);

        assertEquals(1, dfsPostOrderResult.size());
        assertEquals(root, dfsPostOrderResult.getFirst());

        ArrayList<BSTNode> dfsPreOrderResult = tree.DeepAllNodes(2);

        assertEquals(1, dfsPreOrderResult.size());
        assertEquals(root, dfsPreOrderResult.getFirst());
    }

    @Test
    void testDeepAllNodesSingleNodeInvalidOrder() {
        assertTrue(tree.DeepAllNodes(-1).isEmpty());
        assertTrue(tree.DeepAllNodes(3).isEmpty());
    }

    @Test
    void testDeepAllNodesSmallTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        ArrayList<BSTNode> dfsInOrderResult = tree.DeepAllNodes(0);

        assertEquals(3, dfsInOrderResult.size());
        assertEquals(5, dfsInOrderResult.getFirst().NodeKey);
        assertEquals(root, dfsInOrderResult.get(1));
        assertEquals(15, dfsInOrderResult.getLast().NodeKey);

        ArrayList<BSTNode> dfsPostOrderResult = tree.DeepAllNodes(1);

        assertEquals(3, dfsPostOrderResult.size());
        assertEquals(5, dfsPostOrderResult.getFirst().NodeKey);
        assertEquals(15, dfsPostOrderResult.get(1).NodeKey);
        assertEquals(root, dfsPostOrderResult.getLast());

        ArrayList<BSTNode> dfsPreOrderResult = tree.DeepAllNodes(2);

        assertEquals(3, dfsPreOrderResult.size());
        assertEquals(root, dfsPreOrderResult.getFirst());
        assertEquals(5, dfsPreOrderResult.get(1).NodeKey);
        assertEquals(15, dfsPreOrderResult.getLast().NodeKey);
    }

    @Test
    void testDeepAllNodesLargeTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(12, 12);
        tree.AddKeyValue(18, 18);

        ArrayList<BSTNode> dfsInOrderResult = tree.DeepAllNodes(0);

        assertEquals(7, dfsInOrderResult.size());
        assertEquals(3, dfsInOrderResult.getFirst().NodeKey);
        assertEquals(5, dfsInOrderResult.get(1).NodeKey);
        assertEquals(7, dfsInOrderResult.get(2).NodeKey);
        assertEquals(root, dfsInOrderResult.get(3));
        assertEquals(12, dfsInOrderResult.get(4).NodeKey);
        assertEquals(15, dfsInOrderResult.get(5).NodeKey);
        assertEquals(18, dfsInOrderResult.getLast().NodeKey);

        ArrayList<BSTNode> dfsPostOrderResult = tree.DeepAllNodes(1);

        assertEquals(7, dfsPostOrderResult.size());
        assertEquals(3, dfsPostOrderResult.getFirst().NodeKey);
        assertEquals(7, dfsPostOrderResult.get(1).NodeKey);
        assertEquals(5, dfsPostOrderResult.get(2).NodeKey);
        assertEquals(12, dfsPostOrderResult.get(3).NodeKey);
        assertEquals(18, dfsPostOrderResult.get(4).NodeKey);
        assertEquals(15, dfsPostOrderResult.get(5).NodeKey);
        assertEquals(root, dfsPostOrderResult.getLast());

        ArrayList<BSTNode> dfsPreOrderResult = tree.DeepAllNodes(2);

        assertEquals(7, dfsPreOrderResult.size());
        assertEquals(root, dfsPreOrderResult.getFirst());
        assertEquals(5, dfsPreOrderResult.get(1).NodeKey);
        assertEquals(3, dfsPreOrderResult.get(2).NodeKey);
        assertEquals(7, dfsPreOrderResult.get(3).NodeKey);
        assertEquals(15, dfsPreOrderResult.get(4).NodeKey);
        assertEquals(12, dfsPreOrderResult.get(5).NodeKey);
        assertEquals(18, dfsPreOrderResult.getLast().NodeKey);
    }

    @Test
    void testEqualsEmptyTree() {
        BST<Integer> emptyTree = new BST<>(null);

        assertTrue(emptyTree.Equals(emptyTree));
        assertFalse(emptyTree.equals(tree));
        assertFalse(tree.Equals(emptyTree));
    }

    @Test
    void testEqualsLeftSmallTreesNonIdentical() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        BST<Integer> nonIdenticalTree = new BST<>(new BSTNode<>(10, 10, null));
        nonIdenticalTree.AddKeyValue(4, 4);
        nonIdenticalTree.AddKeyValue(15, 15);

        assertFalse(tree.Equals(nonIdenticalTree));
        assertFalse(nonIdenticalTree.Equals(tree));
        assertTrue(nonIdenticalTree.Equals(nonIdenticalTree));
    }

    @Test
    void testEqualsRightSmallTreesNonIdentical() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        BST<Integer> nonIdenticalTree = new BST<>(new BSTNode<>(10, 10, null));
        nonIdenticalTree.AddKeyValue(5, 5);
        nonIdenticalTree.AddKeyValue(12, 12);

        assertFalse(tree.Equals(nonIdenticalTree));
        assertFalse(nonIdenticalTree.Equals(tree));
        assertTrue(nonIdenticalTree.Equals(nonIdenticalTree));
    }

    @Test
    void testEqualsSmallTreesIdentical() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        BST<Integer> identicalTree = new BST<>(new BSTNode<>(10, 10, null));
        identicalTree.AddKeyValue(5, 5);
        identicalTree.AddKeyValue(15, 15);

        assertTrue(tree.Equals(tree));
        assertTrue(tree.Equals(identicalTree));
        assertTrue(identicalTree.Equals(tree));
    }

    @Test
    void testEqualsLeftLargeTreesNonIdentical() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(12, 12);
        tree.AddKeyValue(18, 18);

        BST<Integer> nonIdenticalTree = new BST<>(new BSTNode<>(10, 10, null));
        nonIdenticalTree.AddKeyValue(5, 5);
        nonIdenticalTree.AddKeyValue(15, 15);
        nonIdenticalTree.AddKeyValue(3, 3);
        nonIdenticalTree.AddKeyValue(6, 6);
        nonIdenticalTree.AddKeyValue(11, 11);
        nonIdenticalTree.AddKeyValue(21, 21);

        assertFalse(tree.Equals(nonIdenticalTree));
        assertFalse(nonIdenticalTree.Equals(tree));
        assertTrue(nonIdenticalTree.Equals(nonIdenticalTree));
    }

    @Test
    void testEqualsRightLargeTreesNonIdentical() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(12, 12);
        tree.AddKeyValue(18, 18);

        BST<Integer> nonIdenticalTree = new BST<>(new BSTNode<>(10, 10, null));
        nonIdenticalTree.AddKeyValue(5, 5);
        nonIdenticalTree.AddKeyValue(15, 15);
        nonIdenticalTree.AddKeyValue(3, 3);
        nonIdenticalTree.AddKeyValue(7, 7);
        nonIdenticalTree.AddKeyValue(11, 11);
        nonIdenticalTree.AddKeyValue(21, 21);

        assertFalse(tree.Equals(nonIdenticalTree));
        assertFalse(nonIdenticalTree.Equals(tree));
        assertTrue(nonIdenticalTree.Equals(nonIdenticalTree));
    }

    @Test
    void testEqualsLargeTreesIdentical() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(12, 12);
        tree.AddKeyValue(18, 18);

        BST<Integer> identicalTree = new BST<>(new BSTNode<>(10, 10, null));
        identicalTree.AddKeyValue(5, 5);
        identicalTree.AddKeyValue(15, 15);
        identicalTree.AddKeyValue(3, 3);
        identicalTree.AddKeyValue(7, 7);
        identicalTree.AddKeyValue(12, 12);
        identicalTree.AddKeyValue(18, 18);

        assertTrue(tree.Equals(tree));
        assertTrue(tree.Equals(identicalTree));
        assertTrue(identicalTree.Equals(tree));
    }

    @Test
    void testFindLeafPathsByLengthEmptyTree() {
        BST<Integer> emptyTree = new BST<>(null);

        assertTrue(emptyTree.FindLeafPathsByLength(1).isEmpty());
    }

    @Test
    void testFindLeafPathsByLengthSingleNode() {
        ArrayList<ArrayList<BSTNode>> singleNodesPaths = tree.FindLeafPathsByLength(1);

        assertEquals(1, singleNodesPaths.size());
        assertEquals(1, singleNodesPaths.getFirst().size());
        assertEquals(root, singleNodesPaths.getFirst().getFirst());
    }

    @Test
    void testFindLeafPathsByLengthSmallTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        ArrayList<ArrayList<BSTNode>> singleNodesPaths = tree.FindLeafPathsByLength(1);
        assertTrue(singleNodesPaths.isEmpty());

        ArrayList<ArrayList<BSTNode>> twoNodesPaths = tree.FindLeafPathsByLength(2);
        assertEquals(2, twoNodesPaths.size());
        assertEquals(2, twoNodesPaths.getFirst().size());
        assertEquals(2, twoNodesPaths.getLast().size());
        assertEquals(root, twoNodesPaths.getFirst().getFirst());
        assertEquals(root.LeftChild, twoNodesPaths.getFirst().getLast());
        assertEquals(root, twoNodesPaths.getLast().getFirst());
        assertEquals(root.RightChild, twoNodesPaths.getLast().getLast());
    }

    @Test
    void testFindLeafPathsByLengthLargeTree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(2, 2);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(12, 12);
        tree.AddKeyValue(18, 18);

        ArrayList<ArrayList<BSTNode>> singleNodesPaths = tree.FindLeafPathsByLength(1);
        assertTrue(singleNodesPaths.isEmpty());

        ArrayList<ArrayList<BSTNode>> fourNodesPaths = tree.FindLeafPathsByLength(4);
        assertEquals(1, fourNodesPaths.size());
        assertEquals(4, fourNodesPaths.getFirst().size());
        assertEquals(root, fourNodesPaths.getFirst().getFirst());
        assertEquals(root.LeftChild, fourNodesPaths.getFirst().get(1));
        assertEquals(root.LeftChild.LeftChild, fourNodesPaths.getFirst().get(2));
        assertEquals(root.LeftChild.LeftChild.LeftChild, fourNodesPaths.getFirst().getLast());

        ArrayList<ArrayList<BSTNode>> threeNodesPaths = tree.FindLeafPathsByLength(3);
        assertEquals(3, threeNodesPaths.size());
        assertEquals(3, threeNodesPaths.getFirst().size());
        assertEquals(3, threeNodesPaths.getLast().size());
        assertEquals(root, threeNodesPaths.getFirst().getFirst());
        assertEquals(root.LeftChild, threeNodesPaths.getFirst().get(1));
        assertEquals(root.LeftChild.RightChild, threeNodesPaths.getFirst().getLast());
    }

}
