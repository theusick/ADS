import java.util.*;

public class AlgorithmsDataStructures2
{

    public static int[] GenerateBBSTArray(int[] a)
    {
        if ((a == null) || (a.length == 0)) {
            return new int[]{};
        }

        int[] sorted = Arrays.copyOf(a, a.length);
        Arrays.sort(sorted);

        int[] result = new int[sorted.length];
        BuildBSTRoot(sorted, 0, sorted.length - 1, result, 0);

        return result;
    }

    private static int BuildBSTRoot(int[] sorted,
                                    int startIndex,
                                    int endIndex,
                                    int[] result,
                                    int currentIndex) {
        if (startIndex > endIndex) {
            return currentIndex;
        }

        int middleIndex = (startIndex + endIndex) / 2;
        result[currentIndex] = sorted[middleIndex];

        currentIndex++;
        currentIndex = BuildBSTRoot(sorted, startIndex, middleIndex - 1, result, currentIndex);
        currentIndex = BuildBSTRoot(sorted, middleIndex + 1, endIndex, result, currentIndex);

        return currentIndex;
    }

}
