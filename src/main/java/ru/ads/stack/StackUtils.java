package ru.ads.stack;

public class StackUtils {

    public static boolean isParenthesisValid(String str) {
        if ((str == null) || (str.isEmpty())) {
            return false;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                stack.push(str.charAt(i));
            } else if ((str.charAt(i) == ')')
                    && (stack.peek() != null)) {
                stack.pop();
            } else {
                return false;
            }
        }
        return stack.size() == 0;
    }

    public static Integer countExpression(Stack<Character> s1) {
        if ((s1 == null) || (s1.size() == 0)) {
            return null;
        }

        Stack<Integer> s2 = new Stack<>();
        while (s1.size() != 0) {
            char curr = s1.pop();

            if (Character.isDigit(curr)) {
                s2.push(Character.digit(curr, 10));
            } else if (curr == '+') {
                s2.push(s2.pop() + s2.pop());
            } else if (curr == '-') {
                s2.push(s2.pop() - s2.pop());
            } else if (curr == '*') {
                s2.push(s2.pop() * s2.pop());
            } else if (curr == '/') {
                s2.push(s2.pop() / s2.pop());
            } else if (curr == '=') {
                return s2.pop();
            }
        }
        return s2.pop();
    }

}
