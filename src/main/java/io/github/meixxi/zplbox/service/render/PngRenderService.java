package io.github.meixxi.zplbox.service.render;

import java.awt.image.BufferedImage;
import java.net.URI;

public interface PngRenderService {

    /**
     * Rendering a given png content.
     *
     * @param sourceUri The png source to be rendered.
     * @return The rendered content as buffered image.
     */
    BufferedImage render(URI sourceUri) throws Exception;
}
