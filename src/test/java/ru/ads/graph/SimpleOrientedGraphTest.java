package ru.ads.graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleOrientedGraphTest {

    private SimpleOrientedGraph smallGraph;
    private SimpleOrientedGraph mediumGraph;
    private SimpleOrientedGraph largeGraph;

    @BeforeEach
    void setUp() {
        smallGraph = new SimpleOrientedGraph(3);
        mediumGraph = new SimpleOrientedGraph(5);
        largeGraph = new SimpleOrientedGraph(10);

        smallGraph.AddVertex(1);
        smallGraph.AddVertex(2);

        mediumGraph.AddVertex(1);
        mediumGraph.AddVertex(2);
        mediumGraph.AddVertex(3);
        mediumGraph.AddVertex(4);

        largeGraph.AddVertex(1);
        largeGraph.AddVertex(2);
        largeGraph.AddVertex(3);
        largeGraph.AddVertex(4);
        largeGraph.AddVertex(5);
        largeGraph.AddVertex(6);
        largeGraph.AddVertex(7);
        largeGraph.AddVertex(8);
        largeGraph.AddVertex(9);
    }

    @Test
    void testAddVertex() {
        smallGraph.AddVertex(3);
        mediumGraph.AddVertex(5);
        largeGraph.AddVertex(10);

        assertEquals(smallGraph.max_vertex, smallGraph.currentSize);
        assertEquals(mediumGraph.max_vertex, mediumGraph.currentSize);
        assertEquals(largeGraph.max_vertex, largeGraph.currentSize);

        smallGraph.AddVertex(4);
        mediumGraph.AddVertex(6);
        largeGraph.AddVertex(11);
        assertEquals(smallGraph.max_vertex, smallGraph.currentSize);
        assertEquals(mediumGraph.max_vertex, mediumGraph.currentSize);
        assertEquals(largeGraph.max_vertex, largeGraph.currentSize);
    }

    @Test
    void testAddEdge() {
        smallGraph.AddEdge(0, 1);
        assertTrue(smallGraph.IsEdge(0, 1));
        assertFalse(smallGraph.IsEdge(1, 0));

        smallGraph.AddVertex(3);
        assertFalse(smallGraph.IsEdge(1, 2));

        smallGraph.AddEdge(1, 2);
        assertTrue(smallGraph.IsEdge(1, 2));
        assertFalse(smallGraph.IsEdge(2, 1));
    }

    @Test
    void testRemoveEdge() {
        smallGraph.AddEdge(0, 1);
        assertTrue(smallGraph.IsEdge(0, 1));

        smallGraph.RemoveEdge(0, 1);
        assertFalse(smallGraph.IsEdge(0, 1));

        smallGraph.RemoveEdge(1, 0);
        assertFalse(smallGraph.IsEdge(1, 0));
    }

    @Test
    void testRemoveVertex() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(0, 2);

        assertTrue(mediumGraph.IsEdge(0, 1));
        assertTrue(mediumGraph.IsEdge(0, 2));

        mediumGraph.RemoveVertex(0);

        assertFalse(mediumGraph.IsEdge(0, 1));
        assertFalse(mediumGraph.IsEdge(0, 2));
    }

    @Test
    void testOutOfBoundsAddVertex() {
        for (int i = 0; i < 5; i++) {
            largeGraph.AddVertex(i + 1);
        }
        assertEquals(largeGraph.max_vertex, largeGraph.currentSize);

        largeGraph.AddVertex(6);
        assertEquals(largeGraph.max_vertex, largeGraph.currentSize);
    }

    @Test
    void testOutOfBoundsAddEdge() {
        assertFalse(smallGraph.IsEdge(-1, 0));
        assertFalse(smallGraph.IsEdge(0, 3));

        smallGraph.AddEdge(0, 1);
        assertTrue(smallGraph.IsEdge(0, 1));

        assertFalse(smallGraph.IsEdge(1, 3));
    }

    @Test
    void testIsCyclic() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(2, 3);
        assertFalse(mediumGraph.IsCyclic());

        mediumGraph.AddEdge(3, 1);
        assertTrue(mediumGraph.IsCyclic());

        mediumGraph.RemoveEdge(3, 1);
        assertFalse(mediumGraph.IsCyclic());
    }

    @Test
    void testIsCyclicLargeGraph() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(2, 3);
        largeGraph.AddEdge(3, 4);
        largeGraph.AddEdge(4, 5);
        assertFalse(largeGraph.IsCyclic());

        largeGraph.AddEdge(5, 0);
        assertTrue(largeGraph.IsCyclic());
    }

    @Test
    void testFindMaxSimplePathNoEdges() {
        assertEquals(0, smallGraph.FindMaxSimplePath());
        assertEquals(0, mediumGraph.FindMaxSimplePath());
        assertEquals(0, largeGraph.FindMaxSimplePath());
    }

    @Test
    void testFindMaxSimplePathSmallGraph() {
        smallGraph.AddEdge(0, 1);
        assertEquals(1, smallGraph.FindMaxSimplePath());

        smallGraph.AddVertex(3);

        smallGraph.AddEdge(1, 2);
        assertEquals(2, smallGraph.FindMaxSimplePath());
    }

    @Test
    void testFindMaxSimplePathMediumCyclicGraph() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(2, 3);
        mediumGraph.AddEdge(3, 1);

        assertEquals(3, mediumGraph.FindMaxSimplePath());

        mediumGraph.AddVertex(5);

        mediumGraph.AddEdge(3, 4);
        assertEquals(4, mediumGraph.FindMaxSimplePath());
    }

    @Test
    void testFindMaxSimplePathLargeGraph() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(2, 3);
        largeGraph.AddEdge(3, 4);
        largeGraph.AddEdge(4, 5);
        largeGraph.AddEdge(5, 6);
        largeGraph.AddEdge(6, 7);
        largeGraph.AddEdge(7, 8);

        assertEquals(8, largeGraph.FindMaxSimplePath());

        largeGraph.AddEdge(3, 6);
        assertEquals(8, largeGraph.FindMaxSimplePath());
    }

    @Test
    void testFindMaxSimplePathDisconnectedGraph() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(2, 3);

        assertEquals(1, mediumGraph.FindMaxSimplePath());
    }

}
