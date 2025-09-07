package net.ricebean.zplbox.service.image;

import java.awt.image.BufferedImage;

public interface ImageService {

    /**
     * Convert a buffered image object into a monochrome buffered image object.
     * @param img The image to be converted.
     * @return The monochrome buffered image.
     */
    BufferedImage convertToMonochrome(BufferedImage img);

    /**
     * Rotation of an image by a given number of degrees.
     * @param img The image to be rotated.
     * @param degrees The degrees to be applied.
     * @return The rotated image
     */
    BufferedImage rotate(BufferedImage img, double degrees);

}
