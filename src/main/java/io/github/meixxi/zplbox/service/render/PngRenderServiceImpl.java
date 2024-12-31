package io.github.meixxi.zplbox.service.render;

import io.github.meixxi.zplbox.util.URIs;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URI;

@Service
public class PngRenderServiceImpl implements PngRenderService {

    @Override
    public BufferedImage render(URI sourceUri) throws Exception {
        return ImageIO.read(new ByteArrayInputStream(URIs.readAllBytes(sourceUri)));
    }
}
