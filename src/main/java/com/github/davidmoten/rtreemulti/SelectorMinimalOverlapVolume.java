package com.github.davidmoten.rtreemulti;

import static java.util.Collections.min;

import java.util.List;

import com.github.davidmoten.rtreemulti.geometry.Geometry;
import com.github.davidmoten.rtreemulti.internal.Comparators;

public final class SelectorMinimalOverlapVolume implements Selector {

    @Override
    public <T, S extends Geometry> Node<T, S> select(Geometry g, List<? extends Node<T, S>> nodes) {
        return min(nodes,
                Comparators.overlapVolumeThenVolumeIncreaseThenVolumeComparator(g.mbr(), nodes));
    }

}
