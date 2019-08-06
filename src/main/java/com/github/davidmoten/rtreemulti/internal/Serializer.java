package com.github.davidmoten.rtreemulti.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.github.davidmoten.rtreemulti.RTree;
import com.github.davidmoten.rtreemulti.geometry.Geometry;

public interface Serializer<T, S extends Geometry> {

    void write(RTree<T,S> tree, OutputStream out) throws IOException;
    
    RTree<T,S> read(InputStream in) throws IOException;
}
