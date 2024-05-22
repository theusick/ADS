import java.util.*;

public class LinkedList2
{
    public Node head;
    public Node tail;

    public LinkedList2()
    {
        head = null;
        tail = null;
    }

    public void addInTail(Node _item)
    {
        if (head == null) {
            this.head = _item;
            this.head.next = null;
            this.head.prev = null;
        } else {
            this.tail.next = _item;
            _item.prev = tail;
        }
        this.tail = _item;
    }

    public Node find(int _value)
    {
        Node node = this.head;
        while (node != null) {
            if (node.value == _value)
                return node;
            node = node.next;
        }
        return null;
    }

    public ArrayList<Node> findAll(int _value)
    {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Node node = this.head;
        while (node != null) {
            if (node.value == _value) {
                nodes.add(node);
            }
            node = node.next;
        }
        return nodes;
    }

    public boolean remove(int _value)
    {
        Node node = this.head;
        while (node != null) {
            if (node.value == _value) {
                if (node == this.head) {
                    this.head = node.next;
                    if (this.head == null) {
                        this.tail = null;
                    } else {
                        this.head.prev = null;
                    }
                } else if (node == this.tail) {
                    node.prev.next = null;
                    this.tail = node.prev;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public void removeAll(int _value)
    {
        Node node = this.head;
        while (node != null) {
            if (node.value == _value) {
                if (node == this.head) {
                    this.head = node.next;
                    node = this.head;

                    if (this.head == null) {
                        this.tail = null;
                    } else {
                        this.head.prev = null;
                    }
                } else if (node == this.tail) {
                    node.prev.next = null;
                    this.tail = node.prev;
                    return;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    node = node.next;
                }
            } else {
                node = node.next;
            }
        }
    }

    public void clear()
    {
        this.head = null;
        this.tail = null;
    }

    public int count()
    {
        int count = 0;
        Node node = this.head;
        while (node != null) {
            count++;
            node = node.next;
        }
        return count;
    }

    public void insertAfter(Node _nodeAfter, Node _nodeToInsert)
    {
        if (_nodeAfter == null) {
            _nodeToInsert.next = this.head;
            _nodeToInsert.prev = null;
            this.head = _nodeToInsert;
            if (this.tail == null) {
                this.tail = this.head;
            } else {
                this.head.next.prev = this.head;
            }
            return;
        }

        Node node = this.head;
        while (node != null) {
            if (node == _nodeAfter) {
                _nodeToInsert.next = node.next;
                _nodeToInsert.prev = node;
                if (node == this.tail) {
                    this.tail = _nodeToInsert;
                } else {
                    node.next.prev = _nodeToInsert;
                }
                node.next = _nodeToInsert;
                return;
            }
            node = node.next;
        }
    }
}

class Node
{
    public int value;
    public Node next;
    public Node prev;

    public Node(int _value)
    {
        value = _value;
        next = null;
        prev = null;
    }
}


