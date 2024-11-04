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

        if (!stack.isEmpty()) {
            DepthFirstSearch(stack.pop(), vTo, stack);
        }
    }

    public boolean IsGraphConnected() {
        if (currentSize <= 1) {
            return false;
        }

        ClearVisitedVertices();
        DepthFirstSearch(0);

        return IsAllVerticesVisited();
    }

    private void DepthFirstSearch(int vFrom) {
        if (IndexOutOfBounds(vFrom)) {
            return;
        }

        vertex[vFrom].Hit = true;

        for (int neighborIndex = 0; neighborIndex < currentSize; neighborIndex++) {
            if (IsEdge(vFrom, neighborIndex) && (!vertex[neighborIndex].Hit)) {
                DepthFirstSearch(neighborIndex);
            }
        }
    }

    private boolean IsAllVerticesVisited() {
        for (int vertexIndex = 0; vertexIndex < currentSize; vertexIndex++) {
            if (!vertex[vertexIndex].Hit) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Vertex> BreadthFirstSearch(int VFrom, int VTo)
    {
        Deque<Integer> queue = new ArrayDeque<>();
        Map<Integer, Integer> visitedPathVertices = new HashMap<>();

        ClearVisitedVertices();
        BreadthFirstSearch(VFrom, VTo, visitedPathVertices, queue);
        return BuildBFSPath(VTo, visitedPathVertices);
    }

    private void BreadthFirstSearch(int VFrom,
                                    int VTo,
                                    Map<Integer, Integer> parents,
                                    Deque<Integer> queue) {
        if (OneIsOutOfBounds(VFrom, VTo)) {
            return;
        }

        queue.addLast(VFrom);
        vertex[VFrom].Hit = true;
        parents.put(VFrom, null);

        while(!queue.isEmpty()) {
            int currentIndex = queue.removeFirst();

            if (currentIndex == VTo) {
                return;
            }
            ProcessBFSNeighbors(currentIndex, queue, parents);
        }
    }

    private void ProcessBFSNeighbors(int currentIndex,
                                     Deque<Integer> queue,
                                     Map<Integer, Integer> parents) {
        for (int neighborIndex = 0; neighborIndex < currentSize; neighborIndex++) {
            if (IsEdge(currentIndex, neighborIndex) && !vertex[neighborIndex].Hit) {
                queue.addLast(neighborIndex);
                vertex[neighborIndex].Hit = true;
                parents.put(neighborIndex, currentIndex);
            }
        }
    }

    private ArrayList<Vertex> BuildBFSPath(int VTo, Map<Integer, Integer> parents) {
        ArrayList<Vertex> resultPath = new ArrayList<>();

        Integer vertexIndex = VTo;
        while (vertexIndex != null) {
            resultPath.addFirst(vertex[vertexIndex]);
            vertexIndex = parents.get(vertexIndex);
        }

        if (!resultPath.isEmpty() && (resultPath.getFirst() == vertex[VTo])) {
            resultPath.clear();
        }
        return resultPath;
    }

}
