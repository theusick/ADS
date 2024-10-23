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
        return (index < 0) || (index >= max_vertex);
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
        boolean[] visited = new boolean[max_vertex];
        boolean[] recursion = new boolean[max_vertex];

        for (int i = 0; i < max_vertex; i++) {
            visited[i] = false;
            recursion[i] = false;
        }

        for (int i = 0; i < max_vertex; i++) {
            if (IsCyclic(i, visited, recursion)) {
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

        for (int i = 0; i < max_vertex; i++) {
            if ((m_adjacency[v][i] == 1) && (IsCyclic(i, visited, recursionStack))) {
                return true;
            }
        }

        recursionStack[v] = false;
        return false;
    }

}
