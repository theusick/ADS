package ru.ads.tree;

import java.util.*;

class BSTNode<T> {
    public int NodeKey;
    public T NodeValue;
    public BSTNode<T> Parent;
    public BSTNode<T> LeftChild;
    public BSTNode<T> RightChild;

    public BSTNode(int key, T val, BSTNode<T> parent) {
        NodeKey = key;
        NodeValue = val;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }
}

class BSTFind<T> {
    public BSTNode<T> Node;
    public boolean NodeHasKey;
    public boolean ToLeft;

    public BSTFind() {
        Node = null;
    }
}

class BST<T> {
    BSTNode<T> Root;

    public BST(BSTNode<T> node) {
        Root = node;
    }

    public BSTFind<T> FindNodeByKey(int key) {
        return FindNodeRecursive(key, Root);
    }

    private BSTFind<T> FindNodeRecursive(int key, BSTNode<T> currentNode) {
        if (currentNode == null) {
            return new BSTFind<>();
        }

        if (key == currentNode.NodeKey) {
            return CreateBSTFindResult(currentNode, true, false);
        }

        if (key < currentNode.NodeKey) {
            return HandleLeftBranch(key, currentNode);
        }
        return HandleRightBranch(key, currentNode);
    }

    private BSTFind<T> HandleLeftBranch(int key, BSTNode<T> currentNode) {
        if (currentNode.LeftChild == null) {
            return CreateBSTFindResult(currentNode, false, true);
        }
        return FindNodeRecursive(key, currentNode.LeftChild);
    }

    private BSTFind<T> HandleRightBranch(int key, BSTNode<T> currentNode) {
        if (currentNode.RightChild == null) {
            return CreateBSTFindResult(currentNode, false, false);
        }
        return FindNodeRecursive(key, currentNode.RightChild);
    }

    private BSTFind<T> CreateBSTFindResult(BSTNode<T> node, boolean nodeHasKey, boolean toLeft) {
        BSTFind<T> result = new BSTFind<>();
        result.Node = node;
        result.NodeHasKey = nodeHasKey;
        result.ToLeft = toLeft;
        return result;
    }

    public boolean AddKeyValue(int key, T val) {
        if (Root == null) {
            Root = new BSTNode<>(key, val, null);
            return true;
        }

        BSTFind<T> findNodePlace = FindNodeByKey(key);
        if (findNodePlace.NodeHasKey) {
            return false;
        }

        if (findNodePlace.ToLeft) {
            findNodePlace.Node.LeftChild = new BSTNode<T>(key, val, findNodePlace.Node);
        } else {
            findNodePlace.Node.RightChild = new BSTNode<T>(key, val, findNodePlace.Node);
        }
        return true;
    }

    public BSTNode<T> FinMinMax(BSTNode<T> FromNode, boolean FindMax) {
        if (FromNode == null) {
            return null;
        }

        if (FindMax) {
            return FromNode.RightChild == null ? FromNode : FinMinMax(FromNode.RightChild, FindMax);
        }
        return FromNode.LeftChild == null ? FromNode : FinMinMax(FromNode.LeftChild, FindMax);
    }

    public boolean DeleteNodeByKey(int key) {
        BSTFind<T> foundNodeByKey = FindNodeByKey(key);
        if (!foundNodeByKey.NodeHasKey) {
            return false;
        }

        BSTNode<T> nodeToDelete = foundNodeByKey.Node;

        if ((nodeToDelete.LeftChild != null) && (nodeToDelete.RightChild == null)) {
            DeleteWithLeftSubtree(nodeToDelete);
            return true;
        }

        if (nodeToDelete.RightChild != null) {
            DeleteWithRightSubtree(nodeToDelete);
            return true;
        }

        ReplaceNodeInParent(nodeToDelete, null);
        CleanUpNode(nodeToDelete);
        return true;
    }

