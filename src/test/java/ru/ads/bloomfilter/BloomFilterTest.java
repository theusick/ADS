package ru.ads.bloomfilter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloomFilterTest {

    private final String[] BASE_STRINGS = new String[]
            {
                    "0123456789",
                    "1234567890",
                    "2345678901",
                    "3456789012",
                    "4567890123",
                    "5678901234",
                    "6789012345",
                    "7890123456",
                    "8901234567",
                    "9012345678"
            };

    @Test
    void testFullFilter() {
        BloomFilter baseFilter = new BloomFilter(32);
        // Take the length when the number of hash functions
        //      k =  0,6931 * f_len / n (where k - num of hash functions,
        //      n=10 - number of values at the filter input)
        // is enough for 2
        BloomFilter smallFilter = new BloomFilter(29);
        BloomFilter largeFilter = new BloomFilter(43);

        for (String baseString : BASE_STRINGS) {
            baseFilter.add(baseString);

            smallFilter.add(baseString);
            largeFilter.add(baseString);
        }

        for (String baseString : BASE_STRINGS) {
            assertTrue(baseFilter.isValue(baseString));

            assertTrue(smallFilter.isValue(baseString));
            assertTrue(largeFilter.isValue(baseString));
        }

        assertFalse(baseFilter.isValue("0123443210"));
        assertFalse(baseFilter.isValue("1111111111"));

        assertFalse(smallFilter.isValue("0123443210"));
        assertFalse(smallFilter.isValue("1111111111"));
        assertFalse(largeFilter.isValue("0123443210"));
        assertFalse(largeFilter.isValue("1111111111"));
    }

}