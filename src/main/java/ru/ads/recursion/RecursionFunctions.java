package ru.ads.recursion;

import ru.ads.stack.Stack;

public class RecursionFunctions {

    public static double powRecursive(double number, int power) {
        if (power == 0) {
            return 1;
        }
        if (number == 0) {
            return 0;
        }
        if (power < 0) {
            number = 1 / number;
            power = -power;
        }

        return number * powRecursive(number, power - 1);
    }

    public static int sumDigitsRecursive(int number) {
        if (number == 0) {
            return 0;
        }

        return (number % 10) + sumDigitsRecursive(number / 10);
    }

    public static <T> int getListLengthRecursive(Stack<T> stack) {
        if (stack.pop() == null) {
            return 0;
        }
        return 1 + getListLengthRecursive(stack);
    }

    public static boolean isPalindromeRecursive(String sentence) {
        if (sentence == null) {
            return false;
        }
        String wordToCheck = sentence.trim().replace(" ", "").toLowerCase();
        return isPalindromeRecursive(wordToCheck, 0, wordToCheck.length() - 1);
    }

    private static boolean isPalindromeRecursive(String word,
                                                 int leftIndex,
                                                 int rightIndex) {
        if (leftIndex >= rightIndex) {
            return true;
        }
        if (word.charAt(leftIndex) != word.charAt(rightIndex)) {
            return false;
        }
        return isPalindromeRecursive(word, leftIndex + 1, rightIndex - 1);
    }

}
