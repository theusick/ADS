package ru.ads.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleGraphTest {

    private SimpleGraph smallGraph;
    private SimpleGraph mediumGraph;
    private SimpleGraph largeGraph;

    @BeforeEach
    void setUp() {
        smallGraph = new SimpleGraph(3);
        mediumGraph = new SimpleGraph(5);
        largeGraph = new SimpleGraph(10);

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
        assertFalse(smallGraph.IsEdge(1, 2));

        smallGraph.AddVertex(3);
        smallGraph.AddEdge(1, 2);
        assertTrue(smallGraph.IsEdge(1, 2));
    }

    @Test
    void testRemoveEdge() {
        smallGraph.AddEdge(0, 1);
        assertTrue(smallGraph.IsEdge(0, 1));

        smallGraph.RemoveEdge(0, 1);
        assertFalse(smallGraph.IsEdge(0, 1));
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
    void testOutOfBoundsRemoveEdge() {
        assertDoesNotThrow(() -> smallGraph.RemoveEdge(-1, 0));
        assertDoesNotThrow(() -> smallGraph.RemoveEdge(0, 3));
        smallGraph.AddEdge(0, 1);
        smallGraph.RemoveEdge(0, 1);
        assertFalse(smallGraph.IsEdge(0, 1));
    }

    @Test
    void testOutOfBoundsRemoveVertex() {
        assertDoesNotThrow(() -> smallGraph.RemoveVertex(-1));
        assertDoesNotThrow(() -> smallGraph.RemoveVertex(3));
        smallGraph.RemoveVertex(0);
        assertNull(smallGraph.vertex[0]);
    }

    @Test
    void testDepthFirstSearchSmallGraph() {
        List<Vertex> path = smallGraph.DepthFirstSearch(0, 1);
        assertTrue(path.isEmpty());

        smallGraph.AddEdge(0, 1);

        path = smallGraph.DepthFirstSearch(0, 1);
        assertEquals(2, path.size());
        assertEquals(1, path.getFirst().Value);
        assertEquals(2, path.getLast().Value);

        path = smallGraph.DepthFirstSearch(1, 0);
        assertEquals(2, path.size());
        assertEquals(2, path.getFirst().Value);
        assertEquals(1, path.getLast().Value);

        path = smallGraph.DepthFirstSearch(0, 2);
        assertTrue(path.isEmpty());
    }

    @Test
    void testDepthFirstSearchMediumGraph() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(0, 3);

        List<Vertex> path = mediumGraph.DepthFirstSearch(0, 2);
        assertEquals(3, path.size());
        assertEquals(1, path.getFirst().Value);
        assertEquals(3, path.getLast().Value);

        path = mediumGraph.DepthFirstSearch(0, 4);
        assertTrue(path.isEmpty());

        path = mediumGraph.DepthFirstSearch(1, 3);
        assertEquals(3, path.size());
        assertEquals(2, path.getFirst().Value);
        assertEquals(4, path.getLast().Value);

        path = mediumGraph.DepthFirstSearch(2, 0);
        assertEquals(3, path.size());
        assertEquals(3, path.getFirst().Value);
        assertEquals(1, path.getLast().Value);
    }

    @Test
    void testDepthFirstSearchLargeGraph() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(2, 3);
        largeGraph.AddEdge(3, 4);
        largeGraph.AddEdge(0, 5);
        largeGraph.AddEdge(5, 6);
        largeGraph.AddEdge(6, 7);

        List<Vertex> path = largeGraph.DepthFirstSearch(0, 3);
        assertEquals(4, path.size());
        assertEquals(1, path.getFirst().Value);
        assertEquals(4, path.getLast().Value);

        path = largeGraph.DepthFirstSearch(5, 7);
        assertEquals(3, path.size());
        assertEquals(6, path.getFirst().Value);
        assertEquals(8, path.getLast().Value);

        path = largeGraph.DepthFirstSearch(2, 8);
        assertTrue(path.isEmpty());
    }

    @Test
    void testIsGraphConnectedEmptyGraph() {
        SimpleGraph emptyGraph = new SimpleGraph(0);

        assertFalse(emptyGraph.IsGraphConnected());
    }

    @Test
    void testIsGraphConnectedSingleVertex() {
        SimpleGraph singleVertexGraph = new SimpleGraph(1);
        singleVertexGraph.AddVertex(1);

        assertFalse(singleVertexGraph.IsGraphConnected());
    }

    @Test
    void testIsGraphConnectedSmallGraph() {
        assertFalse(smallGraph.IsGraphConnected());

        smallGraph.AddEdge(0, 1);
        smallGraph.AddEdge(1, 2);

        assertTrue(smallGraph.IsGraphConnected());
    }

    @Test
    void testIsGraphConnectedMediumGraph() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(1, 2);

        assertFalse(mediumGraph.IsGraphConnected());

        mediumGraph.AddEdge(2, 3);
        mediumGraph.AddEdge(3, 4);

        assertTrue(mediumGraph.IsGraphConnected());
    }

    @Test
    void testIsGraphConnectedLargeGraph() {
        for (int i = 0; i < largeGraph.currentSize - 1; i++) {
            largeGraph.AddEdge(i, i + 1);
        }

        assertTrue(largeGraph.IsGraphConnected());

        largeGraph.RemoveEdge(0, 1);

        assertFalse(largeGraph.IsGraphConnected());
    }

}
