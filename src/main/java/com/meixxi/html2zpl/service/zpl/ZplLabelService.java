package com.meixxi.html2zpl.service.zpl;

import java.awt.image.BufferedImage;

public interface ZplLabelService {

    /**
     * Create an ZPL Label from a buffered image.
     * @param bufferedImage The buffered image.
     * @return The ZPL Data as string.
     */
    String createLabel(BufferedImage bufferedImage);
}
