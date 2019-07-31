package com.github.davidmoten.rtreemulti.internal;

import java.util.Comparator;
import java.util.List;

import com.github.davidmoten.rtreemulti.Entry;
import com.github.davidmoten.rtreemulti.Selector;
import com.github.davidmoten.rtreemulti.Splitter;
import com.github.davidmoten.rtreemulti.geometry.Geometry;
import com.github.davidmoten.rtreemulti.geometry.HasGeometry;
import com.github.davidmoten.rtreemulti.geometry.Rectangle;

/**
 * Utility functions asociated with {@link Comparator}s, especially for use with
 * {@link Selector}s and {@link Splitter}s.
 * 
 */
public final class Comparators {

    private Comparators() {
        // prevent instantiation
    }

    public static <T extends HasGeometry> Comparator<HasGeometry> overlapVolumeThenVolumeIncreaseThenVolumeComparator(
            final Rectangle r, final List<T> list) {
        return new Comparator<HasGeometry>() {

            @Override
            public int compare(HasGeometry g1, HasGeometry g2) {
                int value = Double.compare(overlapVolume(r, list, g1), overlapVolume(r, list, g2));
                if (value == 0) {
                    value = Double.compare(volumeIncrease(r, g1), volumeIncrease(r, g2));
                    if (value == 0) {
                        value = Double.compare(volume(r, g1), volume(r, g2));
                    }
                }
                return value;
            }
        };
    }

    private static double volume(final Rectangle r, HasGeometry g1) {
        return g1.geometry().mbr().add(r).volume();
    }

    public static <T extends HasGeometry> Comparator<HasGeometry> volumeIncreaseThenVolumeComparator(
            final Rectangle r) {
        return new Comparator<HasGeometry>() {
            @Override
            public int compare(HasGeometry g1, HasGeometry g2) {
                int value = Double.compare(volumeIncrease(r, g1), volumeIncrease(r, g2));
                if (value == 0) {
                    value = Double.compare(volume(r, g1), volume(r, g2));
                }
                return value;
            }
        };
    }

    private static float overlapVolume(Rectangle r, List<? extends HasGeometry> list, HasGeometry g) {
        Rectangle gPlusR = g.geometry().mbr().add(r);
        float m = 0;
        for (HasGeometry other : list) {
            if (other != g) {
                m += gPlusR.intersectionVolume(other.geometry().mbr());
            }
        }
        return m;
    }

    private static double volumeIncrease(Rectangle r, HasGeometry g) {
        Rectangle gPlusR = g.geometry().mbr().add(r);
        return gPlusR.volume() - g.geometry().mbr().volume();
    }

    /**
     * <p>
     * Returns a comparator that can be used to sort entries returned by search
     * methods. For example:
     * </p>
     * <p>
     * <code>search(100).toSortedList(ascendingDistance(r))</code>
     * </p>
     * 
     * @param <T>
     *            the value type
     * @param <S>
     *            the entry type
     * @param r
     *            rectangle to measure distance to
     * @return a comparator to sort by ascending distance from the rectangle
     */
    public static <T, S extends Geometry> Comparator<Entry<T, S>> ascendingDistance(
            final Rectangle r) {
        return new Comparator<Entry<T, S>>() {
            @Override
            public int compare(Entry<T, S> e1, Entry<T, S> e2) {
                return Double.compare(e1.geometry().distance(r), e2.geometry().distance(r));
            }
        };
    }

}
