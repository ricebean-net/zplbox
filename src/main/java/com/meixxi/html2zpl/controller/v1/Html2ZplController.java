package com.meixxi.html2zpl.controller.v1;

import com.meixxi.html2zpl.model.RenderingParams;
import com.meixxi.html2zpl.service.image.ImageService;
import com.meixxi.html2zpl.service.render.RenderService;
import com.meixxi.html2zpl.service.zpl.ZplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/v1/html2zpl")
public class Html2ZplController {

    private static final Logger log = LoggerFactory.getLogger(Html2ZplController.class);

    @Autowired
    private RenderService renderService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ZplService zplService;

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = {IMAGE_PNG_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<?> createZplLabel(@RequestBody RenderingParams renderingParams) throws Exception {
        log.info("New request has been received...");

       try {

           // render web content
           BufferedImage bufferedImage = renderService.renderWebContent(renderingParams);

           // convert to monochrome
           bufferedImage = imageService.convertToMonochrome(bufferedImage);

           // create ZPL data
           String zplData = zplService.createLabel(bufferedImage);

           // return zpl data
           HttpHeaders headers = new HttpHeaders();
           headers.add(HttpHeaders.CONTENT_TYPE, TEXT_PLAIN_VALUE);
           return new ResponseEntity<>(zplData, headers, HttpStatus.OK);

       } catch (Exception ex) {

           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
}
