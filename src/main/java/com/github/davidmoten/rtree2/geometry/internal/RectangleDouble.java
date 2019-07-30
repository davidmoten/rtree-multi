package com.github.davidmoten.rtree2.geometry.internal;

import com.github.davidmoten.guavamini.Objects;
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

    public static RectangleDouble create(double x1, double y1, double x2, double y2) {
        return new RectangleDouble(new double[] { x1, y1 }, new double[] { x2, y2 });
    }

    @Override
    public Rectangle add(Rectangle r) {
        double[] a = new double[x.length];
        double[] b = new double[x.length];
        for (int i = 0; i < a.length; i++) {
            //TODO minor perf improvement - use if
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
        return GeometryUtil.intersects(x1, y1, x2, y2, r.x1(), r.y1(), r.x2(), r.y2());
    }

    @Override
    public double distance(Rectangle r) {
        return GeometryUtil.distance(x1, y1, x2, y2, r.x1(), r.y1(), r.x2(), r.y2());
    }

    @Override
    public Rectangle mbr() {
        return this;
    }

    @Override
    public String toString() {
        return "Rectangle [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x1, y1, x2, y2);
    }

    @Override
    public boolean equals(Object obj) {
        RectangleDouble other = ObjectsHelper.asClass(obj, RectangleDouble.class);
        if (other != null) {
            return Objects.equal(x1, other.x1) && Objects.equal(x2, other.x2) && Objects.equal(y1, other.y1)
                    && Objects.equal(y2, other.y2);
        } else
            return false;
    }

    @Override
    public double intersectionArea(Rectangle r) {
        if (!intersects(r))
            return 0;
        else {
            return create(max(x1, r.x1()), max(y1, r.y1()), min(x2, r.x2()), min(y2, r.y2())).area();
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
        return 2 * (x2 - x1) + 2 * (y2 - y1);
    }

    @Override
    public double area() {
        return (x2 - x1) * (y2 - y1);
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
