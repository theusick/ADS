package ru.ads.hashtable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void testHashFun() {
        HashTable table = new HashTable(10, 3);
        assertEquals(0, table.hashFun("apple"));
        assertEquals(9, table.hashFun("banana"));
        assertEquals(3, table.hashFun("cherry"));
    }

    @Test
    void testSeekSlot() {
        HashTable table = new HashTable(10, 3);
        assertEquals(0, table.seekSlot("apple"));
        table.put("apple");
        assertEquals(9, table.seekSlot("banana"));
    }

    @Test
    void testPut() {
        HashTable table = new HashTable(3, 1); // Очень маленькая таблица
        assertTrue(table.put("apple") >= 0);
        assertTrue(table.put("banana") >= 0);
        assertTrue(table.put("cherry") >= 0);
        assertEquals(-1, table.put("date"));
    }

    @Test
    void testFind() {
        HashTable table = new HashTable(10, 3);
        int appleIndex = table.put("apple");
        assertTrue(appleIndex >= 0);
        assertEquals(appleIndex, table.find("apple"));

        int bananaIndex = table.put("banana");
        assertTrue(bananaIndex >= 0);
        assertEquals(bananaIndex, table.find("banana"));

        int cherryIndex = table.put("cherry");
        assertTrue(cherryIndex >= 0);
        assertEquals(cherryIndex, table.find("cherry"));

        assertEquals(-1, table.find("date"));
    }
}