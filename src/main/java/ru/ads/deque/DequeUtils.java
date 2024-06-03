package ru.ads.deque;

public class DequeUtils {

    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }

        Deque<Character> deque = new Deque<>();
        for (int i = 0; i < str.length(); i++) {
            deque.addFront(str.charAt(i));
        }
        for (int i = 0; i < str.length(); i++) {
            if (deque.removeFront() != str.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
