package net.ricebean.zplbox.service.render;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

public interface PdfRenderService {

    /**
     * Rendering a given pdf document.
     * @param sourceUri The pdf source to be rendered.
     * @param dotsPerInch The resolution in dots per inch (dpi).
     * @return The rendered pdf content as buffered image.
     */
    List<BufferedImage> render(URI sourceUri, int dotsPerInch) throws Exception;
}
