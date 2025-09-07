package net.ricebean.zplbox.service.image;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    void convertToMonochrome() throws Exception {

        // arrange
        InputStream is = ImageServiceImplTest.class.getResourceAsStream("/net/ricebean/zplbox/service/image/label-1.png");
        BufferedImage img = ImageIO.read(is);

        // act
        BufferedImage result = imageService.convertToMonochrome(img);

        // assert
        assertNotNull(result, "Result is null.");
    }

    @Test
    void rotate_0() throws Exception {

        // arrange
        InputStream is = ImageServiceImplTest.class.getResourceAsStream("/net/ricebean/zplbox/service/image/label-1.png");
        BufferedImage img = ImageIO.read(is);

        // act
        BufferedImage result = imageService.rotate(img, 0);

        // assert
        assertNotNull(result, "Result is null.");
        assertSame(img, result);
    }

    @Test
    void rotate_90() throws Exception {

        // arrange
        InputStream is = ImageServiceImplTest.class.getResourceAsStream("/net/ricebean/zplbox/service/image/label-1.png");
        BufferedImage img = ImageIO.read(is);

        // act
        BufferedImage result = imageService.rotate(img, 90);

        // assert
        assertNotNull(result, "Result is null.");
        assertNotSame(img, result);
    }
}