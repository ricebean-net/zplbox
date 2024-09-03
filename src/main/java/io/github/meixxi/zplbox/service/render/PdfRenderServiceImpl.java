package io.github.meixxi.zplbox.service.render;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfRenderServiceImpl implements PdfRenderService {

    @Override
    public BufferedImage render(URI sourceUri, int dotsPerInch) throws Exception {

        // load URI as byte array
        byte[] bytes = uriToByteArray(sourceUri);

        // load pdf document
        PDDocument document = PDDocument.load(bytes);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        // render and return
        return pdfRenderer.renderImageWithDPI(0, dotsPerInch, ImageType.RGB);
    }

    /**
     * Helper method to convert URI to a byte array.
     */
    public static byte[] uriToByteArray(URI uri) throws IOException {
        if (uri.getScheme().equals("file")) {
            Path path = Paths.get(uri);
            return Files.readAllBytes(path);

        } else {
            // Handle remote resource
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
