package com.meixxi.html2zpl.service.render;

import com.meixxi.html2zpl.controller.v1.model.HtmlRenderingParams;

import java.awt.image.BufferedImage;

public interface RenderService {

    /**
     * Rendering a given web content.
     * @param renderingParams The rendering params.
     * @return The rendered content as buffered image.
     */
    BufferedImage renderWebContent(HtmlRenderingParams renderingParams) throws Exception;
}
