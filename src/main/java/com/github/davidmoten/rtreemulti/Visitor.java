package com.github.davidmoten.rtreemulti;

import com.github.davidmoten.rtreemulti.geometry.Geometry;

public interface Visitor<T, S extends Geometry> {

    void leaf(Leaf<T, S> leaf);

    void nonLeaf(NonLeaf<T, S> nonLeaf);

}
