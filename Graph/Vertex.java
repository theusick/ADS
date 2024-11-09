import java.util.*;

class Vertex {
    public int Value;
    public boolean Hit;

    public Vertex(int val) {
        Value = val;
        Hit = false;
    }

}

class FarthestNodeFind {
    public int mostDistantVertex;
    public int maxDistance;

    FarthestNodeFind(int mostDistantVertex) {
        this.mostDistantVertex = mostDistantVertex;
        maxDistance = 0;
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

    public ArrayList<Vertex> DepthFirstSearch(int vFrom, int vTo) {
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
        if (currentSize == 0) {
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

    public ArrayList<Vertex> BreadthFirstSearch(int VFrom, int VTo) {
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

        while (!queue.isEmpty()) {
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

    public int FindGraphTreeDiameter() {
        int mostDistantVertexFromRoot = FindMostDistantVertexBFS(0).mostDistantVertex;

        if (mostDistantVertexFromRoot == -1) {
            return 0;
        }
        return FindMostDistantVertexBFS(mostDistantVertexFromRoot).maxDistance;
    }

    private FarthestNodeFind FindMostDistantVertexBFS(int VFrom) {
        if (IndexOutOfBounds(VFrom)) {
            return new FarthestNodeFind(-1);
        }

        Deque<Integer> queue = new ArrayDeque<>();
        ClearVisitedVertices();

        queue.addLast(VFrom);
        vertex[VFrom].Hit = true;

        FarthestNodeFind result = new FarthestNodeFind(VFrom);

        while (!queue.isEmpty()) {
            int treeLevelSize = queue.size();
            result.maxDistance++;

            int mostDistantVertexOnLevel = ProcessBFSTreeLevel(treeLevelSize, queue);
            result.mostDistantVertex = mostDistantVertexOnLevel < 0 ?
                    result.mostDistantVertex : mostDistantVertexOnLevel;
        }
        return result;
    }

    private int ProcessBFSTreeLevel(int levelSize, Deque<Integer> queue) {
        int mostDistantVertex = -1;

        for (int currentSize = 0; currentSize < levelSize; currentSize++) {
            int currentIndex = queue.removeFirst();
            mostDistantVertex = currentIndex;
            ProcessBFSNeighbors(currentIndex, queue);
        }
        return mostDistantVertex;
    }

    private void ProcessBFSNeighbors(int currentIndex, Deque<Integer> queue) {
        for (int neighborIndex = 0; neighborIndex < currentSize; neighborIndex++) {
            if (IsEdge(currentIndex, neighborIndex) && !vertex[neighborIndex].Hit) {
                queue.addLast(neighborIndex);
                vertex[neighborIndex].Hit = true;
            }
        }
    }

    public ArrayList<ArrayList<Integer>> FindAllCyclePaths() {
        ArrayList<ArrayList<Integer>> allCyclePaths = new ArrayList<>();

        if (currentSize <= 2) {
            return allCyclePaths;
        }

        for (int vertexIndex = 0; vertexIndex < currentSize; vertexIndex++) {
            ClearVisitedVertices();
            ArrayList<ArrayList<Integer>> currentVertexCycles = FindAllCyclesBFS(vertexIndex);
            AddUniqueCycles(currentVertexCycles, allCyclePaths);
        }
        return allCyclePaths;
    }

    private void AddUniqueCycles(ArrayList<ArrayList<Integer>> currentVertexCycles,
                                 ArrayList<ArrayList<Integer>> allCyclePaths) {
        for (ArrayList<Integer> cycle : currentVertexCycles) {
            if (!IsCycleDuplicate(allCyclePaths, cycle)
                    && cycle.getFirst().equals(cycle.getLast())) {
                allCyclePaths.add(cycle);
            }
        }
    }

    private ArrayList<ArrayList<Integer>> FindAllCyclesBFS(int vFrom) {
        ArrayList<ArrayList<Integer>> cyclePaths = new ArrayList<>();

        if (IndexOutOfBounds(vFrom)) {
            return cyclePaths;
        }

        Deque<Integer> queue = new ArrayDeque<>();
        Map<Integer, Integer> parents = new HashMap<>();

        queue.addLast(vFrom);
        vertex[vFrom].Hit = true;
        parents.put(vFrom, null);

        while (!queue.isEmpty()) {
            int currentVertex = queue.removeFirst();
            ProcessNeighborsForCycles(currentVertex, cyclePaths, parents, queue);
        }
        return cyclePaths;
    }

    private void ProcessNeighborsForCycles(int currentIndex,
                                           ArrayList<ArrayList<Integer>> cyclePaths,
                                           Map<Integer, Integer> parents,
                                           Deque<Integer> queue) {
        for (int neighborIndex : GetNeighborsList(currentIndex)) {
            if (!vertex[neighborIndex].Hit) {
                queue.addLast(neighborIndex);
                vertex[neighborIndex].Hit = true;
                parents.put(neighborIndex, currentIndex);
            } else if (neighborIndex != parents.get(currentIndex)) {
                cyclePaths.add(BuildCyclePath(neighborIndex, currentIndex, parents));
            }
        }
    }

    private ArrayList<Integer> BuildCyclePath(int startIndex,
                                              int endIndex,
                                              Map<Integer, Integer> parents) {
        ArrayList<Integer> path = new ArrayList<>();
        Set<Integer> pathSet = new HashSet<>();

        for (Integer currentIndex = startIndex; currentIndex != null;
             currentIndex = parents.get(currentIndex)) {
            path.addFirst(currentIndex);
            pathSet.add(currentIndex);
        }

        Integer currentIndex = endIndex;
        while (!pathSet.contains(currentIndex)) {
            path.add(currentIndex);
            currentIndex = parents.get(currentIndex);
        }
        path.add(currentIndex);

        return path;
    }

    private boolean IsCycleDuplicate(ArrayList<ArrayList<Integer>> allCyclePaths,
                                     ArrayList<Integer> newCycle) {
        Set<Integer> newCycleSet = new HashSet<>(newCycle);
        for (ArrayList<Integer> cycle : allCyclePaths) {
            if ((cycle.size() == newCycle.size()) && newCycleSet.containsAll(cycle)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Vertex> WeakVertices() {
        ArrayList<Vertex> weakVertices = new ArrayList<>();

        for (int vertexIndex = 0; vertexIndex < currentSize; vertexIndex++) {
            if (IsNotInTriangle(vertexIndex)) {
                weakVertices.add(vertex[vertexIndex]);
            }
        }
        return weakVertices;
    }

    private ArrayList<Integer> GetNeighborsList(int vertexIndex) {
        if (IndexOutOfBounds(vertexIndex)) {
            return new ArrayList<>();
        }

        ArrayList<Integer> neighbors = new ArrayList<>();

        for (int neighborIndex = 0; neighborIndex < currentSize; neighborIndex++) {
            if ((vertexIndex != neighborIndex) && IsEdge(vertexIndex, neighborIndex)) {
                neighbors.add(neighborIndex);
            }
        }
        return neighbors;
    }

    private boolean IsNotInTriangle(int vertexIndex) {
        if (IndexOutOfBounds(vertexIndex)) {
            return false;
        }

        ArrayList<Integer> vertexNeighbours = GetNeighborsList(vertexIndex);
        for (int neighborIndex : vertexNeighbours) {
            if (HasTriangleEdgeWith(neighborIndex, vertexNeighbours)) {
                return false;
            }
        }
        return true;
    }

    private boolean HasTriangleEdgeWith(int vertexIndex, ArrayList<Integer> vertexNeighbours) {
        for (int neighborIndex : vertexNeighbours) {
            if ((vertexIndex != neighborIndex) && IsEdge(vertexIndex, neighborIndex)) {
                return true;
            }
        }
        return false;
    }

    public int CountTriangles() {
        if (currentSize <= 2) {
            return 0;
        }

        int trianglesCount = 0;
        for (int vertexIndex = 0; vertexIndex < currentSize; vertexIndex++) {
            trianglesCount += CountVertexTriangles(vertexIndex);
        }
        return trianglesCount;
    }

    private int CountVertexTriangles(int vertexIndex) {
        if (IndexOutOfBounds(vertexIndex)) {
            return 0;
        }

        ArrayList<Integer> neighbors = GetNeighborsList(vertexIndex);

        int trianglesCount = 0;
        for (int neighborIndex = 0; neighborIndex < neighbors.size(); neighborIndex++) {
            int neighborVertexIndex = neighbors.get(neighborIndex);

            if (vertexIndex < neighborVertexIndex) {
                trianglesCount +=
                        CountTrianglesWithNeighborPair(vertexIndex, neighborIndex, neighbors);
            }
        }
        return trianglesCount;
    }

    private int CountTrianglesWithNeighborPair(int vertexIndex,
                                               int neighborIndex,
                                               ArrayList<Integer> neighbors) {
        int neighborVertexIndex = neighbors.get(neighborIndex);

        int trianglesCount = 0;
        for (int nextNeighborIndex = neighborIndex + 1; nextNeighborIndex < neighbors.size();
             nextNeighborIndex++) {
            int nextNeighborVertexIndex = neighbors.get(nextNeighborIndex);

            if ((vertexIndex < nextNeighborVertexIndex)
                    && IsEdge(neighborVertexIndex, nextNeighborVertexIndex)) {
                trianglesCount++;
            }
        }
        return trianglesCount;
    }

    public ArrayList<Vertex> WeakVerticesByInterface() {
        ArrayList<Vertex> weakVertices = new ArrayList<>();
        ArrayList<ArrayList<Integer>> allCyclePaths = FindAllCyclePaths();

        Set<Integer> verticesInTriangles = GetVerticesFromTriangleCycles(allCyclePaths);

        for (int vertexIndex = 0; vertexIndex < currentSize; vertexIndex++) {
            if (!verticesInTriangles.contains(vertexIndex)) {
                weakVertices.add(vertex[vertexIndex]);
            }
        }
        return weakVertices;
    }

    private Set<Integer> GetVerticesFromTriangleCycles(ArrayList<ArrayList<Integer>> allCyclePaths) {
        Set<Integer> verticesInTriangleCycles = new HashSet<>();

        for (ArrayList<Integer> cycle : allCyclePaths) {
            if (cycle.size() == 4) {
                verticesInTriangleCycles.addAll(cycle);
            }
        }
        return verticesInTriangleCycles;
    }

    public ArrayList<Vertex> FindWeakVerticesOptimized() {
        ArrayList<Vertex> weakVertices = new ArrayList<>();

        for (int vertexIndex = 0; vertexIndex < currentSize; vertexIndex++) {
            if (IsWeakVertex(vertexIndex)) {
                weakVertices.add(vertex[vertexIndex]);
            }
        }
        return weakVertices;
    }

    private boolean IsWeakVertex(int vertexIndex) {
        for (int neighborIndex = 0; neighborIndex < currentSize; neighborIndex++) {
            if (m_adjacency[vertexIndex][neighborIndex] == 1) {
                for (int nextNeighborIndex = 0; nextNeighborIndex < currentSize;
                     nextNeighborIndex++) {
                    if ((m_adjacency[vertexIndex][nextNeighborIndex] == 1)
                            && (m_adjacency[neighborIndex][nextNeighborIndex] == 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
