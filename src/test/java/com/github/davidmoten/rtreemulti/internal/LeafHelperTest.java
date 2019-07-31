package com.github.davidmoten.rtreemulti.internal;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;
import com.github.davidmoten.rtreemulti.internal.LeafHelper;

public class LeafHelperTest {

    @Test
    public void isUtilityClass() {
        Asserts.assertIsUtilityClass(LeafHelper.class);
    }

}
