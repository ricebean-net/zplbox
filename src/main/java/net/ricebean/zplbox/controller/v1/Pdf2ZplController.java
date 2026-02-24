package net.ricebean.zplbox.controller.v1;

import net.ricebean.zplbox.controller.v1.model.PdfRenderingParams;
import net.ricebean.zplbox.service.render.PdfRenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/pdf2zpl")
public class Pdf2ZplController extends ConverterController<PdfRenderingParams> {

    private static final Logger log = LoggerFactory.getLogger(Pdf2ZplController.class);

    @Autowired
    private PdfRenderService pdfRenderService;

    @Override
    protected List<BufferedImage> renderContent(PdfRenderingParams pdfRenderingParams, URI sourceUri) throws Exception {
        return pdfRenderService.render(sourceUri, pdfRenderingParams.getDotsPerInch());
    }
}
