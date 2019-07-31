package com.github.davidmoten.rtreemulti.geometry;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;
import com.github.davidmoten.rtreemulti.geometry.Geometries;

public class GeometriesTest {

    @Test
    public void testPrivateConstructorForCoverageOnly() {
        Asserts.assertIsUtilityClass(Geometries.class);
    }

}
