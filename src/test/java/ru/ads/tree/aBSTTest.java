package ru.ads.tree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class aBSTTest {

    @Test
    void testFindKeyIndexEmptyTree() {
        aBST tree = new aBST(0);

        assertEquals(0, tree.FindKeyIndex(10));
    }

    @Test
    void testFindKeyIndexSingleNodeOneDepthTree() {
        aBST tree = new aBST(1);
        tree.Tree[0] = 10;

        assertEquals(0, tree.FindKeyIndex(10));
        assertEquals(-1, tree.FindKeyIndex(5));
        assertEquals(-2, tree.FindKeyIndex(15));
    }

    @Test
    void testFindKeyIndexTwoDepthTree() {
        aBST tree = new aBST(2);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;

        assertEquals(0, tree.FindKeyIndex(10));
        assertEquals(1, tree.FindKeyIndex(5));
        assertEquals(2, tree.FindKeyIndex(15));
        assertEquals(-3, tree.FindKeyIndex(2));
        assertEquals(-4, tree.FindKeyIndex(7));
    }

    @Test
    void testFindKeyIndexThreeDepthTree() {
        aBST tree = new aBST(3);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;
        tree.Tree[3] = 3;
        tree.Tree[4] = 7;

        assertEquals(3, tree.FindKeyIndex(3));
        assertEquals(4, tree.FindKeyIndex(7));
        assertEquals(-5, tree.FindKeyIndex(12));
        assertEquals(-6, tree.FindKeyIndex(18));
    }

    @Test
    void testAddKeyEmptyTree() {
        aBST tree = new aBST(0);

        assertEquals(0, tree.AddKey(10));
        assertEquals(10, tree.Tree[0]);
    }

    @Test
    void testAddKeySingleNodeOneDepthTree() {
        aBST tree = new aBST(1);
        tree.Tree[0] = 10;

        assertEquals(0, tree.AddKey(10));
        assertEquals(2, tree.AddKey(15));

        assertEquals(10, tree.Tree[0]);
        assertNull(tree.Tree[1]);
        assertEquals(15, tree.Tree[2]);
    }

    @Test
    void testAddKeyTwoDepthTree() {
        aBST tree = new aBST(2);
        tree.Tree[0] = 10;

        assertEquals(1, tree.AddKey(5));
        assertEquals(2, tree.AddKey(15));

        assertEquals(10, tree.Tree[0]);
        assertEquals(5, tree.Tree[1]);
        assertEquals(15, tree.Tree[2]);
    }

    @Test
    void testAddKeyThreeDepthTree() {
        aBST tree = new aBST(3);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;

        assertEquals(3, tree.AddKey(3));
        assertEquals(4, tree.AddKey(7));
        assertEquals(6, tree.AddKey(20));

        assertEquals(3, tree.Tree[3]);
        assertEquals(7, tree.Tree[4]);
        assertEquals(20, tree.Tree[6]);
    }

    @Test
    void testAddKeyFullTree() {
        aBST tree = new aBST(1);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;

        assertEquals(-1, tree.AddKey(20));
    }

    @Test
    void testFindLCAEmptyTree() {
        aBST tree = new aBST(0);

        assertNull(tree.FindLCA(11, 12));
    }

    @Test
    void testFindLCARootAndRoot() {
        aBST tree = new aBST(2);
        tree.Tree[0] = 10;
        assertEquals(10, tree.FindLCA(10, 10));
    }

    @Test
    void testFindLCANodesDifferentSubtreesSameLevel() {
        aBST tree = new aBST(2);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;

        assertEquals(10, tree.FindLCA(5, 15));
    }

    @Test
    void testFindLCANodesDifferentSubtreesDifferentLevels() {
        aBST tree = new aBST(3);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;
        tree.Tree[3] = 3;
        tree.Tree[4] = 7;

        assertEquals(10, tree.FindLCA(3, 15));
        assertEquals(10, tree.FindLCA(7, 15));
    }

    @Test
    void testFindLCANodesLeftSubtree() {
        aBST tree = new aBST(3);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;
        tree.Tree[3] = 3;
        tree.Tree[4] = 7;

        assertEquals(5, tree.FindLCA(3, 7));
    }

    @Test
    void testFindLCANodesRightSubtreeDifferentLevels() {
        aBST tree = new aBST(3);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;
        tree.Tree[5] = 12;
        tree.Tree[6] = 18;

        assertEquals(15, tree.FindLCA(12, 18));
        assertEquals(10, tree.FindLCA(5, 18));
    }

    @Test
    void testFindLCAOneNodeIsAncestor() {
        aBST tree = new aBST(3);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;
        tree.Tree[3] = 3;
        tree.Tree[4] = 7;

        assertEquals(10, tree.FindLCA(10, 7));
        assertEquals(5, tree.FindLCA(5, 3));
    }

    @Test
    void testWideAllNodesEmptyTree() {
        aBST tree = new aBST(0);

        ArrayList<Integer> result = tree.WideAllNodes();
        assertTrue(result.isEmpty());
    }

    @Test
    void testWideAllNodesSingleNode() {
        aBST tree = new aBST(1);
        tree.Tree[0] = 10;

        ArrayList<Integer> result = tree.WideAllNodes();
        assertEquals(1, result.size());
        assertEquals(10, result.getFirst());
    }

    @Test
    void testWideAllNodesSmallTree() {
        aBST tree = new aBST(2);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;

        ArrayList<Integer> result = tree.WideAllNodes();
        assertEquals(3, result.size());
        assertEquals(10, result.getFirst());
        assertEquals(5, result.get(1));
        assertEquals(15, result.getLast());
    }

    @Test
    void testWideAllNodesLargeTree() {
        aBST tree = new aBST(3);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;
        tree.Tree[3] = 3;
        tree.Tree[4] = 7;
        tree.Tree[5] = 12;
        tree.Tree[6] = 18;

        ArrayList<Integer> result = tree.WideAllNodes();
        assertEquals(7, result.size());
        assertEquals(10, result.getFirst());
        assertEquals(5, result.get(1));
        assertEquals(15, result.get(2));
        assertEquals(3, result.get(3));
        assertEquals(7, result.get(4));
        assertEquals(12, result.get(5));
        assertEquals(18, result.getLast());
    }

    @Test
    void testDeepAllNodesInOrderEmptyTree() {
        aBST tree = new aBST(0);

        ArrayList<Integer> result = tree.DeepAllNodes(0);
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeepAllNodesInOrderSmallTree() {
        aBST tree = new aBST(2);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;

        ArrayList<Integer> result = tree.DeepAllNodes(0);
        assertEquals(3, result.size());
        assertEquals(5, result.getFirst());
        assertEquals(10, result.get(1));
        assertEquals(15, result.getLast());
    }

    @Test
    void testDeepAllNodesInOrderLargeTree() {
        aBST tree = new aBST(3);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;
        tree.Tree[3] = 3;
        tree.Tree[4] = 7;
        tree.Tree[5] = 12;
        tree.Tree[6] = 18;

        ArrayList<Integer> result = tree.DeepAllNodes(0);
        assertEquals(7, result.size());
        assertEquals(3, result.getFirst());
        assertEquals(5, result.get(1));
        assertEquals(7, result.get(2));
        assertEquals(10, result.get(3));
        assertEquals(12, result.get(4));
        assertEquals(15, result.get(5));
        assertEquals(18, result.getLast());
    }

    @Test
    void testDeepAllNodesPostOrderSmallTree() {
        aBST tree = new aBST(2);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;

        ArrayList<Integer> result = tree.DeepAllNodes(1);
        assertEquals(3, result.size());
        assertEquals(5, result.getFirst());
        assertEquals(15, result.get(1));
        assertEquals(10, result.getLast());
    }

    @Test
    void testDeepAllNodesPostOrderLargeTree() {
        aBST tree = new aBST(3);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;
        tree.Tree[3] = 3;
        tree.Tree[4] = 7;
        tree.Tree[5] = 12;
        tree.Tree[6] = 18;

        ArrayList<Integer> result = tree.DeepAllNodes(1);
        assertEquals(7, result.size());
        assertEquals(3, result.getFirst());
        assertEquals(7, result.get(1));
        assertEquals(5, result.get(2));
        assertEquals(12, result.get(3));
        assertEquals(18, result.get(4));
        assertEquals(15, result.get(5));
        assertEquals(10, result.getLast());
    }

    @Test
    void testDeepAllNodesPreOrderSmallTree() {
        aBST tree = new aBST(2);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;

        ArrayList<Integer> result = tree.DeepAllNodes(2);
        assertEquals(3, result.size());
        assertEquals(10, result.getFirst());
        assertEquals(5, result.get(1));
        assertEquals(15, result.getLast());
    }

    @Test
    void testDeepAllNodesPreOrderLargeTree() {
        aBST tree = new aBST(3);
        tree.Tree[0] = 10;
        tree.Tree[1] = 5;
        tree.Tree[2] = 15;
        tree.Tree[3] = 3;
        tree.Tree[4] = 7;
        tree.Tree[5] = 12;
        tree.Tree[6] = 18;

        ArrayList<Integer> result = tree.DeepAllNodes(2);
        assertEquals(7, result.size());
        assertEquals(10, result.getFirst());
        assertEquals(5, result.get(1));
        assertEquals(3, result.get(2));
        assertEquals(7, result.get(3));
        assertEquals(15, result.get(4));
        assertEquals(12, result.get(5));
        assertEquals(18, result.getLast());
    }

}
