import java.util.*;

class Vertex {
    public int Value;
    public boolean Hit;

    public Vertex(int val) {
        Value = val;
        Hit = false;
    }

}

class SimpleGraph {
    Vertex[] vertex;
    int[][] m_adjacency;
    int max_vertex;
    int currentSize = 0;

    public SimpleGraph(int size) {
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
        return (m_adjacency[v1][v2] == 1) || (m_adjacency[v2][v1] == 1);
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
        m_adjacency[v2][v1] = 1;
    }

    public void RemoveEdge(int v1, int v2) {
        if (OneIsOutOfBounds(v1, v2)) {
            return;
        }
        m_adjacency[v1][v2] = 0;
        m_adjacency[v2][v1] = 0;
    }

    public ArrayList<Vertex> DepthFirstSearch(int vFrom, int vTo)
    {
        Deque<Integer> stack = new ArrayDeque<>();

        ClearVisitedVertices();
        DepthFirstSearch(vFrom, vTo, stack);

        ArrayList<Vertex> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(vertex[stack.removeLast()]);
        }
        return result;
    }

    private void ClearVisitedVertices() {
        for (int i = 0; i < currentSize; i++) {
            vertex[i].Hit = false;
        }
    }

    private void DepthFirstSearch(int vFrom,
                                  int vTo,
                                  Deque<Integer> stack) {
        if (OneIsOutOfBounds(vFrom, vTo)) {
            return;
        }

        vertex[vFrom].Hit = true;
        stack.addFirst(vFrom);

        if (IsEdge(vFrom, vTo)) {
            stack.addFirst(vTo);
            return;
        }

        for (int neighborIndex = 0; neighborIndex < currentSize; neighborIndex++) {
            if (IsEdge(vFrom, neighborIndex) && (!vertex[neighborIndex].Hit)) {
                DepthFirstSearch(neighborIndex, vTo, stack);
                return;
            }
        }
        stack.pop();

        if (stack.isEmpty()) {
            return;
        }
        DepthFirstSearch(stack.pop(), vTo, stack);
    }

}
