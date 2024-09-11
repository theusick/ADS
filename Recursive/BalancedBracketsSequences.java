package ru.ads.recursion;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BalancedBracketsSequences {

    private static class TripleBracketsHelper {
        public int openBrackets;
        public int closedBrackets;
        public String bracketsSequence;

        public TripleBracketsHelper(int openBrackets, int closedBrackets, String bracketsSequence) {
            this.openBrackets = openBrackets;
            this.closedBrackets = closedBrackets;
            this.bracketsSequence = bracketsSequence;
        }

    }

    public static List<String> generate(int numberOfBrackets) {
        if (numberOfBrackets <= 0) {
            return new ArrayList<>();
        }

        List<String> balancedBracketsVariants = new ArrayList<>();
        Stack<TripleBracketsHelper> stack = new Stack<>();
        stack.push(new TripleBracketsHelper(1, 0, "("));

        generate(numberOfBrackets, stack, balancedBracketsVariants);
        return balancedBracketsVariants;
    }

    private static void generate(int numberOfBrackets,
                                 Stack<TripleBracketsHelper> stack,
                                 List<String> result) {
        if (stack.isEmpty()) {
            return;
        }

        TripleBracketsHelper triple = stack.pop();
        boolean isSequenceFull = (triple.openBrackets + triple.closedBrackets == 2 * numberOfBrackets);

        if (isSequenceFull) {
            result.add(triple.bracketsSequence);
        }
        if (!isSequenceFull && (triple.openBrackets < numberOfBrackets)) {
            stack.push(new TripleBracketsHelper(triple.openBrackets + 1,
                    triple.closedBrackets,
                    triple.bracketsSequence + "("));
        }
        if (!isSequenceFull && (triple.openBrackets > triple.closedBrackets)) {
            stack.push(new TripleBracketsHelper(triple.openBrackets,
                    triple.closedBrackets + 1,
                    triple.bracketsSequence + ")"));
        }
        generate(numberOfBrackets, stack, result);
    }

}
