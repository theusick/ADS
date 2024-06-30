package ru.ads.cache;

import java.lang.reflect.Array;

class NativeCache<T> {
    public int size;
    public String[] slots;
    public T[] values;
    public int[] hits;

    public final int step = 3;

    public NativeCache(int size, Class clazz) {
        this.size = size;

        slots = new String[this.size];
        values = (T[]) Array.newInstance(clazz, this.size);
        hits = new int[this.size];
    }

    public int hashFun(String key) {
        int hash = 0;
        if (key == null) {
            return hash;
        }

        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
        }
        return hash % size;
    }

    public void put(String key, T value) {
        if (key == null) {
            return;
        }

        int index = hashFun(key);
        if ((slots[index] == null) || (slots[index].equals(key))) {
            putValue(index, key, value);
            return;
        }

        int minUsageIndex = index;

        for (int i = 1; i < size; i++) {
            index = (index + i * step) % size;
            if (slots[index] == null) {
                putValue(index, key, value);
                return;
            }

            if (hits[index] < hits[minUsageIndex]) {
                minUsageIndex = index;
            }
        }
        putValue(minUsageIndex, key, value);
    }

    public boolean isKey(String key) {
        if (key == null) {
            return false;
        }

        int index = hashFun(key);
        for (int i = 0; i < size; i++) {
            if (slots[index] == null) {
                return false;
            }
            if (slots[index].equals(key)) {
                return true;
            }
            index = (index + i * step) % size;
        }
        return false;
    }

    public T get(String key) {
        if (key == null) {
            return null;
        }

        int index = hashFun(key);
        for (int i = 0; i < size; i++) {
            if ((slots[index] != null) && (slots[index].equals(key))) {
                hits[index]++;
                return values[index];
            }
            index = (index + i * step) % size;
        }
        return null;
    }

    private void putValue(int index, String key, T value) {
        slots[index] = key;
        values[index] = value;
        hits[index] = 0;
    }

}


