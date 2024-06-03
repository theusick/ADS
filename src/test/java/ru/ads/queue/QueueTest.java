package ru.ads.queue;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    @Test
    public void testEmptyQueue() {
        Queue<Integer> queue = new Queue<>();
        assertEquals(0, queue.size());
    }

    @Test
    public void testEnqueueSingleElement() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        assertEquals(1, queue.size());
    }

    @Test
    public void testDequeueSingleElement() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        int element = queue.dequeue();
        assertEquals(1, element);
        assertEquals(0, queue.size());
    }

    @Test
    public void testEnqueueMultipleElements() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(3, queue.size());
    }

    @Test
    public void testDequeueMultipleElements() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        int element1 = queue.dequeue();
        assertEquals(1, element1);
        assertEquals(2, queue.size());

        int element2 = queue.dequeue();
        assertEquals(2, element2);
        assertEquals(1, queue.size());

        int element3 = queue.dequeue();
        assertEquals(3, element3);
        assertEquals(0, queue.size());
    }

    @Test
    public void testDequeueFromEmptyQueue() {
        Queue<Integer> queue = new Queue<>();
        assertNull(queue.dequeue());
    }

    @Test
    public void testRotateZero() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        QueueUtils.rotateQueue(queue, 0);
        assertEquals(List.of(1, 2, 3, 4, 5), new LinkedList<>(queue.data));
    }

    @Test
    public void testRotateEqualToSize() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        QueueUtils.rotateQueue(queue, 5);
        assertEquals(List.of(1, 2, 3, 4, 5), new LinkedList<>(queue.data));
    }

    @Test
    public void testRotateMoreThanSize() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        QueueUtils.rotateQueue(queue, 7); // 7 % 5 == 2
        assertEquals(List.of(3, 4, 5, 1, 2), new LinkedList<>(queue.data));
    }

    @Test
    public void testRotateNormal() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        QueueUtils.rotateQueue(queue, 2);
        assertEquals(List.of(3, 4, 5, 1, 2), new LinkedList<>(queue.data));
    }

    @Test
    public void testRotateEmptyQueue() {
        Queue<Integer> queue = new Queue<>();

        QueueUtils.rotateQueue(queue, 3);
        assertEquals(List.of(), new LinkedList<>(queue.data));
    }
}