package com.github.davidmoten.rtreemulti.geometry;

public interface Point extends Rectangle {

    double[] mins();
    
    default double[] maxes() {
        return mins();
    }

}
