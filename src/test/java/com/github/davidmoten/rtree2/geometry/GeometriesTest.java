package com.github.davidmoten.rtree2.geometry;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;

public class GeometriesTest {

    @Test
    public void testPrivateConstructorForCoverageOnly() {
        Asserts.assertIsUtilityClass(Geometries.class);
    }

}
