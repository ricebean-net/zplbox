package io.github.meixxi.zplbox.controller.v1;

import io.github.meixxi.zplbox.controller.v1.model.PdfRenderingParams;
import io.github.meixxi.zplbox.service.render.PdfRenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.net.URI;

@RestController
@RequestMapping("/v1/pdf2zpl")
public class Pdf2ZplController extends ConverterController<PdfRenderingParams> {

    private static final Logger log = LoggerFactory.getLogger(Pdf2ZplController.class);

    @Autowired
    private PdfRenderService pdfRenderService;

    @Override
    protected BufferedImage renderContent(PdfRenderingParams pdfRenderingParams, URI sourceUri) throws Exception {
        return pdfRenderService.render(sourceUri, pdfRenderingParams.getDotsPerInch());
    }
}
