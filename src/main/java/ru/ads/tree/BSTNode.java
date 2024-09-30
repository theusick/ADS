package ru.ads.tree;

import java.util.*;

class BSTNode<T extends Number> {
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

class BSTFind<T extends Number> {
    public BSTNode<T> Node;
    public boolean NodeHasKey;
    public boolean ToLeft;

    public BSTFind() {
        Node = null;
    }
}

class BST<T extends Number> {
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
            return HandleBranch(currentNode.LeftChild, key, currentNode, true);
        }
        return HandleBranch(currentNode.RightChild, key, currentNode, false);
    }

    private BSTFind<T> HandleBranch(BSTNode<T> childNode, int key, BSTNode<T> parentNode,
                                    boolean toLeft) {
        if (childNode == null) {
            return CreateBSTFindResult(parentNode, false, toLeft);
        }
        return FindNodeRecursive(key, childNode);
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

        BSTFind<T> foundNodePlace = FindNodeByKey(key);
        if (foundNodePlace.NodeHasKey) {
            return false;
        }

        if (foundNodePlace.ToLeft) {
            foundNodePlace.Node.LeftChild = new BSTNode<T>(key, val, foundNodePlace.Node);
        } else {
            foundNodePlace.Node.RightChild = new BSTNode<T>(key, val, foundNodePlace.Node);
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
        int[] maxSum = new int[]{CalculateInitialMaxSumPath(Root, 0)};
        FindMaxSumLeafPaths(Root, 0, maxSum, maxSumPaths, new ArrayList<>());
        return maxSumPaths;
    }

    private int CalculateInitialMaxSumPath(BSTNode<T> currentNode, int currentSum) {
        if (currentNode == null) {
            return currentSum;
        }
        currentSum += currentNode.NodeValue.intValue();
        return CalculateInitialMaxSumPath(currentNode.LeftChild, currentSum);
    }

    public void FindMaxSumLeafPaths(BSTNode<T> currentNode,
                                    int currentPathSum,
                                    int[] maxSum,
                                    ArrayList<ArrayList<BSTNode>> result,
                                    ArrayList<BSTNode> currentPathNodes) {
        if (currentNode == null) {
            return;
        }

        currentPathNodes.add(currentNode);
        currentPathSum += currentNode.NodeValue.intValue();


        if (IsLeaf(currentNode) && (currentPathSum > maxSum[0])) {
            maxSum[0] = currentPathSum;
            result.clear();
            result.add(new ArrayList<>(currentPathNodes));
        } else if (IsLeaf(currentNode) && (currentPathSum == maxSum[0])) {
            result.add(new ArrayList<>(currentPathNodes));
        }

        FindMaxSumLeafPaths(currentNode.LeftChild, currentPathSum, maxSum, result,
            currentPathNodes);
        FindMaxSumLeafPaths(currentNode.RightChild, currentPathSum, maxSum, result,
            currentPathNodes);

        currentPathNodes.removeLast();
    }

    public void InvertBST() {
        InvertNodesRecursive(Root);
    }

    private void InvertNodesRecursive(BSTNode<T> currentNode) {
        if ((currentNode == null) || IsLeaf(currentNode)) {
            return;
        }

        BSTNode<T> leftChild = currentNode.LeftChild;
        currentNode.LeftChild = currentNode.RightChild;
        currentNode.RightChild = leftChild;

        InvertNodesRecursive(currentNode.LeftChild);
        InvertNodesRecursive(currentNode.RightChild);
    }

    public Integer FindMaxSumLevel() {
        if (Root == null) {
            return null;
        }

        Queue<BSTNode> queue = new LinkedList<>();
        Integer initialMaxSum = Root.NodeValue.intValue();

        queue.add(Root);

        return FindMaxSumLevel(queue, initialMaxSum, 0, 0);
    }

    private Integer FindMaxSumLevel(Queue<BSTNode> queue,
                                    Integer currentMaxSum,
                                    int currentMaxLevel,
                                    int currentLevel) {
        if (queue.isEmpty()) {
            return currentMaxLevel;
        }

        int levelSum = 0;
        int levelSize = queue.size();

        for (int i = 0; i < levelSize; i++) {
            BSTNode<T> currentNode = queue.poll();
            if (currentNode == null) {
                continue;
            }
            levelSum += currentNode.NodeValue.intValue();

            if (currentNode.LeftChild != null) {
                queue.add(currentNode.LeftChild);
            }
            if (currentNode.RightChild != null) {
                queue.add(currentNode.RightChild);
            }
        }

        if (levelSum >= currentMaxSum) {
            currentMaxSum = levelSum;
            currentMaxLevel = currentLevel;
        }

        return FindMaxSumLevel(queue, currentMaxSum, currentMaxLevel, currentLevel + 1);
    }

    public void BuildBST(int[] preorderTree, int[] inorderTree) {
        if ((preorderTree.length == 0)
            || (inorderTree.length == 0)
            || ArraysNotMatch(preorderTree, inorderTree)) {
            return;
        }

        Root = BuildRoot(preorderTree,
                        inorderTree,
                        0,
                        0,
                        inorderTree.length);
    }

    private boolean ArraysNotMatch(int[] preorderTree, int[] inorderTree) {
        return (preorderTree.length != inorderTree.length);
    }

    private BSTNode<T> BuildRoot(int[] preorderTree,
                                 int[] inorderTree,
                                 int preorderStartIndex,
                                 int inorderStartIndex,
                                 int inorderEndIndex) {
        if ((preorderStartIndex >= preorderTree.length) || (inorderStartIndex >= inorderEndIndex)) {
            return null;
        }

        int rootKey = preorderTree[preorderStartIndex];
        BSTNode<T> root = new BSTNode<>(rootKey, null, null);

        int rootIndexInInorder = findRootIndexInInorder(inorderTree,
                                                        rootKey,
                                                        inorderStartIndex,
                                                        inorderEndIndex);
        int leftTreeSize = rootIndexInInorder - inorderStartIndex;

        root.LeftChild = BuildRoot(preorderTree,
                                    inorderTree,
                                    preorderStartIndex + 1,
                                    inorderStartIndex,
                                    rootIndexInInorder);
        root.RightChild = BuildRoot(preorderTree,
                                    inorderTree,
                                    preorderStartIndex + 1 + leftTreeSize,
                                    rootIndexInInorder + 1, inorderEndIndex);

        return root;
    }

    private int findRootIndexInInorder(int[] inorderTree,
                                       int rootKey,
                                       int startIndex,
                                       int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            if (inorderTree[i] == rootKey) {
                return i;
            }
        }
        return -1;
    }

}
