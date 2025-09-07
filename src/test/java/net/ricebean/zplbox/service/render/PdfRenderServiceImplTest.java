package net.ricebean.zplbox.service.render;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PdfRenderServiceImplTest {

    @InjectMocks
    private PdfRenderServiceImpl pdfRenderService;

    private final static String RES_ROOT = "/com/meixxi/zplbox/service/render/";

//    @Test
    void render() throws Exception {

        // arrange
        URI sourceUri = PdfRenderServiceImplTest.class.getResource(RES_ROOT + "source-1.pdf").toURI();

        BufferedImage expected = ImageIO.read(PdfRenderServiceImplTest.class.getResource(RES_ROOT + "source-1.result.png"));

        // act
        BufferedImage actual = pdfRenderService.render(sourceUri, 203);

        // assert
        assertNotNull(actual, "BufferedImage is null.");
        assertTrue(compareImages(actual, expected), "Rendering is wrong.");
    }

    /**
     * Helper image to compare images.
     */
    public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // Check if the images have the same dimensions
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }

        // Compare pixel by pixel
        for (int y = 0; y < imgA.getHeight(); y++) {
            for (int x = 0; x < imgA.getWidth(); x++) {
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }
}