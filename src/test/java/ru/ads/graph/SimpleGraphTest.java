package ru.ads.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
    void testDepthFirstSearchLargeTreeGraph() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(2, 3);
        largeGraph.AddEdge(1, 4);
        largeGraph.AddEdge(4, 5);
        largeGraph.AddEdge(1, 6);
        largeGraph.AddEdge(6, 7);
        largeGraph.AddEdge(7, 8);

        List<Vertex> path = largeGraph.DepthFirstSearch(0, 8);
        assertEquals(5, path.size());
        assertEquals(1, path.getFirst().Value);
        assertEquals(9, path.getLast().Value);
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

        // One-vertex graph is connected as all vertices are reachable
        assertTrue(singleVertexGraph.IsGraphConnected());
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

    @Test
    void testIsGraphConnectedLargeTreeGraph() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(2, 3);
        largeGraph.AddEdge(1, 4);
        largeGraph.AddEdge(4, 5);
        largeGraph.AddEdge(1, 6);
        largeGraph.AddEdge(6, 7);
        largeGraph.AddEdge(7, 8);

        assertTrue(largeGraph.IsGraphConnected());

        largeGraph.RemoveEdge(1, 6);

        assertFalse(largeGraph.IsGraphConnected());
    }

    @Test
    void testBreadthFirstSearchSmallGraph() {
        List<Vertex> path = smallGraph.BreadthFirstSearch(0, 1);
        assertTrue(path.isEmpty());

        smallGraph.AddEdge(0, 1);

        path = smallGraph.BreadthFirstSearch(0, 1);
        assertEquals(2, path.size());
        assertEquals(1, path.get(0).Value);
        assertEquals(2, path.get(1).Value);

        path = smallGraph.BreadthFirstSearch(1, 0);
        assertEquals(2, path.size());
        assertEquals(2, path.get(0).Value);
        assertEquals(1, path.get(1).Value);

        path = smallGraph.BreadthFirstSearch(0, 2);
        assertTrue(path.isEmpty());
    }

    @Test
    void testBreadthFirstSearchMediumGraph() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(1, 3);

        List<Vertex> path = mediumGraph.BreadthFirstSearch(0, 3);
        assertEquals(3, path.size());
        assertEquals(1, path.get(0).Value);
        assertEquals(2, path.get(1).Value);
        assertEquals(4, path.get(2).Value);

        path = mediumGraph.BreadthFirstSearch(3, 0);
        assertEquals(3, path.size());
        assertEquals(4, path.get(0).Value);
        assertEquals(2, path.get(1).Value);
        assertEquals(1, path.get(2).Value);

        path = mediumGraph.BreadthFirstSearch(0, 4);
        assertTrue(path.isEmpty());
    }

    @Test
    void testBreadthFirstSearchLargeGraph() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(0, 2);
        largeGraph.AddEdge(1, 3);
        largeGraph.AddEdge(2, 4);
        largeGraph.AddEdge(3, 5);
        largeGraph.AddEdge(4, 5);
        largeGraph.AddEdge(5, 6);
        largeGraph.AddEdge(6, 7);
        largeGraph.AddEdge(7, 8);

        List<Vertex> path = largeGraph.BreadthFirstSearch(0, 8);
        assertEquals(7, path.size());
        assertEquals(1, path.getFirst().Value);
        assertEquals(2, path.get(1).Value);
        assertEquals(4, path.get(2).Value);
        assertEquals(6, path.get(3).Value);
        assertEquals(7, path.get(4).Value);
        assertEquals(8, path.get(5).Value);
        assertEquals(9, path.getLast().Value);

        path = largeGraph.BreadthFirstSearch(8, 0);
        assertEquals(7, path.size());

        path = largeGraph.BreadthFirstSearch(2, 6);
        assertEquals(4, path.size());

        path = largeGraph.BreadthFirstSearch(0, 7);
        assertEquals(6, path.size());
    }

    @Test
    void testBreadthFirstSearchDisconnectedGraph() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(2, 3);

        List<Vertex> path = mediumGraph.BreadthFirstSearch(0, 3);
        assertTrue(path.isEmpty());

        path = mediumGraph.BreadthFirstSearch(1, 2);
        assertTrue(path.isEmpty());
    }

    @Test
    void testFindGraphTreeDiameterEmptyGraph() {
        SimpleGraph emptyGraph = new SimpleGraph(0);
        assertEquals(0, emptyGraph.FindGraphTreeDiameter());
    }

    @Test
    void testFindGraphTreeDiameterSingleVertex() {
        SimpleGraph singleVertexGraph = new SimpleGraph(1);
        singleVertexGraph.AddVertex(1);
        assertEquals(1, singleVertexGraph.FindGraphTreeDiameter());
    }

    @Test
    void testFindGraphTreeDiameterSmallGraph() {
        smallGraph.AddEdge(0, 1);
        assertEquals(2, smallGraph.FindGraphTreeDiameter());
    }

    @Test
    void testFindGraphTreeDiameterLinearTree() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(2, 3);
        mediumGraph.AddEdge(3, 4);
        assertEquals(4, mediumGraph.FindGraphTreeDiameter());
    }

    @Test
    void testFindGraphTreeDiameterBalancedTree() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(0, 2);
        largeGraph.AddEdge(1, 3);
        largeGraph.AddEdge(1, 4);
        largeGraph.AddEdge(2, 5);
        largeGraph.AddEdge(2, 6);
        assertEquals(5, largeGraph.FindGraphTreeDiameter());
    }

    @Test
    void testFindGraphTreeDiameterUnbalancedTree() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(2, 3);
        largeGraph.AddEdge(3, 4);
        largeGraph.AddEdge(1, 5);
        largeGraph.AddEdge(5, 6);
        largeGraph.AddEdge(6, 7);
        assertEquals(7, largeGraph.FindGraphTreeDiameter());
    }

    @Test
    void testWeakVerticesTriangle() {
        smallGraph.AddVertex(3);

        smallGraph.AddEdge(0, 1);
        smallGraph.AddEdge(1, 2);
        smallGraph.AddEdge(2, 0);

        List<Vertex> weakVertices = smallGraph.WeakVertices();
        assertTrue(weakVertices.isEmpty());
    }

    @Test
    void testWeakVerticesSingleWeakVertex() {
        mediumGraph.AddVertex(5);

        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(2, 0);
        mediumGraph.AddEdge(3, 4);

        List<Vertex> weakVertices = mediumGraph.WeakVertices();
        assertEquals(2, weakVertices.size());
        assertEquals(4, weakVertices.getFirst().Value);
        assertEquals(5, weakVertices.getLast().Value);
    }

    @Test
    void testWeakVerticesIsolatedVertices() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(2, 0);
        largeGraph.AddEdge(3, 4);
        largeGraph.AddEdge(5, 6);

        List<Vertex> weakVertices = largeGraph.WeakVertices();
        assertEquals(6, weakVertices.size());
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 4));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 5));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 6));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 7));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 8));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 9));
    }

    @Test
    void testWeakVerticesNoEdges() {
        List<Vertex> weakVertices = mediumGraph.WeakVertices();
        assertEquals(4, weakVertices.size());
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 1));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 2));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 3));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 4));
    }

    @Test
    void testWeakVerticesFullyConnected() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(0, 2);
        mediumGraph.AddEdge(0, 3);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(1, 3);
        mediumGraph.AddEdge(2, 3);

        List<Vertex> weakVertices = mediumGraph.WeakVertices();
        assertTrue(weakVertices.isEmpty());
    }

    @Test
    void testWeakVerticesLargeGraph() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(0, 2);
        largeGraph.AddEdge(0, 3);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(1, 4);
        largeGraph.AddEdge(2, 3);
        largeGraph.AddEdge(2, 5);
        largeGraph.AddEdge(4, 5);
        largeGraph.AddEdge(5, 6);
        largeGraph.AddEdge(6, 7);
        largeGraph.AddEdge(5, 7);
        largeGraph.AddEdge(7, 8);


        List<Vertex> weakVertices = largeGraph.WeakVertices();
        assertEquals(2, weakVertices.size());
        assertEquals(5, weakVertices.getFirst().Value);
        assertEquals(9, weakVertices.getLast().Value);
    }

    @Test
    void testFindAllCyclePathsIsolatedVertices() {
        ArrayList<ArrayList<Integer>> cycles = largeGraph.FindAllCyclePaths();
        assertTrue(cycles.isEmpty());
    }

    @Test
    void testFindAllCyclePathsNoCycles() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(2, 3);
        mediumGraph.AddEdge(3, 4);

        ArrayList<ArrayList<Integer>> cycles = mediumGraph.FindAllCyclePaths();

        assertTrue(cycles.isEmpty());
    }

    @Test
    void testFindAllCyclePathsSingleCycle() {
        smallGraph.AddVertex(3);
        smallGraph.AddEdge(0, 1);
        smallGraph.AddEdge(1, 2);
        smallGraph.AddEdge(2, 0);

        ArrayList<ArrayList<Integer>> cycles = smallGraph.FindAllCyclePaths();

        assertEquals(1, cycles.size());
        assertTrue(cycles.get(0).containsAll(List.of(0, 1, 2)));
    }

    @Test
    void testFindAllCyclePathsMultipleCycles() {
        mediumGraph.AddVertex(5);

        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(2, 0);
        mediumGraph.AddEdge(1, 3);
        mediumGraph.AddEdge(3, 4);
        mediumGraph.AddEdge(4, 1);

        ArrayList<ArrayList<Integer>> cycles = mediumGraph.FindAllCyclePaths();

        assertEquals(2, cycles.size());
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(0, 1, 2))));
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(1, 3, 4))));
    }

    @Test
    void testFindAllCyclePathsFullyConnectedGraph() {
        mediumGraph.AddVertex(5);

        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(0, 2);
        mediumGraph.AddEdge(0, 3);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(1, 3);
        mediumGraph.AddEdge(2, 3);
        mediumGraph.AddEdge(3, 4);

        ArrayList<ArrayList<Integer>> cycles = mediumGraph.FindAllCyclePaths();

        assertEquals(4, cycles.size());
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(0, 1, 2))));
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(0, 3, 2))));
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(0, 1, 3))));
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(1, 2, 3))));
    }

    @Test
    void testFindAllCyclePathsLargeGraphWithTreeStructure() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(0, 2);
        largeGraph.AddEdge(1, 3);
        largeGraph.AddEdge(1, 4);
        largeGraph.AddEdge(2, 5);
        largeGraph.AddEdge(2, 6);

        ArrayList<ArrayList<Integer>> cycles = largeGraph.FindAllCyclePaths();

        assertTrue(cycles.isEmpty());
    }

    @Test
    void testFindAllCyclePathsComplexGraph() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(0, 2);
        largeGraph.AddEdge(0, 3);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(1, 4);
        largeGraph.AddEdge(2, 3);
        largeGraph.AddEdge(2, 5);
        largeGraph.AddEdge(4, 5);
        largeGraph.AddEdge(5, 6);
        largeGraph.AddEdge(6, 7);
        largeGraph.AddEdge(5, 7);
        largeGraph.AddEdge(7, 8);

        ArrayList<ArrayList<Integer>> cycles = largeGraph.FindAllCyclePaths();

        assertEquals(7, cycles.size());
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(0, 1, 2))));
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(0, 2, 3))));
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(0, 2, 5, 4, 1))));
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(1, 2, 5, 4))));
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(0, 1, 2, 3))));
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(3, 0, 1, 4, 5, 2))));
        assertTrue(cycles.stream().anyMatch(cycle -> cycle.containsAll(List.of(5, 6, 7))));
    }

    @Test
    void testCountTrianglesEmptyGraph() {
        SimpleGraph emptyGraph = new SimpleGraph(0);
        assertEquals(0, emptyGraph.CountTriangles());
    }

    @Test
    void testCountTrianglesSingleVertex() {
        SimpleGraph singleVertexGraph = new SimpleGraph(1);
        singleVertexGraph.AddVertex(1);
        assertEquals(0, singleVertexGraph.CountTriangles());
    }

    @Test
    void testCountTrianglesNoEdges() {
        assertEquals(0, mediumGraph.CountTriangles());
    }

    @Test
    void testCountTrianglesSingleTriangle() {
        smallGraph.AddVertex(3);

        smallGraph.AddEdge(0, 1);
        smallGraph.AddEdge(1, 2);
        smallGraph.AddEdge(2, 0);

        assertEquals(1, smallGraph.CountTriangles());
    }

    @Test
    void testCountTrianglesMultipleTriangles() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(2, 0);
        mediumGraph.AddEdge(1, 3);
        mediumGraph.AddEdge(2, 3);

        assertEquals(2, mediumGraph.CountTriangles());
    }

    @Test
    void testCountTrianglesFullyConnected() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(0, 2);
        mediumGraph.AddEdge(0, 3);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(1, 3);
        mediumGraph.AddEdge(2, 3);

        assertEquals(4, mediumGraph.CountTriangles());
    }

    @Test
    void testCountTrianglesLargeGraph() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(0, 2);
        largeGraph.AddEdge(0, 3);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(1, 4);
        largeGraph.AddEdge(2, 3);
        largeGraph.AddEdge(2, 5);
        largeGraph.AddEdge(4, 5);
        largeGraph.AddEdge(5, 6);
        largeGraph.AddEdge(6, 7);
        largeGraph.AddEdge(5, 7);
        largeGraph.AddEdge(7, 8);

        assertEquals(3, largeGraph.CountTriangles());
    }

    @Test
    void testWeakVerticesByInterfaceTriangle() {
        smallGraph.AddVertex(3);

        smallGraph.AddEdge(0, 1);
        smallGraph.AddEdge(1, 2);
        smallGraph.AddEdge(2, 0);

        List<Vertex> weakVertices = smallGraph.WeakVerticesByInterface();
        assertTrue(weakVertices.isEmpty());
    }

    @Test
    void testWeakVerticesByInterfaceSingleWeakVertex() {
        mediumGraph.AddVertex(5);

        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(2, 0);
        mediumGraph.AddEdge(3, 4);

        List<Vertex> weakVertices = mediumGraph.WeakVerticesByInterface();
        assertEquals(2, weakVertices.size());
        assertEquals(4, weakVertices.get(0).Value);
        assertEquals(5, weakVertices.get(1).Value);
    }

    @Test
    void testWeakVerticesByInterfaceIsolatedVertices() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(2, 0);
        largeGraph.AddEdge(3, 4);
        largeGraph.AddEdge(5, 6);

        List<Vertex> weakVertices = largeGraph.WeakVerticesByInterface();
        assertEquals(6, weakVertices.size());
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 4));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 5));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 6));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 7));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 8));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 9));
    }

    @Test
    void testWeakVerticesByInterfaceNoEdges() {
        List<Vertex> weakVertices = mediumGraph.WeakVerticesByInterface();
        assertEquals(4, weakVertices.size());
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 1));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 2));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 3));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 4));
    }

    @Test
    void testWeakVerticesByInterfaceFullyConnected() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(0, 2);
        mediumGraph.AddEdge(0, 3);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(1, 3);
        mediumGraph.AddEdge(2, 3);

        List<Vertex> weakVertices = mediumGraph.WeakVerticesByInterface();
        assertTrue(weakVertices.isEmpty());
    }

    @Test
    void testWeakVerticesByInterfaceLargeGraph() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(0, 2);
        largeGraph.AddEdge(0, 3);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(1, 4);
        largeGraph.AddEdge(2, 3);
        largeGraph.AddEdge(2, 5);
        largeGraph.AddEdge(4, 5);
        largeGraph.AddEdge(5, 6);
        largeGraph.AddEdge(6, 7);
        largeGraph.AddEdge(5, 7);
        largeGraph.AddEdge(7, 8);

        List<Vertex> weakVertices = largeGraph.WeakVerticesByInterface();
        assertEquals(2, weakVertices.size());
        assertEquals(5, weakVertices.get(0).Value);
        assertEquals(9, weakVertices.get(1).Value);
    }

    @Test
    void testFindWeakVerticesOptimizedEmptyGraph() {
        SimpleGraph emptyGraph = new SimpleGraph(0);
        assertTrue(emptyGraph.FindWeakVerticesOptimized().isEmpty());
    }

    @Test
    void testFindWeakVerticesOptimizedSingleVertex() {
        SimpleGraph singleVertexGraph = new SimpleGraph(1);
        singleVertexGraph.AddVertex(1);
        assertEquals(1, singleVertexGraph.FindWeakVerticesOptimized().size());
    }

    @Test
    void testFindWeakVerticesOptimizedNoEdges() {
        List<Vertex> weakVertices = mediumGraph.FindWeakVerticesOptimized();
        assertEquals(4, weakVertices.size());
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 1));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 2));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 3));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 4));
    }

    @Test
    void testFindWeakVerticesOptimizedSingleTriangle() {
        smallGraph.AddVertex(3);
        smallGraph.AddEdge(0, 1);
        smallGraph.AddEdge(1, 2);
        smallGraph.AddEdge(2, 0);
        List<Vertex> weakVertices = smallGraph.FindWeakVerticesOptimized();
        assertTrue(weakVertices.isEmpty());
    }

    @Test
    void testFindWeakVerticesOptimizedMixedConnections() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(2, 0);
        largeGraph.AddEdge(3, 4);
        largeGraph.AddEdge(4, 5);

        List<Vertex> weakVertices = largeGraph.FindWeakVerticesOptimized();

        assertEquals(6, weakVertices.size());
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 4));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 5));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 6));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 7));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 8));
        assertTrue(weakVertices.stream().anyMatch(v -> v.Value == 9));
    }

    @Test
    void testFindWeakVerticesOptimizedFullyConnected() {
        mediumGraph.AddEdge(0, 1);
        mediumGraph.AddEdge(0, 2);
        mediumGraph.AddEdge(0, 3);
        mediumGraph.AddEdge(1, 2);
        mediumGraph.AddEdge(1, 3);
        mediumGraph.AddEdge(2, 3);
        List<Vertex> weakVertices = mediumGraph.FindWeakVerticesOptimized();
        assertTrue(weakVertices.isEmpty());
    }

    @Test
    void testFindWeakVerticesOptimizedLargeGraph() {
        largeGraph.AddEdge(0, 1);
        largeGraph.AddEdge(0, 2);
        largeGraph.AddEdge(0, 3);
        largeGraph.AddEdge(1, 2);
        largeGraph.AddEdge(1, 4);
        largeGraph.AddEdge(2, 3);
        largeGraph.AddEdge(2, 5);
        largeGraph.AddEdge(4, 5);
        largeGraph.AddEdge(5, 6);
        largeGraph.AddEdge(6, 7);
        largeGraph.AddEdge(5, 7);
        largeGraph.AddEdge(7, 8);


        List<Vertex> weakVertices = largeGraph.FindWeakVerticesOptimized();
        assertEquals(2, weakVertices.size());
        assertEquals(5, weakVertices.getFirst().Value);
        assertEquals(9, weakVertices.getLast().Value);
    }

}