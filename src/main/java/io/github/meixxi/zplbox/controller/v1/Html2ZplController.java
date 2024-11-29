package io.github.meixxi.zplbox.controller.v1;

import io.github.meixxi.zplbox.controller.v1.model.HtmlRenderingParams;
import io.github.meixxi.zplbox.service.render.HtmlRenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.net.URI;

@RestController
@RequestMapping("/v1/html2zpl")
public class Html2ZplController extends ConverterController<HtmlRenderingParams> {

    private static final Logger log = LoggerFactory.getLogger(Html2ZplController.class);

    @Autowired
    private HtmlRenderService htmlRenderService;

    @Override
    protected BufferedImage renderContent(HtmlRenderingParams htmlRenderingParams, URI sourceUri) throws Exception {
        return htmlRenderService.render(sourceUri, htmlRenderingParams.getWidthPts(), htmlRenderingParams.getHeightPts());
    }
}
