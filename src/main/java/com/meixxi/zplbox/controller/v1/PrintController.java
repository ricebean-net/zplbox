package com.meixxi.zplbox.controller.v1;

import com.meixxi.zplbox.controller.util.Responses;
import com.meixxi.zplbox.service.print.PrintService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/v1/print")
public class PrintController {

    private static final Logger log = LoggerFactory.getLogger(PrintController.class);

    @Autowired
    private PrintService printService;

    @PostMapping(value = "/{tcpAddress}", consumes = TEXT_PLAIN_VALUE, produces = {APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<?> printLabel(@PathVariable String tcpAddress, @RequestBody String zplData, HttpServletRequest httpServletRequest) {

        try {
            URI uri = new URI("tcp://" + tcpAddress);

            printService.printLabel(zplData, uri.getHost(), uri.getPort());

            log.info("Print label...");
            System.out.println("test");

            return null;
        } catch (Exception ex) {
            log.error("Error printing label.", ex);
            return Responses.createBadRequestResponse(httpServletRequest, ex);
        }

    }
}
