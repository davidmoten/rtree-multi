package com.github.davidmoten.rtreemulti.internal.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.github.davidmoten.rtreemulti.internal.util.ObjectsHelper;

public class ObjectsHelperTest {

    @Test
    public void testAsClassIsAbsentIfNull() {
        assertNull(ObjectsHelper.asClass(null, Integer.class));
    }

    @Test
    public void testAsClassIsAbsentIfDifferentClass() {
        assertNull(ObjectsHelper.asClass(1, String.class));
    }

    @Test
    public void testAsClassIsPresentIfSameTypeAndNotNull() {
        assertNotNull(ObjectsHelper.asClass(1, Integer.class));
    }

    @Test
    public void coverPrivateConstructor() {
        ObjectsHelper.instantiateForTestCoveragePurposesOnly();
    }
}
