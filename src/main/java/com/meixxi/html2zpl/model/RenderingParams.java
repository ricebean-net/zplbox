package com.meixxi.html2zpl.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * RenderingParams model object.
 */
@JsonDeserialize(builder = RenderingParams.Builder.class)
public class RenderingParams {

    private final String url;
    private final int widthPts;
    private final int heightPts;

    /**
     * Private constructor.
     * @param builder Builder object.
     */
    private RenderingParams(Builder builder) {
        this.url = builder.url;
        this.widthPts = builder.widthPts;
        this.heightPts = builder.heightPts;
    }

    public String getUrl() {
        return url;
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
        private int widthPts;
        private int heightPts;

        public Builder() {
        }

        public Builder withUrl(String url) {
            this.url = url;
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

        public RenderingParams build() {
            return new RenderingParams(this);
        }
    }
}
