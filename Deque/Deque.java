import java.util.*;

public class Deque<T> {
    public LinkedList<T> data;

    public Deque() {
        data = new LinkedList<>();
    }

    public void addFront(T item) {
        data.addFirst(item);
    }

    public void addTail(T item) {
        data.addLast(item);
    }

    public T removeFront() {
        if (data.isEmpty()) {
            return null;
        }
        return data.removeFirst();
    }

    public T removeTail() {
        if (data.isEmpty()) {
            return null;
        }
        return data.removeLast();
    }

    public int size() {
        return data.size();
    }
}


