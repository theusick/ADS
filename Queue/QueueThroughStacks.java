import java.util.Stack;

public class QueueThroughStacks<T> {

    public Stack<T> s1;
    public Stack<T> s2;

    public QueueThroughStacks() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }

    public void enqueue(T item) {
        if (s1.isEmpty()) {
            s1.push(item);
            return;
        }

        while (!s1.isEmpty()) {
            s2.push(s1.pop());
        }
        s1.push(item);
        while (!s2.isEmpty()) {
            s1.push(s2.pop());
        }
    }

    public T dequeue() {
        if (s1.isEmpty()) {
            return null;
        }
        return s1.pop();
    }

    public int size() {
        return s1.size();
    }

}


