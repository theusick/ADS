package ru.ads.stack;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    private static final int[] ARRAY = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    void testSize() {
        Stack<Integer> stack = new Stack<>();
        assertEquals(0, stack.size());

        stack.push(0);
        assertEquals(1, stack.size());

        stack.pop();
        assertEquals(0, stack.size());

        Arrays.stream(ARRAY).forEach(stack::push);
        assertEquals(9, stack.size());
        stack.peek();
        assertEquals(9, stack.size());
    }

    @Test
    void testPop() {
        Stack<Integer> stack = new Stack<>();
        assertNull(stack.peek());
        stack.push(0);
        assertEquals(1, stack.size());
        assertEquals(0, stack.pop());
        assertNull(stack.pop());

        Arrays.stream(ARRAY).forEach(stack::push);
        IntStream.range(0, ARRAY.length)
                .map(i -> ARRAY[ARRAY.length - 1 - i])
                .forEach(num -> assertEquals(num, stack.pop()));
        assertNull(stack.pop());
    }

    @Test
    void testPush() {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        assertEquals(1, stack.size());
        assertEquals(0, stack.pop());

        Arrays.stream(ARRAY).forEach(stack::push);
        Arrays.stream(ARRAY).forEach(stack::push);
        assertEquals(18, stack.size());
    }

    @Test
    void testPeek() {
        Stack<Integer> stack = new Stack<>();
        assertNull(stack.peek());
        stack.push(0);
        assertEquals(1, stack.size());
        assertEquals(0, stack.peek());
        assertEquals(1, stack.size());

        Arrays.stream(ARRAY).forEach(stack::push);
        assertEquals(10, stack.size());
        assertEquals(9, stack.peek());
        assertEquals(9, stack.peek());
    }
}