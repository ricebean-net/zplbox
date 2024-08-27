package com.meixxi.html2zpl.service.render;

import com.meixxi.html2zpl.controller.v1.model.HtmlRenderingParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

@Service
public class RenderServiceImpl implements RenderService {

    private static final Logger log = LoggerFactory.getLogger(RenderServiceImpl.class);

    @Override
    public BufferedImage renderWebContent(HtmlRenderingParams renderingParams) throws Exception {

        // check inputs
        final Path pathSourceHtml;
        final String url;

        if(renderingParams.getUrl() != null) {
            pathSourceHtml = null;
            url = renderingParams.getUrl();

        } else if(renderingParams.getUrl() == null && renderingParams.getDataBase64() != null) {
            pathSourceHtml = Files.createTempFile("html2zpl-" + UUID.randomUUID(), ".html");
            url = pathSourceHtml.toString();
            Files.write(pathSourceHtml, Base64.getDecoder().decode(renderingParams.getDataBase64()));

        } else {
            throw new IllegalArgumentException("URL or DataBAse64 must be provided.");
        }

        // create temp file
        Path pathRenderedPng = Files.createTempFile("html2zpl-" + UUID.randomUUID(), ".png");

        // construct chromium rendering command
        String[] command = new String[]{
                "chromium-browser",
                "--headless",
                "--disable-gpu",
                "--no-sandbox",
                "--virtual-time-budget=10000",
                "--run-all-compositor-stages-before-draw",
                "--hide-scrollbars",
                "--disable-software-rasterizer",
                String.format("--window-size=%d,%d", renderingParams.getWidthPts(), renderingParams.getHeightPts()),
                String.format("--screenshot=%s", pathRenderedPng),
                url
        } ;

        BufferedImage bufferedImage;

        try {
            // execute command
            executeCommand(command);

            // cache image
            bufferedImage = ImageIO.read(pathRenderedPng.toFile());

        } catch (Exception ex) {
            throw new Exception();

        } finally {
            // clean up
            Files.delete(pathRenderedPng);

            if(pathSourceHtml != null) {
                Files.delete(pathSourceHtml);
            }
        }

        // return rendered image
        return bufferedImage;
    }

    /**
     * Helper method to execute chromium as process.
     */
    private void executeCommand(String[] command) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        // read stdout
        BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = stdOut.readLine()) != null) {
            log.info("Chromium: {}", line);
        }

        // stderr
        BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorLine;
        while ((errorLine = stdErr.readLine()) != null) {
            log.warn("Chromium: {}", errorLine);
        }

        int exitCode = process.waitFor();
        log.info("Chromium: Process exited with code: {}", exitCode);
    }
}
