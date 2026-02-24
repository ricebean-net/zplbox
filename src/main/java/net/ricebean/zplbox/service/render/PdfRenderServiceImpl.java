package net.ricebean.zplbox.service.render;

import net.ricebean.zplbox.util.URIs;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfRenderServiceImpl implements PdfRenderService {

    @Override
    public List<BufferedImage> render(URI sourceUri, int dotsPerInch) throws Exception {

        // load URI as byte array
        byte[] bytes = URIs.readAllBytes(sourceUri);

        // load pdf document
        PDDocument document = PDDocument.load(bytes);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        // render and return
        List<BufferedImage> bufferedImages = new ArrayList<>();

        for(int i = 0; i < document.getNumberOfPages(); i ++) {
            bufferedImages.add(pdfRenderer.renderImageWithDPI(i, dotsPerInch, ImageType.RGB));
        }

        // return buffered images
        return bufferedImages;
    }


}
