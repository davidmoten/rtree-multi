package com.github.davidmoten.rtree2.geometry.internal;

import java.util.Arrays;

import com.github.davidmoten.guavamini.Preconditions;
import com.github.davidmoten.rtree2.geometry.Geometry;
import com.github.davidmoten.rtree2.geometry.Rectangle;
import com.github.davidmoten.rtree2.internal.util.ObjectsHelper;

public final class RectangleDouble implements Rectangle {

    private final double[] x;
    private final double[] y;

    private RectangleDouble(double[] x, double y[]) {
        Preconditions.checkArgument(x.length == y.length);
        for (int i = 0; i < x.length; i++) {
            Preconditions.checkArgument(y[i] >= x[i]);
        }
        this.x = x;
        this.y = y;
    }

    public static RectangleDouble create(double x[], double[] y) {
        return new RectangleDouble(x, y);
    }

    @Override
    public Rectangle add(Rectangle r) {
        double[] a = new double[x.length];
        double[] b = new double[x.length];
        for (int i = 0; i < a.length; i++) {
            // TODO minor perf improvement - use if
            a[i] = min(x[i], r.x()[i]);
            b[i] = max(x[i], r.x()[i]);
        }
        return new RectangleDouble(a, b);
    }

    @Override
    public boolean contains(double[] p) {
        Preconditions.checkArgument(x.length == p.length);
        for (int i = 0; i < p.length; i++) {
            if (p[i] < x[i] || p[i] > y[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean intersects(Rectangle r) {
        return GeometryUtil.intersects(x, y, r.x(), r.y());
    }

    @Override
    public double distance(Rectangle r) {
        return GeometryUtil.distance(x, y, r.x(), r.y());
    }

    @Override
    public Rectangle mbr() {
        return this;
    }

    @Override
    public String toString() {
        return "Rectangle [x=" + Arrays.toString(x) + ", y=" + Arrays.toString(y) + "]";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (x == null ? 0 : Arrays.hashCode(x));
        result = 31 * result + (y == null ? 0 : Arrays.hashCode(y));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        RectangleDouble other = ObjectsHelper.asClass(obj, RectangleDouble.class);
        if (other != null) {
            return Arrays.equals(x, other.x) && Arrays.equals(y, other.y);
        } else
            return false;
    }

    @Override
    public double intersectionArea(Rectangle r) {
        if (!intersects(r))
            return 0;
        else {
            return create(min(x, r.x()), max(y, r.y())).area();
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
    public double perimeter() {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            double product = 1;
            for (int j = 0; j < x.length; j++) {
                if (i != j) {
                    product *= y[i] - x[i];
                }
            }
            sum += product;
        }
        return 2 * sum;
    }

    @Override
    public double area() {
        double v = 1;
        for (int i = 0; i < x.length; i++) {
            v *= y[i] - x[i];
        }
        return v;
    }

    @Override
    public boolean isDoublePrecision() {
        return true;
    }

    @Override
    public double[] x() {
        return x;
    }

    @Override
    public double[] y() {
        return y;
    }

}
