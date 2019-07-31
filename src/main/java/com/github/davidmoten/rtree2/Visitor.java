package com.github.davidmoten.rtree2;

import com.github.davidmoten.rtree2.geometry.Geometry;

public interface Visitor<T, S extends Geometry> {

    void leaf(Leaf<T, S> leaf);

    void nonLeaf(NonLeaf<T, S> nonLeaf);

}
