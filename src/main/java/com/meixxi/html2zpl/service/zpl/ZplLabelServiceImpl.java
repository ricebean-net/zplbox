package com.meixxi.html2zpl.service.zpl;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;

@Service
public class ZplLabelServiceImpl implements ZplLabelService {

    @Override
    public String createLabel(BufferedImage bufferedImage) {

        // convert to grf bytes
        byte[] grfBytes = createGrfBytes(bufferedImage);

        // create final ZPL String
        String zplData = String.format("""
                ^XA
                ^FO0,0^GFA,%d,%d,%d,%s
                ^XZ
                """,
                grfBytes.length,
                grfBytes.length,
                grfBytes.length / bufferedImage.getHeight(),
                Hex.encodeHexString(grfBytes)
                );

        // return zpl data
        return zplData;
    }

    /**
     * Create an ascii hex string value of a monochrome image.
     * @param bufferedImage THe monochrome image as byte array.
     * @return The ASCII hexadecimal string.
     */
    byte[] createGrfBytes(BufferedImage bufferedImage) {
        DataBuffer dataBuffer = bufferedImage.getData().getDataBuffer();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int imgWidth = bufferedImage.getWidth() / 8;
        if (bufferedImage.getWidth() % 8 > 0) {
            imgWidth ++;
        }

        int imgHeight = bufferedImage.getHeight();
        int dataPointer = 0;
        for(int y = 0; y < imgHeight; y++) {
            for(int x = 0; x < imgWidth; x ++) {
                int byteValue = invert(dataBuffer.getElem(dataPointer ++));
                if (x == imgWidth - 1) {
                    byteValue = maskOff(byteValue, getNumBitsToMask(bufferedImage.getWidth()));
                }
                bos.write(byteValue);
            }
        }

        // convert to hex code and return
        return bos.toByteArray();
    }

    private static int getNumBitsToMask(int value) {
        return (8 - value % 8) % 8;
    }

    private static int maskOff(int value1, int value2) {
        return value1 & 255 << value2 & 255;
    }

    private static int invert(int value) {
        return ~value & 255;
    }
}
