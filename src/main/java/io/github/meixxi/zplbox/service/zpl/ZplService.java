package io.github.meixxi.zplbox.service.zpl;

import java.awt.image.BufferedImage;

public interface ZplService {

    /**
     * Create an ZPL Label from a monochrome buffered image.
     * @param bufferedImage The monochrome buffered image.
     * @return The ZPL Data as string.
     */
    String createLabel(BufferedImage bufferedImage);
}
