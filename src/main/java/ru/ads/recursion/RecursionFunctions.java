package ru.ads.recursion;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

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
        if ((stack == null) || (stack.size() == 0)) {
            return 0;
        }
        stack.pop();
        return 1 + getListLengthRecursive(stack);
    }

    public static boolean isPalindromeRecursive(String sentence) {
        if (sentence == null) {
            return false;
        }
        String wordToCheck = sentence.trim().replace(" ", "").toLowerCase();
        return isPalindromeRecursive(wordToCheck, 0, wordToCheck.length() - 1);
    }

    private static boolean isPalindromeRecursive(String word, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return true;
        }
        if (word.charAt(leftIndex) != word.charAt(rightIndex)) {
            return false;
        }
        return isPalindromeRecursive(word, leftIndex + 1, rightIndex - 1);
    }

    public static <T extends Number> String getEvenValuesRecursive(List<T> list) {
        if (list == null) {
            return "";
        }
        return getEvenValuesRecursive(list.iterator()).trim();
    }

    private static <T extends Number> String getEvenValuesRecursive(Iterator<T> listIterator) {
        if (!listIterator.hasNext()) {
            return "";
        }

        T value = listIterator.next();
        StringBuilder evenNumberStr = new StringBuilder();

        if (isNumberEven(value)) {
            evenNumberStr.append(value.intValue()).append(" ");
        }
        return evenNumberStr + getEvenValuesRecursive(listIterator);
    }

    private static boolean isNumberEven(Number value) {
        return value.intValue() % 2 == 0;
    }

    public static <T> String getEvenIndexValuesRecursive(List<T> list) {
        if (list == null) {
            return "";
        }
        return getEvenIndexValuesRecursive(list, 0, list.size()).trim();
    }

    private static <T> String getEvenIndexValuesRecursive(List<T> list, int currentIndex, int endIndex) {
        if (currentIndex >= endIndex) {
            return "";
        }

        return list.get(currentIndex)
                + " "
                + getEvenIndexValuesRecursive(list, currentIndex + 2, endIndex);
    }

    public static <T extends Comparable<T>> T getSecondMaxRecursive(List<T> array) throws IllegalArgumentException {
        if ((array == null) || (array.size() < 2)) {
            throw new IllegalArgumentException("Array must contain at least 2 non null elements.");
        }

        T initialMax = array.get(0);
        T initialSecondMax = array.get(1);

        if (initialMax.compareTo(initialSecondMax) <= 0) {
            T initialMaxSwap = initialMax;
            initialMax = initialSecondMax;
            initialSecondMax = initialMaxSwap;
        }
        return getSecondMaxRecursive(array, 2, initialMax, initialSecondMax);
    }

    private static <T extends Comparable<T>> T getSecondMaxRecursive(List<T> array,
                                                                     int currentIndex,
                                                                     T max,
                                                                     T secondMax) {
        if (currentIndex == array.size()) {
            return secondMax;
        }

        T currentValue = array.get(currentIndex);

        if (currentValue.compareTo(max) >= 0) {
            secondMax = max;
            max = currentValue;
        }
        if ((currentValue.compareTo(max) < 0) && (currentValue.compareTo(secondMax) >= 0)) {
            secondMax = currentValue;
        }
        return getSecondMaxRecursive(array, currentIndex + 1, max, secondMax);
    }

    public static List<String> findDirFilesRecursive(String path) {
        if (path == null) {
            return new ArrayList<>();
        }

        File source = new File(path);
        if (!source.exists()) {
            return new ArrayList<>();
        }

        List<String> dirNames = new ArrayList<>();
        File[] files = source.listFiles();
        if (files == null) {
            return dirNames;
        }

        for (File file : files) {
            if (file.isFile()) {
                dirNames.add(file.getName());
            }
            if (file.isDirectory()) {
                dirNames.addAll(findDirFilesRecursive(file.getPath()));
            }
        }
        return dirNames;
    }

}
