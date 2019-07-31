package com.github.davidmoten.rtreemulti;

import com.github.davidmoten.rtreemulti.geometry.Geometry;
import com.github.davidmoten.rtreemulti.geometry.HasGeometry;
import com.github.davidmoten.rtreemulti.internal.EntryDefault;

public interface Entry<T, S extends Geometry> extends HasGeometry {

    T value();

    @Override
    S geometry();
    
    public static <T, S extends Geometry> Entry<T,S> entry(T object, S geometry) {
        return EntryDefault.entry(object, geometry);
    }

}