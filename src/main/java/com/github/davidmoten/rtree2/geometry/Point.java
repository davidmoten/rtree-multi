package com.github.davidmoten.rtree2.geometry;

public interface Point extends Rectangle {

    double[] mins();
    
    default double[] maxes() {
        return mins();
    }

}
