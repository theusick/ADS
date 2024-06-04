import java.util.*;


class Node<T> {
    public T value;
    public Node<T> next, prev;

    public Node(T _value) {
        value = _value;
        next = null;
        prev = null;
    }
}

public class OrderedList<T> {
    public Node<T> head, tail;
    private boolean _ascending;

    public OrderedList(boolean asc) {
        head = null;
        tail = null;
        _ascending = asc;
    }

    public int compare(T v1, T v2) {
        if ((v1 instanceof Number) && (v2 instanceof Number)) {
            double diff = ((Number) v1).doubleValue() - ((Number) v2).doubleValue();
            return diff < 0 ? -1 : diff > 0 ? 1 : 0;
        }

        if ((v1 instanceof String) && (v2 instanceof String)) {
            int stringsComparison = (((String) v1).trim()).compareTo(((String) v2).trim());
            return Integer.compare(stringsComparison, 0);
        }

        throw new IllegalArgumentException("Unsupported type");
    }

    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if ((head == null) && (tail == null)) {
            head = newNode;
            tail = newNode;
            return;
        }

        if (shouldInsertBefore(head.value, newNode.value)) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            return;
        }

        if (shouldInsertAfter(tail.value, newNode.value)) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            return;
        }

        Node<T> curr = head.next;
        while (curr != null) {
            if (shouldInsertBefore(curr.value, newNode.value)) {
                newNode.next = curr;
                newNode.prev = curr.prev;
                curr.prev.next = newNode;
                curr.prev = newNode;
                return;
            }
            curr = curr.next;
        }
    }

    public Node<T> find(T val) {
        if (((head == null) && (tail == null)) || isOutOfRange(val)) {
            return null;
        }

        Node<T> curr = head;
        while (curr != null) {
            int comparison = compare(curr.value, val);
            if (comparison == 0) {
                return curr;
            }

            if ((_ascending && (comparison > 0))
                    || (!_ascending && (comparison < 0))) {
                return null;
            }
            curr = curr.next;
        }
        return null;
    }

    public void delete(T val) {
        if (((head == null) && (tail == null)) || isOutOfRange(val)) {
            return;
        }

        Node<T> curr = head;
        while (curr != null) {
            if (compare(curr.value, val) != 0) {
                curr = curr.next;
                continue;
            }

            if ((curr.next != null) && (curr.prev != null)) {
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
                return;
            }

            if ((curr.next == null) && (curr.prev == null)) {
                head = null;
                tail = null;
                return;
            }

            if (curr.next == null) {
                curr.prev.next = null;
                tail = curr.prev;
            }

            if (curr.prev == null) {
                curr.next.prev = null;
                head = curr.next;
            }
            return;
        }
    }

    public void clear(boolean asc) {
        head = null;
        tail = null;
        _ascending = asc;
    }

    public int count() {
        int count = 0;
        Node<T> node = head;
        while (node != null) {
            count++;
            node = node.next;
        }
        return count;
    }

    ArrayList<Node<T>> getAll() {
        ArrayList<Node<T>> r = new ArrayList<Node<T>>();
        Node<T> node = head;
        while (node != null) {
            r.add(node);
            node = node.next;
        }
        return r;
    }

    private boolean shouldInsertBefore(T currValue, T newValue) {
        int comparison = compare(currValue, newValue);
        return (_ascending && (comparison >= 0)) || (!_ascending && (comparison <= 0));
    }

    private boolean shouldInsertAfter(T currValue, T newValue) {
        int comparison = compare(currValue, newValue);
        return (_ascending && (comparison <= 0)) || (!_ascending && (comparison >= 0));
    }

    private boolean isOutOfRange(T val) {
        boolean isOutOfAscending = _ascending && (compare(head.value, val) > 0
                || compare(tail.value, val) < 0);
        boolean isOutOfDescending = !_ascending && (compare(head.value, val) < 0
                || compare(tail.value, val) > 0);
        return isOutOfAscending || isOutOfDescending;
    }
}


