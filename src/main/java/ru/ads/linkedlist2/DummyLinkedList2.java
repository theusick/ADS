package ru.ads.linkedlist2;

import java.util.ArrayList;

public class DummyLinkedList2
{
    public Node head;
    public Node tail;

    public DummyLinkedList2()
    {
        head = new DummyNode();
        tail = new DummyNode();
        head.next = tail;
        tail.prev = head;
    }

    public void addInTail(Node _item)
    {
        this.tail.prev.next = _item;
        _item.next = this.tail;
        _item.prev = this.tail.prev;
        this.tail.prev = _item;
    }

    public Node find(int _value)
    {
        Node node = this.head.next;
        while (!(node instanceof DummyNode)) {
            if (node.value == _value)
                return node;
            node = node.next;
        }
        return null;
    }

    public ArrayList<Node> findAll(int _value)
    {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Node node = this.head.next;
        while (!(node instanceof DummyNode)) {
            if (node.value == _value) {
                nodes.add(node);
            }
            node = node.next;
        }
        return nodes;
    }

    public boolean remove(int _value)
    {
        Node node = this.head.next;
        while (!(node instanceof DummyNode)) {
            if (node.value == _value) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public void removeAll(int _value)
    {
        Node node = this.head.next;
        while (!(node instanceof DummyNode)) {
            if (node.value == _value) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            node = node.next;
        }
    }

    public void clear()
    {
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    public int count()
    {
        int count = 0;
        Node node = this.head.next;
        while (!(node instanceof DummyNode)) {
            count++;
            node = node.next;
        }
        return count;
    }

    public void insertAfter(Node _nodeAfter, Node _nodeToInsert)
    {
        if (_nodeAfter == null) {
            _nodeToInsert.next = this.head.next;
            _nodeToInsert.prev = this.head;
            this.head.next.prev = _nodeToInsert;
            this.head.next = _nodeToInsert;
            return;
        }

        Node node = this.head.next;
        while (!(node instanceof DummyNode)) {
            if (node == _nodeAfter) {
                _nodeToInsert.next = node.next;
                _nodeToInsert.prev = node;
                node.next.prev = _nodeToInsert;
                node.next = _nodeToInsert;
                return;
            }
            node = node.next;
        }
    }
}

class DummyNode extends Node
{
    public DummyNode()
    {
        super(0);
    }
}
