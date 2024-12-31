package io.github.meixxi.zplbox.util;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class URIsTest {

    @Test
    void normalize_0() throws Exception {

        // arrange
        final String url = null;
        final String dataBase64 = null;

        // act
        Throwable t = assertThrows(IllegalArgumentException.class, () -> URIs.normalize(url, dataBase64));

        // assert
        assertEquals("URL or DataBase64 must be provided.", t.getMessage(), "Error message is wrong.");
    }

    @Test
    void normalize_1() throws Exception {

        // arrange
        final String url = "http://localhost:8080/labels/ups-example.html";
        final String dataBase64 = null;

        // act
        URI result = URIs.normalize(url, dataBase64);

        // assert
        assertEquals("http://localhost:8080/labels/ups-example.html", result.toString(), "Normalized URL is wrong.");
        assertNotEquals("file", result.getScheme(), "File is not a local file.");
    }

    @Test
    void normalize_2() throws Exception {

        // arrange
        final String url = null;
        final String dataBase64 = Base64.getEncoder().encodeToString("MY_CONTENT".getBytes());

        // act
        URI result = URIs.normalize(url, dataBase64);

        // assert
        Path path = Path.of(result);
        assertTrue(Files.exists(path), "File does not exist.");
        assertEquals("file", result.getScheme(), "File is a local file.");
        assertEquals("MY_CONTENT", new String(Files.readAllBytes(path)), "File content is wrong.s");

        Files.delete(path);
        assertFalse(Files.exists(path), "File does not exist.");
    }
}