package com.github.davidmoten.rtree2.geometry;

public interface Rectangle extends Geometry, HasGeometry {

    double[] x();

    double[] y();

    double area();

    double intersectionArea(Rectangle r);

    double perimeter();

    Rectangle add(Rectangle r);

    boolean contains(double[] x);
    
    default double x(int index) {
        return x()[index];
    }
    
    default double y(int index) {
        return y()[index];
    }
    
}