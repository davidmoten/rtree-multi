package com.github.davidmoten.rtree2.geometry.internal;

import java.util.Arrays;

import com.github.davidmoten.guavamini.Preconditions;
import com.github.davidmoten.rtree2.geometry.Geometry;
import com.github.davidmoten.rtree2.geometry.Rectangle;
import com.github.davidmoten.rtree2.internal.util.ObjectsHelper;

public final class RectangleDouble implements Rectangle {

    private final double[] mins;
    private final double[] maxes;

    private RectangleDouble(double[] mins, double[] maxes) {
        Preconditions.checkArgument(mins.length == maxes.length);
        for (int i = 0; i < mins.length; i++) {
            Preconditions.checkArgument(maxes[i] >= mins[i]);
        }
        this.mins = mins;
        this.maxes = maxes;
    }

    public static RectangleDouble create(double[] mins, double[] maxes) {
        return new RectangleDouble(mins, maxes);
    }

    @Override
    public Rectangle add(Rectangle r) {
        double[] a = new double[mins.length];
        double[] b = new double[mins.length];
        for (int i = 0; i < a.length; i++) {
            // TODO minor perf improvement - use if
            a[i] = min(mins[i], r.min(i));
            b[i] = max(maxes[i], r.max(i));
        }
        return new RectangleDouble(a, b);
    }

    @Override
    public boolean contains(double... p) {
        Preconditions.checkArgument(mins.length == p.length);
        for (int i = 0; i < p.length; i++) {
            if (p[i] < mins[i] || p[i] > maxes[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean intersects(Rectangle r) {
        return GeometryUtil.intersects(mins, maxes, r.mins(), r.maxes());
    }

    @Override
    public double distance(Rectangle r) {
        return GeometryUtil.distance(mins, maxes, r.mins(), r.maxes());
    }

    @Override
    public Rectangle mbr() {
        return this;
    }

    @Override
    public String toString() {
        return "Rectangle [x=" + Arrays.toString(mins) + ", y=" + Arrays.toString(maxes) + "]";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (mins == null ? 0 : Arrays.hashCode(mins));
        result = 31 * result + (maxes == null ? 0 : Arrays.hashCode(maxes));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        RectangleDouble other = ObjectsHelper.asClass(obj, RectangleDouble.class);
        if (other != null) {
            return Arrays.equals(mins, other.mins) && Arrays.equals(maxes, other.maxes);
        } else
            return false;
    }

    @Override
    public double intersectionArea(Rectangle r) {
        if (!intersects(r))
            return 0;
        else {
            return create(GeometryUtil.max(mins, r.mins()), GeometryUtil.min(maxes, r.maxes())).volume();
        }
    }

    @Override
    public Geometry geometry() {
        return this;
    }

    private static double max(double a, double b) {
        if (a < b)
            return b;
        else
            return a;
    }

    private static double min(double a, double b) {
        if (a < b)
            return a;
        else
            return b;
    }

    @Override
    public double surfaceArea() {
        double sum = 0;
        for (int i = 0; i < mins.length; i++) {
            double product = 1;
            for (int j = 0; j < mins.length; j++) {
                if (i != j) {
                    product *= maxes[j] - mins[j];
                }
            }
            sum += product;
        }
        return 2 * sum;
    }

    @Override
    public double volume() {
        double v = 1;
        for (int i = 0; i < mins.length; i++) {
            v *= maxes[i] - mins[i];
        }
        return v;
    }

    @Override
    public double[] mins() {
        return mins;
    }

    @Override
    public double[] maxes() {
        return maxes;
    }

    @Override
    public int dimensions() {
        return mins.length;
    }

}
