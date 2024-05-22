package ru.ads.linkedlist2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedList2Test {

    private static final int[] EMPTY_ARRAY = new int[]{};
    private static final int[] ONE_ELEM_ARRAY = new int[]{ 2 };
    private static final int[] MANY_ELEMS_ARRAY = new int[]{ -10, 0, 4, 5, 0, 0, 4, 3, 5, -10 };

    @Test
    void testAddInTail() {
        LinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        Node node = new Node(2);
        list.addInTail(node);
        assertEquals(node, list.head);
        assertNull(list.head.prev);
        assertNull(list.head.next);
        assertEquals(node, list.tail);
        assertNull(list.tail.prev);
        assertNull(list.tail.next);

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        node = new Node(3);
        list.addInTail(node);
        assertEquals(node, list.tail);
        assertEquals(list.head, list.tail.prev);
        assertEquals(node, list.head.next);
    }

    @Test
    void testFind() {
        LinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        assertNull(list.find(2));

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        assertNull(list.find(1));
        assertEquals(2, list.find(2).value);
        assertEquals(list.head, list.find(2));
        assertEquals(list.tail, list.find(2));

        list = generateLinkedList2(MANY_ELEMS_ARRAY);
        assertNull(list.find(99));
        assertEquals(0, list.find(0).value);
        assertEquals(list.head.next, list.find(0));
    }

    @Test
    void testFindAll() {
        LinkedList2 list = generateLinkedList2(EMPTY_ARRAY);

        ArrayList<Node> nodes = list.findAll(1);
        assertEquals(0, nodes.size());

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        nodes = list.findAll(1);
        assertEquals(0, nodes.size());
        nodes = list.findAll(2);
        assertEquals(1, nodes.size());
        assertEquals(2, nodes.get(0).value);

        list = generateLinkedList2(MANY_ELEMS_ARRAY);
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
    void testRemove() {
        LinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        assertFalse(list.remove(1));

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        assertFalse(list.remove(1));
        assertTrue(list.remove(2));
        assertNull(list.head);
        assertNull(list.tail);

        list = generateLinkedList2(MANY_ELEMS_ARRAY); // { -10, 0, 4, 5, 0, 0, 4, 3, 5, -10 }
        assertFalse(list.remove(1));

        assertTrue(list.remove(-10)); // { 0, 4, 5, 0, 0, 4, 3, 5, -10 }
        assertEquals(MANY_ELEMS_ARRAY.length - 1, list.count());
        assertEquals(0, list.head.value);
        assertNull(list.head.prev);
        assertEquals(4, list.head.next.value);
        assertEquals(-10, list.tail.value);

        assertTrue(list.remove(4)); // { 0, 5, 0, 0, 4, 3, 5, -10 }
        assertEquals(MANY_ELEMS_ARRAY.length - 2, list.count());
        assertEquals(5, list.head.next.value);
        assertEquals(0, list.head.next.prev.value);

        assertTrue(list.remove(-10)); // { 0, 5, 0, 0, 4, 3, 5 }
        assertEquals(MANY_ELEMS_ARRAY.length - 3, list.count());
        assertEquals(0, list.head.value);
        assertEquals(5, list.tail.value);
        assertEquals(3, list.tail.prev.value);
        assertNull(list.tail.next);
    }

    @Test
    void testRemoveAll() {
        LinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        list.removeAll(1);
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        list.removeAll(1);
        assertEquals(2, list.head.value);
        assertEquals(2, list.tail.value);
        assertNull(list.head.prev);
        assertNull(list.head.next);
        assertNull(list.tail.prev);
        assertNull(list.tail.next);

        list.removeAll(2);
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        list = generateLinkedList2(MANY_ELEMS_ARRAY); // { -10, 0, 4, 5, 0, 0, 4, 3, 5, -10 }
        list.removeAll(-10); // { 0, 4, 5, 0, 0, 4, 3, 5 }
        assertEquals(0, list.head.value);
        assertNull(list.head.prev);
        assertEquals(4, list.head.next.value);
        assertEquals(5, list.tail.value);
        assertNull(list.tail.next);
        assertEquals(3, list.tail.prev.value);
        assertEquals(MANY_ELEMS_ARRAY.length - 2, list.count());

        list.removeAll(0); // { 4, 5, 4, 3, 5 }
        assertEquals(4, list.head.value);
        assertNull(list.head.prev);
        assertEquals(5, list.head.next.value);
        assertEquals(4, list.head.next.prev.value);
        assertEquals(4, list.head.next.next.value);
        assertEquals(5, list.head.next.next.prev.value);
        assertEquals(5, list.tail.value);
        assertNull(list.tail.next);
        assertEquals(3, list.tail.prev.value);
        assertEquals(5, list.tail.prev.next.value);
        assertEquals(5, list.tail.prev.prev.prev.value);
        assertEquals(MANY_ELEMS_ARRAY.length - 5, list.count());
    }

    @Test
    void testClear() {
        LinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        list.clear();
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        list.clear();
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);

        list = generateLinkedList2(MANY_ELEMS_ARRAY);
        list.clear();
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    void testCount() {
        LinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        assertEquals(0, list.count());

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        assertEquals(1, list.count());
        assertTrue(list.remove(2));
        assertEquals(0, list.count());

        list = generateLinkedList2(MANY_ELEMS_ARRAY);
        assertEquals(10, list.count());
        list.removeAll(5);
        assertEquals(MANY_ELEMS_ARRAY.length - 2, list.count());
    }

    @Test
    void testInsertAfter() {
        LinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        Node node = new Node(4);
        list.insertAfter(null, node);
        assertEquals(node, list.head);
        assertNull(list.head.prev);
        assertNull(list.head.next);
        assertEquals(node, list.tail);
        assertNull(list.tail.prev);
        assertNull(list.tail.next);

        list = generateLinkedList2(ONE_ELEM_ARRAY); // { 2 }
        list.insertAfter(null, node); // { 4, 2 }
        assertEquals(4, list.head.value);
        assertEquals(node, list.head);
        assertNull(list.head.prev);
        assertEquals(2, list.head.next.value);
        assertEquals(2, list.tail.value);
        assertEquals(node, list.tail.prev);
        assertNull(list.head.prev);
        assertNull(list.tail.next);

        list.insertAfter(node, new Node(3)); // { 4, 3, 2 }
        assertEquals(4, list.head.value);
        assertEquals(3, list.head.next.value);
        assertEquals(4, list.head.next.prev.value);
        assertEquals(2, list.head.next.next.value);
        assertEquals(2, list.tail.value);
        assertEquals(3, list.tail.prev.value);
        assertNull(list.head.prev);
        assertNull(list.tail.next);

        list = generateLinkedList2(MANY_ELEMS_ARRAY);
        list.insertAfter(list.tail, new Node(0));
        assertEquals(0, list.tail.value);
        assertEquals(-10, list.tail.prev.value);
        assertNull(list.head.prev);
        assertNull(list.tail.next);
    }

    private LinkedList2 generateLinkedList2(int[] data) {
        LinkedList2 list = new LinkedList2();
        for (int value : data) {
            list.addInTail(new Node(value));
        }
        return list;
    }

}
