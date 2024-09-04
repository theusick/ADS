package ru.ads.recursion;

import org.junit.jupiter.api.Test;

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

}