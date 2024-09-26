package ru.ads.tree;

import org.junit.jupiter.api.Test;

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

}
