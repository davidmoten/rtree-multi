package com.github.davidmoten.rtreemulti.geometry;

import com.github.davidmoten.rtreemulti.geometry.internal.PointDouble;

public interface Point extends Rectangle {

    double[] mins();
    
    default double[] maxes() {
        return mins();
    }
    
    public static Point create(double... x) {
        return PointDouble.create(x);
    }

}
