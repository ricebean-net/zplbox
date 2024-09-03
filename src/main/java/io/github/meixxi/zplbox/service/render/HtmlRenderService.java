package io.github.meixxi.zplbox.service.render;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.nio.file.Path;

public interface HtmlRenderService {

    /**
     * Rendering a given web content.
     *
     * @param sourceUri The web content source to be rendered.
     * @param widthPts The target width in points.
     * @param heightPts The target height in points.
     * @return The rendered content as buffered image.
     */
    BufferedImage render(URI sourceUri, int widthPts, int heightPts) throws Exception;
}
