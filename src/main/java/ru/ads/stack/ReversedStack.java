package ru.ads.stack;

import java.util.LinkedList;
import java.util.List;

public class ReversedStack {
    public List<Object> data;

    public ReversedStack() {
        data = new LinkedList<>();
    }

    public int size() {
        return data.size();
    }

    public Object pop() {
        if (data.isEmpty()) {
            return null;
        }
        return data.removeFirst();
    }

    public void push(Object element) {
        data.addFirst(element);
    }

    public Object peek() {
        if (data.isEmpty()) {
            return null;
        }
        return data.getFirst();
    }
}


