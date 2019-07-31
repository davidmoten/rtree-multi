package com.github.davidmoten.rtreemulti;

import java.util.Collections;

import org.junit.Test;

import com.github.davidmoten.rtreemulti.Node;
import com.github.davidmoten.rtreemulti.geometry.Geometry;
import com.github.davidmoten.rtreemulti.internal.NonLeafDefault;

public class NonLeafTest {

    @Test(expected=IllegalArgumentException.class)
    public void testNonLeafPrecondition() {
        new NonLeafDefault<Object,Geometry>(Collections.<Node<Object,Geometry>>emptyList(), null);
    }
    
}
