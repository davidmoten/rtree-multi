package com.github.davidmoten.rtree2.geometry;

public interface Point extends Rectangle {

    double[] x();
    
    default double[] y() {
        return x();
    }

}
