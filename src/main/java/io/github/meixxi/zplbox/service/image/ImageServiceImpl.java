package io.github.meixxi.zplbox.service.image;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public BufferedImage convertToMonochrome(BufferedImage img) {
        BufferedImage monochromeImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g2d = monochromeImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return monochromeImage;
    }

    @Override
    public BufferedImage rotate(BufferedImage img, double degrees) {
        if(degrees == 0) {
            return img;
        }

        double radians = Math.toRadians(degrees);

        int width = img.getWidth();
        int height = img.getHeight();

        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.floor(width * cos + height * sin);
        int newHeight = (int) Math.floor(height * cos + width * sin);

        AffineTransform transform = new AffineTransform();
        transform.translate((newWidth - width) / 2, (newHeight - height) / 2); // Center the image
        transform.rotate(-radians, width / 2.0, height / 2.0); // Rotate around the center

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, img.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.drawImage(img, transform, null);
        g2d.dispose();

        return rotatedImage;
    }
}
