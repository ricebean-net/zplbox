package io.github.meixxi.zplbox.controller.v1.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * RenderingParams model object.
 */
@JsonDeserialize(builder = PdfRenderingParams.Builder.class)
public class PdfRenderingParams {

    private final String url;
    private final String dataBase64;
    private final int dotsPerInch;

    /**
     * Private constructor.
     * @param builder Builder object.
     */
    private PdfRenderingParams(Builder builder) {
        this.url = builder.url;
        this.dataBase64 = builder.dataBase64;
        this.dotsPerInch = builder.dotsPerInch;
    }

    public String getUrl() {
        return url;
    }

    public String getDataBase64() {
        return dataBase64;
    }

    public int getDotsPerInch() {
        return dotsPerInch;
    }

    /**
     * PdfRenderingParams Builder
     */
    public static class Builder{

        private String url;
        private String dataBase64;
        private int dotsPerInch;

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

        public Builder withDotsPerInch(int dotsPerInch) {
            this.dotsPerInch = dotsPerInch;
            return this;
        }

        public PdfRenderingParams build() {
            return new PdfRenderingParams(this);
        }
    }
}
