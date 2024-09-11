package ru.ads.recursion;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class RecursionFunctionsTest {

    private static final double DELTA = 0.0001;

    @Test
    void testPowRecursivePositivePower() {
        assertEquals(4, RecursionFunctions.powRecursive(2, 2), DELTA);
        assertEquals(-8, RecursionFunctions.powRecursive(-2, 3), DELTA);
        assertEquals(0, RecursionFunctions.powRecursive(0, 99), DELTA);
        assertEquals(1073741824, RecursionFunctions.powRecursive(2, 30), DELTA);
        assertEquals(387420489, RecursionFunctions.powRecursive(9, 9), DELTA);

        assertEquals(3.375, RecursionFunctions.powRecursive(1.5, 3), DELTA);
        assertEquals(22.5625, RecursionFunctions.powRecursive(-4.75, 2), DELTA);

    }

    @Test
    void testPowRecursiveZeroPower() {
        final int zeroPower = 0;

        assertEquals(1, RecursionFunctions.powRecursive(2, zeroPower), DELTA);
        assertEquals(1, RecursionFunctions.powRecursive(-100, zeroPower), DELTA);
        assertEquals(1, RecursionFunctions.powRecursive(1000, zeroPower), DELTA);
        assertEquals(1, RecursionFunctions.powRecursive(1073741824, zeroPower), DELTA);
        assertEquals(1, RecursionFunctions.powRecursive(1.5, zeroPower), DELTA);
        assertEquals(1, RecursionFunctions.powRecursive(-4.75, zeroPower), DELTA);
        // tricky moment, accepted that 0^0 = 1 like in java.lang.Math.pow
        assertEquals(1, RecursionFunctions.powRecursive(0, zeroPower), DELTA);
    }

    @Test
    void testPowRecursiveNegativePower() {
        assertEquals(0.25, RecursionFunctions.powRecursive(2, -2), DELTA);
        assertEquals(-0.125, RecursionFunctions.powRecursive(-2, -3), DELTA);
        assertEquals(0, RecursionFunctions.powRecursive(0, -99), DELTA);
        assertEquals(0.0156, RecursionFunctions.powRecursive(2, -6), DELTA);
        assertEquals(1073741824.0, RecursionFunctions.powRecursive(0.5, -30), DELTA);

        assertEquals(4.0, RecursionFunctions.powRecursive(0.5, -2), DELTA);
    }

    @Test
    void testSumDigitsRecursive() {
        assertEquals(0, RecursionFunctions.sumDigitsRecursive(0));
        assertEquals(1, RecursionFunctions.sumDigitsRecursive(1));
        assertEquals(10, RecursionFunctions.sumDigitsRecursive(19));
        assertEquals(46, RecursionFunctions.sumDigitsRecursive(2147483647));
        assertEquals(-3, RecursionFunctions.sumDigitsRecursive(-12));
    }

    @Test
    void testGetListLengthRecursive() {
        assertEquals(0, RecursionFunctions.getListLengthRecursive(null));

        Stack<Integer> stack = new Stack<>();
        assertEquals(0, RecursionFunctions.getListLengthRecursive(stack));

        stack.push(1);
        assertEquals(1, RecursionFunctions.getListLengthRecursive(stack));

        int stackSize = 15;
        for (int i = 0; i < stackSize; i++) {
            stack.push(1);
        }
        assertEquals(stackSize, RecursionFunctions.getListLengthRecursive(stack));

        assertEquals(0, RecursionFunctions.getListLengthRecursive(stack));

        stackSize = 150;
        for (int i = 0; i < stackSize; i++) {
            stack.push(1);
        }
        assertEquals(stackSize, RecursionFunctions.getListLengthRecursive(stack));
    }

    @Test
    void testIsPalindromeRecursiveValid() {
        String palindrome = "aaa";
        assertTrue(RecursionFunctions.isPalindromeRecursive(palindrome));

        palindrome = "saippuakivikauppias";
        assertTrue(RecursionFunctions.isPalindromeRecursive(palindrome));

        palindrome = "Norma is as selfless as I am Ron";
        assertTrue(RecursionFunctions.isPalindromeRecursive(palindrome));
    }

    @Test
    void testIsPalindromeRecursiveInvalid() {
        String notPalindrome = null;
        assertFalse(RecursionFunctions.isPalindromeRecursive(notPalindrome));

        notPalindrome = "gigolos";
        assertFalse(RecursionFunctions.isPalindromeRecursive(notPalindrome));

        notPalindrome = "Not palindrome";
        assertFalse(RecursionFunctions.isPalindromeRecursive(notPalindrome));

        notPalindrome = "Mama";
        assertFalse(RecursionFunctions.isPalindromeRecursive(notPalindrome));

        notPalindrome = "abcdefdcba";
        assertFalse(RecursionFunctions.isPalindromeRecursive(notPalindrome));
    }

    @Test
    void testGetEvenValuesRecursive() {
        assertEquals("", RecursionFunctions.getEvenValuesRecursive(null));

        List<Integer> integerArrayList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals("2 4 6 8", RecursionFunctions.getEvenValuesRecursive(integerArrayList));

        List<Double> doubleArrayList = Arrays.asList(1.1, 2.2, 3.0, 4.1, 5.1, 6.1, 7.1, 8.1);
        assertEquals("2 4 6 8", RecursionFunctions.getEvenValuesRecursive(doubleArrayList));

        assertEquals("", RecursionFunctions.getEvenValuesRecursive(new ArrayList<>()));
    }

    @Test
    void testGetEvenIndexValuesRecursive() {
        assertEquals("", RecursionFunctions.getEvenIndexValuesRecursive(null));

        List<Integer> integerArrayList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertEquals("1 3 5 7 9", RecursionFunctions.getEvenIndexValuesRecursive(integerArrayList));

        List<String> stringArrayList = Arrays.asList("hello", "apple", "banana", "orange");
        assertEquals("hello banana", RecursionFunctions.getEvenIndexValuesRecursive(stringArrayList));

        assertEquals("", RecursionFunctions.getEvenIndexValuesRecursive(new ArrayList<>()));
    }

    @Test
    void testGetSecondMaxRecursiveInvalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> RecursionFunctions.getSecondMaxRecursive(null));
        assertThrows(IllegalArgumentException.class,
                () -> RecursionFunctions.getSecondMaxRecursive(new ArrayList<Integer>()));
        assertThrows(IllegalArgumentException.class,
                () -> RecursionFunctions.getSecondMaxRecursive(List.of(1)));
    }

    @Test
    void testGetSecondMaxRecursiveIntegerValidInput() {
        List<Integer> integerArray = Arrays.asList(2, 5, 3, 1, 4, 5);
        assertEquals(5, RecursionFunctions.getSecondMaxRecursive(integerArray));

        integerArray = Arrays.asList(5, 6);
        assertEquals(5, RecursionFunctions.getSecondMaxRecursive(integerArray));

        integerArray = Arrays.asList(-10000, 100000);
        assertEquals(-10000, RecursionFunctions.getSecondMaxRecursive(integerArray));

        integerArray = Arrays.asList(100000, -10000);
        assertEquals(-10000, RecursionFunctions.getSecondMaxRecursive(integerArray));

        integerArray = Arrays.asList(-100000, -10000);
        assertEquals(-100000, RecursionFunctions.getSecondMaxRecursive(integerArray));

        integerArray = Arrays.asList(5, 5, 5, 5, 5, 5);
        assertEquals(5, RecursionFunctions.getSecondMaxRecursive(integerArray));

        integerArray = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(4, RecursionFunctions.getSecondMaxRecursive(integerArray));
    }

    @Test
    void testGetSecondMaxRecursiveDoubleValidInput() {
        final double delta = 0.0001;
        List<Double> doubleArray = Arrays.asList(-0.3, 5.85, 3.0, 1.5, 4.9, 5.8);
        assertEquals(5.8, RecursionFunctions.getSecondMaxRecursive(doubleArray), delta);

        doubleArray = Arrays.asList(5.9, 6.0);
        assertEquals(5.9, RecursionFunctions.getSecondMaxRecursive(doubleArray), delta);

        doubleArray = Arrays.asList(-10000.9, 100000.9);
        assertEquals(-10000.9, RecursionFunctions.getSecondMaxRecursive(doubleArray), delta);

        doubleArray = Arrays.asList(100000.9, -10000.9);
        assertEquals(-10000.9, RecursionFunctions.getSecondMaxRecursive(doubleArray), delta);

        doubleArray = Arrays.asList(-100000.9, -10000.9);
        assertEquals(-100000.9, RecursionFunctions.getSecondMaxRecursive(doubleArray), delta);

        doubleArray = Arrays.asList(5.0, 5.0, 5.0, 5.0, 5.0, 5.0);
        assertEquals(5.0, RecursionFunctions.getSecondMaxRecursive(doubleArray), delta);

        doubleArray = Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5);
        assertEquals(4.4, RecursionFunctions.getSecondMaxRecursive(doubleArray),delta);
    }

    @Test
    void testGetSecondMaxRecursiveStringsValidInput() {
        List<String> stringArray = Arrays.asList("apple", "banana", "orange", "hello");
        assertEquals("hello", RecursionFunctions.getSecondMaxRecursive(stringArray));

        stringArray = Arrays.asList("apple", "banana");
        assertEquals("apple", RecursionFunctions.getSecondMaxRecursive(stringArray));

        stringArray = Arrays.asList("banana", "apple");
        assertEquals("apple", RecursionFunctions.getSecondMaxRecursive(stringArray));
    }

    @Test
    void testGenerateBalancedBracketsSequencesEmpty() {
        List<String> result = RecursionFunctions.generateBalancedBracketsSequences(0);
        assertTrue(result.isEmpty());

        result = RecursionFunctions.generateBalancedBracketsSequences(-10);
        assertTrue(result.isEmpty());

        result = RecursionFunctions.generateBalancedBracketsSequences(-1);
        assertTrue(result.isEmpty());
    }
    
}