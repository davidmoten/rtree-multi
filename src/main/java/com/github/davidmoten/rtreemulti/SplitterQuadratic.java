package com.github.davidmoten.rtreemulti;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.davidmoten.guavamini.Lists;
import com.github.davidmoten.guavamini.Preconditions;
import com.github.davidmoten.guavamini.annotations.VisibleForTesting;
import com.github.davidmoten.rtreemulti.geometry.HasGeometry;
import com.github.davidmoten.rtreemulti.geometry.ListPair;
import com.github.davidmoten.rtreemulti.geometry.Rectangle;
import com.github.davidmoten.rtreemulti.internal.Util;
import com.github.davidmoten.rtreemulti.internal.util.Pair;

public final class SplitterQuadratic implements Splitter {

    public static final SplitterQuadratic INSTANCE = new SplitterQuadratic();
    
    private SplitterQuadratic() {
        // prevent instantiation
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends HasGeometry> ListPair<T> split(List<T> items, int minSize) {
        Preconditions.checkArgument(items.size() >= 2);

        // according to
        // http://en.wikipedia.org/wiki/R-tree#Splitting_an_overflowing_node

        // find the worst combination pairwise in the list and use them to start
        // the two groups
        final Pair<T> worstCombination = worstCombination(items);

        // worst combination to have in the same node is now e1,e2.

        // establish a group around e1 and another group around e2
        final List<T> group1 = Lists.newArrayList(worstCombination.value1());
        final List<T> group2 = Lists.newArrayList(worstCombination.value2());

        final List<T> remaining = new ArrayList<T>(items);
        remaining.remove(worstCombination.value1());
        remaining.remove(worstCombination.value2());

        final int minGroupSize = items.size() / 2;

        // now add the remainder to the groups using least mbr volume increase
        // except in the case where minimumSize would be contradicted
        while (remaining.size() > 0) {
            assignRemaining(group1, group2, remaining, minGroupSize);
        }
        return new ListPair<T>(group1, group2);
    }

    private <T extends HasGeometry> void assignRemaining(final List<T> group1, final List<T> group2,
            final List<T> remaining, final int minGroupSize) {
        final Rectangle mbr1 = Util.mbr(group1);
        final Rectangle mbr2 = Util.mbr(group2);
        final T item1 = getBestCandidateForGroup(remaining, group1, mbr1);
        final T item2 = getBestCandidateForGroup(remaining, group2, mbr2);
        final boolean volume1LessThanVolume2 = item1.geometry().mbr().add(mbr1).volume() <= item2.geometry().mbr().add(mbr2)
                .volume();

        if (volume1LessThanVolume2 && (group2.size() + remaining.size() - 1 >= minGroupSize)
                || !volume1LessThanVolume2 && (group1.size() + remaining.size() == minGroupSize)) {
            group1.add(item1);
            remaining.remove(item1);
        } else {
            group2.add(item2);
            remaining.remove(item2);
        }
    }

    @VisibleForTesting
    static <T extends HasGeometry> T getBestCandidateForGroup(List<T> list, List<T> group, Rectangle groupMbr) {
        // TODO reduce allocations by not using Optional
        Optional<T> minEntry = empty();
        Optional<Double> minVolume = empty();
        for (final T entry : list) {
            final double volume = groupMbr.add(entry.geometry().mbr()).volume();
            if (!minVolume.isPresent() || volume < minVolume.get()) {
                minVolume = of(volume);
                minEntry = of(entry);
            }
        }
        return minEntry.get();
    }

    @VisibleForTesting
    static <T extends HasGeometry> Pair<T> worstCombination(List<T> items) {
        // TODO reduce allocations by not using Optional
        Optional<T> e1 = empty();
        Optional<T> e2 = empty();
        {
            Optional<Double> maxVolume = empty();
            for (int i = 0; i < items.size(); i++) {
                for (int j = i + 1; j < items.size(); j++) {
                    T entry1 = items.get(i);
                    T entry2 = items.get(j);
                    final double volume = entry1.geometry().mbr().add(entry2.geometry().mbr()).volume();
                    if (!maxVolume.isPresent() || volume > maxVolume.get()) {
                        e1 = of(entry1);
                        e2 = of(entry2);
                        maxVolume = of(volume);
                    }
                }
            }
        }
        if (e1.isPresent())
            return new Pair<T>(e1.get(), e2.get());
        else
            // all items are the same item
            return new Pair<T>(items.get(0), items.get(1));
    }
}
