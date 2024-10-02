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

        BuildBBST(sorted, result, 2 * currentIndex + 1, startIndex, middleIndex - 1);
        BuildBBST(sorted, result, 2 * currentIndex + 2, middleIndex + 1, endIndex);
    }

}
