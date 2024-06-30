package ru.ads.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NativeCacheTest {
    private NativeCache<String> cache;
    private final int CACHE_SIZE = 5;

    @BeforeEach
    void setUp() {
        cache = new NativeCache<>(CACHE_SIZE, String.class);
    }

    @Test
    void testPutAndGet() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        cache.put("key4", "value4");
        cache.put("key5", "value5");

        assertEquals("value1", cache.get("key1"));
        assertEquals("value2", cache.get("key2"));
        assertEquals("value3", cache.get("key3"));
        assertEquals("value4", cache.get("key4"));
        assertEquals("value5", cache.get("key5"));
    }

    @Test
    void testHashCollisionAndDisplacement() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        cache.put("key4", "value4");
        cache.put("key5", "value5");

        assertEquals("value1", cache.get("key1"));
        assertEquals("value2", cache.get("key2"));
        assertEquals("value3", cache.get("key3"));
        assertEquals("value4", cache.get("key4"));
        assertEquals("value5", cache.get("key5"));

        cache.put("key6", "value6");

        assertNull(cache.get("key1"));
        assertEquals("value6", cache.get("key6"));
    }

    @Test
    void testKeyUsageCount() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");

        cache.get("key1");
        cache.get("key1");
        cache.get("key2");

        cache.put("key3", "value3");
        cache.put("key4", "value4");
        cache.put("key5", "value5");
        cache.put("key6", "value6");

        assertEquals("value1", cache.get("key1"));
        assertNull(cache.get("key4"));
    }

    @Test
    void testIsKey() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");

        assertTrue(cache.isKey("key1"));
        assertTrue(cache.isKey("key2"));
        assertFalse(cache.isKey("key3"));

        cache.put("key3", "value3");

        assertTrue(cache.isKey("key3"));
    }
}