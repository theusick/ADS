public class BloomFilter {
    public int filter_len;
    public int[] bitFilter;

    public final int RANDOM_NUM_HASH1 = 17;
    public final int RANDOM_NUM_HASH2 = 223;

    private static final int BITS_PER_INT = 32;

    public BloomFilter(int f_len) {
        filter_len = f_len;
        bitFilter = new int[(filter_len + BITS_PER_INT - 1) / BITS_PER_INT];
    }

    public int hash1(String str1) {
        return hashFun(str1, RANDOM_NUM_HASH1);
    }

    public int hash2(String str1) {
        return hashFun(str1, RANDOM_NUM_HASH2);
    }

    public void add(String str1) {
        if (str1 == null) {
            return;
        }

        int hash1Result = hash1(str1);
        int hash2Result = hash2(str1);

        bitFilter[hash1Result / BITS_PER_INT] |= (1 << (hash1Result % BITS_PER_INT));
        bitFilter[hash2Result / BITS_PER_INT] |= (1 << (hash2Result % BITS_PER_INT));
    }

    public boolean isValue(String str1) {
        if (str1 == null) {
            return false;
        }

        int hash1Result = hash1(str1);
        int hash2Result = hash2(str1);

        boolean hasHash1Bit = (bitFilter[hash1Result / BITS_PER_INT]
                & (1 << (hash1Result % BITS_PER_INT))) != 0;
        boolean hasHash2Bit = (bitFilter[hash2Result / BITS_PER_INT]
                & (1 << (hash2Result % BITS_PER_INT))) != 0;

        return hasHash1Bit && hasHash2Bit;
    }

    private int hashFun(String str, int randomNum) {
        int hash = 0;
        if (str == null) {
            return hash;
        }

        for (int i = 0; i < str.length(); i++) {
            int code = (int) str.charAt(i);
            hash = (hash * randomNum + code) % filter_len;
        }
        return hash;
    }

}


