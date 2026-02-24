package net.ricebean.zplbox.service.zpl;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ZplService {

    /**
     * Create an ZPL Label from a monochrome buffered image.
     * @param bufferedImages The monochrome buffered images.
     * @return The ZPL Data as string.
     */
    String createZplData(List<BufferedImage> bufferedImages);
}
