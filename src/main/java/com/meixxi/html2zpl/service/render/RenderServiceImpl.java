package com.meixxi.html2zpl.service.render;

import com.meixxi.html2zpl.controller.v1.Html2ZplController;
import com.meixxi.html2zpl.model.RenderingParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class RenderServiceImpl implements RenderService {

    private static final Logger log = LoggerFactory.getLogger(RenderServiceImpl.class);

    @Override
    public BufferedImage renderWebContent(RenderingParams renderingParams) throws Exception {

        // create temp file
        Path pathRenderedPng = Files.createTempFile("html2zpl-" + UUID.randomUUID(), ".png");

        // construct chromium rendering command
        String[] command = new String[]{
                "chromium-browser",
                "--headless",
                "--no-sandbox",
                "--timeout=5000",
                String.format("--window-size=%d,%d", renderingParams.getWidthPts(), renderingParams.getHeightPts()),
                String.format("--screenshot=%s", pathRenderedPng),
                renderingParams.getUrl()
        } ;

        // execute command
        executeCommand(command);

        // cache image and remove temp file
        BufferedImage bufferedImage = ImageIO.read(pathRenderedPng.toFile());
        Files.delete(pathRenderedPng);

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
            log.error("Chromium: {}", errorLine);
        }

        int exitCode = process.waitFor();
        log.info("Chromium: Process exited with code: {}", exitCode);
    }
}
