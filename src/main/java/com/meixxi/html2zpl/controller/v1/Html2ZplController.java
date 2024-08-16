package com.meixxi.html2zpl.controller.v1;

import com.meixxi.html2zpl.model.RenderingParams;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/v1/html2zpl")
public class Html2ZplController {

    private static final Logger log = LoggerFactory.getLogger(Html2ZplController.class);

    @PostMapping(value = "/render", consumes = APPLICATION_JSON_VALUE, produces = {IMAGE_PNG_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<?> createZplLabel(@RequestBody RenderingParams renderingParams) throws Exception {
        log.info("New request has received.");

        String zplData = "[ZPL_DATA]";

        Path tempDir = Files.createTempDirectory("html2zpl-" + UUID.randomUUID().toString());
        Path pathRenderedPng = tempDir.resolve("rendered.png");
        log.info("Temp file: {}", pathRenderedPng);

        String cmd = String.format(
                "chromium-browser --headless --no-sandbox --timeout=5000 --window-size=%d,%d --screenshot=%s %s",
                renderingParams.getWidthPts(),
                renderingParams.getHeightPts(),
                pathRenderedPng,
                renderingParams.getUrl()
        );

        log.info("Command: {}", cmd);

        ProcessBuilder processBuilder = new ProcessBuilder(cmd.split(" "));
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));


        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }


        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));


//        String line;
        while ((line = errorReader.readLine()) != null) {
            System.out.println("ERR: " + line);
        }

        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Command executed successfully.");
        } else {
            System.out.println("Execution failed with exit code: " + exitCode);
        }

        byte[] png = Files.readAllBytes(pathRenderedPng);
        FileUtils.forceDelete(tempDir.toFile());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, IMAGE_PNG_VALUE);
        return new ResponseEntity<>(png, headers, HttpStatus.OK);

    }
}
