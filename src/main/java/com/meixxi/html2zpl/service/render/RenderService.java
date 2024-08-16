package com.meixxi.html2zpl.service.render;

import com.meixxi.html2zpl.model.RenderingParams;

import java.awt.image.BufferedImage;

public interface RenderService {

    /**
     * Rendering a given web content.
     * @param renderingParams The rendering params.
     * @return The rendered content as buffered image.
     */
    BufferedImage renderWebContent(RenderingParams renderingParams) throws Exception;
}
