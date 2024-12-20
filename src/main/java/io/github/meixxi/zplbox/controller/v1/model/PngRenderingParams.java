package io.github.meixxi.zplbox.controller.v1.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.meixxi.zplbox.controller.v1.model.types.Orientation;

/**
 * PNG RenderingParams model object.
 */
@JsonDeserialize(builder = PngRenderingParams.Builder.class)
public class PngRenderingParams implements RenderingParams {

    private final String url;
    private final String dataBase64;
    private final Orientation orientation;

    /**
     * Private constructor.
     * @param builder Builder object.
     */
    private PngRenderingParams(Builder builder) {
        this.url = builder.url;
        this.dataBase64 = builder.dataBase64;
        this.orientation = builder.orientation;
    }

    public String getUrl() {
        return url;
    }

    public String getDataBase64() {
        return dataBase64;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * PdfRenderingParams Builder
     */
    public static class Builder{

        private String url;
        private String dataBase64;
        private Orientation orientation = Orientation.Rotate0;

        public Builder() {
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withDataBase64(String dataBase64) {
            this.dataBase64 = dataBase64;
            return this;
        }

        public Builder withOrientation(Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public PngRenderingParams build() {
            return new PngRenderingParams(this);
        }
    }
}
