import java.util.*;

public class Queue<T> {
    public LinkedList<T> data;

    public Queue() {
        data = new LinkedList<>();
    }

    /**
     * LinkedList add in the tail = O(1)
     * enqueue = O(1)
     */
    public void enqueue(T item) {
        data.addLast(item);
    }

    /**
     * LinkedList remove head = O(1)
     * dequeue = O(1)
     */
    public T dequeue() {
        if (data.isEmpty()) {
            return null;
        }
        return data.removeFirst();
    }

    public int size() {
        return data.size();
    }

}


