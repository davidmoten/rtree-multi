package com.github.davidmoten.rtreemulti;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;
import com.github.davidmoten.rtreemulti.internal.Comparators;

public class ComparatorsTest {

    @Test
    public void testConstructorIsPrivate() {
        Asserts.assertIsUtilityClass(Comparators.class);
    }

}
