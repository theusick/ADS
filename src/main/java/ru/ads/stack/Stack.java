package ru.ads.stack;

import java.util.*;

public class Stack<T> {
    public List<T> data;

    public Stack() {
        data = new LinkedList<T>();
    }

    public int size() {
        return data.size();
    }

    public T pop() {
        if (data.isEmpty()) {
            return null;
        }
        return data.removeLast();
    }

    public void push(T val) {
        data.addLast(val);
    }

    public T peek() {
        if (data.isEmpty()) {
            return null;
        }
        return data.getLast();
    }
}


