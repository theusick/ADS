package ru.ads.recursion;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DirTraversalRecursiveTest {

    private static final String TEST_DIR = "src/test/resources/directories";

    @BeforeAll
    public static void setUp() throws IOException {
        Path dir1 = Paths.get(TEST_DIR + "/dir1");
        Path dir2 = Paths.get(TEST_DIR + "/dir2");
        Path subdir2 = Paths.get(TEST_DIR + "/dir2/subdir2");
        Path dir3 = Paths.get(TEST_DIR + "/dir3");

        Files.createDirectories(dir1);
        Files.createDirectories(subdir2);
        Files.createDirectories(dir3);

        Files.createFile(dir1.resolve("file1.txt"));
        Files.createFile(dir2.resolve("file2.txt"));
        Files.createFile(dir2.resolve("file3.txt"));
        Files.createFile(subdir2.resolve("file4.txt"));
        Files.createFile(subdir2.resolve("file5.txt"));
        Files.createFile(subdir2.resolve("file6.txt"));
    }

    @AfterAll
    public static void tearDown() throws IOException {
        Path path = Paths.get(TEST_DIR);

        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    void testFindDirFilesFullDir() {
        List<String> files = RecursionFunctions.findDirFilesRecursive(TEST_DIR);

        assertTrue(files.contains("file1.txt"));
        assertTrue(files.contains("file2.txt"));
        assertTrue(files.contains("file3.txt"));
        assertTrue(files.contains("file4.txt"));
        assertTrue(files.contains("file5.txt"));
        assertTrue(files.contains("file6.txt"));

        assertEquals(6, files.size());
    }

    @Test
    void testFindDirFilesDir1() {
        List<String> files = RecursionFunctions.findDirFilesRecursive(TEST_DIR + "/dir1");

        assertTrue(files.contains("file1.txt"));
        assertFalse(files.contains("file2.txt"));

        assertEquals(1, files.size());
    }

    @Test
    void testFindDirFilesDir2() {
        List<String> files = RecursionFunctions.findDirFilesRecursive(TEST_DIR + "/dir2");

        assertTrue(files.contains("file2.txt"));
        assertTrue(files.contains("file3.txt"));
        assertTrue(files.contains("file4.txt"));
        assertTrue(files.contains("file5.txt"));
        assertTrue(files.contains("file6.txt"));

        assertFalse(files.contains("file1.txt"));

        assertEquals(5, files.size());
    }

    @Test
    void testFindDirFilesEmptyDir() throws IOException {
        String emptyDir = TEST_DIR + "/empty";
        Files.createDirectory(Paths.get(emptyDir));

        List<String> files = RecursionFunctions.findDirFilesRecursive(emptyDir);
        assertTrue(files.isEmpty());

        Files.delete(Paths.get(emptyDir));
    }

    @Test
    void testFindDirFilesNonExistentDir() {
        List<String> files = RecursionFunctions.findDirFilesRecursive(TEST_DIR + "/nonexistent");
        assertTrue(files.isEmpty());
    }

    @Test
    void testFindDirFilesNullPath() {
        List<String> files = RecursionFunctions.findDirFilesRecursive(null);
        assertTrue(files.isEmpty());
    }

}
