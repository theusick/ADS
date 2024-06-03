package ru.ads.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackUtilsTest {

    @Test
    void testIsParenthesisValid() {
        assertTrue(StackUtils.isParenthesisValid("()"));
        assertTrue(StackUtils.isParenthesisValid("(()((())()))"));
        assertTrue(StackUtils.isParenthesisValid("(()()(()))"));

        assertFalse(StackUtils.isParenthesisValid(""));
        assertFalse(StackUtils.isParenthesisValid(null));
        assertFalse(StackUtils.isParenthesisValid("("));
        assertFalse(StackUtils.isParenthesisValid(")("));
        assertFalse(StackUtils.isParenthesisValid("((()"));
    }

    @Test
    void testCountExpression() {
        assertNull(StackUtils.countExpression(null));

        Stack<Character> s1 = new Stack<>();
        assertNull(StackUtils.countExpression(s1));

        s1.push('5');
        assertEquals(5, StackUtils.countExpression(s1));

        s1 = new Stack<>();
        s1.push('+');
        s1.push('3');
        s1.push('2');
        assertEquals(5, StackUtils.countExpression(s1));

        s1 = new Stack<>();
        s1.push('-');
        s1.push('2');
        s1.push('5');
        assertEquals(3, StackUtils.countExpression(s1));

        s1 = new Stack<>();
        s1.push('*');
        s1.push('3');
        s1.push('4');
        assertEquals(3, StackUtils.countExpression(s1));

        s1 = new Stack<>();
        s1.push('/');
        s1.push('2');
        s1.push('8');
        assertEquals(3, StackUtils.countExpression(s1));

        s1 = new Stack<>();
        s1.push('=');
        s1.push('+');
        s1.push('9');
        s1.push('*');
        s1.push('5');
        s1.push('+');
        s1.push('2');
        s1.push('8');
        assertEquals(59, StackUtils.countExpression(s1));
    }
}