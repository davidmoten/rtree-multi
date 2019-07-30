package com.github.davidmoten.rtree2.geometry.internal;

import java.util.Arrays;

import com.github.davidmoten.rtree2.geometry.Geometries;
import com.github.davidmoten.rtree2.geometry.Geometry;
import com.github.davidmoten.rtree2.geometry.Point;
import com.github.davidmoten.rtree2.geometry.Rectangle;

public final class PointDouble implements Point {

    private final double[] x;

    private PointDouble(double[] x) {
        this.x = x;
    }

    public static PointDouble create(double x[]) {
        return new PointDouble(x);
    }

    @Override
    public Rectangle mbr() {
        return this;
    }

    @Override
    public double distance(Rectangle r) {
        return GeometryUtil.distance(x, r);
    }

    @Override
    public boolean intersects(Rectangle r) {
        return GeometryUtil.intersects(x, x, r.x(), r.y());
    }

    @Override
    public double[] x() {
        return x;
    }

    @Override
    public String toString() {
        return "Point " + Arrays.toString(x());
    }

    @Override
    public Geometry geometry() {
        return this;
    }

    @Override
    public double area() {
        return 0;
    }

    @Override
    public Rectangle add(Rectangle r) {
        return Geometries.rectangle(Math.min(x, r.x1()), Math.min(y, r.y1()), Math.max(x, r.x2()), Math.max(y, r.y2()));
    }

    @Override
    public boolean contains(double[] p) {
        return Arrays.equals(x, p);
    }

    @Override
    public double intersectionArea(Rectangle r) {
        return 0;
    }

    @Override
    public double perimeter() {
        return 0;
    }

    @Override
    public boolean isDoublePrecision() {
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PointDouble other = (PointDouble) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        return true;
    }

}