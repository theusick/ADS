package ru.ads.tree;

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

    private void SiftDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        if (leftChild >= currentSize) {
            return;
        }

        int largerChild = leftChild;
        if (rightChild < currentSize && HeapArray[rightChild] > HeapArray[leftChild]) {
            largerChild = rightChild;
        }

        if (HeapArray[index] >= HeapArray[largerChild]) {
            return;
        }

        SwapChildWithParent(index, largerChild);

        SiftDown(largerChild);
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

        int parentIndex = (index - 1) / 2;
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
            int leftIndex = 2 * i + 1;
            int rightIndex = 2 * i + 2;

            if ((leftIndex < maxHeapSize) && (HeapArray[i] < HeapArray[leftIndex])) {
                return false;
            }

            if ((rightIndex < maxHeapSize) && (HeapArray[i] < HeapArray[rightIndex])) {
                return false;
            }
        }
        return true;
    }

}
