package ru.ads.tree;

import java.util.*;

class aBST
{
    public Integer[] Tree;

    public aBST(int depth)
    {
        int tree_size = (2 << depth) - 1;
        Tree = new Integer[tree_size];
        for(int i = 0; i < tree_size; i++) {
            Tree[i] = null;
        }
    }

    public Integer FindKeyIndex(int key)
    {
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

    public int AddKey(int key)
    {
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

}
