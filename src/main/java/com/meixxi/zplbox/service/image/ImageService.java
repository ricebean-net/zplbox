package com.meixxi.zplbox.service.image;

import java.awt.image.BufferedImage;

public interface ImageService {

    /**
     * Convert a buffered image object into a monochrome buffered image object.
     * @param img The image to be converted.
     * @return The monochrome buffered image.
     */
    BufferedImage convertToMonochrome(BufferedImage img);

}
