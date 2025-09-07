package net.ricebean.zplbox.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

public class URIs {

    /**
     * Normalize an input if url and base64 can be provided simultaneously.
     * @param url The url the file.
     * @param dataBase64 The file's content as base 64.
     * @return The normalized URL.
     */
    public static URI normalize(String url, String dataBase64) throws IOException {
        final URI normalizedUri;

        if(url != null) {
            normalizedUri = URI.create(url);

        } else if(dataBase64 != null) {
            normalizedUri = Files.createTempFile("zplbox-" + UUID.randomUUID(), ".html").toUri();
            Files.write(Path.of(normalizedUri), Base64.getDecoder().decode(dataBase64));

        } else {
            throw new IllegalArgumentException("URL or DataBase64 must be provided.");
        }

        return normalizedUri;
    }

    /**
     * Return uri as byte array.
     * @param uri The uri identifying the source.
     * @return The uris content as byte array.
     */
    public static byte[] readAllBytes(URI uri) throws IOException {
        if (uri.getScheme().equals("file")) {
            Path path = Paths.get(uri);
            return Files.readAllBytes(path);

        } else {
            HttpURLConnection httpURLConnection = (HttpURLConnection) uri.toURL().openConnection();
            httpURLConnection.setRequestMethod("GET");

            try (InputStream inputStream = httpURLConnection.getInputStream();
                 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

                int bytesRead;
                byte[] data = new byte[1024];
                while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, bytesRead);
                }
                return buffer.toByteArray();
            }
        }
    }
}
