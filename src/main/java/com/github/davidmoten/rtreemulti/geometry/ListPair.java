package com.github.davidmoten.rtreemulti.geometry;

import java.util.List;

/**
 *
 * Not thread safe.
 *
 * @param <T>
 *            list type
 */
public final class ListPair<T extends HasGeometry> {
    private final Group<T> group1;
    private final Group<T> group2;
    // these non-final variable mean that this class is not thread-safe
    // because access to them is not synchronized
    private double volumeSum = -1;
    private final double marginSum;

    public ListPair(List<T> list1, List<T> list2) {
        this.group1 = new Group<T>(list1);
        this.group2 = new Group<T>(list2);
        this.marginSum = group1.geometry().mbr().surfaceArea() + group2.geometry().mbr().surfaceArea();
    }

    public Group<T> group1() {
        return group1;
    }

    public Group<T> group2() {
        return group2;
    }

    public double volumeSum() {
        if (volumeSum == -1) {
            volumeSum = group1.geometry().mbr().volume() + group2.geometry().mbr().volume();
        }
        return volumeSum;
    }

    public double marginSum() {
        return marginSum;
    }

}
