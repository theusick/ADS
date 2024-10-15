import java.util.*;

public class AlgorithmsDataStructures2 {

    public static int[] GenerateBBSTArray(int[] a) {
        if ((a == null) || (a.length == 0)) {
            return new int[]{};
        }

        int[] sorted = Arrays.copyOf(a, a.length);
        Arrays.sort(sorted);

        int[] result = new int[sorted.length];
        BuildBBST(sorted, result, 0, 0, sorted.length - 1);

        return result;
    }

    private static void BuildBBST(int[] sorted,
                                  int[] result,
                                  int currentIndex,
                                  int startIndex,
                                  int endIndex) {
        if ((startIndex > endIndex) || (currentIndex >= sorted.length)) {
            return;
        }

        int middleIndex = (startIndex + endIndex) / 2;
        result[currentIndex] = sorted[middleIndex];

        BuildBBST(sorted, result, GetLeftChildIndex(currentIndex), startIndex, middleIndex - 1);
        BuildBBST(sorted, result, GetRightChildIndex(currentIndex), middleIndex + 1, endIndex);
    }

    public static boolean DeleteNodeBBST(Integer[] balancedTree, int key) {
        if ((balancedTree == null) || (balancedTree.length == 0) || (balancedTree[0] == null)) {
            return false;
        }
        return DeleteNodeBBSTRecursive(balancedTree, key, 0);
    }

    public static boolean DeleteNodeBBSTRecursive(Integer[] tree, int key, int index) {
        if (IndexOutOfBoundsOrKeyNull(tree, index)) {
            return false;
        }

        if (key == tree[index]) {
            DeleteNodeBBSTByIndex(tree, index);
        } else {
            int nextIndex = (key < tree[index])
                    ? GetLeftChildIndex(index) : GetRightChildIndex(index);
            DeleteNodeBBSTRecursive(tree, key, nextIndex);
        }
        BalanceTree(tree, index);
        return true;
    }

    private static void DeleteNodeBBSTByIndex(Integer[] tree, int index) {
        if (IsLeaf(tree, index)) {
            tree[index] = null;
            return;
        }

        if (tree[GetLeftChildIndex(index)] == null) {
            tree[index] = tree[GetRightChildIndex(index)];
            tree[GetRightChildIndex(index)] = null;
            return;
        }

        if (tree[GetRightChildIndex(index)] == null) {
            tree[index] = tree[GetLeftChildIndex(index)];
            tree[GetLeftChildIndex(index)] = null;
            return;
        }

        Integer replacementIndex = FindMax(tree, GetLeftChildIndex(index));
        if (replacementIndex != null) {
            tree[index] = tree[replacementIndex];
            DeleteNodeBBSTRecursive(tree, tree[replacementIndex], replacementIndex);
        }
    }

    private static boolean IsLeaf(Integer[] tree, int index) {
        boolean LeftChildNotExists = (GetLeftChildIndex(index) >= tree.length) ||
                (tree[GetLeftChildIndex(index)] == null);
        boolean RightChildNotExists = (GetRightChildIndex(index) >= tree.length) ||
                (tree[GetRightChildIndex(index)] == null);

        return LeftChildNotExists && RightChildNotExists;
    }

    private static Integer FindMax(Integer[] tree, int index) {
        if (IndexOutOfBoundsOrKeyNull(tree, index)) {
            return null;
        }

        while ((GetRightChildIndex(index) < tree.length) &&
                (tree[GetRightChildIndex(index)] != null)) {
            index = GetRightChildIndex(index);
        }
        return index;
    }

    private static void BalanceTree(Integer[] tree, int index) {
        int balance = GetBalance(tree, index);

        if (balance > 1) {
            BalanceLeftSubtree(tree, index);
        } else if (balance < -1) {
            BalanceRightSubtree(tree, index);
        }
    }

    private static void BalanceLeftSubtree(Integer[] tree, int index) {
        if (GetBalance(tree, GetLeftChildIndex(index)) < 0) {
            LeftRotate(tree, GetLeftChildIndex(index));
        }
        RightRotate(tree, index);
    }

    private static void BalanceRightSubtree(Integer[] tree, int index) {
        if (GetBalance(tree, GetRightChildIndex(index)) > 0) {
            RightRotate(tree, GetRightChildIndex(index));
        }
        LeftRotate(tree, index);
    }

    private static int GetBalance(Integer[] tree, int index) {
        return CountHeightRecursive(tree, GetLeftChildIndex(index))
                - CountHeightRecursive(tree, GetRightChildIndex(index));
    }

    private static int CountHeightRecursive(Integer[] tree, int index) {
        if (IndexOutOfBoundsOrKeyNull(tree, index)) {
            return 0;
        }
        return 1 + Math.max(CountHeightRecursive(tree, GetLeftChildIndex(index)),
                CountHeightRecursive(tree, GetRightChildIndex(index)));
    }

    private static int GetLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private static int GetRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private static void LeftRotate(Integer[] tree, int index) {
        if (IndexOutOfBoundsOrKeyNull(tree, index)) {
            return;
        }

        int rightChildIndex = GetRightChildIndex(index);
        Integer replacedValue = tree[index];

        tree[index] = tree[rightChildIndex];
        tree[rightChildIndex] = replacedValue;

        Integer replacedRightLeftValue = tree[GetLeftChildIndex(rightChildIndex)];
        tree[GetLeftChildIndex(rightChildIndex)] = tree[rightChildIndex];
        tree[rightChildIndex] = replacedRightLeftValue;
    }

    private static void RightRotate(Integer[] tree, int index) {
        if (IndexOutOfBoundsOrKeyNull(tree, index)) {
            return;
        }

        int leftChildIndex = GetLeftChildIndex(index);
        Integer replacedValue = tree[index];

        tree[index] = tree[leftChildIndex];
        tree[leftChildIndex] = replacedValue;

        Integer replacedLeftRightValue = tree[GetRightChildIndex(leftChildIndex)];
        tree[GetRightChildIndex(leftChildIndex)] = tree[leftChildIndex];
        tree[leftChildIndex] = replacedLeftRightValue;
    }

    private static boolean IndexOutOfBoundsOrKeyNull(Integer[] tree, int index) {
        return (index >= tree.length) || (index < 0) || (tree[index] == null);
    }

    public static boolean IsBBST(Integer[] tree) {
        if ((tree == null) || (tree.length <= 1)) {
            return true;
        }
        return IsBBSTRecursive(tree, 0) != -1;
    }

    private static int IsBBSTRecursive(Integer[] tree, int index) {
        if (IndexOutOfBoundsOrKeyNull(tree, index)) {
            return 0;
        }

        int leftHeight = IsBBSTRecursive(tree, GetLeftChildIndex(index));
        int rightHeight = IsBBSTRecursive(tree, GetRightChildIndex(index));

        if ((Math.abs(leftHeight - rightHeight) > 1) || !AreChildrenBalanced(tree, index)) {
            return -1;
        }

        if ((leftHeight == -1) || (rightHeight == -1)) {
            return -1;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    private static boolean AreChildrenBalanced(Integer[] tree, int index) {
        int leftChildIndex = GetLeftChildIndex(index);
        boolean isLeftChildSmaller = IndexOutOfBoundsOrKeyNull(tree, leftChildIndex)
                || (tree[leftChildIndex] < tree[index]);

        int rightChildIndex = GetRightChildIndex(index);
        boolean isRightChildBigger = IndexOutOfBoundsOrKeyNull(tree, rightChildIndex)
                || (tree[rightChildIndex] > tree[index]);
        return isLeftChildSmaller && isRightChildBigger;
    }

}
