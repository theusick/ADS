package ru.ads.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueThroughStacksTest {
    
    @Test
    public void testEmptyQueue() {
        QueueThroughStacks<Integer> queue = new QueueThroughStacks<>();
        assertEquals(0, queue.size());
    }

    @Test
    public void testEnqueueSingleElement() {
        QueueThroughStacks<Integer> queue = new QueueThroughStacks<>();
        queue.enqueue(1);
        assertEquals(1, queue.size());
    }

    @Test
    public void testDequeueSingleElement() {
        QueueThroughStacks<Integer> queue = new QueueThroughStacks<>();
        queue.enqueue(1);
        int element = queue.dequeue();
        assertEquals(1, element);
        assertEquals(0, queue.size());
    }

    @Test
    public void testEnqueueMultipleElements() {
        QueueThroughStacks<Integer> queue = new QueueThroughStacks<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(3, queue.size());
    }

    @Test
    public void testDequeueMultipleElements() {
        QueueThroughStacks<Integer> queue = new QueueThroughStacks<>();
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
        QueueThroughStacks<Integer> queue = new QueueThroughStacks<>();
        assertNull(queue.dequeue());
    }
}