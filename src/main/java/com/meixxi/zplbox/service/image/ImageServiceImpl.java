package com.meixxi.zplbox.service.image;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

@Service
public class ImageServiceImpl implements ImageService {

    /**
     * Convert a BufferedImage object to monochrome.
     */
    public BufferedImage convertToMonochrome(BufferedImage img) {
        BufferedImage monochromeImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g2d = monochromeImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return monochromeImage;
    }

    /**
     * Scale a BufferedImage object by a factor.
     */
    public BufferedImage resize(BufferedImage image, float scaleFactor) {
        int scaledWidth = Math.round(image.getWidth() * scaleFactor);
        int scaledHeight = Math.round(image.getHeight() * scaleFactor);

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(scaleFactor, scaleFactor);

        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, image.getType());
        resizedImage = affineTransformOp.filter(image, resizedImage);

        return resizedImage;
    }


}
