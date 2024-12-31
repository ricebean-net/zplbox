package io.github.meixxi.zplbox.service.render;

import io.github.meixxi.zplbox.util.URIs;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.net.URI;

@Service
public class PdfRenderServiceImpl implements PdfRenderService {

    @Override
    public BufferedImage render(URI sourceUri, int dotsPerInch) throws Exception {

        // load URI as byte array
        byte[] bytes = URIs.readAllBytes(sourceUri);

        // load pdf document
        PDDocument document = PDDocument.load(bytes);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        // render and return
        return pdfRenderer.renderImageWithDPI(0, dotsPerInch, ImageType.RGB);
    }


}
