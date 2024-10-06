package ru.ads.tree;

import java.util.*;

class aBST {
    public Integer[] Tree;

    public aBST(int depth) {
        int tree_size = (2 << depth) - 1;
        Tree = new Integer[tree_size];
        for (int i = 0; i < tree_size; i++) {
            Tree[i] = null;
        }
    }

    public Integer FindKeyIndex(int key) {
        return FindKeyIndexRecursive(key, 0);
    }

    private Integer FindKeyIndexRecursive(int key, int currentIndex) {
        if (currentIndex >= Tree.length) {
            return null;
        }
        if (Tree[currentIndex] == null) {
            return -currentIndex;
        }
        if (Tree[currentIndex] == key) {
            return currentIndex;
        }

        int nextIndex = (key < Tree[currentIndex] ? currentIndex * 2 + 1 : currentIndex * 2 + 2);
        return FindKeyIndexRecursive(key, nextIndex);
    }

    public int AddKey(int key) {
        Integer foundKeyPlace = FindKeyIndex(key);
        if (foundKeyPlace == null) {
            return -1;
        }
        if ((foundKeyPlace < 0) || ((foundKeyPlace == 0) && (Tree[foundKeyPlace] == null))) {
            foundKeyPlace = -foundKeyPlace;
            Tree[foundKeyPlace] = key;
        }
        return foundKeyPlace;
    }

    public Integer FindLCA(int firstKey, int secondKey) {
        return FindLCARecursive(firstKey, secondKey, 0);
    }

    private Integer FindLCARecursive(int firstKey, int secondKey, int currentIndex) {
        if ((currentIndex >= Tree.length) || (Tree[currentIndex] == null)) {
            return null;
        }

        int currentValue = Tree[currentIndex];

        if ((firstKey < currentValue) && (secondKey < currentValue)) {
            return FindLCARecursive(firstKey, secondKey, currentIndex * 2 + 1);
        }
        if ((firstKey > currentValue) && (secondKey > currentValue)) {
            return FindLCARecursive(firstKey, secondKey, currentIndex * 2 + 2);
        }
        return currentValue;
    }

    public Integer FindLCAIterative(int firstKey, int secondKey) {
        Integer firstKeyIndex = FindKeyIndex(firstKey);
        Integer secondKeyIndex = FindKeyIndex(secondKey);

        if (KeyIndexNotExists(firstKeyIndex) || KeyIndexNotExists(secondKeyIndex)) {
            return null;
        }

        while (!firstKeyIndex.equals(secondKeyIndex)) {
            if (firstKeyIndex > secondKeyIndex) {
                firstKeyIndex = GetParentIndex(firstKeyIndex);
            } else {
                secondKeyIndex = GetParentIndex(secondKeyIndex);
            }
        }
        return Tree[firstKeyIndex];
    }

    private boolean KeyIndexNotExists(Integer keyIndex) {
        return (keyIndex == null) || (keyIndex < 0);
    }

    private Integer GetParentIndex(Integer keyIndex) {
        if (keyIndex == 0) {
            return 0;
        }
        return (keyIndex - 1) / 2;
    }

    public ArrayList<Integer> WideAllNodes() {
        ArrayList<Integer> traversedNodes = new ArrayList<>();

        for (Integer key : Tree) {
            if (key != null) {
                traversedNodes.add(key);
            }
        }
        return traversedNodes;
    }

    public ArrayList<Integer> DeepAllNodes(int order) {
        ArrayList<Integer> traversedNodes = new ArrayList<>();
        DeepAllNodes(0, traversedNodes, order);
        return traversedNodes;
    }

    private void DeepAllNodes(int currentIndex, ArrayList<Integer> result, int order) {
        if ((currentIndex >= Tree.length) || (Tree[currentIndex] == null)) {
            return;
        }

        Integer currentValue = Tree[currentIndex];

        if (order == 2) {
            result.add(currentValue);
        }

        DeepAllNodes(currentIndex * 2 + 1, result, order);
        if (order == 0) {
            result.add(currentValue);
        }
        DeepAllNodes(currentIndex * 2 + 2, result, order);
        if (order == 1) {
            result.add(currentValue);
        }
    }

}
