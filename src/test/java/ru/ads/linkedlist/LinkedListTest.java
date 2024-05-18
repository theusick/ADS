package ru.ads.linkedlist;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class LinkedListTest {

    private static final int[] EMPTY_ARRAY = new int[]{};
    private static final int[] ONE_ELEM_ARRAY = new int[]{ 2 };
    private static final int[] MANY_ELEMS_ARRAY = new int[]{ -10, 0, 4, 5, 0, 0, 4, 3, 5, -10 };

    @Test
    void addInTail() {
        LinkedList list = generateLinkedList(EMPTY_ARRAY);
        Node node = new Node(2);
        list.addInTail(node);
        assertEquals(node, list.head);
        assertEquals(node, list.tail);

        list = generateLinkedList(ONE_ELEM_ARRAY);
        node = new Node(3);
        Node prevTail = list.tail;
        list.addInTail(node);
        assertEquals(prevTail.next, list.tail);
        assertEquals(node, list.tail);
    }

    @Test
    void find() {
        LinkedList list = generateLinkedList(EMPTY_ARRAY);
        assertNull(list.find(2));

        list = generateLinkedList(ONE_ELEM_ARRAY);
        assertNull(list.find(1));
        assertEquals(2, list.find(2).value);
        assertEquals(list.head, list.find(2));
        assertEquals(list.tail, list.find(2));

        list = generateLinkedList(MANY_ELEMS_ARRAY);
        assertNull(list.find(-100));
        assertEquals(0, list.find(0).value);
        assertEquals(list.head.next, list.find(0));
    }

    @Test
    void testFindAll() {
        LinkedList list = generateLinkedList(EMPTY_ARRAY);

        ArrayList<Node> nodes = list.findAll(1);
        assertEquals(0, nodes.size());

        list = generateLinkedList(ONE_ELEM_ARRAY);
        nodes = list.findAll(1);
        assertEquals(0, nodes.size());
        nodes = list.findAll(2);
        assertEquals(1, nodes.size());
        assertEquals(2, nodes.get(0).value);

        list = generateLinkedList(MANY_ELEMS_ARRAY);
        nodes = list.findAll(1);
        assertEquals(0, nodes.size());

        nodes = list.findAll(0);
        assertEquals(3, nodes.size());
        assertEquals(0, nodes.get(0).value);

        nodes = list.findAll(4);
        assertEquals(2, nodes.size());
        assertEquals(4, nodes.get(0).value);
    }

    @Test
    void remove() {
        LinkedList list = generateLinkedList(EMPTY_ARRAY);
        assertFalse(list.remove(1));

        list = generateLinkedList(ONE_ELEM_ARRAY);
        assertFalse(list.remove(1));
        assertTrue(list.remove(2));
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        list = generateLinkedList(MANY_ELEMS_ARRAY);
        assertFalse(list.remove(1));

        assertTrue(list.remove(-10));
        assertEquals(MANY_ELEMS_ARRAY.length - 1, list.count());
        assertEquals(0, list.head.value);
        assertEquals(-10, list.tail.value);

        assertTrue(list.remove(4));
        assertEquals(MANY_ELEMS_ARRAY.length - 2, list.count());
        assertEquals(5, list.head.next.value);

        assertTrue(list.remove(-10));
        assertEquals(MANY_ELEMS_ARRAY.length - 3, list.count());
        assertEquals(0, list.head.value);
        assertEquals(5, list.tail.value);
        assertNull(list.tail.next);
    }

    @Test
    void removeAll() {
        LinkedList list = generateLinkedList(EMPTY_ARRAY);
        list.removeAll(1);
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        list = generateLinkedList(ONE_ELEM_ARRAY);
        list.removeAll(1);
        assertEquals(2, list.head.value);
        assertEquals(2, list.tail.value);

        list.removeAll(2);
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        list = generateLinkedList(MANY_ELEMS_ARRAY);
        list.removeAll(-10);
        assertEquals(0, list.head.value);
        assertEquals(5, list.tail.value);
        assertEquals(MANY_ELEMS_ARRAY.length - 2, list.count());

        list.removeAll(0);
        assertEquals(4, list.head.value);
        assertEquals(5, list.tail.value);
        assertEquals(MANY_ELEMS_ARRAY.length - 5, list.count());
    }

    @Test
    void clear() {
        LinkedList list = generateLinkedList(EMPTY_ARRAY);
        list.clear();
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        list = generateLinkedList(ONE_ELEM_ARRAY);
        list.clear();
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        list = generateLinkedList(MANY_ELEMS_ARRAY);
        list.clear();
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    void count() {
        LinkedList list = generateLinkedList(EMPTY_ARRAY);
        assertEquals(0, list.count());

        list = generateLinkedList(ONE_ELEM_ARRAY);
        assertEquals(1, list.count());
        assertTrue(list.remove(2));
        assertEquals(0, list.count());

        list = generateLinkedList(MANY_ELEMS_ARRAY);
        assertEquals(10, list.count());
        list.removeAll(5);
        assertEquals(MANY_ELEMS_ARRAY.length - 2, list.count());
    }

    @Test
    void insertAfter() {
        LinkedList list = generateLinkedList(EMPTY_ARRAY);
        list.insertAfter(null, new Node(4));
        assertEquals(4, list.head.value);
        assertEquals(4, list.tail.value);
        assertNull(list.tail.next);

        list = generateLinkedList(ONE_ELEM_ARRAY);
        Node node = new Node(4);
        list.insertAfter(null, node);
        assertEquals(4, list.head.value);
        assertEquals(2, list.head.next.value);
        assertEquals(2, list.tail.value);
        assertNull(list.tail.next);

        list.insertAfter(node, new Node(3));
        assertEquals(4, list.head.value);
        assertEquals(3, list.head.next.value);
        assertEquals(2, list.head.next.next.value);
        assertEquals(2, list.tail.value);
        assertNull(list.tail.next);

        list = generateLinkedList(MANY_ELEMS_ARRAY);
        node = list.tail;
        list.insertAfter(node, new Node(0));
        assertEquals(0, list.tail.value);
        assertEquals(list.tail, node.next);
        assertNull(list.tail.next);
    }

    private LinkedList generateLinkedList(int[] data) {
        LinkedList list = new LinkedList();
        for (int value : data) {
            list.addInTail(new Node(value));
        }
        return list;
    }

}
