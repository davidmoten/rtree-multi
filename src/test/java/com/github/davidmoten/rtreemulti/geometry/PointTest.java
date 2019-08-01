package com.github.davidmoten.rtreemulti.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class PointTest {

    private static final double PRECISION = 0.000001;

    @Test
    public void testCoordinates() {
        Point point = Point.create(1, 2);
        assertEquals(1, point.min(0), PRECISION);
        assertEquals(2, point.min(1), PRECISION);
    }

    @Test
    public void testDistanceToRectangle() {
        Point p1 = Point.create(1, 2);
        Rectangle r = Rectangle.create(4, 6, 4, 6);
        assertEquals(5, p1.distance(r), PRECISION);
    }

    @Test
    public void testDistanceToPoint() {
        Point p1 = Point.create(1, 2);
        Point p2 = Point.create(4, 6);
        assertEquals(5, p1.distance(p2), PRECISION);
    }

    @Test
    public void testMbr() {
        Point p = Point.create(1, 2);
        Point p2 = Point.create(1, 2);
        assertEquals(p, p2);
    }

    @Test
    public void testPointIntersectsItself() {
        Point p = Point.create(1, 2);
        assertTrue(p.distance(p.mbr()) == 0);
    }

    @Test
    public void testIntersectIsFalseWhenPointsDiffer() {
        Point p1 = Point.create(1, 2);
        Point p2 = Point.create(1, 2.000001);
        assertFalse(p1.distance(p2.mbr()) == 0);
    }

    @Test
    public void testEquality() {
        Point p1 = Point.create(1, 2);
        Point p2 = Point.create(1, 2);
        assertTrue(p1.equals(p2));
    }

    @Test
    public void testInequality() {
        Point p1 = Point.create(1, 2);
        Point p2 = Point.create(1, 3);
        assertFalse(p1.equals(p2));
    }

    @Test
    public void testInequalityToNull() {
        Point p1 = Point.create(1, 2);
        assertFalse(p1.equals(null));
    }

    @Test
    public void testHashCode() {
        Point p = Point.create(1, 2);
        assertEquals(Arrays.hashCode(new double[] { 1, 2 }), p.hashCode());
    }

    @Test
    public void testDoesNotContain() {
        Point p = Point.create(1, 2);
        assertFalse(p.contains(1, 3));
    }

    @Test
    public void testContains() {
        Point p = Point.create(1, 2);
        assertTrue(p.contains(1, 2));
    }
    
    @Test
    public void testVolumeIsZero() {
        Point p = Point.create(1, 2);
        assertEquals(0, p.volume(), PRECISION);
    }
    
    @Test
    public void testSurfaceAreaIsZero() {
        Point p = Point.create(1, 2);
        assertEquals(0, p.surfaceArea(), PRECISION);
    }
}
