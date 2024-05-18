package ru.ads.linkedlist;

import java.util.*;

public class LinkedList
{
    public Node head;
    public Node tail;

    public LinkedList()
    {
        head = null;
        tail = null;
    }

    public void addInTail(Node item) {
        if (this.head == null)
            this.head = item;
        else
            this.tail.next = item;
        this.tail = item;
    }

    public Node find(int value) {
        Node node = this.head;
        while (node != null) {
            if (node.value == value)
                return node;
            node = node.next;
        }
        return null;
    }

    public ArrayList<Node> findAll(int _value) {
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
        Node prev = null;
        Node curr = this.head;
        while (curr != null) {
            if (curr.value == _value) {
                if (curr == this.head) {
                    this.head = this.head.next;
                    if (this.head == null) {
                        this.tail = null;
                    }
                } else if (curr == this.tail) {
                    prev.next = null;
                    this.tail = prev;
                } else {
                    prev.next = curr.next;
                }
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    public void removeAll(int _value)
    {
        Node prev = null;
        Node curr = this.head;
        while (curr != null) {
            if (curr.value == _value) {
                if (curr == this.head) {
                    this.head = this.head.next;
                    curr = this.head;

                    if (this.head == null) {
                        this.tail = null;
                    }
                } else if (curr == this.tail) {
                    prev.next = null;
                    this.tail = prev;
                    return;
                } else {
                    prev.next = curr.next;
                    curr = curr.next;
                }
            } else {
                prev = curr;
                curr = curr.next;
            }
        }
    }

    public void clear()
    {
        Node next = null;
        Node curr = this.head;
        while (curr != null) {
            next = curr.next;
            curr.next = null;
            curr = next;
        }
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
            this.head = _nodeToInsert;
            if (this.tail == null) {
                this.tail = this.head;
                this.tail.next = null;
            }
            return;
        }

        Node node = this.head;
        while (node != null) {
            if (node == _nodeAfter) {
                _nodeToInsert.next = node.next;
                node.next = _nodeToInsert;
                if (node == this.tail) {
                    this.tail = _nodeToInsert;
                }
            }
            node = node.next;
        }
    }

    public static LinkedList sumEqualLengthLists(LinkedList left, LinkedList right)
    {
        LinkedList result = new LinkedList();
        if (left.count() == right.count()) {
            Node currLeft = left.head;
            Node currRight = right.head;
            while (currLeft != null) {
                result.addInTail(new Node(currLeft.value + currRight.value));
                currLeft = currLeft.next;
                currRight = currRight.next;
            }
        }
        return result;
    }

}

class Node
{
    public int value;
    public Node next;
    public Node(int _value)
    {
        value = _value;
        next = null;
    }
}


