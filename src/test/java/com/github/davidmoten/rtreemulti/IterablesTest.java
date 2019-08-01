package com.github.davidmoten.rtreemulti;

import java.util.NoSuchElementException;

import org.junit.Test;

import com.github.davidmoten.guavamini.Lists;
import com.github.davidmoten.junit.Asserts;

public class IterablesTest {
    
    @Test
    public void assertIsUtilityClass() {
        Asserts.assertIsUtilityClass(Iterables.class);
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testNoSuchElements() {
        Iterables.filter(Lists.newArrayList(), x -> x != null).iterator().next();
    }

}
