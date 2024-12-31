package io.github.meixxi.zplbox.controller.v1;

import io.github.meixxi.zplbox.controller.v1.model.PngRenderingParams;
import io.github.meixxi.zplbox.service.render.PngRenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.net.URI;

@RestController
@RequestMapping("/v1/png2zpl")
public class Png2ZplController extends ConverterController<PngRenderingParams> {

    private static final Logger log = LoggerFactory.getLogger(Png2ZplController.class);

    @Autowired
    private PngRenderService pngRenderService;

    @Override
    protected BufferedImage renderContent(PngRenderingParams pngRenderingParams, URI sourceUri) throws Exception {
        return pngRenderService.render(sourceUri);
    }
}
