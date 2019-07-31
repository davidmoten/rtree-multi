package com.github.davidmoten.rtree2.geometry;

import com.github.davidmoten.guavamini.Preconditions;
import com.github.davidmoten.rtree2.geometry.internal.PointDouble;
import com.github.davidmoten.rtree2.geometry.internal.RectangleDouble;

public final class Geometries {

    private Geometries() {
        // prevent instantiation
    }

    public static Point point(double... x) {
        return PointDouble.create(x);
    }

    public static Rectangle rectangle(double[] x, double[] y) {
        return RectangleDouble.create(x, y);
    }

    public static Rectangle rectangle(double... values) {
        Preconditions.checkArgument(values.length >= 4 && values.length % 2 == 0,
                "must be at least 4 values passed and the number of values must be even");
        double[] x = new double[values.length / 2];
        double[] y = new double[x.length];
        for (int i = 0; i < values.length / 2; i++) {
            x[i] = values[i];
        }
        for (int i = values.length / 2; i < values.length; i++) {
            y[i] = values[i];
        }
        return rectangle(x, y);
    }

}
