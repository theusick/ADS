package ru.ads.tree;

import java.io.*;
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

}
