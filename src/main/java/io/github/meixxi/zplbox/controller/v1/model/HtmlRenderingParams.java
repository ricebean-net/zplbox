package io.github.meixxi.zplbox.controller.v1.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.meixxi.zplbox.controller.v1.model.types.Orientation;

/**
 * RenderingParams model object.
 */
@JsonDeserialize(builder = HtmlRenderingParams.Builder.class)
public class HtmlRenderingParams implements RenderingParams {

    private final String url;
    private final String dataBase64;
    private final int widthPts;
    private final int heightPts;
    private final Orientation orientation;

    /**
     * Private constructor.
     * @param builder Builder object.
     */
    private HtmlRenderingParams(Builder builder) {
        this.url = builder.url;
        this.dataBase64 = builder.dataBase64;
        this.widthPts = builder.widthPts;
        this.heightPts = builder.heightPts;
        this.orientation = builder.orientation;
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

    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * HtmlRenderingParams Builder
     */
    public static class Builder {

        private String url;
        private String dataBase64;
        private int widthPts;
        private int heightPts;
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

        public Builder withWidthPts(int widthPts) {
            this.widthPts = widthPts;
            return this;
        }

        public Builder withHeightPts(int heightPts) {
            this.heightPts = heightPts;
            return this;
        }

        public Builder withOrientation(Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public HtmlRenderingParams build() {
            return new HtmlRenderingParams(this);
        }
    }
}
