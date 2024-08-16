package com.meixxi.html2zpl.service.image;

import java.awt.image.BufferedImage;

public interface ImageService {

    /**
     * Convert a BufferedImage object to monochrome.
     */
    BufferedImage convertToMonochrome(BufferedImage img);

    /**
     * Scale a BufferedImage object by a factor.
     */
    BufferedImage resize(BufferedImage image, float scaleFactor);
}
