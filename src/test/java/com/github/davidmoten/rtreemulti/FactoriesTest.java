package com.github.davidmoten.rtreemulti;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;
import com.github.davidmoten.rtreemulti.Factories;

public class FactoriesTest {

    @Test
    public void isUtilityClass() {
        Asserts.assertIsUtilityClass(Factories.class);
    }

}
