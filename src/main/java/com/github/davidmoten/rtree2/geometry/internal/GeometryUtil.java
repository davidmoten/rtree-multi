package com.github.davidmoten.rtree2.geometry.internal;

import com.github.davidmoten.rtree2.geometry.Circle;
import com.github.davidmoten.rtree2.geometry.Rectangle;

public final class GeometryUtil {

    private GeometryUtil() {
        // prevent instantiation
    }

    public static double distanceSquared(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return dx * dx + dy * dy;
    }

    public static double max(double a, double b) {
        if (a < b)
            return b;
        else
            return a;
    }

    public static float max(float a, float b) {
        if (a < b)
            return b;
        else
            return a;
    }

    public static double min(double a, double b) {
        if (a < b)
            return a;
        else
            return b;
    }

    public static float min(float a, float b) {
        if (a < b)
            return a;
        else
            return b;
    }

    public static double distance(double x[], Rectangle r) {
        return distance(x, r.x(), r.y());
    }

    public static double distance(double[] x, double[] a, double[] b) {
        return distance(x, x, a, b);
    }

    public static double distance(double[] x, double[] y, double[] a, double[] b) {
        if (intersects(x, y, a, b)) {
            return 0;
        }

        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            boolean xyMostLeft = x[i] < a[i];
            double mostLeftX1 = xyMostLeft ? x[i] : a[i];
            double mostRightX1 = xyMostLeft ? a[i] : x[i];
            double mostLeftX2 = xyMostLeft ? y[i] : b[i];
            double xDifference = max(0, mostLeftX1 == mostRightX1 ? 0 : mostRightX1 - mostLeftX2);
            sum += xDifference * xDifference;
        }
        return Math.sqrt(sum);
    }

    public static boolean intersects(double[] mins, double[] maxes, double [] minsOther, double[] maxesOther) {
        for (int i = 0;i < mins.length;i ++) {
            if (mins[i] > maxesOther[i] || maxes[i] < minsOther[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean lineIntersects(double x1, double y1, double x2, double y2, Circle circle) {

        // using Vector Projection
        // https://en.wikipedia.org/wiki/Vector_projection
        Vector c = Vector.create(circle.x(), circle.y());
        Vector a = Vector.create(x1, y1);
        Vector cMinusA = c.minus(a);
        double radiusSquared = circle.radius() * circle.radius();
        if (x1 == x2 && y1 == y2) {
            return cMinusA.modulusSquared() <= radiusSquared;
        } else {
            Vector b = Vector.create(x2, y2);
            Vector bMinusA = b.minus(a);
            double bMinusAModulus = bMinusA.modulus();
            double lambda = cMinusA.dot(bMinusA) / bMinusAModulus;
            // if projection is on the segment
            if (lambda >= 0 && lambda <= bMinusAModulus) {
                Vector dMinusA = bMinusA.times(lambda / bMinusAModulus);
                // calculate distance to line from c using pythagoras' theorem
                return cMinusA.modulusSquared() - dMinusA.modulusSquared() <= radiusSquared;
            } else {
                // return true if and only if an endpoint is within radius of
                // centre
                return cMinusA.modulusSquared() <= radiusSquared
                        || c.minus(b).modulusSquared() <= radiusSquared;
            }
        }

    }


}
