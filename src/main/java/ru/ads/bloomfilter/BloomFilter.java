package ru.ads.bloomfilter;

public class BloomFilter {
    public int filter_len;
    public long bitFilter = 0;

    public final int RANDOM_NUM_HASH1 = 17;
    public final int RANDOM_NUM_HASH2 = 223;

    private static final int BITS_PER_LONG = 64;

    public BloomFilter(int f_len) {
        filter_len = f_len;
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

        bitFilter |= (1L << (hash1Result % BITS_PER_LONG));
        bitFilter |= (1L << (hash2Result % BITS_PER_LONG));
    }

    public boolean isValue(String str1) {
        if (str1 == null) {
            return false;
        }

        int hash1Result = hash1(str1);
        int hash2Result = hash2(str1);

        boolean hasHash1Bit = (bitFilter & (1L << (hash1Result % BITS_PER_LONG))) != 0;
        boolean hasHash2Bit = (bitFilter & (1L << (hash2Result % BITS_PER_LONG))) != 0;

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


