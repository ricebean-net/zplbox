package com.meixxi.zplbox.util;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

public class Urls {

    /**
     * Normalize an input if url and base64 can be provided simultaneously.
     * @param url The url the file.
     * @param dataBase64 The file's content as base 64.
     * @return The normalized URL.
     */
    public static URI normalize(String url, String dataBase64) throws IOException {
        final URI normalizedUri;

        if(url != null) {
            normalizedUri = URI.create(url);;

        } else if(dataBase64 != null) {
            normalizedUri = Files.createTempFile("zplbox-" + UUID.randomUUID(), ".html").toUri();
            Files.write(Path.of(normalizedUri), Base64.getDecoder().decode(dataBase64));

        } else {
            throw new IllegalArgumentException("URL or DataBase64 must be provided.");
        }

        return normalizedUri;
    }
}
