package ru.ads.tree;

import java.util.*;

class Heap {
    public int[] HeapArray;
    public int currentSize = 0;

    public Heap() {
        HeapArray = null;
    }

    public void MakeHeap(int[] a, int depth) {
        if (a == null) {
            return;
        }

        HeapArray = new int[(2 << depth) - 1];

        for (int key : a) {
            if (!Add(key)) {
                return;
            }
        }
    }

    public int GetMax() {
        if (currentSize == 0) {
            return -1;
        }

        int rootValue = HeapArray[0];
        HeapArray[0] = HeapArray[currentSize - 1];
        HeapArray[currentSize - 1] = 0;
        currentSize--;

        SiftDown(0);

        return rootValue;
    }

    public int GetMaxSize() {
        return HeapArray.length;
    }

    private void SiftDown(int index) {
        int leftChildIndex = GetLeftChildIndex(index);
        int rightChildIndex = GetRightChildIndex(index);

        if (IndexOutOfBounds(leftChildIndex)) {
            return;
        }

        int largerChildIndex = leftChildIndex;
        if ((rightChildIndex < currentSize) &&
            (HeapArray[rightChildIndex] > HeapArray[leftChildIndex])) {
            largerChildIndex = rightChildIndex;
        }

        if (HeapArray[index] >= HeapArray[largerChildIndex]) {
            return;
        }

        SwapChildWithParent(index, largerChildIndex);

        SiftDown(largerChildIndex);
    }

    private void SwapChildWithParent(int parentIndex, int childIndex) {
        int parentValue = HeapArray[parentIndex];
        HeapArray[parentIndex] = HeapArray[childIndex];
        HeapArray[childIndex] = parentValue;
    }

    public boolean Add(int key) {
        if (currentSize == HeapArray.length) {
            return false;
        }

        HeapArray[currentSize] = key;

        SiftUp(currentSize);
        currentSize++;
        return true;
    }

    private void SiftUp(int index) {
        if (index <= 0) {
            return;
        }

        int parentIndex = GetParentIndex(index);
        if (HeapArray[index] > HeapArray[parentIndex]) {
            SwapChildWithParent(parentIndex, index);

            SiftUp(parentIndex);
        }
    }

    public boolean IsHeap() {
        if (HeapArray.length == 0) {
            return true;
        }

        int maxHeapSize = HeapArray.length;
        for (int i = 0; i <= (maxHeapSize - 2) / 2; i++) {
            int leftIndex = GetLeftChildIndex(i);
            int rightIndex = GetRightChildIndex(i);

            if ((leftIndex < maxHeapSize) && (HeapArray[i] < HeapArray[leftIndex])) {
                return false;
            }

            if ((rightIndex < maxHeapSize) && (HeapArray[i] < HeapArray[rightIndex])) {
                return false;
            }
        }
        return true;
    }

    public Integer GetMaxInRange(int leftIndex, int rightIndex) {
        if (IndexOutOfBounds(leftIndex) || IndexOutOfBounds(rightIndex)
            || (leftIndex > rightIndex)) {
            return null;
        }

        int max = HeapArray[leftIndex];
        for (int i = leftIndex + 1; i <= rightIndex; i++) {
            if (HeapArray[i] > max) {
                max = HeapArray[i];
            }
        }
        return max;
    }

    private boolean IndexOutOfBounds(int index) {
        return (index < 0) || (index >= currentSize);
    }

    private static int GetLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private static int GetRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private static int GetParentIndex(int index) {
        return (index - 1) / 2;
    }

    public List<Integer> FindElementsGreaterThan(int value) {
        List<Integer> result = new ArrayList<>();
        FindElementsGreaterThanRecursive(0, value, result);
        return result;
    }

    private void FindElementsGreaterThanRecursive(int index,
                                                  int value,
                                                  List<Integer> result) {
        if (IndexOutOfBounds(index)) {
            return;
        }

        int currentValue = HeapArray[index];

        if (currentValue > value) {
            result.add(currentValue);
            FindElementsGreaterThanRecursive(GetLeftChildIndex(index), value, result);
            FindElementsGreaterThanRecursive(GetRightChildIndex(index), value, result);
        }
    }

    public Integer FindElementLessThan(int value) {
        if (currentSize == 0) {
            return null;
        }

        int lastValueIndex = currentSize - 1;
        int firstLeafIndex = GetParentIndex(lastValueIndex) + 1;

        for (int i = lastValueIndex; i >= firstLeafIndex; i--) {
            int currentValue = HeapArray[i];
            if (currentValue < value) {
                return currentValue;
            }
        }
        return null;
    }

    public void MergeHeap(Heap heap) {
        if ((heap == null) || (this.GetMaxSize() < heap.GetMaxSize())) {
            return;
        }

        while (heap.currentSize > 0) {
            this.Add(heap.GetMax());
        }
    }

}
