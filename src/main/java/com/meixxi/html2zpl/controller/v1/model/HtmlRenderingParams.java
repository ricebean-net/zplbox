package com.meixxi.html2zpl.controller.v1.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * RenderingParams model object.
 */
@JsonDeserialize(builder = HtmlRenderingParams.Builder.class)
public class HtmlRenderingParams {

    private final String url;
    private final String dataBase64;
    private final int widthPts;
    private final int heightPts;

    /**
     * Private constructor.
     * @param builder Builder object.
     */
    private HtmlRenderingParams(Builder builder) {
        this.url = builder.url;
        this.dataBase64 = builder.dataBase64;
        this.widthPts = builder.widthPts;
        this.heightPts = builder.heightPts;
    }

    public String getUrl() {
        return url;
    }

    public String getDataBase64() {
        return dataBase64;
    }

    public int getWidthPts() {
        return widthPts;
    }

    public int getHeightPts() {
        return heightPts;
    }

    /**
     * RenderingParams Builder
     */
    public static class Builder{

        private String url;
        private String dataBase64;
        private int widthPts;
        private int heightPts;

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

        public Builder withWidthPts(int widthPts) {
            this.widthPts = widthPts;
            return this;
        }

        public Builder withHeightPts(int heightPts) {
            this.heightPts = heightPts;
            return this;
        }

        public HtmlRenderingParams build() {
            return new HtmlRenderingParams(this);
        }
    }
}
