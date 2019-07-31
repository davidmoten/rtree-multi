package com.github.davidmoten.rtreemulti.geometry;

import java.util.List;

import com.github.davidmoten.rtreemulti.geometry.internal.PointDouble;

public interface Point extends Rectangle {

    double[] mins();
    
    default double[] maxes() {
        return mins();
    }
    
    default double[] values() {
        return mins();
    }
    
    public static Point create(double... x) {
        return PointDouble.create(x);
    }
    
    public static Point create(List<? extends Number> x) {
        double[] a = new double[x.size()];
        for (int i = 0; i< x.size(); i++) {
            a[i] = x.get(i).doubleValue();
        }
        return create(a);
    }

}