    private void DeleteWithLeftSubtree(BSTNode<T> nodeToDelete) {
        BSTNode<T> maxInLeftSubtree = FinMinMax(nodeToDelete.LeftChild, true);

        if (maxInLeftSubtree != nodeToDelete.LeftChild) {
            ReplaceNodeInParent(maxInLeftSubtree, maxInLeftSubtree.RightChild);
            maxInLeftSubtree.LeftChild = nodeToDelete.LeftChild;
            nodeToDelete.LeftChild.Parent = maxInLeftSubtree;
        }
        ReplaceNodeInParent(nodeToDelete, maxInLeftSubtree);

        CleanUpNode(nodeToDelete);
    }

    private void DeleteWithRightSubtree(BSTNode<T> nodeToDelete) {
        BSTNode<T> minInRightSubtree = FinMinMax(nodeToDelete.RightChild, false);

        if ((minInRightSubtree != null) && (minInRightSubtree != nodeToDelete.RightChild)) {
            ReplaceNodeInParent(minInRightSubtree, minInRightSubtree.RightChild);
            minInRightSubtree.RightChild = nodeToDelete.RightChild;
            nodeToDelete.RightChild.Parent = minInRightSubtree;
        }

        ReplaceNodeInParent(nodeToDelete, minInRightSubtree != null ? minInRightSubtree : nodeToDelete.RightChild);

        if (minInRightSubtree != null) {
            minInRightSubtree.LeftChild = nodeToDelete.LeftChild;
        }
        if ((minInRightSubtree != null) && (nodeToDelete.LeftChild != null)) {
            nodeToDelete.LeftChild.Parent = minInRightSubtree;
        }

        CleanUpNode(nodeToDelete);
    }

    private void ReplaceNodeInParent(BSTNode<T> targetNode, BSTNode<T> replaceNode) {
        if (targetNode.Parent == null) {
            Root = replaceNode;
        } else if (targetNode == targetNode.Parent.LeftChild) {
            targetNode.Parent.LeftChild = replaceNode;
        } else {
            targetNode.Parent.RightChild = replaceNode;
        }

        if (replaceNode != null) {
            replaceNode.Parent = targetNode.Parent;
        }
    }

    private void CleanUpNode(BSTNode<T> node) {
        node.Parent = null;
        node.LeftChild = null;
        node.RightChild = null;
    }

    public int Count() {
        return CountAllNodes(Root);
    }

    private int CountAllNodes(BSTNode<T> currentNode) {
        if (currentNode == null) {
            return 0;
        }
        return 1 + CountAllNodes(currentNode.LeftChild) + CountAllNodes(currentNode.RightChild);
    }

    public ArrayList<BSTNode> WideAllNodes() {
        ArrayList<BSTNode> traversedNodes = new ArrayList<>();
        Queue<BSTNode> queue = new LinkedList<>();

        queue.add(Root);
        WideAllNodes(queue, traversedNodes);

        return traversedNodes;
    }

    private void WideAllNodes(Queue<BSTNode> queue, ArrayList<BSTNode> result) {
        if (queue.isEmpty() || (queue.peek() == null)) {
            return;
        }

        BSTNode currentNode = queue.poll();
        result.add(currentNode);

        if (currentNode.LeftChild != null) {
            queue.add(currentNode.LeftChild);
        }
        if (currentNode.RightChild != null) {
            queue.add(currentNode.RightChild);
        }
        WideAllNodes(queue, result);
    }

    public ArrayList<BSTNode> DeepAllNodes(int order) {
        ArrayList<BSTNode> traversedNodes = new ArrayList<>();
        DeepAllNodes(Root, traversedNodes, order);
        return traversedNodes;
    }

    private void DeepAllNodes(BSTNode<T> currentNode, ArrayList<BSTNode> result, int order) {
        if ((currentNode == null) || (order < 0) || (order > 2)) {
            return;
        }

        if (order == 2) {
            result.add(currentNode);
        }

        if (currentNode.LeftChild != null) {
            DeepAllNodes(currentNode.LeftChild, result, order);
        }
        if (order == 0) {
            result.add(currentNode);
        }
        if (currentNode.RightChild != null) {
            DeepAllNodes(currentNode.RightChild, result, order);
        }

        if (order == 1) {
            result.add(currentNode);
        }
    }

