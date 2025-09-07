package net.ricebean.zplbox.controller.v1;

import net.ricebean.zplbox.controller.util.Responses;
import net.ricebean.zplbox.controller.v1.model.RenderingParams;
import net.ricebean.zplbox.service.image.ImageService;
import net.ricebean.zplbox.service.zpl.ZplService;
import net.ricebean.zplbox.util.URIs;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.springframework.http.MediaType.*;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

public abstract class ConverterController<T extends RenderingParams> {

    private static final Logger log = LoggerFactory.getLogger(ConverterController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private ZplService zplService;

    @Autowired
    private ZplController zplController;

    @PostMapping(value = "", consumes = APPLICATION_JSON_VALUE, produces = {TEXT_PLAIN_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<?> createZplLabel(@RequestBody T renderingParams, HttpServletRequest httpServletRequest) throws IOException {
        log.info("New request 'Create ZPL Label' has been received...");

        try {
            String zplData = createZplData(renderingParams);
            return Responses.createOkResponse(zplData, TEXT_PLAIN_VALUE);
        } catch (Exception ex) {
            return Responses.createBadRequestResponse(httpServletRequest, ex);
        }
    }

    @PostMapping(value = "/print/{tcpAddress}", consumes = APPLICATION_JSON_VALUE, produces = {APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<?> createSendZplLabel(@PathVariable String tcpAddress, @RequestBody T renderingParams, HttpServletRequest httpServletRequest) throws IOException {
        log.info("New request 'Create and Send ZPL Label' has been received...");

        // create ZPL data
        String zplData;

        try {
            zplData = createZplData(renderingParams);
        } catch (Exception ex) {
            return Responses.createBadRequestResponse(httpServletRequest, ex);
        }

        // print ZPL label (via controller)
        return zplController.printLabel(tcpAddress, zplData, httpServletRequest);
    }

    @PostMapping(value = "/render", consumes = APPLICATION_JSON_VALUE, produces = {IMAGE_PNG_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<?> renderContent(@RequestBody T renderingParams, HttpServletRequest httpServletRequest) throws IOException {
        log.info("New request 'Render HTML' has been received...");

        try {
            // render web content
            BufferedImage bufferedImage = renderContent(renderingParams);

            // create response
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", bos);
            return Responses.createOkResponse(bos.toByteArray(), IMAGE_PNG_VALUE);

        } catch (Exception ex) {
            return Responses.createBadRequestResponse(httpServletRequest, ex);
        }
    }

    /**
     * Creation of the ZPL data from users content.
     * @param renderingParams The rendering params.
     * @return ZPL data as string.
     */
    private String createZplData(T renderingParams) throws Exception {

        // render web content
        BufferedImage bufferedImage = renderContent(renderingParams);

        // rotate image
        bufferedImage = imageService.rotate(bufferedImage, renderingParams.getOrientation().getDegrees());

        // convert to monochrome
        bufferedImage = imageService.convertToMonochrome(bufferedImage);

        // create and return ZPL data
        return zplService.createLabel(bufferedImage);
    }

    /**
     * Rendering of the user's content to a buffered image object.
     * @param renderingParams The rendering params.
     * @return The rendered content as buffered image.
     */
    private BufferedImage renderContent(T renderingParams) throws Exception {
        URI sourceUri = null;

        try {

            // normalize url
            sourceUri = URIs.normalize(renderingParams.getUrl(), renderingParams.getDataBase64());

            // render and return content
            return renderContent(renderingParams, sourceUri);

        } finally {
            if (sourceUri != null && Objects.equals(sourceUri.getScheme(), "file")) {
                Files.delete(Path.of(sourceUri));
                log.info("File '{}' has been cleaned.", sourceUri);
            }
        }
    }

    /**
     * Rendering of the specific content.
     * @param renderingParams Rendering parameters.
     * @return The rendered content as buffered image.
     */
    protected abstract BufferedImage renderContent(T renderingParams, URI sourceUri) throws Exception;
}
