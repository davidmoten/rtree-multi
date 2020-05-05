package com.github.davidmoten.rtreemulti;

import org.junit.Test;

import com.github.davidmoten.rtreemulti.Context;
import com.github.davidmoten.rtreemulti.Factory;
import com.github.davidmoten.rtreemulti.SelectorMinimalVolumeIncrease;
import com.github.davidmoten.rtreemulti.SplitterQuadratic;
import com.github.davidmoten.rtreemulti.geometry.Geometry;

public class ContextTest {

    @Test(expected = RuntimeException.class)
    public void testContextIllegalMinChildren() {
        new Context<Object, Geometry>(2, 0, 4, SelectorMinimalVolumeIncrease.INSTANCE,
                SplitterQuadratic.INSTANCE, Factory.defaultFactory());
    }

    @Test(expected = RuntimeException.class)
    public void testContextIllegalMaxChildren() {
        new Context<Object, Geometry>(2, 1, 2, SelectorMinimalVolumeIncrease.INSTANCE,
                SplitterQuadratic.INSTANCE, Factory.defaultFactory());
    }

    @Test(expected = RuntimeException.class)
    public void testContextIllegalMinMaxChildren() {
        new Context<Object, Geometry>(2, 4, 3, SelectorMinimalVolumeIncrease.INSTANCE,
                SplitterQuadratic.INSTANCE, Factory.defaultFactory());
    }

    @Test
    public void testContextLegalChildren() {
        new Context<Object, Geometry>(2, 2, 4, SelectorMinimalVolumeIncrease.INSTANCE,
                SplitterQuadratic.INSTANCE, Factory.defaultFactory());
    }

    @Test(expected = NullPointerException.class)
    public void testContextSelectorNullThrowsNPE() {
        new Context<Object, Geometry>(2, 2, 4, null, SplitterQuadratic.INSTANCE,
                Factory.defaultFactory());
    }

    @Test(expected = NullPointerException.class)
    public void testContextSplitterNullThrowsNPE() {
        new Context<Object, Geometry>(2, 2, 4, SelectorMinimalVolumeIncrease.INSTANCE, null,
                Factory.defaultFactory());
    }

    @Test(expected = NullPointerException.class)
    public void testContextNodeFactoryNullThrowsNPE() {
        new Context<Object, Geometry>(2, 2, 4, SelectorMinimalVolumeIncrease.INSTANCE,
                SplitterQuadratic.INSTANCE, null);
    }
}