    public boolean Equals(BST<T> tree) {
        return AreNodesIdentical(Root, tree.Root);
    }

    private boolean AreNodesIdentical(BSTNode<T> leftNode, BSTNode<T> rightNode) {
        if ((leftNode == null) && (rightNode == null)) {
            return true;
        }
        if (OneNodeIsNull(leftNode, rightNode) || (leftNode.NodeKey != rightNode.NodeKey)) {
            return false;
        }

        return AreNodesIdentical(leftNode.LeftChild, rightNode.LeftChild)
            && AreNodesIdentical(leftNode.RightChild, rightNode.RightChild);
    }

    private boolean OneNodeIsNull(BSTNode<T> leftNode, BSTNode<T> rightNode) {
        return ((leftNode == null) && (rightNode != null)) || ((leftNode != null) && (rightNode == null));
    }

    public ArrayList<ArrayList<BSTNode>> FindLeafPathsByLength(int pathLength) {
        ArrayList<ArrayList<BSTNode>> leafPathsByLength = new ArrayList<>();
        FindLeafPathsByLength(Root, pathLength, leafPathsByLength, new ArrayList<>());
        return leafPathsByLength;
    }

    private void FindLeafPathsByLength(BSTNode<T> currentNode,
                                       int pathLength,
                                       ArrayList<ArrayList<BSTNode>> result,
                                       ArrayList<BSTNode> currentPathNodes) {
        if ((currentNode == null) || (currentPathNodes.size() >= pathLength)) {
            return;
        }

        currentPathNodes.add(currentNode);

        if ((currentPathNodes.size() == pathLength) && IsLeaf(currentNode)) {
            result.add(new ArrayList<>(currentPathNodes));
        }

        FindLeafPathsByLength(currentNode.LeftChild, pathLength, result, currentPathNodes);
        FindLeafPathsByLength(currentNode.RightChild, pathLength, result, currentPathNodes);

        currentPathNodes.removeLast();
    }

    private boolean IsLeaf(BSTNode<T> node) {
        return (node.LeftChild == null) && (node.RightChild == null);
    }

    public ArrayList<ArrayList<BSTNode>> FindMaxSumLeafPaths() {
        ArrayList<ArrayList<BSTNode>> maxSumPaths = new ArrayList<>();
        int[] maxSum = new int[]{CalculateInitialMaxSumPath((BSTNode<? extends Number>) Root, 0)};
        FindMaxSumLeafPaths((BSTNode<? extends Number>) Root,
            0,
            maxSum,
            maxSumPaths,
            new ArrayList<>());
        return maxSumPaths;
    }

    private int CalculateInitialMaxSumPath(BSTNode<? extends Number> currentNode, int currentSum) {
        if (currentNode == null) {
            return currentSum;
        }
        currentSum += currentNode.NodeValue.intValue();
        return CalculateInitialMaxSumPath(currentNode.LeftChild, currentSum);
    }

    public void FindMaxSumLeafPaths(BSTNode<? extends Number> currentNode,
                                    int currentPathSum,
                                    int[] maxSum,
                                    ArrayList<ArrayList<BSTNode>> result,
                                    ArrayList<BSTNode> currentPathNodes) {
        if (currentNode == null) {
            return;
        }

        currentPathNodes.add(currentNode);
        currentPathSum += currentNode.NodeValue.intValue();


        if (IsLeaf((BSTNode<T>) currentNode) && (currentPathSum > maxSum[0])) {
            maxSum[0] = currentPathSum;
            result.clear();
            result.add(new ArrayList<>(currentPathNodes));
        } else if (IsLeaf((BSTNode<T>) currentNode) && (currentPathSum == maxSum[0])) {
            result.add(new ArrayList<>(currentPathNodes));
        }

        FindMaxSumLeafPaths(currentNode.LeftChild, currentPathSum, maxSum, result,
            currentPathNodes);
        FindMaxSumLeafPaths(currentNode.RightChild, currentPathSum, maxSum, result,
            currentPathNodes);

        currentPathNodes.removeLast();
    }

}
