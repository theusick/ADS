package ru.ads.graph;

import java.util.*;

public class SimpleOrientedGraph {
    Vertex[] vertex;
    int[][] m_adjacency;
    int max_vertex;
    int currentSize = 0;

    public SimpleOrientedGraph(int size) {
        max_vertex = size;
        m_adjacency = new int[size][size];
        vertex = new Vertex[size];
    }

    public void AddVertex(int value) {
        if (currentSize < max_vertex) {
            vertex[currentSize] = new Vertex(value);
            currentSize++;
        }
    }

    public void RemoveVertex(int v) {
        if (IndexOutOfBounds(v)) {
            return;
        }

        for (int i = 0; i < max_vertex; i++) {
            m_adjacency[v][i] = 0;
            m_adjacency[i][v] = 0;
        }
        vertex[v] = null;
        currentSize--;
    }

    public boolean IsEdge(int v1, int v2) {
        if (OneIsOutOfBounds(v1, v2)) {
            return false;
        }
        return m_adjacency[v1][v2] == 1;
    }

    private boolean IndexOutOfBounds(int index) {
        return (index < 0) || (index >= currentSize);
    }

    private boolean OneIsOutOfBounds(int v1, int v2) {
        return (IndexOutOfBounds(v1) || IndexOutOfBounds(v2));
    }

    public void AddEdge(int v1, int v2) {
        if (OneIsOutOfBounds(v1, v2)) {
            return;
        }
        m_adjacency[v1][v2] = 1;
    }

    public void RemoveEdge(int v1, int v2) {
        if (OneIsOutOfBounds(v1, v2)) {
            return;
        }
        m_adjacency[v1][v2] = 0;
    }

    public boolean IsCyclic() {
        boolean[] visited = new boolean[currentSize];
        boolean[] recursionStack = new boolean[currentSize];

        for (int i = 0; i < currentSize; i++) {
            visited[i] = false;
            recursionStack[i] = false;
        }

        for (int vertexIndex = 0; vertexIndex < currentSize; vertexIndex++) {
            if (IsCyclic(vertexIndex, visited, recursionStack)) {
                return true;
            }
        }
        return false;
    }

    private boolean IsCyclic(int v, boolean[] visited, boolean[] recursionStack) {
        if (recursionStack[v]) {
            return true;
        }

        if (visited[v]) {
            return false;
        }

        visited[v] = true;
        recursionStack[v] = true;

        for (int neighborIndex = 0; neighborIndex < max_vertex; neighborIndex++) {
            if (IsEdge(v, neighborIndex) && IsCyclic(neighborIndex, visited, recursionStack)) {
                return true;
            }
        }

        recursionStack[v] = false;
        return false;
    }

    public int FindMaxSimplePath() {
        boolean[] visited = new boolean[currentSize];
        int maxLength = 0;

        for (int vertexIndex = 0; vertexIndex < currentSize; vertexIndex++) {
            Arrays.fill(visited, false);
            maxLength = Math.max(maxLength, DepthFirstSearch(vertexIndex, visited, 0));
        }
        return maxLength;
    }

    private int DepthFirstSearch(int v, boolean[] visited, int pathLength) {
        if (IndexOutOfBounds(v)) {
            return 0;
        }

        visited[v] = true;
        int maxLength = pathLength;

        for (int neighborIndex = 0; neighborIndex < currentSize; neighborIndex++) {
            if (IsEdge(v, neighborIndex) && !visited[neighborIndex]) {
                maxLength = Math.max(maxLength,
                        DepthFirstSearch(neighborIndex, visited, pathLength + 1));
            }
        }

        visited[v] = false;
        return maxLength;
    }

}
