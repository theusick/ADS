package ru.ads.linkedlist2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DummyLinkedList2Test {

    private static final int[] EMPTY_ARRAY = new int[]{};
    private static final int[] ONE_ELEM_ARRAY = new int[]{2};
    private static final int[] MANY_ELEMS_ARRAY = new int[]{-10, 0, 4, 5, 0, 0, 4, 3, 5, -10};

    @Test
    void testAddInTail() {
        DummyLinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        Node node = new Node(2);
        list.addInTail(node);
        assertEquals(node, list.tail.prev);
        assertEquals(node, list.head.next);
        assertNull(list.head.prev);
        assertNull(list.tail.next);

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        node = new Node(3);
        list.addInTail(node);
        assertEquals(node, list.tail.prev);
        assertEquals(node, list.head.next.next);
    }

    @Test
    void testFind() {
        DummyLinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        assertNull(list.find(2));

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        assertNull(list.find(1));
        assertEquals(2, list.find(2).value);
        assertEquals(list.head.next, list.find(2));
        assertEquals(list.tail.prev, list.find(2));

        list = generateLinkedList2(MANY_ELEMS_ARRAY);
        assertNull(list.find(99));
        assertEquals(0, list.find(0).value);
        assertEquals(list.head.next.next, list.find(0));
    }

    @Test
    void testFindAll() {
        DummyLinkedList2 list = generateLinkedList2(EMPTY_ARRAY);

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
        DummyLinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        assertFalse(list.remove(1));

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        assertFalse(list.remove(1));
        assertTrue(list.remove(2));
        assertEquals(list.head.next, list.tail);
        assertEquals(list.tail.prev, list.head);

        list = generateLinkedList2(MANY_ELEMS_ARRAY); // { -10, 0, 4, 5, 0, 0, 4, 3, 5, -10 }
        assertFalse(list.remove(1));

        assertTrue(list.remove(-10)); // { 0, 4, 5, 0, 0, 4, 3, 5, -10 }
        assertEquals(MANY_ELEMS_ARRAY.length - 1, list.count());
        assertEquals(0, list.head.next.value);
        assertEquals(4, list.head.next.next.value);
        assertEquals(-10, list.tail.prev.value);

        assertTrue(list.remove(4)); // { 0, 5, 0, 0, 4, 3, 5, -10 }
        assertEquals(MANY_ELEMS_ARRAY.length - 2, list.count());
        assertEquals(5, list.head.next.next.value);
        assertEquals(0, list.head.next.next.prev.value);

        assertTrue(list.remove(-10)); // { 0, 5, 0, 0, 4, 3, 5 }
        assertEquals(MANY_ELEMS_ARRAY.length - 3, list.count());
        assertEquals(0, list.head.next.value);
        assertEquals(5, list.tail.prev.value);
        assertEquals(3, list.tail.prev.prev.value);
    }

    @Test
    void testRemoveAll() {
        DummyLinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        list.removeAll(1);
        assertEquals(0, list.count());
        assertEquals(list.head.next, list.tail);
        assertEquals(list.tail.prev, list.head);

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        list.removeAll(1);
        assertEquals(2, list.head.next.value);
        assertEquals(2, list.tail.prev.value);

        list.removeAll(2);
        assertEquals(0, list.count());
        assertEquals(list.head.next, list.tail);
        assertEquals(list.tail.prev, list.head);

        list = generateLinkedList2(MANY_ELEMS_ARRAY); // { -10, 0, 4, 5, 0, 0, 4, 3, 5, -10 }
        list.removeAll(-10); // { 0, 4, 5, 0, 0, 4, 3, 5 }
        assertEquals(0, list.head.value);
        assertEquals(4, list.head.next.next.value);
        assertEquals(5, list.tail.prev.value);
        assertEquals(3, list.tail.prev.prev.value);
        assertEquals(MANY_ELEMS_ARRAY.length - 2, list.count());

        list.removeAll(0); // { 4, 5, 4, 3, 5 }
        assertEquals(4, list.head.next.value);
        assertEquals(5, list.head.next.next.value);
        assertEquals(4, list.head.next.next.prev.value);
        assertEquals(4, list.head.next.next.next.value);
        assertEquals(5, list.head.next.next.next.prev.value);
        assertEquals(5, list.tail.prev.value);
        assertEquals(3, list.tail.prev.prev.value);
        assertEquals(5, list.tail.prev.prev.next.value);
        assertEquals(5, list.tail.prev.prev.prev.prev.value);
        assertEquals(MANY_ELEMS_ARRAY.length - 5, list.count());
    }

    @Test
    void testClear() {
        DummyLinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        list.clear();
        assertEquals(0, list.count());
        assertEquals(list.head.next, list.tail);
        assertEquals(list.tail.prev, list.head);

        list = generateLinkedList2(ONE_ELEM_ARRAY);
        list.clear();
        assertEquals(0, list.count());
        assertEquals(list.head.next, list.tail);
        assertEquals(list.tail.prev, list.head);

        list = generateLinkedList2(MANY_ELEMS_ARRAY);
        list.clear();
        assertEquals(0, list.count());
        assertEquals(list.head.next, list.tail);
        assertEquals(list.tail.prev, list.head);
    }

    @Test
    void testCount() {
        DummyLinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
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
        DummyLinkedList2 list = generateLinkedList2(EMPTY_ARRAY);
        Node node = new Node(4);
        list.insertAfter(null, node);
        assertEquals(node, list.head.next);
        assertEquals(node, list.tail.prev);
        assertEquals(list.tail, node.next);
        assertEquals(list.head, node.prev);

        list = generateLinkedList2(ONE_ELEM_ARRAY); // { 2 }
        list.insertAfter(null, node); // { 4, 2 }
        assertEquals(4, list.head.next.value);
        assertEquals(node, list.head.next);
        assertEquals(2, list.head.next.next.value);
        assertEquals(2, list.tail.prev.value);
        assertEquals(node, list.tail.prev.prev);

        list.insertAfter(node, new Node(3)); // { 4, 3, 2 }
        assertEquals(4, list.head.next.value);
        assertEquals(3, list.head.next.next.value);
        assertEquals(4, list.head.next.next.prev.value);
        assertEquals(2, list.head.next.next.next.value);
        assertEquals(2, list.tail.prev.value);
        assertEquals(3, list.tail.prev.prev.value);

        list = generateLinkedList2(MANY_ELEMS_ARRAY);
        list.insertAfter(list.tail, new Node(0));
        assertEquals(0, list.tail.value);
        assertEquals(-10, list.tail.prev.value);
    }

    private DummyLinkedList2 generateLinkedList2(int[] data) {
        DummyLinkedList2 list = new DummyLinkedList2();
        for (int value : data) {
            list.addInTail(new Node(value));
        }
        return list;
    }

}
