package ru.ads.tree;

import java.lang.reflect.Array;
import java.util.*;

public class SimpleTreeNode<T> {
    public T NodeValue;
    public SimpleTreeNode<T> Parent;
    public List<SimpleTreeNode<T>> Children;
    public int Level;

    public SimpleTreeNode(T val, SimpleTreeNode<T> parent) {
        NodeValue = val;
        Parent = parent;
        Children = null;
        Level = 0;
    }

}

class SimpleTree<T> {
    public SimpleTreeNode<T> Root;

    public SimpleTree(SimpleTreeNode<T> root) {
        Root = root;
    }

    public void AddChild(SimpleTreeNode<T> ParentNode, SimpleTreeNode<T> NewChild) {
        if (NewChild == null) {
            return;
        }
        if (ParentNode == null) {
            Root = NewChild;
            return;
        }

        if (ParentNode.Children == null) {
            ParentNode.Children = new ArrayList<>();
        }
        ParentNode.Children.add(NewChild);
        NewChild.Parent = ParentNode;
        NewChild.Level = ParentNode.Level + 1;

        UpdateChildrenLevels(NewChild);
    }

    private void UpdateChildrenLevels(SimpleTreeNode<T> node) {
        if ((node == null) || (node.Children == null)) {
            return;
        }

        for (SimpleTreeNode<T> child : node.Children) {
            child.Level = node.Level + 1;
            UpdateChildrenLevels(child);
        }
    }

    public void DeleteNode(SimpleTreeNode<T> NodeToDelete) {
        if ((NodeToDelete == null) || (Root == NodeToDelete)) {
            return;
        }

        DeleteChildrenNodes(NodeToDelete);

        if ((NodeToDelete.Parent != null) && (NodeToDelete.Parent.Children != null)) {
            NodeToDelete.Parent.Children.remove(NodeToDelete);
            NodeToDelete.Parent = null;
        }
    }

    private void DeleteChildrenNodes(SimpleTreeNode<T> node) {
        if (node.Children == null) {
            return;
        }

        for (SimpleTreeNode<T> child : new ArrayList<>(node.Children)) {
            DeleteNode(child);
        }
        node.Children = null;
    }

    public List<SimpleTreeNode<T>> GetAllNodes() {
        List<SimpleTreeNode<T>> nodes = new ArrayList<>();
        GetAllNodes(Root, nodes);
        return nodes;
    }

    private void GetAllNodes(SimpleTreeNode<T> currentNode, List<SimpleTreeNode<T>> nodes) {
        if (currentNode == null) {
            return;
        }

        nodes.add(currentNode);

        if (currentNode.Children == null) {
            return;
        }
        for (SimpleTreeNode<T> child : currentNode.Children) {
            GetAllNodes(child, nodes);
        }
    }

    public List<SimpleTreeNode<T>> FindNodesByValue(T val) {
        List<SimpleTreeNode<T>> nodesByValue = new ArrayList<>();
        FindNodesByValue(Root, val, nodesByValue);
        return nodesByValue;
    }

    private void FindNodesByValue(SimpleTreeNode<T> currentNode, T val, List<SimpleTreeNode<T>> nodes) {
        if (currentNode == null) {
            return;
        }

        if (currentNode.NodeValue.equals(val)) {
            nodes.add(currentNode);
        }

        if (currentNode.Children == null) {
            return;
        }
        for (SimpleTreeNode<T> child : currentNode.Children) {
            FindNodesByValue(child, val, nodes);
        }
    }

    public void MoveNode(SimpleTreeNode<T> OriginalNode, SimpleTreeNode<T> NewParent) {
        if ((OriginalNode == null) || (NewParent == null)) {
            return;
        }
        if ((OriginalNode == Root) || (OriginalNode == NewParent) || isAncestor(OriginalNode, NewParent)) {
            return;
        }

        if ((OriginalNode.Parent != null) && (OriginalNode.Parent.Children != null)) {
            OriginalNode.Parent.Children.remove(OriginalNode);
        }
        AddChild(NewParent, OriginalNode);
    }

    private boolean isAncestor(SimpleTreeNode<T> node, SimpleTreeNode<T> potentialChild) {
        if (node == null) {
            return false;
        }
        if (node == potentialChild) {
            return true;
        }
        if (node.Children == null) {
            return false;
        }

        for (SimpleTreeNode<T> child : node.Children) {
            if (isAncestor(child, potentialChild)) {
                return true;
            }
        }
        return false;
    }

    public int Count() {
        return CountAllNodes(Root);
    }

    private int CountAllNodes(SimpleTreeNode<T> currentNode) {
        if (currentNode == null) {
            return 0;
        }

        if ((currentNode.Children == null) || currentNode.Children.isEmpty()) {
            return 1;
        }

        int nodesCounter = 1;
        for (SimpleTreeNode<T> child : currentNode.Children) {
            nodesCounter += CountAllNodes(child);
        }
        return nodesCounter;
    }

    public int LeafCount() {
        return CountLeafs(Root);
    }

    private int CountLeafs(SimpleTreeNode<T> currentNode) {
        if (currentNode == null) {
            return 0;
        }

        if ((currentNode.Children == null) || currentNode.Children.isEmpty()) {
            return 1;
        }

        int leafsCounter = 0;
        for (SimpleTreeNode<T> child : currentNode.Children) {
            leafsCounter += CountLeafs(child);
        }
        return leafsCounter;
    }

    public void AssignNodesLevels() {
        AssignNodesLevels(Root, 0);
    }

    private void AssignNodesLevels(SimpleTreeNode<T> currentNode, int currentLevel) {
        if (currentNode == null) {
            return;
        }

        currentNode.Level = currentLevel;

        if (currentNode.Children == null) {
            return;
        }
        for (SimpleTreeNode<T> child : currentNode.Children) {
            AssignNodesLevels(child, currentLevel + 1);
        }
    }

    public ArrayList<T> EvenTrees() {
        return GetMaxEdgeCutPairs(Root);
    }

    public ArrayList<T> GetMaxEdgeCutPairs(SimpleTreeNode<T> node) {
        ArrayList<T> edgeCutPairs = new ArrayList<>();

        int currentNodesCount = CountAllNodes(node);
        if (currentNodesCount <= 1) {
            return edgeCutPairs;
        }

        if ((currentNodesCount % 2 == 0) && (node.Parent != null)) {
            edgeCutPairs.add(node.Parent.NodeValue);
            edgeCutPairs.add(node.NodeValue);
        }

        for (SimpleTreeNode<T> child : node.Children) {
            edgeCutPairs.addAll(GetMaxEdgeCutPairs(child));
        }
        return edgeCutPairs;
    }

}
