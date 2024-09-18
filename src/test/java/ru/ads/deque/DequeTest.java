package ru.ads.deque;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DequeTest {

    @Test
    public void testEmptyDeque() {
        Deque<Integer> deque = new Deque<>();
        assertEquals(0, deque.size());
        assertNull(deque.removeFront());
        assertNull(deque.removeTail());
    }

    @Test
    public void testSingleElementDeque() {
        Deque<Integer> deque = new Deque<>();
        deque.addFront(1);
        assertEquals(1, deque.size());
        assertEquals(1, deque.data.getFirst());
        assertEquals(1, deque.data.getLast());

        int removed = deque.removeFront();
        assertEquals(1, removed);
        assertEquals(0, deque.size());

        deque.addTail(2);
        assertEquals(1, deque.size());
        assertEquals(2, deque.data.getFirst());
        assertEquals(2, deque.data.getLast());

        removed = deque.removeTail();
        assertEquals(2, removed);
        assertEquals(0, deque.size());
    }

    @Test
    public void testDequeBecomesEmptyAfterRemovals() {
        Deque<Integer> deque = new Deque<>();
        deque.addFront(1);
        deque.addTail(2);
        deque.addFront(3);
        deque.addTail(4);

        assertEquals(4, deque.size());

        deque.removeFront();
        deque.removeFront();
        deque.removeTail();
        deque.removeTail();

        assertEquals(0, deque.size());
        assertNull(deque.removeFront());
        assertNull(deque.removeTail());
    }

    @Test
    public void testDequeWithMultipleElements() {
        Deque<Integer> deque = new Deque<>();
        deque.addFront(1);
        deque.addFront(2);
        deque.addTail(3);
        deque.addTail(4);

        assertEquals(4, deque.size());
        assertEquals(2, deque.data.getFirst());
        assertEquals(4, deque.data.getLast());

        assertEquals(2, deque.removeFront());
        assertEquals(3, deque.size());

        assertEquals(4, deque.removeTail());
        assertEquals(2, deque.size());

        assertEquals(1, deque.removeFront());
        assertEquals(1, deque.size());

        assertEquals(3, deque.removeTail());
        assertEquals(0, deque.size());
    }

    @Test
    public void testSize() {
        Deque<Integer> deque = new Deque<>();
        assertEquals(0, deque.size());
        deque.addFront(1);
        assertEquals(1, deque.size());
        deque.addTail(2);
        assertEquals(2, deque.size());
        deque.removeFront();
        assertEquals(1, deque.size());
        deque.removeTail();
        assertEquals(0, deque.size());
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(DequeUtils.isPalindrome(""));
        assertTrue(DequeUtils.isPalindrome("aa"));
        assertTrue(DequeUtils.isPalindrome("aaa"));
        assertTrue(DequeUtils.isPalindrome("10bab01"));
        assertTrue(DequeUtils.isPalindrome("wasitacaroracatisaw"));
        assertFalse(DequeUtils.isPalindrome(null));
        assertFalse(DequeUtils.isPalindrome("null"));
        assertFalse(DequeUtils.isPalindrome("deque"));
        assertFalse(DequeUtils.isPalindrome("queue"));
        assertFalse(DequeUtils.isPalindrome("aaabaaaa"));
    }

}