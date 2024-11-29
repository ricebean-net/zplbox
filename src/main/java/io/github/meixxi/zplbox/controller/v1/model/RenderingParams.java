package io.github.meixxi.zplbox.controller.v1.model;

import io.github.meixxi.zplbox.controller.v1.model.types.Orientation;

/**
 * Common rendering params.
 */
public interface RenderingParams {

    String getUrl();

    String getDataBase64();

    Orientation getOrientation();
}
