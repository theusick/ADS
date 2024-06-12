package ru.ads.dictionary;

import java.lang.reflect.Array;

class NativeDictionary<T> {
    public int size;
    public String[] slots;
    public T[] values;
    public final int step = 3;

    public NativeDictionary(int sz, Class clazz) {
        size = sz;
        slots = new String[size];
        values = (T[]) Array.newInstance(clazz, this.size);
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

    public void put(String key, T value) {
        if (key == null) {
            return;
        }

        int index = hashFun(key);
        if ((slots[index] == null) || (slots[index].equals(key))) {
            slots[index] = key;
            values[index] = value;
            return;
        }

        for (int i = 1; i < size; i++) {
            index = (index + i * step) % size;
            if (slots[index] == null) {
                slots[index] = key;
                values[index] = value;
                return;
            }
        }
    }

    public T get(String key) {
        if (key == null) {
            return null;
        }

        int index = hashFun(key);
        for (int i = 0; i < size; i++) {
            if ((slots[index] != null) && (slots[index].equals(key))) {
                return values[index];
            }
            index = (index + i * step) % size;
        }
        return null;
    }
}


