package com.github.davidmoten.rtreemulti.geometry;

import com.github.davidmoten.guavamini.Preconditions;
import com.github.davidmoten.rtreemulti.geometry.internal.PointDouble;
import com.github.davidmoten.rtreemulti.geometry.internal.RectangleDouble;

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

    // the first half of the values correspond to the minimum values of every
    // ordinate and the next half of the values correspond to the maximum values of
    // every ordinate
    public static Rectangle rectangle(double... values) {
        Preconditions.checkArgument(values.length >= 4 && values.length % 2 == 0,
                "must be at least 4 values passed and the number of values must be even");
        double[] x = new double[values.length / 2];
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            x[i] = values[i];
            y[i] = values[i + x.length];
        }
        return rectangle(x, y);
    }

}
