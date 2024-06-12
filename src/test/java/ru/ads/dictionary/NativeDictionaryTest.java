package ru.ads.dictionary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NativeDictionaryTest {

    @Test
    void testIsKey() {
        NativeDictionary<Integer> dictionary = new NativeDictionary<>(10, Integer.class);
        dictionary.put("key1", 1);
        assertTrue(dictionary.isKey("key1"));
        assertFalse(dictionary.isKey("key2"));
    }

    @Test
    void testPut() {
        NativeDictionary<Integer> dictionary = new NativeDictionary<>(10, Integer.class);
        dictionary.put("key1", 1);
        assertEquals(1, dictionary.get("key1"));

        dictionary.put("key1", 2);
        assertEquals(2, dictionary.get("key1"));
        assertTrue(dictionary.isKey("key1"));
        assertFalse(dictionary.isKey("key2"));

        dictionary.put("key1", 1);
        dictionary.put("key2", 2);
        dictionary.put("key3", 3);

        assertEquals(1, dictionary.get("key1"));
        assertEquals(2, dictionary.get("key2"));
        assertEquals(3, dictionary.get("key3"));

        dictionary.put(null, 1);
        assertNull(dictionary.get(null));
    }

    @Test
    void testGet() {
        NativeDictionary<Integer> dictionary = new NativeDictionary<>(10, Integer.class);
        assertNull(dictionary.get("key1"));

        dictionary.put("key1", 1);
        assertEquals(1, dictionary.get("key1"));
    }
}
