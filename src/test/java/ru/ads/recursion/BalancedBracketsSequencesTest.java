package ru.ads.recursion;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BalancedBracketsSequencesTest {

    @Test
    void testGenerateEmptySequence() {
        List<String> result = BalancedBracketsSequences.generate(0);
        assertTrue(result.isEmpty());

        result = BalancedBracketsSequences.generate(-10);
        assertTrue(result.isEmpty());

        result = BalancedBracketsSequences.generate(-1);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGenerateOnePair() {
        List<String> result = BalancedBracketsSequences.generate(1);
        assertTrue(result.contains("()"));
        assertEquals(1, result.size());
    }

    @Test
    void testGenerateTwoPairs() {
        List<String> result = BalancedBracketsSequences.generate(2);
        assertTrue(result.contains("()()"));
        assertTrue(result.contains("(())"));
        assertEquals(2, result.size());
    }

    @Test
    void testGenerateThreePairs() {
        List<String> result = BalancedBracketsSequences.generate(3);
        assertTrue(result.contains("()()()"));
        assertTrue(result.contains("((()))"));
        assertTrue(result.contains("(()())"));
        assertTrue(result.contains("(())()"));
        assertTrue(result.contains("()(())"));
        assertEquals(5, result.size());
    }

    @Test
    void testGenerateFourPairs() {
        List<String> result = BalancedBracketsSequences.generate(4);
        assertTrue(result.contains("()()()()"));
        assertTrue(result.contains("(())()()"));
        assertTrue(result.contains("()(())()"));
        assertTrue(result.contains("()()(())"));
        assertTrue(result.contains("((()))()"));
        assertTrue(result.contains("(()())()"));
        assertTrue(result.contains("(()()())"));
        assertTrue(result.contains("((())())"));
        assertTrue(result.contains("(()(()))"));
        assertTrue(result.contains("(((())))"));
        assertTrue(result.contains("()((()))"));
        assertTrue(result.contains("()(()())"));
        assertTrue(result.contains("(())(())"));
        assertTrue(result.contains("((()()))"));
        assertEquals(14, result.size());
    }

}
