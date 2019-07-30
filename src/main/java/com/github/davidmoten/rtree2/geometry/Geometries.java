package com.github.davidmoten.rtree2.geometry;

import com.github.davidmoten.rtree2.geometry.internal.PointDouble;
import com.github.davidmoten.rtree2.geometry.internal.RectangleDouble;

public final class Geometries {

    private Geometries() {
        // prevent instantiation
    }

    public static Point point(double[] x) {
        return PointDouble.create(x);
    }

    public static Rectangle rectangle(double[] x, double[] y) {
        return RectangleDouble.create(x, y);
    }

}
