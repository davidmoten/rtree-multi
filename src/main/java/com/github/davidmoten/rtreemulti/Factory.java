package com.github.davidmoten.rtreemulti;

import com.github.davidmoten.rtreemulti.geometry.Geometry;
import com.github.davidmoten.rtreemulti.internal.FactoryDefault;

public interface Factory<T, S extends Geometry>
        extends LeafFactory<T, S>, NonLeafFactory<T, S>, EntryFactory<T,S> {
    
    public static <T, S extends Geometry> Factory<T, S> defaultFactory() {
        return FactoryDefault.instance();
    }
}
