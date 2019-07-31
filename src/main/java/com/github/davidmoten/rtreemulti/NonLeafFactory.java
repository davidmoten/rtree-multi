package com.github.davidmoten.rtreemulti;

import java.util.List;

import com.github.davidmoten.rtreemulti.geometry.Geometry;

public interface NonLeafFactory<T, S extends Geometry> {

    NonLeaf<T, S> createNonLeaf(List<? extends Node<T, S>> children, Context<T, S> context);
}
