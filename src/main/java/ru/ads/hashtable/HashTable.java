package ru.ads.hashtable;

public class HashTable {
    public int size;
    public int step;
    public String[] slots;

    public HashTable(int sz, int stp) {
        size = sz;
        step = stp;
        slots = new String[size];
        for (int i = 0; i < size; i++) slots[i] = null;
    }

    public int hashFun(String value) {
        int hash = 0;
        if (value == null) {
            return hash;
        }

        for (int i = 0; i < value.length(); i++) {
            hash += value.charAt(i);
        }
        return hash % size;
    }

    public int seekSlot(String value) {
        if (value == null) {
            return -1;
        }

        int index = hashFun(value);
        if (slots[index] == null) {
            return index;
        }

        for (int i = 1; i < size; i++) {
            int newIndex = (index + i * step) % size;
            if (slots[newIndex] == null) {
                return newIndex;
            }
        }
        return -1;
    }

    public int put(String value) {
        int index = seekSlot(value);
        if (index != -1) {
            slots[index] = value;
        }
        return index;
    }

    public int find(String value) {
        if (value == null) {
            return -1;
        }

        int index = hashFun(value);
        for (int i = 0; i < size; i++) {
            int newIndex = (index + i * step) % size;
            if (slots[newIndex] == null) {
                return -1;
            }
            if (slots[newIndex].equals(value)) {
                return newIndex;
            }
        }
        return -1;
    }
}


