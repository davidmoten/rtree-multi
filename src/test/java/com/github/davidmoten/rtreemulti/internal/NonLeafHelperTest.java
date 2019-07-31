package com.github.davidmoten.rtreemulti.internal;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;
import com.github.davidmoten.rtreemulti.internal.NonLeafHelper;

public class NonLeafHelperTest {
    @Test
    public void isUtilityClass() {
        Asserts.assertIsUtilityClass(NonLeafHelper.class);
    }
}
