package com.github.davidmoten.rtree2;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

import com.github.davidmoten.guavamini.Lists;
import com.github.davidmoten.junit.Asserts;
import com.github.davidmoten.rtree2.geometry.Geometries;
import com.github.davidmoten.rtree2.geometry.Rectangle;
import com.github.davidmoten.rtree2.internal.Util;

public class UtilTest {

    private static final double PRECISION = 0.00001;

    @Test
    public void coverPrivateConstructor() {
        Asserts.assertIsUtilityClass(Util.class);
    }

    @Test
    public void testMbrWithNegativeValues() {
        Rectangle r = Geometries.rectangle(-2D, -2, -1, -1);
        Rectangle mbr = Util.mbr(Collections.singleton(r));
        assertEquals(r, mbr);
        System.out.println(r);
    }

    @Test
    public void testMbr() {
        Rectangle a = Geometries.point(38.9, 23.9);
        Rectangle b = Geometries.point(39.75, 25.25);
        Rectangle c = Geometries.point(38.5, 22.25);
        Rectangle mbr = Util.mbr(Lists.newArrayList(a, b, c));
        System.out.println(mbr);
        assertEquals(22.25, mbr.min(1), PRECISION);
        assertEquals(25.25, mbr.max(1), PRECISION);
    }

}
