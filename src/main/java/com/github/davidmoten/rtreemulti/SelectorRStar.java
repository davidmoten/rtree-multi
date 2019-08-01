package com.github.davidmoten.rtreemulti;

import java.util.List;

import com.github.davidmoten.rtreemulti.geometry.Geometry;

/**
 * Uses minimal overlap volume selector for leaf nodes and minimal volume increase
 * selector for non-leaf nodes.
 */
public final class SelectorRStar implements Selector {

    private static Selector overlapVolumeSelector = new SelectorMinimalOverlapVolume();
    private static Selector volumeIncreaseSelector = new SelectorMinimalVolumeIncrease();

    @Override
    public <T, S extends Geometry> Node<T, S> select(Geometry g, List<? extends Node<T, S>> nodes) {
        boolean leafNodes = nodes.get(0).isLeaf();
        if (leafNodes)
            return overlapVolumeSelector.select(g, nodes);
        else
            return volumeIncreaseSelector.select(g, nodes);
    }

}
