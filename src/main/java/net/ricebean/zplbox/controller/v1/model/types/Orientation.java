package net.ricebean.zplbox.controller.v1.model.types;

/**
 * Orientations enum.
 */
public enum Orientation {
    Rotate0(0.0),
    Rotate90(90.0),
    Rotate180(180.0),
    Rotate270(270.0);

    private final double degrees;

    Orientation(double degrees) {
        this.degrees = degrees;
    }

    public double getDegrees() {
        return degrees;
    }

    @Override
    public String toString() {
        return name() + " (" + degrees + "°)";
    }
}
