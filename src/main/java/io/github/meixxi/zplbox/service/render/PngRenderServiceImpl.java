package io.github.meixxi.zplbox.service.render;

import io.github.meixxi.zplbox.util.URIs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URI;

public class PngRenderServiceImpl implements PngRenderService {

    @Override
    public BufferedImage render(URI sourceUri) throws Exception {
        return ImageIO.read(new ByteArrayInputStream(URIs.readAllBytes(sourceUri)));
    }
}
