package ru.ads.graph;

import java.util.*;

class Vertex {
    public int Value;
    public boolean Hit;

    public Vertex(int val) {
        Value = val;
        Hit = false;
    }

}

class TreeDiameterFind {
    public int mostDistantVertex;
    public int maxDistance;

    TreeDiameterFind(int mostDistantVertex) {
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
        TreeDiameterFind result = BreadthFirstSearch(0);
        return BreadthFirstSearch(result.mostDistantVertex).maxDistance;
    }

    private TreeDiameterFind BreadthFirstSearch(int VFrom) {
        if (IndexOutOfBounds(VFrom)) {
            return new TreeDiameterFind(-1);
        }

        Deque<Integer> queue = new ArrayDeque<>();
        ClearVisitedVertices();

        queue.addLast(VFrom);
        vertex[VFrom].Hit = true;

        TreeDiameterFind result = new TreeDiameterFind(VFrom);

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
            ProcessCyclesForVertex(vertexIndex, allCyclePaths);
        }
        return allCyclePaths;
    }

    private void ProcessCyclesForVertex(int vertexIndex,
                                        ArrayList<ArrayList<Integer>> allCyclePaths) {
        ArrayList<ArrayList<Integer>> currentVertexCycles = FindAllCyclesBFSFromVertex(vertexIndex);
        AddUniqueCycles(currentVertexCycles, allCyclePaths);
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

    private ArrayList<ArrayList<Integer>> FindAllCyclesBFSFromVertex(int vFrom) {
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
                                           Map<Integer, Integer> parents, Deque<Integer> queue) {
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
        return trianglesCount / 3;
    }

    private int CountVertexTriangles(int vertexIndex) {
        if (IndexOutOfBounds(vertexIndex)) {
            return 0;
        }

        ArrayList<Integer> neighbors = GetNeighborsList(vertexIndex);

        int trianglesCount = 0;
        for (int neighborIndex = 0; neighborIndex < neighbors.size(); neighborIndex++) {
            for (int nextNeighborIndex = neighborIndex + 1; nextNeighborIndex < neighbors.size();
                 nextNeighborIndex++) {

                if (IsEdge(neighbors.get(neighborIndex), neighbors.get(nextNeighborIndex))) {
                    trianglesCount++;
                }
            }
        }
        return trianglesCount;
    }

    public ArrayList<Vertex> WeakVerticesByInterface() {
        ArrayList<Vertex> weakVertices = new ArrayList<>();
        ArrayList<ArrayList<Integer>> allCyclePaths = FindAllCyclePaths();

        for (int vertexIndex = 0; vertexIndex < currentSize; vertexIndex++) {
            if (!IsVertexInCycleTriangle(vertexIndex, allCyclePaths)) {
                weakVertices.add(vertex[vertexIndex]);
            }
        }

        return weakVertices;
    }

    private boolean IsVertexInCycleTriangle(int vertexIndex,
                                            ArrayList<ArrayList<Integer>> allCyclePaths) {
        if (IndexOutOfBounds(vertexIndex)) {
            return false;
        }

        for (ArrayList<Integer> cycle : allCyclePaths) {
            if ((cycle.size() == 4) && cycle.contains(vertexIndex)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Vertex> FindWeakVerticesOptimized() {
        int[][] squaredMatrix = SquareAdjacencyMatrix();
        return CollectWeakVertices(squaredMatrix);
    }

    private int[][] SquareAdjacencyMatrix() {
        int[][] squaredMatrix = new int[currentSize][currentSize];
        for (int i = 0; i < currentSize; i++) {
            for (int j = 0; j < currentSize; j++) {
                squaredMatrix[i][j] = CalculateMatrixSquareValue(i, j);
            }
        }
        return squaredMatrix;
    }

    private int CalculateMatrixSquareValue(int row, int col) {
        int value = 0;
        for (int k = 0; k < currentSize; k++) {
            value += m_adjacency[row][k] * m_adjacency[k][col];
        }
        return value;
    }

    private ArrayList<Vertex> CollectWeakVertices(int[][] squaredMatrix) {
        ArrayList<Vertex> weakVertices = new ArrayList<>();
        for (int i = 0; i < currentSize; i++) {
            if (IsWeakVertex(squaredMatrix, i)) {
                weakVertices.add(vertex[i]);
            }
        }
        return weakVertices;
    }

    private boolean IsWeakVertex(int[][] squaredMatrix, int vertexIndex) {
        for (int j = 0; j < currentSize; j++) {
            if ((m_adjacency[vertexIndex][j] == 1) && (squaredMatrix[vertexIndex][j] > 0)) {
                return false;
            }
        }
        return true;
    }

}
