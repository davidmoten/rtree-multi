package com.github.davidmoten.rtreemulti.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RectangleTest {

    private static final double PRECISION = 0.00001;

    @Test
    public void testDistanceToSelfIsZero() {
        Rectangle r = Rectangle.create(0, 0, 1, 1);
        assertEquals(0, r.distance(r), PRECISION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testXParametersWrongOrderThrowsException() {
        Rectangle.create(2, 0, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testYParametersWrongOrderThrowsException() {
        Rectangle.create(0, 2, 1, 1);
    }

    @Test
    public void testDistanceToOverlapIsZero() {
        Rectangle r = Rectangle.create(0, 0, 2, 2);
        Rectangle r2 = Rectangle.create(1, 1, 3, 3);

        assertEquals(0, r.distance(r2), PRECISION);
        assertEquals(0, r2.distance(r), PRECISION);
    }

    @Test
    public void testDistanceWhenSeparatedByXOnly() {
        Rectangle r = Rectangle.create(0, 0, 2, 2);
        Rectangle r2 = Rectangle.create(3, 0, 4, 2);

        assertEquals(1, r.distance(r2), PRECISION);
        assertEquals(1, r2.distance(r), PRECISION);
    }

    @Test
    public void testDistanceWhenSeparatedByXOnlyAndOverlapOnY() {
        Rectangle r = Rectangle.create(0, 0, 2, 2);
        Rectangle r2 = Rectangle.create(3, 1.5f, 4, 3.5f);

        assertEquals(1, r.distance(r2), PRECISION);
        assertEquals(1, r2.distance(r), PRECISION);
    }

    @Test
    public void testDistanceWhenSeparatedByDiagonally() {
        Rectangle r = Rectangle.create(0, 0, 2, 1);
        Rectangle r2 = Rectangle.create(3, 6, 10, 8);

        assertEquals(Math.sqrt(26), r.distance(r2), PRECISION);
        assertEquals(Math.sqrt(26), r2.distance(r), PRECISION);
    }

    @Test
    public void testInequalityWithNull() {
        assertFalse(Rectangle.create(0, 0, 1, 1).equals(null));
    }

    @Test
    public void testSimpleEquality() {
        Rectangle r = Rectangle.create(0, 0, 2, 1);
        Rectangle r2 = Rectangle.create(0, 0, 2, 1);

        assertTrue(r.equals(r2));
    }

    @Test
    public void testSimpleInEquality1() {
        Rectangle r = Rectangle.create(0, 0, 2, 1);
        Rectangle r2 = Rectangle.create(0, 0, 2, 2);

        assertFalse(r.equals(r2));
    }

    @Test
    public void testSimpleInEquality2() {
        Rectangle r = Rectangle.create(0, 0, 2, 1);
        Rectangle r2 = Rectangle.create(1, 0, 2, 1);

        assertFalse(r.equals(r2));
    }

    @Test
    public void testSimpleInEquality3() {
        Rectangle r = Rectangle.create(0, 0, 2, 1);
        Rectangle r2 = Rectangle.create(0, 1, 2, 1);

        assertFalse(r.equals(r2));
    }

    @Test
    public void testSimpleInEquality4() {
        Rectangle r = Rectangle.create(0, 0, 2, 2);
        Rectangle r2 = Rectangle.create(0, 0, 1, 2);

        assertFalse(r.equals(r2));
    }

    @Test
    public void testGeometry() {
        Rectangle r = Rectangle.create(0, 0, 2, 1);
        assertTrue(r.equals(r.geometry()));
    }

    @Test
    public void testIntersects() {
        Rectangle a = Rectangle.create(14, 14, 86, 37);
        Rectangle b = Rectangle.create(13, 23, 50, 80);
        assertTrue(a.intersects(b));
        assertTrue(b.intersects(a));
    }

    @Test
    public void testIntersectsNoRectangleContainsCornerOfAnother() {
        Rectangle a = Rectangle.create(10, 10, 50, 50);
        Rectangle b = Rectangle.create(28.0, 4.0, 34.0, 85.0);
        assertTrue(a.intersects(b));
        assertTrue(b.intersects(a));
    }

    @Test
    public void testIntersectsOneRectangleContainsTheOther() {
        Rectangle a = Rectangle.create(10, 10, 50, 50);
        Rectangle b = Rectangle.create(20, 20, 40, 40);
        assertTrue(a.intersects(b));
        assertTrue(b.intersects(a));
    }

    @Test
    public void testIntersectsOneRectangleReturnsTrueDespiteZeroArea() {
        Rectangle a = Rectangle.create(10, 50, 50, 50);
        Rectangle b = Rectangle.create(20, 20, 60, 60);
        assertTrue(a.intersects(b));
        assertTrue(b.intersects(a));
    }
    
    @Test
    public void testContains() {
        Rectangle r = Rectangle.create(10, 20, 30, 40);
        assertTrue(r.contains(20, 30));
    }

    @Test
    public void testContainsReturnsFalseWhenLessThanMinY() {
        Rectangle r = Rectangle.create(10, 20, 30, 40);
        assertFalse(r.contains(20, 19));
    }

    @Test
    public void testContainsReturnsFalseWhenGreaterThanMaxY() {
        Rectangle r = Rectangle.create(10, 20, 30, 40);
        assertFalse(r.contains(20, 41));
    }

    @Test
    public void testContainsReturnsFalseWhenGreaterThanMaxX() {
        Rectangle r = Rectangle.create(10, 20, 30, 40);
        assertFalse(r.contains(31, 30));
    }

    @Test
    public void testContainsReturnsFalseWhenLessThanMinX() {
        Rectangle r = Rectangle.create(10, 20, 30, 40);
        assertFalse(r.contains(9, 30));
    }

    @Test
    public void testIntersectionAreWhenEqual() {
        Rectangle a = Rectangle.create(10, 10, 30, 20);
        Rectangle b = Rectangle.create(10, 10, 30, 20);
        assertEquals(200f, a.intersectionVolume(b), 0.0001);
    }

    @Test
    public void testIntersectionAreaWhenDontIntersect() {
        Rectangle a = Rectangle.create(10, 10, 30, 20);
        Rectangle b = Rectangle.create(50, 50, 60, 60);
        assertEquals(0f, a.intersectionVolume(b), 0.0001);
    }

    @Test
    public void testIntersectionAreaCornerIntersect() {
        Rectangle a = Rectangle.create(10, 10, 30, 20);
        Rectangle b = Rectangle.create(28, 17, 40, 40);
        assertEquals(6f, a.intersectionVolume(b), 0.0001);
    }

    @Test
    public void testIntersectionAreaTopIntersect() {
        Rectangle a = Rectangle.create(10, 10, 30, 20);
        Rectangle b = Rectangle.create(8, 17, 40, 40);
        assertEquals(60f, a.intersectionVolume(b), 0.0001);
    }
    
    @Test
    public void testVolume() {
        Rectangle a = Rectangle.create(1, 2, 3, 3, 5, 7);
        assertEquals(24, a.volume(), PRECISION);
    }
    
    @Test
    public void testSurfaceArea() {
        Rectangle a = Rectangle.create(1, 2, 3, 3, 5, 7);
        assertEquals(52, a.surfaceArea(), PRECISION);
    }

}