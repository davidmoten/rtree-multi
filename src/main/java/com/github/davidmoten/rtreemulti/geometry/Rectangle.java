package com.github.davidmoten.rtreemulti.geometry;

/**
 * Represents a rectangle in n dimensions (a hyper-rectangle).
 */
public interface Rectangle extends Geometry, HasGeometry {

    double[] mins();

    double[] maxes();

    double volume();

    double intersectionVolume(Rectangle r);

    double surfaceArea();

    Rectangle add(Rectangle r);

    boolean contains(double... x);

    default double min(int index) {
        return mins()[index];
    }

    default double max(int index) {
        return maxes()[index];
    }

}