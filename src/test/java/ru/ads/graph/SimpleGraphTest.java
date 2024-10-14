package ru.ads.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

}
