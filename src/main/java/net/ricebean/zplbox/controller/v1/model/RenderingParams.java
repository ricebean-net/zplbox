package net.ricebean.zplbox.controller.v1.model;

import net.ricebean.zplbox.controller.v1.model.types.Orientation;

/**
 * Common rendering params.
 */
public interface RenderingParams {

    String getUrl();

    String getDataBase64();

    Orientation getOrientation();
}
